package elementsmc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elementsmc.common.item.ItemTablet;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TilePodest;
import elementsmc.common.util.GenericHelper;

public class BlockPodest extends BlockContainer
{

	public BlockPodest()
	{
		super(Material.circuits);
		this.disableStats();
		this.setHardness(1.0f);
		this.setCreativeTab(ElementsMC.tab);
		this.setBlockBounds(0.375f, 0f, 0.375f, 0.625f, 0.5625f, 0.625f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int arg1)
	{
		return new TilePodest();
	}

	private ResourceLocation resource;

	public BlockPodest setResource(ResourceLocation res)
	{
		this.resource = res;
		return this;
	}

	public ResourceLocation getResource(int meta)
	{
		return resource;
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof IInventory)
		{
			IInventory inv = (IInventory) te;

			for (int i1 = 0; i1 < inv.getSizeInventory(); ++i1)
			{
				ItemStack itemstack = inv.getStackInSlot(i1);

				if (itemstack != null)
				{
					float f = GenericHelper.RNG.nextFloat() * 0.8F + 0.1F;
					float f1 = GenericHelper.RNG.nextFloat() * 0.8F + 0.1F;
					float f2 = GenericHelper.RNG.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0)
					{
						int j1 = GenericHelper.RNG.nextInt(21) + 10;

						if (j1 > itemstack.stackSize)
						{
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (double)((float)GenericHelper.RNG.nextGaussian() * f3);
						entityitem.motionY = (double)((float)GenericHelper.RNG.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double)((float)GenericHelper.RNG.nextGaussian() * f3);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}

			world.func_147453_f(x, y, z, block);
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public int getRenderType()
	{
		return ElementsMC.podestRenderID;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) 
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TilePodest)
		{
			TilePodest tep = (TilePodest) te;
			if(tep.getStackInSlot(7) == null)
			{
				this.setBlockBounds(0.375f, 0f, 0.375f, 0.625f, 0.5625f, 0.625f);
			}
			else
			{
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.625f, 1f);
			}
		}
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float partX, float partY, float partZ)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(!player.isSneaking() && te != null && te instanceof TilePodest)
		{
			TilePodest tep = (TilePodest) te;
			if(tep.getStackInSlot(8) != null)
			{
				if(player.inventory.addItemStackToInventory(tep.getStackInSlot(8)))
				{
					tep.setInventorySlotContents(8, null);
					return true;
				}
			}
			else if(tep.getStackInSlot(7) == null && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemTablet)
			{
				tep.setInventorySlotContents(7, player.getHeldItem());
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
				return true;
			}
			else if(!tep.hasInputItems() && tep.getStackInSlot(7) != null && player.getHeldItem() == null)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, tep.getStackInSlot(7));
				tep.setInventorySlotContents(7, null);
				return true;
			}
			else if(tep.getStackInSlot(7) != null && side == 1)
			{
				int i = getClickedTriangle(partX, partZ);
				if(i != -1)
				{
					if(tep.getStackInSlot(i) == null && player.getHeldItem() != null)
					{
						ItemStack stack = player.getHeldItem().copy();
						stack.stackSize = 1;
						tep.setInventorySlotContents(i, stack);
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
						return true;
					}
					else if(tep.getStackInSlot(i) != null && player.inventory.addItemStackToInventory(tep.getStackInSlot(i)))
					{
						tep.setInventorySlotContents(i, null);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static int getClickedTriangle(float partX, float partZ)
	{
		float px = partX - 0.5f;
		float pz = partZ - 0.5f;
		float angle = (float) Math.toRadians(51.43D);
		Vec3 vec = Vec3.createVectorHelper(0, 0, 0.5);
		Vec3 vec2 = Vec3.createVectorHelper(0, 0, 0.5);
		vec2.rotateAroundY(angle);
		for(int i = 0; i < 7; i++)
		{
			if(GenericHelper.pointInTriangle(px, pz, 0f, 0f, (float)vec.xCoord, (float)vec.zCoord, (float)vec2.xCoord, (float)vec2.zCoord))
			{
				return i;
			}
			vec.rotateAroundY(angle);
			vec2.rotateAroundY(angle);
		}
		return -1;
	}

}

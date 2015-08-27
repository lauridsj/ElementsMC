package elementsmc.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import elementsmc.common.elements.ElementNetwork;
import elementsmc.common.elements.INetworkPart;
import elementsmc.common.util.GenericHelper;

public abstract class BlockNetworkPart extends BlockContainer
{

	public BlockNetworkPart(Material arg0)
	{
		super(arg0);
	}

	public void onPostBlockPlaced(World world, int x, int y, int z, int metadata)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof INetworkPart)
		{
			ElementNetwork.onNetworkPartAddedToWorld((INetworkPart) te);
		}
	}

	public void onBlockPreDestroy(World world, int x, int y, int z, int metadata)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof INetworkPart)
		{
			ElementNetwork.onNetworkPartRemovedFromWorld((INetworkPart) te);
		}
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

}

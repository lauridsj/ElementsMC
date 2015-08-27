package elementsmc.common.block;

import elementsmc.client.util.Animation;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TileInfuser;
import elementsmc.common.util.Coords4;
import elementsmc.common.util.GenericHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInfuser extends BlockNetworkPart {

	public BlockInfuser()
	{
		super(Material.circuits);
		this.disableStats();
		this.setHardness(1f);
		this.setBlockName("infuser");
		this.setCreativeTab(ElementsMC.tab);
		this.setBlockTextureName("elementsmc:infuser");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileInfuser();
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public int getRenderType()
	{
		return ElementsMC.infuserRenderID;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float partX, float partY, float partZ)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(!player.isSneaking() && te != null && te instanceof TileInfuser)
		{
			TileInfuser tei = (TileInfuser) te;
			if(tei.getStackInSlot(0) != null)
			{
				if(player.getHeldItem() == null)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, tei.getStackInSlot(0));
					tei.setInventorySlotContents(0, null);
					return true;
				}
				else if(player.getHeldItem().stackSize < player.getHeldItem().getMaxStackSize() && player.getHeldItem().isItemEqual(tei.getStackInSlot(0)))
				{
					player.getHeldItem().stackSize++;
					tei.setInventorySlotContents(0, null);			
					return true;
				}
			}
			else if(tei.getStackInSlot(0) == null && player.getHeldItem() != null && tei.isItemValidForSlot(0, player.getHeldItem()))
			{
				tei.setInventorySlotContents(0, player.getHeldItem());
				if(player.getHeldItem().stackSize > 1)
				{
					player.getHeldItem().stackSize--;
				}
				else
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}
				
				return true;
			}	
		}
		return false;
	}

	

}

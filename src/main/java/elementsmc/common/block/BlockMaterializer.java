package elementsmc.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import elementsmc.common.main.EMConstants;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TileMaterializer;

public class BlockMaterializer extends BlockNetworkPart
{
	public BlockMaterializer()
	{
		super(Material.circuits);
		this.disableStats();
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
		this.setHardness(1.0f);
		this.setBlockName("materializer");
		this.setCreativeTab(ElementsMC.tab);
		this.setBlockTextureName("elementsmc:materializer");
	}
	
	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1)
	{
		return new TileMaterializer();
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}

	public int getRenderType()
	{
		return ElementsMC.materializerRenderID;
	}
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8,
			float par9)
	{
		if(par1World.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity te = par1World.getTileEntity(par2, par3, par4);
			if(te != null && par5EntityPlayer instanceof EntityPlayerMP)
			{
				if(te instanceof TileMaterializer)
				{
					par5EntityPlayer.openGui(ElementsMC.instance, EMConstants.MATERIALIZER_GUI, par1World, par2, par3, par4);
					return true;
				}
			}
		}
		return false;
	}
}

package elementsmc.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockEMFluid extends BlockFluidClassic {

	public final String textureNameStill;
	public final String textureNameFlowing;
	
	public BlockEMFluid(Fluid fluid, String textureNameStill, String textureNameFlowing)
	{
		super(fluid, Material.water);
		this.textureNameStill = textureNameStill;
		this.textureNameFlowing = textureNameFlowing;
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return (side != 0) && (side != 1) ? this.getFluid().getFlowingIcon() : this.getFluid().getStillIcon();
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.getFluid().setStillIcon(register.registerIcon(this.textureNameStill));
		this.getFluid().setFlowingIcon(register.registerIcon(this.textureNameFlowing));
	}
	
}

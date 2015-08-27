package elementsmc.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elementsmc.common.item.ItemEssenceBottle;
import elementsmc.common.main.ElementsMC;

public class BlockDeepWater extends BlockFluidClassic
{
	public BlockDeepWater(Fluid fluid)
	{
		super(fluid, Material.water);
		fluid.setBlock(this);
		this.disableStats();
		this.setBlockName("deepWater");
		this.setTickRandomly(true);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return (side != 0) && (side != 1) ? this.getFluid().getFlowingIcon() : this.getFluid().getStillIcon();
	}

	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.getFluid().setStillIcon(register.registerIcon("water_still"));
		this.getFluid().setFlowingIcon(register.registerIcon("water_flow"));
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		super.randomDisplayTick(world, x, y, z, rand);
		int count = rand.nextInt(8);
		for(int i = 0; i < count; i++)
		{
			double d = rand.nextDouble() * 0.1;
			world.spawnParticle("reddust", x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), d, d, d + 0.9);
		}
	}

	@Override
	public Fluid getFluid()
	{
		return FluidRegistry.getFluid(fluidName);
	}

	@Override
	public float getFilledPercentage(World world, int x, int y, int z)
	{
		int quantaRemaining = getQuantaValue(world, x, y, z) + 1;
		float remaining = quantaRemaining / quantaPerBlockFloat;
		if (remaining > 1) remaining = 1.0f;
		return remaining * (density > 0 ? 1 : -1);
	}


	public boolean canDisplace(IBlockAccess world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, x, y, z);
	}

	public boolean displaceIfPossible(World world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, x, y, z);
	}
	
	public Vec3 getFlowVector(IBlockAccess world, int x, int y, int z)
    {
		return Vec3.createVectorHelper(0d, 0d, 0d);
    }
	
	@Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.shouldSideBeRendered(world, x, y, z, side);
    }
	
	public int getRenderType()
	{
		return ElementsMC.deepWaterRenderID;
	}
	
	public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
	{
		return ItemEssenceBottle.rayTracing;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
	{
		if(ItemEssenceBottle.rayTracing) return AxisAlignedBB.getBoundingBox((double)p_149668_2_ + this.minX, (double)p_149668_3_ + this.minY, (double)p_149668_4_ + this.minZ, (double)p_149668_2_ + this.maxX, (double)p_149668_3_ + this.maxY, (double)p_149668_4_ + this.maxZ);
		else return null;
	}

}

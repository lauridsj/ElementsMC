package elementsmc.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elementsmc.common.item.ItemEssenceBottle;

public class BlockHighAir extends Block
{

	public static final Material highAirMaterial = new MaterialTransparent(MapColor.airColor);

	public BlockHighAir()
	{
		super(highAirMaterial);
		this.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
		this.disableStats();
		this.setBlockName("highAir");
		this.setTickRandomly(true);
		this.setBlockTextureName("elementsmc:highAirBottle");
	}

	public int getRenderType()
	{
		return -1;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
	{
		if(ItemEssenceBottle.rayTracing) return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
		else return null;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
	{
		return ItemEssenceBottle.rayTracing;
	}

	public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
	{

	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int count = rand.nextInt(8);
		for(int i = 0; i < count; i++)
		{
			double d = (rand.nextDouble() * 0.1) + 0.9;
			world.spawnParticle("reddust", x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), d, d, d);
		}
	}

}

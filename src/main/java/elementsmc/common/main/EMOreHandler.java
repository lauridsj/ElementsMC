package elementsmc.common.main;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import elementsmc.common.util.GenericHelper;

public class EMOreHandler
{
	public WorldGenMinable highAirGen = new WorldGenMinable(ElementsMC.highAir, 0, 7, Blocks.air);
	public WorldGenMinable deepWaterGen = new WorldGenMinable(ElementsMC.deepWater, 0, 10, Blocks.water);

	@SubscribeEvent
	public void onOreGenPost(OreGenEvent.Post event)
	{
		this.genStandardOre1(event, 1, highAirGen, 196, 254);

		int i1 = event.rand.nextInt(4);
		for(int i = 0; i < i1; i++)
		{
			int x1 = event.worldX + event.rand.nextInt(16);
			int z1 = event.worldZ + event.rand.nextInt(16);
			BiomeGenBase biome = event.world.getBiomeGenForCoords(x1, z1);
			if(biome == BiomeGenBase.deepOcean || biome == BiomeGenBase.frozenOcean || biome == BiomeGenBase.ocean)
			{
				int y1 = GenericHelper.getTopSolidBlock(event.world, x1, z1);
				deepWaterGen.generate(event.world, event.rand, x1, y1, z1);
			}
		}
	}

	protected void genStandardOre1(OreGenEvent event, int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
	{
		for (int l = 0; l < par1; ++l)
		{
			int i1 = event.worldX + event.rand.nextInt(16);
			int j1 = event.rand.nextInt(par4 - par3) + par3;
			int k1 = event.worldZ + event.rand.nextInt(16);
			par2WorldGenerator.generate(event.world, event.rand, i1, j1, k1);
		}
	}
}

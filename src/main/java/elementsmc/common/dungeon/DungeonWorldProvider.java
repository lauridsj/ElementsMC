package elementsmc.common.dungeon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elementsmc.common.main.ElementsMC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class DungeonWorldProvider extends WorldProvider {

	public DungeonWorldProvider()
	{
		this.worldChunkMgr = new WorldChunkManagerHell(ElementsMC.dungeonBiome, 0f);
	}
	
	@Override
	public String getDimensionName() {
		return "EMDungeon_" + this.getDungeon().name;
	}
	
	public Dungeon getDungeon()
	{
		return Dungeon.getByDimension(dimensionId);
	}
	
	 public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
	    {
		 return null;
	    }
	 
	 public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
	    {
		 return this.getDungeon().type.color;
	    }
	 
	 public boolean canRespawnHere()
	    {
		 return false;
	    }
	 
	 public IChunkProvider createChunkGenerator()
	    {
		 return new DungeonChunkProvider(this.worldObj, this.getSeed(), this.getDungeon());
	    }
	 
	 public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_)
	    {
		 return false;
	    }
	 
	 public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
	    {
	        return 0.5F;
	    }
	 
	 public boolean isSurfaceWorld()
	    {
		 return false;
	    }
	 
	 public ChunkCoordinates getEntrancePortalLocation()
	    {
		 //TODO
		  return null;
	    }

	 @SideOnly(Side.CLIENT)
	    public float getCloudHeight()
	    {
		 return 1f;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public boolean getWorldHasVoidParticles()
	    {
		 return false;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public double getVoidFogYFactor()
	    {
		 return 0;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public boolean doesXZShowFog(int p_76568_1_, int p_76568_2_)
	    {
		 return false;
	    }
	 
	 public String getSaveFolder()
	    {
		 return "ElementsMC/Dungeon_" + this.getDungeon().name;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
	    {
		 return this.getDungeon().type.color;
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public Vec3 drawClouds(float partialTicks)
	    {
		 return this.getDungeon().type.color;
	    }

	 public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	    {
	        return false;
	    }

	    public boolean canSnowAt(int x, int y, int z, boolean checkLight)
	    {
	        return false;
	    }
	    
	    public boolean canMineBlock(EntityPlayer player, int x, int y, int z)
	    {
	    	//TODO
	    	return false;
	    }
	 
}

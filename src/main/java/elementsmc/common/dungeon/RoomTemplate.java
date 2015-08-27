package elementsmc.common.dungeon;

import java.util.Random;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public class RoomTemplate extends WeightedRandom.Item
{

	public Structure roomStructure;
		
	public RoomTemplate(int weight, Structure structure)
	{
		super(weight);
		roomStructure = structure;
	}
	
	public RoomTemplate(int weight, String structure)
	{
		super(weight);
		roomStructure = Structure.getStructure(structure);
	}
	
	public void generate(World world, Random rand, int x, int y, int z, Dungeon dungeon)
	{
		roomStructure.generate(world, x, y, z, dungeon.type.blockReplacements);
	}
	
	public int getSizeX()
	{
		return roomStructure.xSize;
	}
	
	public int getSizeY()
	{
		return roomStructure.ySize;
	}
	
	public int getSizeZ()
	{
		return roomStructure.zSize;
	}

}

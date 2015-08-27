package elementsmc.common.dungeon;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;

public enum DungeonType {

	ELEMENTAL(0.9f, 0.9f, 0.9f);
	
	public HashMap<EnumBlockReplacement, StructureBlock> blockReplacements = new HashMap<EnumBlockReplacement, StructureBlock>();
	public Vec3 color;
	
	public HashMap<RoomType, ArrayList<RoomTemplate>> roomMap = new HashMap<RoomType, ArrayList<RoomTemplate>>();
	
	private DungeonType(float red, float green, float blue)
	{
		for(EnumBlockReplacement rep : EnumBlockReplacement.values())
		{
			blockReplacements.put(rep, rep.defaultBlock);
		}
		color = Vec3.createVectorHelper(red, green, blue);
		
		for(RoomType rt : RoomType.values())
		{
			roomMap.put(rt, new ArrayList<RoomTemplate>());
		}
		
		
	}
	
	public void changeReplacement(EnumBlockReplacement rep, Block block, int meta)
	{
		blockReplacements.put(rep, new StructureBlock(block, meta));
	}
	
	public void changeReplacement(EnumBlockReplacement rep, Block block)
	{
		changeReplacement(rep, block, 0);
	}
	
	public void addRoom(RoomType type, RoomTemplate room)
	{
		roomMap.get(type).add(room);
	}
	
	static
	{
		ELEMENTAL.changeReplacement(EnumBlockReplacement.WALL1, Blocks.quartz_block);
	}
}

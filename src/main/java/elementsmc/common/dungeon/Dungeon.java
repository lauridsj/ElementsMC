package elementsmc.common.dungeon;

import java.util.HashMap;

import com.google.common.collect.HashBiMap;

public class Dungeon {

	public String name;
	
	public DungeonType type;
	
	public int dimensionID;
	
	public static HashBiMap<Integer, Dungeon> dungeonMap = HashBiMap.create();
	
	public static Dungeon getByDimension(int id)
	{
		return dungeonMap.get(Integer.valueOf(id));
	}
	
	
	
	
}

package elementsmc.common.dungeon;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;

public enum EnumBlockReplacement {

	WALL1(-1, "wall1", Blocks.stonebrick),
	WALL2(-2, "wall2", Blocks.cobblestone),
	FLOOR1(-3, "floor1", Blocks.stone),
	FLOOR2(-4, "floor2", Blocks.double_stone_slab),
	CEILING1(-5, "ceiling1", Blocks.quartz_block),
	CEILING2(-6, "ceiling2", Blocks.quartz_block, 1),
	COLUMNS(-7, "columns", Blocks.quartz_block, 2),
	HALF_SLAB(-8, "half_slab", Blocks.stone_slab, 5),
	DOUBLE_SLAB(-9, "double_slab", Blocks.double_stone_slab, 5),
	STAIRS(-10, "stairs", Blocks.stone_brick_stairs, true),
	DECORATION1(-11, "decoration1", Blocks.stonebrick, 3),
	DECORATION2(-12, "decoration2", Blocks.emerald_block),
	DECORATION3(-13, "decoration3", Blocks.lapis_block),
	GLASS(-14, "glass", Blocks.glass),
	CHEST(-15, "chest", Blocks.chest, true),
	TORCH(-16, "torch", Blocks.torch, true),
	LIGHTSTONE(-17, "lightstone", Blocks.glowstone),
	SWIM_LIQUID(-18, "swim_liquid", Blocks.water, true),
	DEADLY_LIQUID(-19, "deadly_liquid", Blocks.lava, true),
	GLASS_PANE(-20, "glass_pane", Blocks.iron_bars),
	OPEN_DOOR(-21, "open_door", Blocks.wooden_door, true),
	CLOSED_DOOR(-22, "closed_door", Blocks.iron_door, true),
	FENCE(-23, "fence", Blocks.fence),
	FENCE_GATE(-24, "fence_gate", Blocks.fence_gate, true),
	ITEM_PRESSURE_PLATE(-25, "item_pressure_plate", Blocks.wooden_pressure_plate),
	PLAYER_PRESSURE_PLATE(-26, "player_pressure_plate", Blocks.stone_pressure_plate),
	TRAPDOOR(-27, "trapdoor", Blocks.trapdoor, true),
	TRAPPED_CHEST(-28, "trapped_chest", Blocks.trapped_chest, true),
	SWIM_LIQUID_FLOWING(-29, "swim_liquid_flowing", Blocks.flowing_water, true),
	DEADLY_LIQUID_FLOWING(-30, "deadly_liquid_flowing", Blocks.flowing_lava, true),
	ARROW_BUTTON(-31, "arrow_button", Blocks.wooden_button),
	PLAYER_BUTTON(-32, "player_button", Blocks.stone_button);
	
	public static String[] NAMES;
	
	public int id;
	public String name;
	public StructureBlock defaultBlock;
	public boolean metaSensitive = false;
	public boolean tileSensitive = false;
	
	private EnumBlockReplacement(int id, String name, StructureBlock defaultBlock) {
		this.id = id;
		this.name = name;
		this.defaultBlock = defaultBlock;
	}
	
	private EnumBlockReplacement(int id, String name, Block defaultBlock) {
		this(id, name, new StructureBlock(defaultBlock));
	}
	
	private EnumBlockReplacement(int id, String name, Block defaultBlock, int defaultMeta) {
		this(id, name, new StructureBlock(defaultBlock, defaultMeta));
	}
	
	private EnumBlockReplacement(int id, String name, Block defaultBlock, boolean metaSensitive) {
		this(id, name, new StructureBlock(defaultBlock));
		this.metaSensitive = metaSensitive;
	}

	static
	{
		NAMES = new String[values().length];
		for(int i = 0; i < values().length; i++)
		{
			NAMES[i] = values()[i].name;
		}
	}
	
	public static EnumBlockReplacement getByID(int id)
	{
		return values()[Math.abs(id) - 1];
	}
	
		
	
}

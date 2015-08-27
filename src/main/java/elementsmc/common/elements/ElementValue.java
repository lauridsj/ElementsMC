package elementsmc.common.elements;

import java.util.HashMap;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import elementsmc.common.main.EMConstants;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.util.GenericHelper;

public class ElementValue implements Cloneable, EMConstants
{

	public static HashMap<String, ElementValue> valueMap = new HashMap<String, ElementValue>();
	public static HashMap<String, ElementValue> valueMapMk2 = new HashMap<String, ElementValue>();

	public boolean isOreDictionary;
	public String name;
	public ItemStack stack;
	public boolean canBeMade = true;
	public int[] values = new int[Element.values().length];

	public ElementValue(ItemStack stack, boolean isOreDictionary)
	{
		if(isOreDictionary)
		{
			this.name = OreDictionary.getOreName(OreDictionary.getOreID(stack));
			this.stack = OreDictionary.getOres(name).get(0);
		}
		else
		{
			this.name = stack.getUnlocalizedName();
			this.stack = stack;
		}
		this.isOreDictionary = isOreDictionary;
	}

	public ElementValue(Item item, boolean isOreDictionary)
	{
		this(new ItemStack(item), isOreDictionary);
	}

	public ElementValue(Item item, int meta, boolean isOreDictionary)
	{
		this(new ItemStack(item, 1, meta), isOreDictionary);
	}

	public ElementValue(Block block, boolean isOreDictionary)
	{
		this(new ItemStack(block), isOreDictionary);
	}

	public ElementValue(Block block, int meta, boolean isOreDictionary)
	{
		this(new ItemStack(block, 1, meta), isOreDictionary);
	}

	public ElementValue(String name)
	{
		this.name = name;
		this.isOreDictionary = true;
		this.stack = OreDictionary.getOres(name).get(0).copy();
		if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) stack.setItemDamage(0);
	}
	
	private ElementValue() {};

	public ElementValue setCanBeMade(boolean b)
	{
		this.canBeMade = b;
		return this;
	}

	public int getElement(Element element)
	{
		return this.values[element.ordinal()];
	}

	public ElementValue setElement(Element element, int i)
	{
		this.values[element.ordinal()] = i;
		return this;
	}

	public ElementValue setItemStack(ItemStack stack)
	{
		this.stack = stack.copy();
		return this;
	}

	public static void initDefaultEntries()
	{
		OreDictionary.registerOre("mushroomRed", Blocks.red_mushroom);
		OreDictionary.registerOre("mushroomBrown", Blocks.brown_mushroom);

		addEntry(new ElementValue(Items.diamond, false).setElement(Element.EARTH, 8192));
		addEntry(new ElementValue(Items.gold_ingot, false).setElement(Element.EARTH, 2048));
		addEntry(new ElementValue(Items.iron_ingot, false).setElement(Element.EARTH, 256));
		addEntry(new ElementValue(Items.coal, false).setElement(Element.EARTH, 64).setElement(Element.FIRE, 16));
		addEntry(new ElementValue(Items.flint, false).setElement(Element.EARTH, 64));
		addEntry(new ElementValue(Items.redstone, false).setElement(Element.EARTH, 64));
		addEntry(new ElementValue(Items.coal, 1, false).setElement(Element.FIRE, 16));
		addEntry(new ElementValue(Items.rotten_flesh, false).setElement(Element.NETHER, 16));
		addEntry(new ElementValue(Items.bone, false).setElement(Element.NETHER, 24));
		addEntry(new ElementValue(Items.string, false).setElement(Element.NETHER, 32));
		addEntry(new ElementValue(Items.gunpowder, false).setElement(Element.NETHER, 64).setElement(Element.FIRE, 16));
		addEntry(new ElementValue(Items.slime_ball, false).setElement(Element.NETHER, 64));
		addEntry(new ElementValue(Items.snowball, false).setElement(Element.WATER, 8).setCanBeMade(false));
		addEntry(new ElementValue(Items.clay_ball, false).setElement(Element.EARTH, 32).setElement(Element.WATER, 32));
		addEntry(new ElementValue(Items.glowstone_dust, false).setElement(Element.MAGIC, 128).setCanBeMade(false));
		addEntry(new ElementValue(Items.ender_pearl, false).setElement(Element.END, 1024));
		addEntry(new ElementValue(Items.blaze_rod, false).setElement(Element.MAGIC, 64).setElement(Element.FIRE, 512));
		addEntry(new ElementValue(Items.blaze_powder, false).setElement(Element.MAGIC, 32).setElement(Element.FIRE, 256));
		addEntry(new ElementValue(Items.ghast_tear, false).setElement(Element.NETHER, 2048));
		addEntry(new ElementValue(Items.nether_wart, false).setElement(Element.NETHER, 32));
		addEntry(new ElementValue(Items.spider_eye, false).setElement(Element.NETHER, 128));
		addEntry(new ElementValue(Items.magma_cream, false).setElement(Element.NETHER, 64).setElement(Element.MAGIC, 32)
				.setElement(Element.FIRE, 256));
		addEntry(new ElementValue(Items.ender_eye, false).setElement(Element.END, 1024).setElement(Element.MAGIC, 32).setElement(Element.FIRE, 256));
		addEntry(new ElementValue(Items.emerald, false).setElement(Element.EARTH, 2048));
		addEntry(new ElementValue(Items.nether_star, false).setElement(Element.NETHER, 131072));
		addEntry(new ElementValue(Items.netherbrick, false).setElement(Element.NETHER, 1));
		addEntry(new ElementValue(Items.quartz, false).setElement(Element.NETHER, 64));

		addEntry(new ElementValue(Items.dye, 4, false).setElement(Element.EARTH, 512));
		addEntry(new ElementValue(Items.dye, 15, false).setElement(Element.NETHER, 8).setCanBeMade(false));
		addEntry(new ElementValue(Items.dye, 12, false).setElement(Element.EARTH, 256).setElement(Element.NETHER, 4).setCanBeMade(false));
		addEntry(new ElementValue(Items.dye, 8, false).setElement(Element.NETHER, 4).setCanBeMade(false));
		addEntry(new ElementValue(Items.dye, 7, false).setElement(Element.NETHER, 4).setCanBeMade(false));

		addEntry(new ElementValue(Items.skull, 1, false).setElement(Element.NETHER, 16384));

		addEntry(new ElementValue(Items.feather, false).setElement(Element.AIR, 32).setCanBeMade(false));

		addEntry(new ElementValue("logWood").setElement(Element.FIRE, 16).setItemStack(new ItemStack(Blocks.log, 1, 0)).setCanBeMade(false));
		addEntry(new ElementValue("plankWood").setElement(Element.FIRE, 4).setCanBeMade(false).setItemStack(new ItemStack(Blocks.planks, 1, 0)));
		addEntry(new ElementValue("stickWood").setElement(Element.FIRE, 2).setCanBeMade(false).setItemStack(new ItemStack(Items.stick, 1, 0)));
		addEntry(new ElementValue("slabWood").setElement(Element.FIRE, 2).setCanBeMade(false).setItemStack(new ItemStack(Blocks.wooden_slab, 1, 0)));
		addEntry(new ElementValue("stairWood").setElement(Element.FIRE, 4).setCanBeMade(false).setItemStack(new ItemStack(Blocks.oak_stairs, 1, 0)));

		
		for(ItemStack stack : OreDictionary.getOres("logWood"))
		{
			ItemStack stack2 = stack.copy();
			if(stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) stack2.setItemDamage(0);
			addEntry(new ElementValue(stack2, false).setElement(Element.FIRE, 16));
		}

		// addEntry(new ElementValue(Blocks.log, 0,
		// false).setElement(Element.FIRE, 16));
		addEntry(new ElementValue(Blocks.log, 1, false).setElement(Element.FIRE, 16));
		addEntry(new ElementValue(Blocks.log, 2, false).setElement(Element.FIRE, 16));
		addEntry(new ElementValue(Blocks.log, 3, false).setElement(Element.FIRE, 16));
		// addEntry(new ElementValue(Blocks.log2, 0,
		// false).setElement(Element.FIRE, 16));
		addEntry(new ElementValue(Blocks.log2, 1, false).setElement(Element.FIRE, 16));

		addEntry(new ElementValue(Blocks.dirt, false).setElement(Element.EARTH, 1));
		addEntry(new ElementValue(Blocks.cobblestone, false).setElement(Element.EARTH, 1)); // Cobblestone
		addEntry(new ElementValue(Blocks.stone, false).setElement(Element.EARTH, 1));
		addEntry(new ElementValue(Blocks.sand, false).setElement(Element.EARTH, 1));
		addEntry(new ElementValue(Blocks.gravel, false).setElement(Element.EARTH, 4));
		addEntry(new ElementValue(Blocks.lapis_block, false).setElement(Element.EARTH, 4608).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.sandstone, 0, false).setElement(Element.EARTH, 4));
		addEntry(new ElementValue(Blocks.sandstone, 1, false).setElement(Element.EARTH, 4).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.sandstone, 2, false).setElement(Element.EARTH, 4).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.web, false).setElement(Element.NETHER, 64));
		addEntry(new ElementValue(Blocks.gold_block, false).setElement(Element.EARTH, 18432).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.iron_block, false).setElement(Element.EARTH, 2304).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.mossy_cobblestone, false).setElement(Element.EARTH, 1).setElement(Element.NETHER, 128));
		addEntry(new ElementValue(Blocks.obsidian, false).setElement(Element.FIRE, 64).setElement(Element.NETHER, 16));
		addEntry(new ElementValue(Blocks.diamond_block, false).setElement(Element.EARTH, 73728).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.ice, false).setElement(Element.WATER, 16).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.clay, false).setElement(Element.EARTH, 128).setElement(Element.WATER, 128));
		addEntry(new ElementValue(Blocks.netherrack, false).setElement(Element.NETHER, 1));
		addEntry(new ElementValue(Blocks.soul_sand, false).setElement(Element.NETHER, 64));
		addEntry(new ElementValue(Blocks.glowstone, false).setElement(Element.MAGIC, 512).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.stonebrick, 0, false).setElement(Element.EARTH, 1));
		addEntry(new ElementValue(Blocks.stonebrick, 1, false).setElement(Element.EARTH, 1));
		addEntry(new ElementValue(Blocks.stonebrick, 2, false).setElement(Element.EARTH, 1));
		addEntry(new ElementValue(Blocks.stonebrick, 3, false).setElement(Element.EARTH, 1));
		addEntry(new ElementValue(Blocks.nether_brick, false).setElement(Element.NETHER, 4));
		addEntry(new ElementValue(Blocks.end_stone, false).setElement(Element.END, 64));
		addEntry(new ElementValue(Blocks.emerald_block, false).setElement(Element.EARTH, 18432).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.redstone_block, false).setElement(Element.EARTH, 576).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.quartz_block, 0, false).setElement(Element.NETHER, 256).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.quartz_block, 1, false).setElement(Element.NETHER, 256).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.quartz_block, 2, false).setElement(Element.NETHER, 256).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.hardened_clay, false).setElement(Element.EARTH, 128).setCanBeMade(false));
		addEntry(new ElementValue(Blocks.coal_block, false).setElement(Element.EARTH, 576).setCanBeMade(false));

		addEntry(new ElementValue(ElementsMC.deepWaterBottle, false).setElement(Element.WATER, 2048));
		addEntry(new ElementValue(ElementsMC.highAirBottle, false).setElement(Element.AIR, 2048));
		addEntry(new ElementValue(ElementsMC.elementCrystal, false).setElement(Element.AIR, 1024).setElement(Element.EARTH, 1024)
				.setElement(Element.END, 1024).setElement(Element.FIRE, 1024).setElement(Element.MAGIC, 1024).setElement(Element.NETHER, 1024)
				.setElement(Element.WATER, 1024));

		if(checkOre("ingotCopper")) addEntry(new ElementValue("ingotCopper").setElement(Element.EARTH, 256));
		if(checkOre("ingotTin")) addEntry(new ElementValue("ingotTin").setElement(Element.EARTH, 256));
		if(checkOre("ingotLead")) addEntry(new ElementValue("ingotLead").setElement(Element.EARTH, 256));
		if(checkOre("ingotBronze")) addEntry(new ElementValue("ingotBronze").setElement(Element.EARTH, 256));
		if(checkOre("ingotSilver")) addEntry(new ElementValue("ingotSilver").setElement(Element.EARTH, 512));
		if(checkOre("ingotNickel")) addEntry(new ElementValue("ingotNickel").setElement(Element.EARTH, 1024));
		if(checkOre("ingotPlatinum")) addEntry(new ElementValue("ingotPlatinum").setElement(Element.EARTH, 4096));
		if(checkOre("ingotInvar")) addEntry(new ElementValue("ingotInvar").setElement(Element.EARTH, 512));
		if(checkOre("ingotElectrum")) addEntry(new ElementValue("ingotElectrum").setElement(Element.EARTH, 1280));

		if(checkOre("dustCopper")) addEntry(new ElementValue("dustCopper").setElement(Element.EARTH, 256).setCanBeMade(false));
		if(checkOre("dustTin")) addEntry(new ElementValue("dustTin").setElement(Element.EARTH, 256).setCanBeMade(false));
		if(checkOre("dustLead")) addEntry(new ElementValue("dustLead").setElement(Element.EARTH, 256).setCanBeMade(false));
		if(checkOre("dustBronze")) addEntry(new ElementValue("dustBronze").setElement(Element.EARTH, 256).setCanBeMade(false));
		if(checkOre("dustSilver")) addEntry(new ElementValue("dustSilver").setElement(Element.EARTH, 512).setCanBeMade(false));
		if(checkOre("dustNickel")) addEntry(new ElementValue("dustNickel").setElement(Element.EARTH, 1024).setCanBeMade(false));
		if(checkOre("dustPlatinum")) addEntry(new ElementValue("dustPlatinum").setElement(Element.EARTH, 4096).setCanBeMade(false));
		if(checkOre("dustInvar")) addEntry(new ElementValue("dustInvar").setElement(Element.EARTH, 512).setCanBeMade(false));
		if(checkOre("dustElectrum")) addEntry(new ElementValue("dustElectrum").setElement(Element.EARTH, 1280).setCanBeMade(false));
		if(checkOre("dustDiamond")) addEntry(new ElementValue("dustDiamond").setElement(Element.EARTH, 8192).setCanBeMade(false));

		if(checkOre("oreUranium")) addEntry(new ElementValue("oreUranium").setElement(Element.EARTH, 2048).setCanBeMade(false));
		if(checkOre("gemDiamond")) addEntry(new ElementValue("gemDiamond").setElement(Element.EARTH, 8192).setCanBeMade(false));
		if(checkOre("dustSulfur")) addEntry(new ElementValue("dustSulfur").setElement(Element.FIRE, 64));
		if(checkOre("woodRubber")) addEntry(new ElementValue("woodRubber").setElement(Element.FIRE, 16).setCanBeMade(false));

		if(checkOre("soilEssence")) addEntry(new ElementValue("soilEssence").setElement(Element.MAGIC, 64).setElement(Element.EARTH, 64));
		if(checkOre("condensedSoilEssence"))
			addEntry(new ElementValue("condensedSoilEssence").setElement(Element.MAGIC, 576).setElement(Element.EARTH, 576));

		for(ElementValue val : valueMap.values())
		{
			addEntryMk2(val.clone());
		}

		addEntryMk2(new ElementValue(Items.redstone, false).setElement(Element.EARTH, 64).setElement(Element.ELECTRICITY, 64));
		addEntryMk2(new ElementValue(Items.rotten_flesh, false).setElement(Element.NETHER, 16).setElement(Element.LIFE, 4));
		addEntryMk2(new ElementValue(Items.snowball, false).setElement(Element.ICE, 16));
		addEntryMk2(new ElementValue(Items.string, false).setElement(Element.NETHER, 32).setElement(Element.LIFE, 8));
		addEntryMk2(new ElementValue(Items.glowstone_dust, false).setElement(Element.MAGIC, 32).setElement(Element.LIGHT, 128));
		addEntryMk2(new ElementValue(Items.beef, false).setElement(Element.LIFE, 64));
		addEntryMk2(new ElementValue(Items.chicken, false).setElement(Element.LIFE, 48));
		addEntryMk2(new ElementValue(Items.carrot, false).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Items.melon, false).setElement(Element.NATURE, 8));
		addEntryMk2(new ElementValue(Items.dye, 4, false).setElement(Element.NATURE, 32));
		addEntryMk2(new ElementValue(Items.dye, 1, false).setElement(Element.NATURE, 32));
		addEntryMk2(new ElementValue(Items.dye, 11, false).setElement(Element.NATURE, 32));
		addEntryMk2(new ElementValue(Items.dye, 14, false).setElement(Element.NATURE, 32));
		addEntryMk2(new ElementValue(Items.dye, 2, false).setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue(Items.egg, false).setElement(Element.LIFE, 32));
		addEntryMk2(new ElementValue(Items.feather, false).setElement(Element.LIFE, 64));
		addEntryMk2(new ElementValue(Items.wheat_seeds, false).setElement(Element.NATURE, 8));
		addEntryMk2(new ElementValue(Items.wheat, false).setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue(Items.porkchop, false).setElement(Element.LIFE, 64));
		addEntryMk2(new ElementValue(Items.leather, false).setElement(Element.LIFE, 64));
		addEntryMk2(new ElementValue(Items.reeds, false).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Items.pumpkin_seeds, false).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Items.melon_seeds, false).setElement(Element.NATURE, 8));
		addEntryMk2(new ElementValue(Items.potato, false).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Items.poisonous_potato, false).setElement(Element.NATURE, 16));

		addEntryMk2(new ElementValue("logWood").setElement(Element.FIRE, 16).setElement(Element.NATURE, 16).setCanBeMade(false)
				.setItemStack(new ItemStack(Blocks.log, 1, 0)));
		addEntryMk2(new ElementValue("plankWood").setElement(Element.FIRE, 4).setElement(Element.NATURE, 4).setCanBeMade(false)
				.setItemStack(new ItemStack(Blocks.planks, 1, 0)));
		addEntryMk2(new ElementValue("stickWood").setElement(Element.FIRE, 2).setElement(Element.NATURE, 2).setCanBeMade(false)
				.setItemStack(new ItemStack(Items.stick, 1, 0)));
		addEntryMk2(new ElementValue("slabWood").setElement(Element.FIRE, 2).setElement(Element.NATURE, 2).setCanBeMade(false)
				.setItemStack(new ItemStack(Blocks.wooden_slab, 1, 0)));
		addEntryMk2(new ElementValue("stairWood").setElement(Element.FIRE, 4).setElement(Element.NATURE, 4).setCanBeMade(false)
				.setItemStack(new ItemStack(Blocks.oak_stairs, 1, 0)));

		for(ItemStack stack : OreDictionary.getOres("logWood"))
		{
			ItemStack stack2 = stack.copy();
			if(stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) stack2.setItemDamage(0);
			addEntryMk2(new ElementValue(stack2, false).setElement(Element.FIRE, 16).setElement(Element.NATURE, 16));
		}
		// addEntry(new ElementValue(Blocks.log, 0,
		// false).setElement(Element.FIRE, 16).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Blocks.log, 1, false).setElement(Element.FIRE, 16).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Blocks.log, 2, false).setElement(Element.FIRE, 16).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Blocks.log, 3, false).setElement(Element.FIRE, 16).setElement(Element.NATURE, 16));
		// addEntry(new ElementValue(Blocks.log2, 0,
		// false).setElement(Element.FIRE, 16).setElement(Element.NATURE, 16));
		addEntryMk2(new ElementValue(Blocks.log2, 1, false).setElement(Element.FIRE, 16).setElement(Element.NATURE, 16));

		for(ItemStack stack : OreDictionary.getOres("treeSapling"))
		{
			addEntryMk2(new ElementValue(stack, false).setElement(Element.NATURE, 128));
		}

		/*
		 * addEntryMk2(new ElementValue("tile.sapling.spruce",
		 * false).setElement(Element.NATURE, 128)); addEntryMk2(new
		 * ElementValue("tile.sapling.birch", false).setElement(Element.NATURE,
		 * 128)); addEntryMk2(new ElementValue("tile.sapling.jungle",
		 * false).setElement(Element.NATURE, 128));
		 */
		addEntryMk2(new ElementValue(Blocks.ice, false).setElement(Element.ICE, 64));
		addEntryMk2(new ElementValue(Blocks.glowstone, false).setElement(Element.MAGIC, 128).setElement(Element.LIGHT, 512));

		addEntryMk2(new ElementValue(Blocks.grass, false).setElement(Element.NATURE, 32).setElement(Element.EARTH, 1));

		addEntryMk2(new ElementValue(Blocks.leaves, 0, false).setElement(Element.NATURE, 1));
		addEntryMk2(new ElementValue(Blocks.leaves, 1, false).setElement(Element.NATURE, 1));
		addEntryMk2(new ElementValue(Blocks.leaves, 2, false).setElement(Element.NATURE, 1));
		addEntryMk2(new ElementValue(Blocks.leaves, 3, false).setElement(Element.NATURE, 1));
		addEntryMk2(new ElementValue(Blocks.leaves2, 0, false).setElement(Element.NATURE, 1));
		addEntryMk2(new ElementValue(Blocks.leaves2, 1, false).setElement(Element.NATURE, 1));

		addEntryMk2(new ElementValue(Blocks.grass, 1, false).setElement(Element.NATURE, 4));
		addEntryMk2(new ElementValue(Blocks.grass, 2, false).setElement(Element.NATURE, 4));
		addEntryMk2(new ElementValue(Blocks.deadbush, false).setElement(Element.NATURE, 1));
		addEntryMk2(new ElementValue(Blocks.wool, false).setElement(Element.LIFE, 32));
		addEntryMk2(new ElementValue(Blocks.yellow_flower, false).setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue(Blocks.red_flower, false).setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue("mushroomBrown").setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue("mushroomRed").setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue(Blocks.torch, false).setElement(Element.LIGHT, 16).setElement(Element.FIRE, 16).setCanBeMade(false));
		addEntryMk2(new ElementValue(Blocks.cactus, false).setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue(Blocks.pumpkin, false).setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue(Blocks.lit_pumpkin, false).setElement(Element.NATURE, 64).setElement(Element.LIGHT, 32)
				.setElement(Element.FIRE, 16).setCanBeMade(false));
		addEntryMk2(new ElementValue(Blocks.melon_block, false).setElement(Element.NATURE, 64));
		addEntryMk2(new ElementValue(Blocks.vine, false).setElement(Element.NATURE, 32));
		addEntryMk2(new ElementValue(Blocks.mycelium, false).setElement(Element.NATURE, 32).setElement(Element.EARTH, 1));
		addEntryMk2(new ElementValue(Blocks.waterlily, false).setElement(Element.NATURE, 32));

		if(checkOre("itemRubber")) addEntryMk2(new ElementValue("itemRubber").setElement(Element.NATURE, 32));
		if(checkOre("oreUranium")) addEntryMk2(new ElementValue("oreUranium").setElement(Element.EARTH, 2048).setElement(Element.ELECTRICITY, 256));
		if(checkOre("woodRubber")) addEntryMk2(new ElementValue("woodRubber").setElement(Element.FIRE, 16).setElement(Element.NATURE, 48));
	}

	public static void addEntry(ElementValue entry)
	{
		valueMap.put(entry.name, entry);
		FMLLog.info("[ElementsMC] Adding element value " + entry.name + " with ItemStack " + entry.stack.toString() + " (oredict=" + entry.isOreDictionary + "): " + GenericHelper.printEssenceCounts(entry.values));
	}

	public static void addEntryMk2(ElementValue entry)
	{

		valueMapMk2.put(entry.name, entry);

	}

	public static boolean hasEntry(ItemStack stack)
	{
		return getEntry(stack) != null;
	}

	public static boolean hasEntry(ItemStack stack, boolean b)
	{
		return getEntry(stack, b) != null;
	}

	public static ElementValue getEntry(String str, boolean b)
	{
		if(b) return valueMapMk2.get(str);
		return valueMap.get(str);
	}

	public static ElementValue getEntry(String str)
	{
		return getEntry(str, false);
	}

	public static ElementValue getEntry(ItemStack stack, boolean b)
	{

		ElementValue val = getEntry(stack.getUnlocalizedName(), b);
		if(val != null) return val;
		val = getEntry(OreDictionary.getOreName(OreDictionary.getOreID(stack)), b);
		return val;
	}

	public static ElementValue getEntry(ItemStack stack)
	{
		return getEntry(stack, false);
	}

	public ElementValue clone()
	{
		ElementValue val = new ElementValue();
		val.stack = this.stack.copy();
		val.isOreDictionary = this.isOreDictionary;
		val.name = this.name;
		val.canBeMade = this.canBeMade;
		val.values = this.values.clone();
		return val;
	}

	@Override
	public String toString()
	{
		return "ElementValue [isOreDictionary=" + isOreDictionary + ", name=" + name + ", stack=" + stack + ", canBeMade=" + canBeMade + "]";
	}

	public static HashMap<String, ElementValue> getValueMap(boolean b)
	{
		if(b) return valueMapMk2;
		else return valueMap;
	}

	private static boolean checkOre(String str)
	{
		return OreDictionary.getOres(str) != null && !OreDictionary.getOres(str).isEmpty();
	}

	public ElementValue multiplyWith(float f)
	{
		for(int i = 0; i < values.length; i++)
		{
			values[i] = Math.round((float) values[i] * f);
		}
		return this;
	}

}

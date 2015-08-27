package elementsmc.common.main;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import elementsmc.client.util.Animation;
import elementsmc.common.block.BlockDeepWater;
import elementsmc.common.block.BlockEMFluid;
import elementsmc.common.block.BlockElementTable;
import elementsmc.common.block.BlockHighAir;
import elementsmc.common.block.BlockIncinerator;
import elementsmc.common.block.BlockInfuser;
import elementsmc.common.block.BlockMaterializer;
import elementsmc.common.block.BlockPodest;
import elementsmc.common.dungeon.BiomeGenDungeon;
import elementsmc.common.dungeon.CommandStructure;
import elementsmc.common.elements.ElementValue;
import elementsmc.common.elements.PodestRecipe;
import elementsmc.common.item.ItemElementMeter;
import elementsmc.common.item.ItemEssenceBottle;
import elementsmc.common.item.ItemTablet;
import elementsmc.common.tileentity.TileElementTable;
import elementsmc.common.tileentity.TileIncinerator;
import elementsmc.common.tileentity.TileInfuser;
import elementsmc.common.tileentity.TileMaterializer;
import elementsmc.common.tileentity.TilePodest;
import elementsmc.common.util.GenericHelper;

@Mod(modid = EMConstants.MOD_ID, version = EMConstants.VERSION)
public class ElementsMC implements EMConstants
{

	@Mod.Instance(value = EMConstants.MOD_ID)
	public static ElementsMC instance;

	public static File configFile;
	public static Configuration config;

	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
	public static EMProxy proxy;

	public static FMLEventChannel networkChannel;
		
	public static Item quartzTablet;
	public static Item highAirBottle;
	public static Item deepWaterBottle;
	public static Item elementCrystal;
	public static Item elementMeter;
	public static Item goldTablet;
	public static Item earthWaterBucket;

	public static Block elementTable;
	public static Block incinerator;
	public static Block materializer;
	public static Block goldPodest;
	public static Block highAir;
	public static Block deepWater;
	public static Block infuser;
	public static Block earthWater;
	
	public static Fluid deepWaterFluid;
	public static Fluid earthWaterFluid;

	public static EMCreativeTab tab = new EMCreativeTab();
	
	public static int incineratorRenderID;
	public static int elementTableRenderID;
	public static int materializerRenderID;
	public static int podestRenderID;
	public static int deepWaterRenderID;
	public static int infuserRenderID;
	
	public static BiomeGenDungeon dungeonBiome;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
		config = new Configuration(configFile);

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
			
		deepWaterFluid = new Fluid("deepWater").setDensity(800).setViscosity(1500);
		FluidRegistry.registerFluid(deepWaterFluid);
		earthWaterFluid = new Fluid("earthWater").setDensity(1200).setViscosity(3000);
		FluidRegistry.registerFluid(earthWaterFluid);

		elementTable = new BlockElementTable();
		incinerator = new BlockIncinerator();
		materializer = new BlockMaterializer();
		goldPodest = new BlockPodest().setResource(new ResourceLocation("elementsmc:textures/blocks/goldPodest.png")).setBlockName("goldPodest").setBlockTextureName("elementsmc:goldPodest");
		highAir = new BlockHighAir();
		deepWater = new BlockDeepWater(deepWaterFluid);
		infuser = new BlockInfuser();
		earthWater = new BlockEMFluid(earthWaterFluid, "elementsmc:earthWater", "elementsmc:earthWater_flow");
		
		quartzTablet = new ItemTablet().setResource(new ResourceLocation("elementsmc:textures/items/quartzTablet.png")).setUnlocalizedName("quartzTablet").setCreativeTab(tab).setTextureName("elementsmc:quartzTablet");
		highAirBottle = new ItemEssenceBottle(highAir, 0, Blocks.air, 0).setUnlocalizedName("highAirBottle").setCreativeTab(tab).setTextureName("elementsmc:highAirBottle");
		deepWaterBottle = new ItemEssenceBottle(deepWater, 0, Blocks.water, 0).setUnlocalizedName("deepWaterBottle").setCreativeTab(tab).setTextureName("elementsmc:deepWaterBottle");	
		elementCrystal = new ItemSimpleFoiled().setUnlocalizedName("elementCrystal").setCreativeTab(tab).setTextureName("elementsmc:elementCrystal");
		elementMeter = new ItemElementMeter();
		goldTablet = new ItemTablet().setResource(new ResourceLocation("elementsmc:textures/items/goldTablet.png")).setUnlocalizedName("goldTablet").setCreativeTab(tab).setTextureName("elementsmc:goldTablet");
		earthWaterBucket = new ItemBucket(earthWater).setUnlocalizedName("earthWaterBucket").setCreativeTab(tab).setTextureName("elementsmc:earthWaterBucket").setContainerItem(Items.bucket);
		
		GameRegistry.registerBlock(elementTable, "elementTable");
		GameRegistry.registerBlock(incinerator, "incinerator");
		GameRegistry.registerBlock(materializer, "materializer");
		GameRegistry.registerBlock(goldPodest, "goldPodest");
		GameRegistry.registerBlock(highAir, "highAir");
		GameRegistry.registerBlock(deepWater, "deepWater");
		GameRegistry.registerBlock(infuser, "infuser");
		GameRegistry.registerBlock(earthWater, "earthWater");

		GameRegistry.registerTileEntity(TileElementTable.class, "elementTable");
		GameRegistry.registerTileEntity(TileIncinerator.class, "incinerator");
		GameRegistry.registerTileEntity(TileMaterializer.class, "materializer");
		GameRegistry.registerTileEntity(TilePodest.class, "podest");
		GameRegistry.registerTileEntity(TileInfuser.class, "infuser");
		
		GameRegistry.registerItem(quartzTablet, "quartzTablet");
		GameRegistry.registerItem(highAirBottle, "highAirBottle");
		GameRegistry.registerItem(deepWaterBottle, "deepWaterBottle");
		GameRegistry.registerItem(elementCrystal, "elementCrystal");
		GameRegistry.registerItem(elementMeter, "elementMeter");
		GameRegistry.registerItem(goldTablet, "goldTablet");
		GameRegistry.registerItem(earthWaterBucket, "earthWaterBucket");
		
		FluidContainerRegistry.registerFluidContainer(deepWaterFluid, new ItemStack(deepWaterBottle), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(earthWaterFluid, new ItemStack(earthWaterBucket), new ItemStack(Items.bucket));
		
		GameRegistry.addShapedRecipe(new ItemStack(goldPodest), "X X", "XXX", " X ", 'X', Items.gold_ingot);
		GameRegistry.addShapelessRecipe(new ItemStack(quartzTablet), Items.quartz, Items.quartz, Items.quartz, Items.quartz, Items.quartz, Items.quartz, Items.quartz);
		GameRegistry.addShapedRecipe(new ItemStack(incinerator), "GGG", "GFG", "SSS", 'G', Items.gold_ingot, 'F', Blocks.furnace, 'S', new ItemStack(Blocks.stone_slab, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(materializer), "G G", "GIG", "SSS", 'G', Items.gold_ingot, 'I', Blocks.heavy_weighted_pressure_plate, 'S', new ItemStack(Blocks.stone_slab, 1, 0));
		
		MinecraftForge.EVENT_BUS.register(new EMEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new EMOreHandler());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new EMGuiHandler());
		networkChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(MOD_ID);
		networkChannel.register(new EMNetworkHandler());
		
		int dungeonBiomeID = config.get("biomes", "dungeon", GenericHelper.getNextFreeBiomeID()).getInt();
		dungeonBiome = new BiomeGenDungeon(dungeonBiomeID);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();

		ElementValue.initDefaultEntries();
		
		CommandStructure.initialize();
		
		PodestRecipe.recipeList.add(new PodestRecipe(goldPodest, new ItemStack(quartzTablet), elementTable, 0,
				new ItemStack(deepWaterBottle), new ItemStack(highAirBottle), new ItemStack(Items.blaze_rod),
				new ItemStack(Items.ender_pearl), new ItemStack(Items.diamond), new ItemStack(Items.skull, 1, 1), new ItemStack(Blocks.glowstone)));
		
		PodestRecipe.recipeList.add(new PodestRecipe(goldPodest, null, new ItemStack(elementMeter),
				new ItemStack(elementCrystal), new ItemStack(Items.comparator), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
				new ItemStack(Items.iron_ingot), new ItemStack(Blocks.glass_pane), new ItemStack(Items.glowstone_dust)));
	
		config.save();
	}
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandStructure());
	}
	
	

}

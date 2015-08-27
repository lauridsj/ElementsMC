package elementsmc.client.main;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import elementsmc.client.render.DeepWaterRenderer;
import elementsmc.client.render.ElementTableRenderer;
import elementsmc.client.render.IncineratorRenderer;
import elementsmc.client.render.InfuserRenderer;
import elementsmc.client.render.MaterializerRenderer;
import elementsmc.client.render.PodestRenderer;
import elementsmc.client.render.TabletRenderer;
import elementsmc.common.main.EMProxy;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TileElementTable;
import elementsmc.common.tileentity.TileIncinerator;
import elementsmc.common.tileentity.TileInfuser;
import elementsmc.common.tileentity.TileMaterializer;
import elementsmc.common.tileentity.TilePodest;

public class EMClientProxy extends EMProxy
{
	
	public static EMClientProxy getInstance()
	{
		return (EMClientProxy) ElementsMC.proxy;
	}
	
	public void preInit()
	{
		ElementsMC.configFile = new File(Minecraft.getMinecraft().mcDataDir, "config/ElementsMC.cfg");
	}

	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new EMClientEventHandler());
	}

	public void postInit()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileElementTable.class, new ElementTableRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileIncinerator.class, new IncineratorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMaterializer.class, new MaterializerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePodest.class, new PodestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileInfuser.class, new InfuserRenderer());
		
		ElementsMC.incineratorRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(ElementsMC.incineratorRenderID, new IncineratorRenderer());
		ElementsMC.elementTableRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(ElementsMC.elementTableRenderID, new ElementTableRenderer());
		ElementsMC.materializerRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(ElementsMC.materializerRenderID, new MaterializerRenderer());
		ElementsMC.podestRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(ElementsMC.podestRenderID, new PodestRenderer());
		ElementsMC.deepWaterRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(ElementsMC.deepWaterRenderID, new DeepWaterRenderer());
		ElementsMC.infuserRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(ElementsMC.infuserRenderID, new InfuserRenderer());
		
		MinecraftForgeClient.registerItemRenderer(ElementsMC.quartzTablet, TabletRenderer.instance);
		MinecraftForgeClient.registerItemRenderer(ElementsMC.goldTablet, TabletRenderer.instance);
	}
}

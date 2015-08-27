package elementsmc.common.main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import elementsmc.client.gui.GuiElementTable;
import elementsmc.client.gui.GuiIncinerator;
import elementsmc.client.gui.GuiMaterializer;
import elementsmc.common.tileentity.ContainerElementTable;
import elementsmc.common.tileentity.ContainerIncinerator;
import elementsmc.common.tileentity.ContainerMaterializer;
import elementsmc.common.tileentity.TileElementTable;
import elementsmc.common.tileentity.TileIncinerator;
import elementsmc.common.tileentity.TileMaterializer;

public class EMGuiHandler implements IGuiHandler
{

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity != null)
		{
			switch(id)
			{
			case EMConstants.ELEMENT_TABLE_GUI:
				return new GuiElementTable(new ContainerElementTable((TileElementTable) tileEntity));
			case EMConstants.INCINERATOR_GUI:
				return new GuiIncinerator(new ContainerIncinerator(player.inventory, (TileIncinerator) tileEntity));
			case EMConstants.MATERIALIZER_GUI:
				return new GuiMaterializer(new ContainerMaterializer(player.inventory, (TileMaterializer) tileEntity));
			}
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity != null)
		{
			switch(id)
			{
			case EMConstants.ELEMENT_TABLE_GUI:
				return new ContainerElementTable((TileElementTable) tileEntity);
			case EMConstants.INCINERATOR_GUI:
				return new ContainerIncinerator(player.inventory, (TileIncinerator) tileEntity);
			case EMConstants.MATERIALIZER_GUI:
				return new ContainerMaterializer(player.inventory, (TileMaterializer) tileEntity);
			}
		}
		return null;
	}

}

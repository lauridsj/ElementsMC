package elementsmc.common.main;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import elementsmc.client.util.Animation;
import elementsmc.common.tileentity.TileMaterializer;
import elementsmc.common.util.StreamHelper;

public class EMNetworkHandler implements EMConstants
{

	@SubscribeEvent
	public void onClientCustomPacket(FMLNetworkEvent.ClientCustomPacketEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onServerCustomPacket(FMLNetworkEvent.ServerCustomPacketEvent event)
	{
		StreamHelper.bindInputStream(event.packet.payload().array());
		NetHandlerPlayServer handler = (NetHandlerPlayServer) event.handler;
		EntityPlayerMP player = handler.playerEntity;
		int action = StreamHelper.readInt();
		switch(action)
		{
		case MATERIALIZER_ITEM_CHOOSE:
			TileEntity te = player.worldObj.getTileEntity(StreamHelper.readInt(), StreamHelper.readInt(), StreamHelper.readInt());
			if(te != null && te instanceof TileMaterializer)
			{
				TileMaterializer materializer = (TileMaterializer) te;
				int id = StreamHelper.readInt();
				int meta = StreamHelper.readInt();
				ItemStack item;
				if(id == 0)
				{
					item = null;
				}
				else
				{
					item = new ItemStack(Item.getItemById(id), 1, meta);
				}
				materializer.producingItem = item;
				materializer.markDirty();
			}
			break;
		}
	}
	
	
		
}

package elementsmc.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import elementsmc.common.elements.ElementNetwork;
import elementsmc.common.elements.INetworkPart;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.util.GenericHelper;

public class ItemElementMeter extends Item
{

	public static final ChatStyle CHAT_STYLE = new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA);
	
	public ItemElementMeter()
	{
		this.setUnlocalizedName("elementMeter");
		this.setCreativeTab(ElementsMC.tab);
		this.setMaxStackSize(1);
		this.setTextureName("elementsmc:elementMeter");
	}

	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float partX, float partY, float partZ)
	{
		if(!world.isRemote)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof INetworkPart)
			{
				INetworkPart part = (INetworkPart) te;
				GenericHelper.sendChatToPlayer(player, "[Element Meter] Analyzing " + part.getClass().getSimpleName() + " at coordinates " + x + "; " + y + "; " + z + "...", CHAT_STYLE);
				if(part.getNetwork() == null)
				{
					GenericHelper.sendChatToPlayer(player, "This block is not connected to any network.", CHAT_STYLE);
				}
				else
				{
					ElementNetwork net = part.getNetwork();
					GenericHelper.sendChatToPlayer(player, "This block is connected to the following network:", CHAT_STYLE);
					GenericHelper.sendChatToPlayer(player, "Network ID: " + net.id, CHAT_STYLE);
					GenericHelper.sendChatToPlayer(player, "Network Level: " + net.mk, CHAT_STYLE);
					GenericHelper.sendChatToPlayer(player, "Network part count: " + net.partCoords.size(), CHAT_STYLE);
					GenericHelper.sendChatToPlayer(player, "Network essence count: " + net.printEssenceCount(), CHAT_STYLE);
				}
			}
		}
		return true;
	}

}

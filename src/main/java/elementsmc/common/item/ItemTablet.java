package elementsmc.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemTablet extends Item
{

	public ItemTablet()
	{
		this.setMaxStackSize(1);
	}
	
	private ResourceLocation resource;
	
	public ItemTablet setResource(ResourceLocation res)
	{
		this.resource = res;
		return this;
	}
	
	public ResourceLocation getResource(ItemStack item)
	{
		return resource;
	}
	
}

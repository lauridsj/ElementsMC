package elementsmc.common.util;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class ItemLocalizedComparator implements Comparator<ItemStack>
{

	public static final ItemLocalizedComparator INSTANCE = new ItemLocalizedComparator();
	
	@Override
	public int compare(ItemStack arg0, ItemStack arg1)
	{
		return arg0.getDisplayName().compareTo(arg1.getDisplayName());
	}

}

package elementsmc.common.util;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class ItemUnlocalizedComparator implements Comparator<ItemStack>
{

	public static final ItemUnlocalizedComparator INSTANCE = new ItemUnlocalizedComparator();
	
	@Override
	public int compare(ItemStack arg0, ItemStack arg1)
	{
		return arg0.getUnlocalizedName().compareTo(arg1.getUnlocalizedName());
	}
	
}


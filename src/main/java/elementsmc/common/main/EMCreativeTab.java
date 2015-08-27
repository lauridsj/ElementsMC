package elementsmc.common.main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class EMCreativeTab extends CreativeTabs
{

	public EMCreativeTab()
	{
		super("elementsMC");
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(ElementsMC.elementTable);
	}

}

package elementsmc.common.item;

import elementsmc.common.elements.Element;

public class ItemMagicTablet extends ItemTablet implements IInfusableItem 
{
	
	public int maxEssence;
	
	public int[] essences = new int[Element.values().length];
	
	public ItemMagicTablet(int maxEssence)
	{
		this.maxEssence = maxEssence;
	}
	
	@Override
	public int getEssenceCount(Element e)
	{
		return essences[e.ordinal()];
	}

	@Override
	public void setEssenceCount(Element e, int count) 
	{
		essences[e.ordinal()] = count;
	}

	@Override
	public int getEssenceMax(Element e)
	{
		return maxEssence;
	}
	

}

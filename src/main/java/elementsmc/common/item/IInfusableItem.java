package elementsmc.common.item;

import elementsmc.common.elements.Element;

public interface IInfusableItem {

	public int getEssenceCount(Element e);
	public void setEssenceCount(Element e, int count);
	public int getEssenceMax(Element e);
	
	
}

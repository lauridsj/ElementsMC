package elementsmc.common.tileentity;

import elementsmc.common.elements.ElementNetwork;

public class TileElementTable extends TileNetworkPart
{

	public int mk = 0;

	@Override
	public void onAddToNetwork(ElementNetwork net)
	{

	}

	@Override
	public void onRemoveFromNetwork(ElementNetwork net)
	{

	}

	@Override
	public int getMk()
	{
		return mk;
	}

	@Override
	public int networkRadius()
	{
		return 16;
	}

	@Override
	public void onNetworkDeleted(ElementNetwork net)
	{

	}

}

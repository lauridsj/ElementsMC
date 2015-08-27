package elementsmc.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import elementsmc.common.elements.ElementNetwork;
import elementsmc.common.elements.INetworkPart;

public abstract class TileNetworkPart extends TileEntity implements INetworkPart
{

	private ElementNetwork net;

	public ElementNetwork getNetwork()
	{
		return this.net;
	}

	public void setNetwork(ElementNetwork net)
	{
		this.net = net;
	}

	@Override
	public World getWorld()
	{
		return this.getWorldObj();
	}

	@Override
	public int getX()
	{
		return this.xCoord;
	}

	@Override
	public int getY()
	{
		return this.yCoord;
	}

	@Override
	public int getZ()
	{
		return this.zCoord;
	}

}

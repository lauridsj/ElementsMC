package elementsmc.common.elements;

import net.minecraft.world.World;

public interface INetworkPart
{

	public void onAddToNetwork(ElementNetwork net);

	public void onRemoveFromNetwork(ElementNetwork net);

	public void onNetworkDeleted(ElementNetwork net);

	/***
	 * The mk this network part should give its network. Only use that in parts
	 * that CHANGE the network's mk!
	 * 
	 * @return the mk to set, or else 0
	 */
	public int getMk();

	public World getWorld();

	public int getX();

	public int getY();

	public int getZ();

	public int networkRadius();

	public ElementNetwork getNetwork();

	public void setNetwork(ElementNetwork net);

}

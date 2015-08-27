package elementsmc.common.elements;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import elementsmc.common.util.Coords4;
import elementsmc.common.util.GenericHelper;

public class ElementNetwork
{

	private static HashMap<Integer, ElementNetwork> networks = new HashMap<Integer, ElementNetwork>();
	private static int nextID = 0;

	public int id;
	//public ArrayList<INetworkPart> parts = new ArrayList<INetworkPart>();
	public ArrayList<Coords4> partCoords = new ArrayList<Coords4>();
	public int mk;
	public boolean isDeleted = false;
	private int[] essenceCount = new int[Element.values().length];

	public void init()
	{
		this.id = nextID;
		nextID++;
		this.mk = 0;
		networks.put(this.id, this);
	}

	public ArrayList<INetworkPart> getParts()
	{
		ArrayList<INetworkPart> list = new ArrayList<INetworkPart>();
		for(Coords4 c : this.partCoords)
		{
			World w = MinecraftServer.getServer().worldServerForDimension(c.dim);
			if(w != null)
			{
				TileEntity tile = w.getTileEntity(c.x, c.y, c.z);
				if(tile != null && tile instanceof INetworkPart)
				{
					list.add((INetworkPart) tile);
				}
			}
		}
		return list;
	}

	public int getEssenceCount(Element e)
	{
		return this.essenceCount[e.ordinal()];
	}

	public void setEssenceCount(Element e, int value)
	{
		if(e.mk <= this.mk)
		{
			this.essenceCount[e.ordinal()] = value;
		}
	}

	public void addEssence(Element e, int value)
	{
		if(e.mk <= this.mk)
		{
			this.essenceCount[e.ordinal()] = this.essenceCount[e.ordinal()] + value;
		}
	}

	public void addEssence(ElementValue val)
	{
		for(Element e : Element.values())
		{
			this.addEssence(e, val.getElement(e));
		}
	}

	public boolean consumeEssence(Element e, int value)
	{
		if(this.essenceCount[e.ordinal()] > value)
		{
			return false;
		}
		else
		{
			this.essenceCount[e.ordinal()] = this.essenceCount[e.ordinal()] - value;
			return true;
		}
	}

	public boolean consumeEssence(ElementValue val)
	{
		if(!containsEssence(val)) return false;
		for(Element e : Element.values())
		{
			this.essenceCount[e.ordinal()] -= val.getElement(e);
		}
		return true;
	}

	public boolean containsEssence(ElementValue val)
	{
		for(Element e : Element.values())
		{
			if(this.getEssenceCount(e) < val.getElement(e))
			{
				return false;
			}
		}
		return true;
	}



	public static ElementNetwork getNetwork(int id)
	{
		return networks.get(id);
	}

	public static void onNetworkPartAddedToWorld(INetworkPart part)
	{
		if(!part.getWorld().isRemote)
		{
			ArrayList<ElementNetwork> list = new ArrayList<ElementNetwork>();
			for(ElementNetwork net : networks.values())
			{
				if(net.canConnectWith(part))
				{
					list.add(net);
				}
			}
			for(ElementNetwork tnet : list)
			{
				System.out.print(tnet.id + "; ");
			}
			if(list.isEmpty())
			{
				if(part.networkRadius() > 0)
				{
					ElementNetwork newNet = new ElementNetwork();
					newNet.init();
					newNet.addNetworkPart(part);
					part.setNetwork(newNet);
					newNet.mk = part.getMk();
					part.onAddToNetwork(newNet);
				}
			}
			else if(list.size() == 1)
			{
				list.get(0).addNetworkPart(part);
			}
			else
			{
				list.get(0).addNetworkPart(part);
				ElementNetwork net = mergeNetworks(list.get(0), list.get(1));
				if(list.size() > 2)
				{
					for(int i = 2; i < list.size(); i++)
					{
						net = mergeNetworks(net, list.get(i));
					}
				}
			}
		}
	}

	public static void onNetworkPartRemovedFromWorld(INetworkPart part)
	{
		if(!part.getWorld().isRemote)
		{
			if(part.getNetwork() != null && !part.getNetwork().isDeleted)
			{
				/*
				part.onRemoveFromNetwork(part.getNetwork());
				part.getNetwork().partCoords.remove(Coords4.fromNetworkPart(part));
				 */
				part.getNetwork().removeNetworkPart(part);
			}


		}
	}

	public void addNetworkPart(INetworkPart part)
	{
		this.partCoords.add(new Coords4(part.getX(), part.getY(), part.getZ(), part.getWorld().provider.dimensionId));
		//	this.parts.add(part);
		part.setNetwork(this);
		this.mk = Math.max(part.getMk(), this.mk);
		part.onAddToNetwork(this);
	}

	public void removeNetworkPart(INetworkPart part)
	{
		part.onRemoveFromNetwork(this);
		this.partCoords.remove(new Coords4(part.getX(), part.getY(), part.getZ(), part.getWorld().provider.dimensionId));
		//this.parts.remove(part);	
		part.setNetwork(null);
		if(!checkAndSplitNetwork(this))
		{
			this.checkMk();
		}

	}

	public boolean canConnectWith(INetworkPart part)
	{
		if(!this.isDeleted)
		{
			for(INetworkPart part2 : this.getParts())
			{
				int i = Math.max(part.networkRadius(), part2.networkRadius());
				if(part != part2 && part.getWorld() == part2.getWorld()
						&& GenericHelper.getDistanceSquared(part.getX(), part.getY(), part.getZ(), part2.getX(), part2.getY(), part2.getZ()) <= i * i)
				{
					return true;
				}
			}
		}
		return false;
	}

	public static ElementNetwork mergeNetworks(ElementNetwork net1, ElementNetwork net2)
	{
		ElementNetwork newNet = new ElementNetwork();
		newNet.init();
		ArrayList<INetworkPart> list = new ArrayList<INetworkPart>();
		for(INetworkPart part : net1.getParts())
		{
			list.add(part);
		}
		for(INetworkPart part : net2.getParts())
		{
			list.add(part);
		}
		for(Element e : Element.values())
		{
			newNet.addEssence(e, net1.getEssenceCount(e));
			newNet.addEssence(e, net2.getEssenceCount(e));
		}
		net1.delete();
		net2.delete();
		for(INetworkPart part : list)
		{
			newNet.addNetworkPart(part);
		}
		return newNet;
	}

	public void delete()
	{
		this.isDeleted = true;
		for(INetworkPart part : this.getParts())
		{
			part.setNetwork(null);
			part.onNetworkDeleted(this);
		}
		networks.remove(this.id);
	}

	public static boolean checkAndSplitNetwork(ElementNetwork net)
	{
		ArrayList<ArrayList<INetworkPart>> map = new ArrayList<ArrayList<INetworkPart>>();
		ArrayList<INetworkPart> parts = net.getParts();
		int k = 0;
		while(!parts.isEmpty())
		{
			k++;
			ArrayList<INetworkPart> newParts = new ArrayList<INetworkPart>();
			newParts.add(parts.get(0));
			parts.remove(0);
			boolean changed = false;
			do
			{
				changed = false;
				for(int j = 0; j < parts.size(); j++)
				{
					if(canConnect(parts.get(j), newParts))
					{
						newParts.add(parts.get(j));
						parts.remove(j);
						changed = true;
						break;
					}
				}
			}
			while(changed && !parts.isEmpty());
			map.add(newParts);
		}

		if(map.size() == 1) return false;

		ElementNetwork[] newNets = new ElementNetwork[map.size()];

		for(int i = 0; i < map.size(); i++)
		{
			newNets[i] = new ElementNetwork();
			newNets[i].init();
			for(Element e : Element.values())
			{
				newNets[i].addEssence(e, MathHelper.floor_double((double)net.getEssenceCount(e) / (double)map.size()));
			}
		}

		net.delete();

		for(int i = 0; i < map.size(); i++)
		{
			ArrayList<INetworkPart> list = map.get(i);
			for(INetworkPart part : list)
			{
				newNets[i].addNetworkPart(part);
			}
		}

		return true;
	}

	public static boolean canConnect(INetworkPart part, ArrayList<INetworkPart> parts)
	{

		for(INetworkPart part2 : parts)
		{
			int i = Math.max(part.networkRadius(), part2.networkRadius());
			if(part != part2 && part.getWorld() == part2.getWorld()
					&& GenericHelper.getDistanceSquared(part.getX(), part.getY(), part.getZ(), part2.getX(), part2.getY(), part2.getZ()) <= i * i)
			{
				return true;
			}
		}

		return false;
	}

	public void checkMk()
	{
		int newMk = 0;
		for(INetworkPart part : this.getParts())
		{
			newMk = Math.max(newMk, part.getMk());
		}
		this.mk = newMk;
	}

	public static void writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("nextID", nextID);
		NBTTagList list = new NBTTagList();
		for(ElementNetwork net : networks.values())
		{
			if(!net.isDeleted)
			{
				NBTTagCompound tag2 = new NBTTagCompound();
				tag2.setInteger("id", net.id);
				tag2.setInteger("mk", net.mk);
				tag2.setIntArray("essenceCount", net.essenceCount);
				NBTTagList list2 = new NBTTagList();
				for(Coords4 c : net.partCoords)
				{
					list2.appendTag(Coords4.toNBT(c));
				}
				tag2.setTag("partCoords", list2);
				list.appendTag(tag2);
			}
		}
		tag.setTag("networks", list);
	}

	public static void readFromNBT(NBTTagCompound tag)
	{
		nextID = tag.getInteger("nextID");
		NBTTagList list = tag.getTagList("networks", 10);
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound tag2 = list.getCompoundTagAt(i);
			ElementNetwork net = new ElementNetwork();
			net.id = tag2.getInteger("id");
			net.mk = tag2.getInteger("mk");
			net.essenceCount = tag2.getIntArray("essenceCount");
			NBTTagList list2 = tag2.getTagList("partCoords", 10);
			for(int j = 0; j < list2.tagCount(); j++)
			{
				net.partCoords.add(Coords4.fromNBT(list2.getCompoundTagAt(j)));
			}
			networks.put(net.id, net);
			for(INetworkPart part : net.getParts())
			{
				if(part != null)
				{
					part.setNetwork(net);
				}
			}
		}
	}

	public static void reset()
	{
		networks.clear();
		nextID = 0;
	}

	public String printEssenceCount()
	{
		String str = "";
		for(int i = 0; i < essenceCount.length; i++)
		{
			if(Element.values().length > i)
			{
				str += Element.values()[i].getLocalizedName();
				str += ": ";
				str += essenceCount[i];
				str += "; ";
			}
			else break;
		}
		return str;
	}



}



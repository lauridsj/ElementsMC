package elementsmc.common.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import elementsmc.common.elements.ElementNetwork;
import elementsmc.common.elements.ElementValue;

public class TileIncinerator extends TileInventoryNetworkPart
{

	public static final int TICKS_PER_OPERATION = 40;

	public int burnTime;

	@Override
	public void onAddToNetwork(ElementNetwork net)
	{

	}

	@Override
	public void onRemoveFromNetwork(ElementNetwork net)
	{

	}

	@Override
	public void onNetworkDeleted(ElementNetwork net)
	{

	}

	@Override
	public int getMk()
	{
		return 0;
	}

	@Override
	public int networkRadius()
	{
		return 0;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int arg0, ItemStack arg1)
	{
		return ElementValue.hasEntry(arg1);
	}

	@Override
	public String getRawInventoryName()
	{
		return "container.incinerator";
	}

	public void updateEntity()
	{
		if(!this.worldObj.isRemote && this.getNetwork() != null && this.getStackInSlot(0) != null)
		{
			this.burnTime++;
			if(this.burnTime >= TICKS_PER_OPERATION)
			{
				this.burnTime = 0;
				ElementValue val = ElementValue.getEntry(this.getStackInSlot(0), this.getNetwork().mk > 0);
				if(val != null)
				{
					this.getNetwork().addEssence(val);
					this.decrStackSize(0, 1);
				}
			}
			this.markDirty();
		}
		else
		{
			this.burnTime = 0;
		}
	}

	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("burnTime", this.burnTime);
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.burnTime = tag.getInteger("burnTime");
	}
	
	public Packet getDescriptionPacket()
	{
		
		NBTTagCompound tag = new NBTTagCompound();
		if(this.getStackInSlot(0) != null)
		{
			NBTTagCompound item = new NBTTagCompound();
			this.getStackInSlot(0).writeToNBT(item);
			tag.setTag("item", item);
		}
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
		
	}
	
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		if(packet.func_148857_g().hasKey("item"))
		{
			this.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(packet.func_148857_g().getCompoundTag("item")));
		}
		else
		{
			this.setInventorySlotContents(0, null);
		}
	}



}

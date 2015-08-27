package elementsmc.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import elementsmc.common.elements.ElementNetwork;
import elementsmc.common.elements.ElementValue;

public class TileMaterializer extends TileInventoryNetworkPart
{

	public ItemStack producingItem;
	public int producingTime = 0;
	public static final int TICKS_PER_OPERATION = 40;
	

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
		return false;
	}

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
	public String getRawInventoryName()
	{
		return "container.materializer";
	}

	public void updateEntity()
	{
		if(!this.worldObj.isRemote && this.getNetwork() != null && this.producingItem != null)
		{
			ElementValue val = ElementValue.getEntry(this.producingItem, this.getNetwork().mk > 0);
			if(val != null && val.canBeMade && (this.getStackInSlot(0) == null || (this.getStackInSlot(0).isItemEqual(this.producingItem) && this.getStackInSlot(0).stackSize < 64)))
			{
				val = val.clone().multiplyWith(2.5f);
				if(this.getNetwork().containsEssence(val))
				{
					this.producingTime++;
					if(this.producingTime >= TICKS_PER_OPERATION)
					{
						this.producingTime = 0;
						ItemStack stack = this.getStackInSlot(0);
						if(stack == null)
						{
							this.setInventorySlotContents(0, this.producingItem.copy());
						}
						else
						{
							stack.stackSize++;
						}
						this.getNetwork().consumeEssence(val);
					}
				}
				return;
			}
		}
		this.producingTime = 0;
	}

	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("producingTime", this.producingTime);
		if(this.producingItem != null)
		{
			NBTTagCompound item = new NBTTagCompound();
			this.producingItem.writeToNBT(item);
			tag.setTag("producingItem", item);
		}
	}

	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.producingTime = tag.getInteger("producingTime");
		if(tag.hasKey("producingItem"))
		{
			this.producingItem = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("producingItem"));
		}
	}
	
	public Packet getDescriptionPacket()
	{
		
		NBTTagCompound tag = new NBTTagCompound();
		if(this.producingItem != null)
		{
			NBTTagCompound item = new NBTTagCompound();
			this.producingItem.writeToNBT(item);
			tag.setTag("producingItem", item);
		}
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
		if(packet.func_148857_g().hasKey("producingItem"))
		{
			this.producingItem = ItemStack.loadItemStackFromNBT(packet.func_148857_g().getCompoundTag("producingItem"));
		}
		else
		{
			this.producingItem = null;
		}
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

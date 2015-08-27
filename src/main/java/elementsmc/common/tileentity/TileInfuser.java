package elementsmc.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import elementsmc.client.util.Animation;
import elementsmc.common.elements.ElementNetwork;
import elementsmc.common.util.Coords4;
import elementsmc.common.util.GenericHelper;

public class TileInfuser extends TileInventoryNetworkPart {

	
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
	{
		return true;
	}

	@Override
	public void onAddToNetwork(ElementNetwork net) {}

	@Override
	public void onRemoveFromNetwork(ElementNetwork net) {}

	@Override
	public void onNetworkDeleted(ElementNetwork net) {}

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
		return "container.infuser";
	}
	
	public void updateEntity()
	{
		super.updateEntity();
		if(this.worldObj.isRemote)
		{
			Coords4 c = Coords4.fromTileEntity(this);
			if(this.getStackInSlot(0) != null && !Animation.hasAnimation(c, "inner"))
			{
				Animation ani = new Animation(Long.MAX_VALUE);
				ani.setRotation(GenericHelper.RNG.nextInt(20) + 10, 0, GenericHelper.RNG.nextInt(20) + 10);
				Animation.addAnimation(ani, c, "inner");
				ani = new Animation(Long.MAX_VALUE);
				ani.setRotation(GenericHelper.RNG.nextInt(20) + 10, 0, GenericHelper.RNG.nextInt(20) + 10);
				Animation.addAnimation(ani, c, "outer");
			}
			if(this.getStackInSlot(0) == null && Animation.hasAnimation(c, "inner"))
			{
				Animation.removeAnimation(Coords4.fromTileEntity(this), "outer");
				Animation.removeAnimation(Coords4.fromTileEntity(this), "inner");
			}
		}
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

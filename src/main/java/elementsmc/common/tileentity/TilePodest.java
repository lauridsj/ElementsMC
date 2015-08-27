package elementsmc.common.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import elementsmc.client.util.Animation;
import elementsmc.common.elements.PodestRecipe;
import elementsmc.common.item.ItemTablet;
import elementsmc.common.util.Coords4;
import elementsmc.common.util.GenericHelper;

public class TilePodest extends TileInventory implements ISidedInventory
{

	//Slots: 0 - 6 for sides, 7 for tablet, 8 for output

	public static final int SLOT_TABLET = 7;
	public static final int SLOT_OUTPUT = 8;
	public static final int TICKS_PER_OPERATION = 120;

	public int producingTime = 0;

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public int getSizeInventory()
	{
		return 9;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
	{
		if(slot == 7)
		{
			return item.getItem() instanceof ItemTablet;
		}
		else if(slot < 7)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getRawInventoryName()
	{
		return "container.podest";
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side)
	{
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side)
	{
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		if(side == ForgeDirection.DOWN.ordinal())
		{
			return new int[] {8};
		}
		else if(side == ForgeDirection.UP.ordinal())
		{
			return new int[] {7};
		}
		else
		{
			return new int[] {0, 1, 2, 3, 4, 5, 6};
		}
	}

	public void updateEntity()
	{
		if(this.getStackInSlot(7) == null)
		{
			this.producingTime = 0;
			for(int i = 0; i < this.getSizeInventory(); i++)
			{
				if(this.getStackInSlot(i) != null)
				{
					GenericHelper.dropItem(worldObj, xCoord, yCoord, zCoord, this.getStackInSlot(i));
				}
			}
		}
		else if(this.getStackInSlot(8) == null)
		{
			PodestRecipe rec = PodestRecipe.getMatching(this);
		
			if(rec != null)
			{
				if(this.worldObj.isRemote && !Animation.hasAnimation(Coords4.fromTileEntity(this), "rotate") )
				{
					Animation.addAnimation(new Animation((TICKS_PER_OPERATION - producingTime) * 50).setRotation(0, 10, 0), Coords4.fromTileEntity(this), "rotate");
				}
				this.producingTime++;
				if(this.producingTime >= TICKS_PER_OPERATION)
				{
					this.producingTime = 0;
					if(rec.blockToSet != null)
					{
						for(int i = 0; i < this.getSizeInventory(); i++)
						{
							this.setInventorySlotContents(i, null);
						}
						worldObj.setBlock(xCoord, yCoord, zCoord, rec.blockToSet, rec.metaToSet, 3);
						worldObj.setTileEntity(xCoord, yCoord, zCoord, rec.blockToSet.createTileEntity(worldObj, rec.metaToSet));
						rec.blockToSet.onPostBlockPlaced(worldObj, xCoord, yCoord, zCoord, rec.metaToSet);
					}
					else if(rec.result != null)
					{
						for(int i = 0; i < 7; i++)
						{
							this.setInventorySlotContents(i, null);
						}
						this.setInventorySlotContents(8, rec.result.copy());
						this.markDirty();
					}
				}
			}
			else
			{
				this.producingTime = 0;
			}
		}
	}

	public Packet getDescriptionPacket()
	{	
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
	}

	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		NBTTagList nbttaglist = packet.func_148857_g().getTagList("Items", 10);
		this.items = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.items.length)
			{
				this.items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		this.producingTime = packet.func_148857_g().getInteger("producingTime");
	}

	public boolean hasInputItems()
	{
		return this.getStackInSlot(0) != null || this.getStackInSlot(1) != null || this.getStackInSlot(2) != null || this.getStackInSlot(3) != null || this.getStackInSlot(4) != null || this.getStackInSlot(5) != null || this.getStackInSlot(6) != null;
	}

	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("producingTime", this.producingTime);
	}

	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		if(tag.hasKey("producingTime"))
		{
			this.producingTime = tag.getInteger("producingTime");
		}
	}

}

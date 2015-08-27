package elementsmc.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class TileInventory extends TileEntity implements IInventory
{
	public ItemStack[] items = new ItemStack[this.getSizeInventory()];
	public String customName;
	
	public ItemStack getStackInSlot(int var1)
	{
		if(items.length > var1)
    	{
			return items[var1];
    	}
		else return null;
	}

	public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.items[par1] != null)
        {
            ItemStack itemstack;

            if (this.items[par1].stackSize <= par2)
            {
                itemstack = this.items[par1];
                this.items[par1] = null;
                this.markDirty();
                this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return itemstack;
            }
            else
            {
                itemstack = this.items[par1].splitStack(par2);

                if (this.items[par1].stackSize == 0)
                {
                    this.items[par1] = null;
                }

                this.markDirty();
                this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int par1)
    {
    	 if (this.items[par1] != null)
         {
             ItemStack itemstack = this.items[par1];
             this.items[par1] = null;
             return itemstack;
         }
         else
         {
             return null;
         }
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.items[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
    	return true;
    }
    
	public void openInventory()
	{
		
	}

    public void closeInventory()
    {
    	
    }
    
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.items = new ItemStack[this.getSizeInventory()];

        if (tag.hasKey("CustomName", 8))
        {
            this.customName = tag.getString("CustomName");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.items.length)
            {
                this.items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }
    
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.items.length; ++i)
        {
            if (this.items[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.items[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            tag.setString("CustomName", this.customName);
        }
    }
    
    public abstract String getRawInventoryName();
    
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : this.getRawInventoryName();
    }

    public boolean hasCustomInventoryName()
    {
        return this.customName != null && this.customName.length() > 0;
    }
}

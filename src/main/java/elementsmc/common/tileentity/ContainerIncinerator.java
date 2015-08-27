package elementsmc.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elementsmc.common.util.SlotItemValid;

public class ContainerIncinerator extends Container
{

	public TileIncinerator tileEntity;
	private int oldBurnTime;
	public int clientBurnTime = 0;
	public static final int BURN_TIME_ID = 0;
	
	public ContainerIncinerator(IInventory player, TileIncinerator incinerator)
	{
		this.tileEntity = incinerator;
		int i;
		int j;
		
		this.addSlotToContainer(new SlotItemValid(incinerator, 0, 80, 27));
		
		
		for(i = 0; i < 3; ++i)
		{
			for(j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer arg0)
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if(par1 == BURN_TIME_ID)
		{
			this.clientBurnTime = par2;
		}
	}
	
	public void addCraftingToCrafters(ICrafting craft)
	{
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, BURN_TIME_ID, tileEntity.burnTime);
	}
	
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			if(tileEntity.burnTime != oldBurnTime)
			{
				icrafting.sendProgressBarUpdate(this, BURN_TIME_ID, tileEntity.burnTime);
			}
		}
		this.oldBurnTime = tileEntity.burnTime;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
		/*
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < 1)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 9, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
        */
		return null;
    }
	
}

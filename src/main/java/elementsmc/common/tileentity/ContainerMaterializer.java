package elementsmc.common.tileentity;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elementsmc.common.elements.Element;
import elementsmc.common.elements.ElementValue;
import elementsmc.common.util.ItemLocalizedComparator;
import elementsmc.common.util.SlotItemValid;

public class ContainerMaterializer extends Container
{

	public TileMaterializer tile;
	private int oldProducingTime;
	public int clientProducingTime;
	private ItemStack oldProducingItem;
	public ItemStack clientProducingItem;
	private int[] oldEssences = new int[Element.values().length];
	public int[] clientEssences = new int[Element.values().length];
	public ArrayList<ItemStack> clientProducables = new ArrayList<ItemStack>();
	
	public static final int PRODUCING_TIME = 0;
	public static final int PRODUCING_ITEM_ID = 1;
	public static final int PRODUCING_ITEM_META = 2;
	public static final int ESSENCES = 3;

	public ContainerMaterializer(InventoryPlayer player, TileMaterializer tile)
	{
		this.tile = tile;
		int i;
		int j;

		this.addSlotToContainer(new SlotItemValid(tile, 0, 152, 46));


		for(i = 0; i < 3; ++i)
		{
			for(j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
			}
		}

		for(i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 165));
		}
	}
	
	public static ArrayList<ItemStack> getProducables(int[] essences)
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
	//	System.out.println();
		for(ElementValue val : ElementValue.valueMap.values())
		{
			if(val.canBeMade)
			{
				ElementValue val2 = val.clone().multiplyWith(2.5f);
				boolean flag = true;
				for(Element e : Element.values())
				{
					if(essences[e.ordinal()] < val2.getElement(e))
					{
					//	System.out.println(val2.toString() + "<>" + e.elementName + "; ");
						flag = false;
						break;
					}
				}
				if(flag)
				{
					list.add(val2.stack.copy());

				}
			}
		}
		Collections.sort(list, ItemLocalizedComparator.INSTANCE);
		return list;
	}

	@Override
	public boolean canInteractWith(EntityPlayer arg0)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if(par1 == PRODUCING_TIME)
		{
			this.clientProducingTime = par2;
		}
		else if(par1 == PRODUCING_ITEM_ID)
		{
			if(par2 == 0)
			{
				this.clientProducingItem = null;
			}
			else
			{
				this.clientProducingItem = new ItemStack(Item.getItemById(par2));
			}
		}
		else if(par1 == PRODUCING_ITEM_META && this.clientProducingItem != null)
		{
			this.clientProducingItem.setItemDamage(par2);
		}
		else if(par1 >= ESSENCES)
		{
			this.clientEssences[par1 - ESSENCES] = par2;
		}
		this.clientProducables = getProducables(this.clientEssences);
		
	}
	
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			ICrafting craft = (ICrafting) this.crafters.get(i);
			if(this.tile.producingTime != this.oldProducingTime)
			{
				craft.sendProgressBarUpdate(this, PRODUCING_TIME, this.tile.producingTime);
			}
			if(this.tile.producingItem == null && this.oldProducingItem != null)
			{
				craft.sendProgressBarUpdate(this, PRODUCING_ITEM_ID, 0);
			}
			else if(this.tile.producingItem != null && (this.oldProducingItem == null || this.tile.producingItem.isItemEqual(this.oldProducingItem)))
			{
				craft.sendProgressBarUpdate(this, PRODUCING_ITEM_ID, Item.getIdFromItem(this.tile.producingItem.getItem()));
				craft.sendProgressBarUpdate(this, PRODUCING_ITEM_META, this.tile.producingItem.getItemDamage());
			}
			if(this.tile.getNetwork() != null)
			{
				for(Element e : Element.values())
				{
					if(this.tile.getNetwork().getEssenceCount(e) != oldEssences[e.ordinal()])
					{
						craft.sendProgressBarUpdate(this, e.ordinal() + ESSENCES, this.tile.getNetwork().getEssenceCount(e));
					}
				}
			}
		}
		this.oldProducingTime = this.tile.producingTime;
		if(this.tile.producingItem == null)
		{
			this.oldProducingItem = null;
		}
		else
		{
			this.oldProducingItem = this.tile.producingItem.copy();
		}
		if(this.tile.getNetwork() != null)
		{
			for(Element e : Element.values())
			{
				this.oldEssences[e.ordinal()] = this.tile.getNetwork().getEssenceCount(e);	
			}
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
		return null;
    }
	
	public void addCraftingToCrafters(ICrafting craft)
	{
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, PRODUCING_TIME, this.tile.producingTime);
		if(this.tile.producingItem == null)
		{
			craft.sendProgressBarUpdate(this, PRODUCING_ITEM_ID, 0);
		}
		else
		{
			craft.sendProgressBarUpdate(this, PRODUCING_ITEM_ID, Item.getIdFromItem(this.tile.producingItem.getItem()));
			craft.sendProgressBarUpdate(this, PRODUCING_ITEM_META, this.tile.producingItem.getItemDamage());
		}
	}
	
	

}

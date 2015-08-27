package elementsmc.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elementsmc.common.elements.Element;

public class ContainerElementTable extends Container
{

	public TileElementTable table;
	public int[] essences = new int[Element.values().length];
	public int[] essencesClient = new int[Element.values().length];

	public ContainerElementTable(TileElementTable table)
	{
		this.table = table;
	}

	@Override
	public boolean canInteractWith(EntityPlayer arg0)
	{
		return true;
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		if(this.table != null && this.table.getNetwork() != null)
		{
			for(Element e : Element.values())
			{
				par1ICrafting.sendProgressBarUpdate(this, e.ordinal(), this.table.getNetwork().getEssenceCount(e));
			}

		}
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		this.essencesClient[par1] = par2;
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(this.table != null && this.table.getNetwork() != null)
		{
			for(int i = 0; i < this.crafters.size(); ++i)
			{
				ICrafting icrafting = (ICrafting) this.crafters.get(i);
				for(Element e : Element.values())
				{
					if(essences[e.ordinal()] != this.table.getNetwork().getEssenceCount(e))
					{
						icrafting.sendProgressBarUpdate(this, e.ordinal(), this.table.getNetwork().getEssenceCount(e));
					}
				}
			}
			for(Element e : Element.values())
			{
				essences[e.ordinal()] = this.table.getNetwork().getEssenceCount(e);
			}
		}
	}
	
	

}

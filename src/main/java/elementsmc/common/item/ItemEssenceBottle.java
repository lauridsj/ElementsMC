package elementsmc.common.item;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemEssenceBottle extends Item
{

	public static ArrayList<ItemEssenceBottle> bottleList = new ArrayList<ItemEssenceBottle>();
	public static boolean rayTracing = false;
	
	public Block placedEssence;
	public int placedEssenceMeta;
	public Block placeIn;
	public int placeInMeta;

	public ItemEssenceBottle(Block placedEssence, int placedEssenceMeta, Block placeIn, int placeInMeta)
	{
		this.placedEssence = placedEssence;
		this.placedEssenceMeta = placedEssenceMeta;
		this.placeIn = placeIn;
		this.placeInMeta = placeInMeta;
		this.setContainerItem(Items.glass_bottle);
		bottleList.add(this);
	}

	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float partX, float partY, float partZ)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		int x1 = x + dir.offsetX;
		int y1 = y + dir.offsetY;
		int z1 = z + dir.offsetZ;
		if(world.getBlock(x1, y1, z1) == placeIn && world.getBlockMetadata(x1, y1, z1) == placeInMeta)
		{
			world.setBlock(x1, y1, z1, placedEssence, placedEssenceMeta, 3);
			if(!player.capabilities.isCreativeMode)
			{
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
				ItemStack stack = new ItemStack(Items.glass_bottle, 1, 0);
				if(!player.inventory.addItemStackToInventory(stack))
				{
					player.dropPlayerItemWithRandomChoice(stack, false);
				}
			}
		}
		return false;
	}


}

package elementsmc.common.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import elementsmc.common.tileentity.TilePodest;
import elementsmc.common.util.ItemUnlocalizedComparator;

public class PodestRecipe
{

	public static final ArrayList<PodestRecipe> recipeList = new ArrayList<PodestRecipe>();

	public List<ItemStack> ingredients;
	public Block podest;
	public ItemStack tablet;
	public ItemStack result;
	public Block blockToSet;
	public int metaToSet;

	public PodestRecipe(Block podest, ItemStack tablet, ItemStack result, ItemStack... ingredients)
	{
		this.ingredients = Arrays.asList(ingredients);
		this.podest = podest;
		this.tablet = tablet;
		this.result = result;
	}

	public PodestRecipe(Block podest, ItemStack tablet, Block blockToSet, int metaToSet, ItemStack... ingredients)
	{
		this.ingredients = Arrays.asList(ingredients);
		this.podest = podest;
		this.tablet = tablet;
		this.blockToSet = blockToSet;
		this.metaToSet = metaToSet;
	}

	@Override
	public String toString()
	{
		return "PodestRecipe [ingredients=" + ingredients + ", podest=" + podest + ", tablet=" + tablet + ", result=" + result + ", blockToSet="
				+ blockToSet + ", metaToSet=" + metaToSet + "]";
	}

	public boolean matches(TilePodest tile)
	{
		if(tile.blockType != this.podest) return false;
		if(tile.getStackInSlot(7) == null || (tablet != null && !tile.getStackInSlot(7).isItemEqual(tablet))) return false;
		Collections.sort(this.ingredients, ItemUnlocalizedComparator.INSTANCE);
		List<ItemStack> list = new ArrayList<ItemStack>();
		for(int i = 0; i < 7; i++)
		{
			if(tile.getStackInSlot(i) != null)
			{
				list.add(tile.getStackInSlot(i));
			}
		}
		if(list.isEmpty() || list.size() != ingredients.size()) return false;
		Collections.sort(list, ItemUnlocalizedComparator.INSTANCE);
		for(int i = 0; i < list.size(); i++)
		{
			if(!list.get(i).isItemEqual(ingredients.get(i)))
			{
				return false;
			}
		}
		return true;
	}

	public static PodestRecipe getMatching(TilePodest tile)
	{
		for(PodestRecipe rec : recipeList)
		{
			if(rec.matches(tile)) return rec;
		}
		return null;
	}


}

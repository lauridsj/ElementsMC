package elementsmc.common.main;

import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import elementsmc.common.elements.ElementNetwork;
import elementsmc.common.item.ItemEssenceBottle;
import elementsmc.common.util.GenericHelper;

public class EMEventHandler
{

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event)
	{
		if(!event.world.isRemote && event.world.provider.dimensionId == 0)
		{
			File f = new File(((SaveHandler)DimensionManager.getWorld(0).getSaveHandler()).getWorldDirectory(), "elementsmc.dat");
			NBTTagCompound tag = new NBTTagCompound();
			NBTTagCompound network = new NBTTagCompound();
			ElementNetwork.writeToNBT(network);
			tag.setTag("elementNetwork", network);
			try
			{
				CompressedStreamTools.write(tag, f);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if(!event.world.isRemote && event.world.provider.dimensionId == 0)
		{
			File f = new File(((SaveHandler)DimensionManager.getWorld(0).getSaveHandler()).getWorldDirectory(), "elementsmc.dat");
			if(f.exists() && !f.isDirectory())
			{
				try
				{
					NBTTagCompound tag = CompressedStreamTools.read(f);
					NBTTagCompound network = tag.getCompoundTag("elementNetwork");
					ElementNetwork.readFromNBT(network);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		if(!event.world.isRemote && event.world.provider.dimensionId == 0)
		{
			ElementNetwork.reset();
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
		{
			if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == Items.glass_bottle)
			{
				ItemEssenceBottle.rayTracing = true;
				MovingObjectPosition pos = GenericHelper.playerRayTrace(event.entity.worldObj, event.entityPlayer, false);
				ItemEssenceBottle.rayTracing = false;
				if(pos != null && pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
				{
					Block block = event.entity.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
					int meta = event.entity.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
					for(ItemEssenceBottle item : ItemEssenceBottle.bottleList)
					{
						if(item.placedEssence == block && item.placedEssenceMeta == meta)
						{
							event.entity.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, item.placeIn, item.placeInMeta, 3);
							if(!event.entityPlayer.capabilities.isCreativeMode)		
							{
								event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
								ItemStack stack = new ItemStack(item, 1, 0);
								if(!event.entityPlayer.inventory.addItemStackToInventory(stack))
								{
									event.entityPlayer.dropPlayerItemWithRandomChoice(stack, false);
								}
							}
							event.entityPlayer.swingItem();
							event.useItem = Event.Result.DENY;
							break;
						}
					}
				}
			}
		}
	}

	
	
}

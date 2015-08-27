package elementsmc.common.dungeon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.HashBiMap;

import cpw.mods.fml.common.FMLLog;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.util.GenericHelper;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;

public class CommandStructure extends CommandBase {

	public static HashMap<String, ChunkCoordinates> pos1Map = new HashMap<String, ChunkCoordinates>();
	public static HashMap<String, ChunkCoordinates> pos2Map = new HashMap<String, ChunkCoordinates>();
	public static HashBiMap<EnumBlockReplacement, StructureBlock> blockReplacements = HashBiMap.create();
	


	@Override
	public String getCommandName() {
		return "structure";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "commands.structure.usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 0) throw new WrongUsageException("commands.structure.wrongUsage", new Object[0]);

		if(sender instanceof EntityPlayerMP)
		{
			EntityPlayerMP emp = (EntityPlayerMP) sender;
			if(args[0].equalsIgnoreCase("pos1"))
			{


				MovingObjectPosition mop = GenericHelper.playerRayTrace(emp.worldObj, emp, false);
				ChunkCoordinates cc =  new ChunkCoordinates(mop.blockX, mop.blockY, mop.blockZ);
				pos1Map.put(emp.getCommandSenderName(), cc);
				sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Set position 1 to " + cc.toString()));
				return;
			}

			else if(args[0].equalsIgnoreCase("pos2"))
			{

				MovingObjectPosition mop = GenericHelper.playerRayTrace(emp.worldObj, emp, false);
				ChunkCoordinates cc =  new ChunkCoordinates(mop.blockX, mop.blockY, mop.blockZ);
				pos2Map.put(emp.getCommandSenderName(), cc);
				sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Set position 2 to " + cc.toString()));
				return;

			}

			else if(args.length > 1)
			{
				if(args[0].equalsIgnoreCase("save"))
				{
					File folder = new File("SavedStructures");
					folder.mkdirs();
					File f = new File(folder, args[1]);

					ChunkCoordinates cc1 = pos1Map.get(emp.getCommandSenderName());
					ChunkCoordinates cc2 = pos2Map.get(emp.getCommandSenderName());
					Structure struct = Structure.createFromWorld(emp.getEntityWorld(), cc1.posX, cc1.posY, cc1.posZ, cc2.posX, cc2.posY, cc2.posZ, CommandStructure.blockReplacements);
					try {
						FileOutputStream fos = new FileOutputStream(f);
						DataOutputStream dos = new DataOutputStream(fos);
						struct.serialize(dos);
						dos.close();
						fos.close();
						sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Successfully saved structure as " + args[1] + "!"));
						return;
					} catch (IOException e) {
						sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Failed to save structure: " + e.getMessage()));
					}
				}
				
				else if(args[0].equalsIgnoreCase("load"))
				{
					File f = new File("SavedStructures", args[1]);
					
					if(f.exists() && !f.isDirectory())
					{
						MovingObjectPosition mop = GenericHelper.playerRayTrace(emp.worldObj, emp, false);
						ChunkCoordinates cc =  new ChunkCoordinates(mop.blockX, mop.blockY, mop.blockZ);
						
						if(mop != null)
						{
							try {
								FileInputStream fis = new FileInputStream(f);
								DataInputStream dis = new DataInputStream(fis);
								Structure struct = Structure.deserialize(dis);
								struct.generate(emp.getEntityWorld(), cc.posX, cc.posY, cc.posZ, CommandStructure.blockReplacements);
								sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Successfully generated structure " + args[1] + " at " + cc.toString() + "!"));
								return;
								} catch(IOException e) {
									sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Failed to load structure: " + e.getMessage()));
								}
						}
						
					}
					else
					{
						sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Could not find structure: " + args[1]));	
					}
				}
				
				else if(args.length > 2)
				{
					if(args[0].equalsIgnoreCase("replacement"))
					{
						EnumBlockReplacement rep = EnumBlockReplacement.valueOf(args[1].toUpperCase());
						Block block = Block.getBlockFromName(args[2]);
						int meta = 0;
						if(args.length > 3)
						{
							try {
								meta = Integer.parseInt(args[3]);
							} catch(NumberFormatException e) {
								sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] " + args[3] + " is no valid number!"));
								return;
							}
						}
						if(rep != null && block != null)
						{
							blockReplacements.put(rep, new StructureBlock(block, meta));
							String blockName = Block.blockRegistry.getNameForObject(block);
							sender.addChatMessage(new ChatComponentText("[Elements of Minecraft] Updated block replacement: " + rep.name + " -> " + blockName + ":" + meta));		
							ElementsMC.config.get(Structure.CATEGORY, rep.name, blockName).set(blockName);
							ElementsMC.config.get(Structure.CATEGORY, rep.name + "_meta", meta).set(meta);
							ElementsMC.config.save();
							return;
						}
					}
				}
			}
			
		}

	}

	public static void initialize() {
		for(EnumBlockReplacement sb : EnumBlockReplacement.values()) {
			String defaultBlockName = Block.blockRegistry.getNameForObject(sb.defaultBlock.block);
			Block block	= Block.getBlockFromName(ElementsMC.config.get(Structure.CATEGORY, sb.name, defaultBlockName).getString());
			int meta = ElementsMC.config.get(Structure.CATEGORY, sb.name + "_meta", sb.defaultBlock.meta).getInt();
			blockReplacements.put(sb, new StructureBlock(block, meta));
			FMLLog.info("[ElementsMC] Loaded structure block replacement: " + sb.name + " -> " + Block.blockRegistry.getNameForObject(block) + ":" + meta);
		}
	}
	
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
		if(args.length < 2)
		{
			return CommandBase.getListOfStringsMatchingLastWord(args, "pos1", "pos2", "load", "save", "replacement");
		}
		else if(args.length < 3)
		{
			if(args[0].equalsIgnoreCase("load"))
			{
				File folder = new File("SavedStructures");
				folder.mkdirs();
				return CommandBase.getListOfStringsMatchingLastWord(args, folder.list());
			}
			else if(args[0].equalsIgnoreCase("replacement"))
			{
				return CommandBase.getListOfStringsMatchingLastWord(args, EnumBlockReplacement.NAMES);
			}
		}
		else if(args.length < 4)
		{
			if(args[0].equalsIgnoreCase("replacement"))
			{
				return CommandBase.getListOfStringsFromIterableMatchingLastWord(args, Block.blockRegistry.getKeys());
			}
		}
		return null;
    }





}

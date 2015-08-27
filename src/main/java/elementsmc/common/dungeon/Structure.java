package elementsmc.common.dungeon;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Structure {

	public static final String CATEGORY = "Structure Block Replacements";
	
	public static HashMap<String, Structure> structureMap = new HashMap<String, Structure>();
	
	public static Structure createFromWorld(IBlockAccess world, int x1, int y1, int z1, int x2, int y2, int z2, BiMap<EnumBlockReplacement, StructureBlock> replacements)
	{
		int minx = Math.min(x1, x2);
		int maxx = Math.max(x1, x2);
		int miny = Math.min(y1, y2);
		int maxy = Math.max(y1, y2);
		int minz = Math.min(z1, z2);
		int maxz = Math.max(z1, z2);
		
		Structure struct = new Structure();
		struct.xSize = maxx - minx + 1;
		struct.ySize = maxy - miny + 1;
		struct.zSize = maxz - minz + 1;
		
		for(int i = minx; i <= maxx; i++)
		{
			for(int j = miny; j <= maxy; j++)
			{
				for(int k = minz; k <= maxz; k++)
				{
					Block block = world.getBlock(i, j, k);
					int meta = world.getBlockMetadata(i, j, k);
					if(block != null && block != Blocks.air)
					{
						StructureBlock cb = new StructureBlock(block, meta);
						TileEntity te = world.getTileEntity(i, j, k);
						if(replacements.containsValue(cb))
						{
							StructureBlock sb = new StructureBlock(replacements.inverse().get(cb));	
							if(sb.replacement.metaSensitive)
							{
								sb.meta = meta;
							}
							if(te != null && sb.replacement.tileSensitive)
							{
								sb.setTileEntity(te);
							}
							struct.blocks.put(new ChunkCoordinates(i - minx, j - miny, k - minz), sb);
						}
						else
						{
							if(te != null)
							{
								cb.setTileEntity(te);
							}
							struct.blocks.put(new ChunkCoordinates(i - minx, j - miny, k - minz), cb);
						}
					}
				}
			}
		}
		return struct;
	}
	
	public void serialize(DataOutput dos) throws IOException
	{
		dos.writeInt(xSize);
		dos.writeInt(ySize);
		dos.writeInt(zSize);
		HashMap<ChunkCoordinates, NBTTagCompound> tilemap = new HashMap<ChunkCoordinates, NBTTagCompound>();
		for(int i = 0; i < xSize; i++)
		{
			for(int j = 0; j < ySize; j++)
			{
				for(int k = 0; k < zSize; k++)
				{
					ChunkCoordinates cc = new ChunkCoordinates(i, j, k);
					StructureBlock sb = blocks.get(cc);
					if(sb == null)
					{
						dos.writeInt(0);
						dos.writeByte(0);
					}
					else
					{
						dos.writeInt((short) sb.getID());
						dos.writeByte(sb.meta);
						if(sb.tileEntityTag != null)
						{
							tilemap.put(cc, sb.tileEntityTag);
						}
					}
				}
			}
		}
		dos.writeInt(tilemap.size());
		for(ChunkCoordinates cc : tilemap.keySet())
		{
			dos.writeInt(cc.posX);
			dos.writeInt(cc.posY);
			dos.writeInt(cc.posZ);
			CompressedStreamTools.write(tilemap.get(cc), dos);
		}
		
	}
	
	public static Structure deserialize(DataInput dis) throws IOException
	{
		Structure struct = new Structure();
		struct.xSize = dis.readInt();
		struct.ySize = dis.readInt();
		struct.zSize = dis.readInt();
		for(int i = 0; i < struct.xSize; i++)
		{
			for(int j = 0; j < struct.ySize; j++)
			{
				for(int k = 0; k < struct.zSize; k++)
				{
					int id = dis.readInt();
					int meta = (int) dis.readByte();
					if(id != 0)
					{
						struct.blocks.put(new ChunkCoordinates(i, j, k), StructureBlock.fromIDMeta(id, meta));
					}
				}
			}
		}
		int tileAmount = dis.readInt();
		for(int i = 0; i < tileAmount; i++)
		{
			ChunkCoordinates cc = new ChunkCoordinates(dis.readInt(), dis.readInt(), dis.readInt());
			struct.blocks.get(cc).tileEntityTag = CompressedStreamTools.func_152456_a(dis, NBTSizeTracker.field_152451_a);
		}
		return struct;
	}
	
	public void generate(World world, int x, int y, int z, Map<EnumBlockReplacement, StructureBlock> replacements)
	{
		for(ChunkCoordinates cc : blocks.keySet())
		{
			StructureBlock sb = blocks.get(cc);
			
			System.out.println(cc.toString() + " -> " + sb.toString());
			if(sb.block != null)
			{
				world.setBlock(x + cc.posX, y + cc.posY, z + cc.posZ, sb.block, sb.meta, 2);
			}
			else if(sb.replacement != null)
			{
				StructureBlock sb2 = replacements.get(sb.replacement);
				if(sb.replacement.metaSensitive)
				{
					world.setBlock(x + cc.posX, y + cc.posY, z + cc.posZ, sb2.block, sb.meta, 2);
				}
				else
				{
					world.setBlock(x + cc.posX, y + cc.posY, z + cc.posZ, sb2.block, sb2.meta, 2);
				}
			}
			if(sb.tileEntityTag != null)
			{
				world.setTileEntity(x + cc.posX, y + cc.posY, z + cc.posZ, TileEntity.createAndLoadEntity(sb.tileEntityTag));
			}
		}
	}
	
	public int xSize;
	public int ySize;
	public int zSize;
	
	public HashMap<ChunkCoordinates, StructureBlock> blocks = new HashMap<ChunkCoordinates, StructureBlock>();
	
	
	public static Structure getStructure(String name)
	{
		if(!structureMap.containsKey(name))
		{
			try
			{
			InputStream is = Structure.class.getResourceAsStream("/assets/elementsmc/dungeon/" + name);
			DataInputStream dis = new DataInputStream(is);
			structureMap.put(name, deserialize(dis));
			} catch(IOException ex) {
			ex.printStackTrace();
			}
		}
		return structureMap.get(name);
	}
	
	
}

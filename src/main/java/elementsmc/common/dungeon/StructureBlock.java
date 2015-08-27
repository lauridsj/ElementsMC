package elementsmc.common.dungeon;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class StructureBlock {

	public final Block block;
	public final EnumBlockReplacement replacement;
	public int meta;
	public NBTTagCompound tileEntityTag;
	
	public StructureBlock(Block block, int meta, NBTTagCompound tileEntityTag) {
		super();
		this.block = block;
		this.replacement = null;
		this.meta = meta;
		this.tileEntityTag = tileEntityTag;
	}
	
	public StructureBlock(Block block, int meta) {
		this(block, meta, null);
	}
	
	public StructureBlock(Block block) {
		this(block, 0);
	}
	
	public StructureBlock(EnumBlockReplacement block, int meta) {
		this.block = null;
		this.replacement = block;
		this.meta = meta;
	}
	
	public StructureBlock(EnumBlockReplacement block) {
		this.block = null;
		this.replacement = block;
		this.meta = 0;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : Block.getIdFromBlock(block));
		result = prime * result + meta;
		result = prime * result
				+ ((replacement == null) ? 0 : replacement.hashCode());
		result = prime * result
				+ ((tileEntityTag == null) ? 0 : tileEntityTag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StructureBlock other = (StructureBlock) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (meta != other.meta)
			return false;
		if (replacement != other.replacement)
			return false;
		if (tileEntityTag == null) {
			if (other.tileEntityTag != null)
				return false;
		} else if (!tileEntityTag.equals(other.tileEntityTag))
			return false;
		return true;
	}

	public void setTileEntity(TileEntity te)
	{
		this.tileEntityTag = new NBTTagCompound();
		te.writeToNBT(tileEntityTag);
	}

	public int getID()
	{
		if(block != null)
		{
			return Block.getIdFromBlock(block);
		}
		else
		{
			return replacement.id;
		}
	}
	
	@Override
	public String toString() {
		return "StructureBlock [block=" + block + ", replacement="
				+ replacement + ", meta=" + meta + ", tileEntityTag="
				+ tileEntityTag + "]";
	}

	public static StructureBlock fromIDMeta(int id, int meta)
	{
		if(id > 0)
		{
			return new StructureBlock(Block.getBlockById(id), meta);
		}
		else
		{
			return new StructureBlock(EnumBlockReplacement.getByID(id), meta);
		}
	}
	
}

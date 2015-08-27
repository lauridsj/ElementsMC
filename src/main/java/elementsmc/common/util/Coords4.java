package elementsmc.common.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import elementsmc.common.elements.INetworkPart;

public class Coords4
{
	
	public int x;
	public int y;
	public int z;
	public int dim;
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Coords4)
		{
			Coords4 c = (Coords4) obj;
			return c.x == this.x && c.y == this.y && c.z == this.z && c.dim == this.dim;
		}
		else return false;
	}
	
	public Coords4(int x, int y, int z, int dim)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim = dim;
	}

	public int hashCode()
    {
        return this.x + this.z << 8 + this.y << 16 + this.dim << 24;
    }
	
	public String toString()
    {
        return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", dim=" + this.dim + '}';
    }
	
	public static Coords4 fromNBT(NBTTagCompound tag)
	{
		return new Coords4(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), tag.getInteger("dim"));
	}
	
	public static NBTTagCompound toNBT(Coords4 c)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", c.x);
		tag.setInteger("y", c.y);
		tag.setInteger("z", c.z);
		tag.setInteger("dim", c.dim);
		return tag;
	}
	
	public static Coords4 fromNetworkPart(INetworkPart part)
	{
		return new Coords4(part.getX(), part.getY(), part.getZ(), part.getWorld().provider.dimensionId);
	}

	public static Coords4 fromTileEntity(TileEntity tile)
	{
		return new Coords4(tile.xCoord, tile.yCoord, tile.zCoord, tile.getWorldObj().provider.dimensionId);
	}
	
}

package elementsmc.common.util;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import elementsmc.common.elements.Element;
import elementsmc.common.elements.INetworkPart;

public class GenericHelper
{

	public static final Random RNG = new Random();
	
	public static int getColorFromRGB(int r, int g, int b)
	{
		int var1 = r << 16;
		int var2 = g << 8;
		int var3 = b;
		return var1 | var2 | var3;
	}

	public static double getDistance(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		int dx = Math.abs(x1 - x2);
		int dy = Math.abs(y1 - y2);
		int dz = Math.abs(z1 - z2);
		return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
	}

	public static double getDistanceSquared(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double dx = Math.abs(x1 - x2);
		double dy = Math.abs(y1 - y2);
		double dz = Math.abs(z1 - z2);
		return (dx * dx) + (dy * dy) + (dz * dz);
	}
	
	public static void dropItem(World world, double x, double y, double z, ItemStack itemstack)
	{
		if (itemstack != null)
		{
			float f = GenericHelper.RNG.nextFloat() * 0.8F + 0.1F;
			float f1 = GenericHelper.RNG.nextFloat() * 0.8F + 0.1F;
			float f2 = GenericHelper.RNG.nextFloat() * 0.8F + 0.1F;

			while (itemstack.stackSize > 0)
			{
				int j1 = GenericHelper.RNG.nextInt(21) + 10;

				if (j1 > itemstack.stackSize)
				{
					j1 = itemstack.stackSize;
				}

				itemstack.stackSize -= j1;
				EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

				if (itemstack.hasTagCompound())
				{
					entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
				}

				float f3 = 0.05F;
				entityitem.motionX = (double)((float)GenericHelper.RNG.nextGaussian() * f3);
				entityitem.motionY = (double)((float)GenericHelper.RNG.nextGaussian() * f3 + 0.2F);
				entityitem.motionZ = (double)((float)GenericHelper.RNG.nextGaussian() * f3);
				world.spawnEntityInWorld(entityitem);
			}
		}
	}
	
	private static float sign(float x1, float y1, float x2, float y2, float x3, float y3)
	{
		return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
	}
	
	public static boolean pointInTriangle(float px, float py, float tx1, float ty1, float tx2, float ty2, float tx3, float ty3)
	{
		boolean b1, b2, b3;
		
		b1 = sign(px, py, tx1, ty1, tx2, ty2) < 0f;
		b2 = sign(px, py, tx2, ty2, tx3, ty3) < 0f;
		b3 = sign(px, py, tx3, ty3, tx1, ty1) < 0f;
		
		return ((b1 == b2) && (b2 == b3));
	}
	
	public static MovingObjectPosition playerRayTrace(World world, EntityPlayer player, boolean par3)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return world.func_147447_a(vec3, vec31, par3, !par3, false);
    }
	
	public static int getTopSolidBlock(World world, int x, int z)
	{
		int y = 254;
		while(!world.getBlock(x, y, z).getMaterial().isSolid())
		{
			y--;
		}
		return y + 1;
	}
	
	public static void sendChatToPlayer(EntityPlayer player, String msg, ChatStyle style)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playermp = (EntityPlayerMP) player;
			playermp.playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentText(msg).setChatStyle(style)));
		}
	}
	
	public static String printEssenceCounts(int[] essences)
	{
		String str = "";
		for(int i = 0; i < essences.length; i++)
		{
			if(Element.values().length > i)
			{
				str += Element.values()[i].getLocalizedName();
				str += ": ";
				str += essences[i];
				str += "; ";
			}
			else break;
		}
		return str;
	}
	
	public static String printNetworkPart(INetworkPart part)
	{
		return "NetworkPart [class=" + part.getClass().getSimpleName() + "; x=" + part.getX() + "; y=" + part.getY() + "; z=" + part.getZ() + "; network=" + (part.getNetwork() == null ? "null" : part.getNetwork().id) + "]";
	}
	
	public static int getNextFreeBiomeID()
	{
		BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();
		for(int i = 0; i < biomes.length; i++)
		{
			if(biomes[i] == null) return i;
		}
		throw new RuntimeException("Biome array is full!");
	}

}

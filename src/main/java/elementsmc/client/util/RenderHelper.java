package elementsmc.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class RenderHelper
{

	public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float width, int red, int green, int blue, int alpha)
	{

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(width);
		// GL11.glScalef(1f / (float) Display.getWidth(), 1f / (float)
		// Display.getHeight(), 0f);
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_LINES);
		tess.setColorRGBA(red, green, blue, alpha);
		tess.addVertex(x1, y1, z1);
		tess.addVertex(x2, y2, z2);
		tess.draw();
	}

	public static void drawTriangle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, int red,
			int green, int blue, int alpha)
	{

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
		// GL11.glScalef(1f / (float) Display.getWidth(), 1f / (float)
		// Display.getHeight(), 0f);
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_TRIANGLES);
		tess.setColorRGBA(red, green, blue, alpha);

		tess.addVertex(x1, y1, z1);
		tess.addVertex(x2, y2, z2);
		tess.addVertex(x3, y3, z3);
		tess.draw();
	}
	
	
	public static void drawTexturedQuad(double x1, double y1, double x2, double y2, double z, ResourceLocation texture, double u, double v, double width, double height)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		Tessellator tess = Tessellator.instance;
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		tess.startDrawingQuads();
		tess.addVertexWithUV(x1, y2, z, u, v + height);
		tess.addVertexWithUV(x2, y2, z, u + width, v + height);
		tess.addVertexWithUV(x2, y1, z, u + width, v);
		tess.addVertexWithUV(x1, y1, z, u, v);	
		tess.draw();

	}
	
	public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float zLevel, int texWidth, int texHeight)
    {
        float f = 1f / (float) texWidth;
        float f1 = 1f / (float) texHeight;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)zLevel, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)zLevel, (double)((float)(u + width) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
    }
	
	public static void drawLyingItem(ItemStack item, World world, double x, double y, double z, float scale)
	{
		GL11.glPushMatrix();
		ItemStack stack = item.copy();
		stack.stackSize = 1;
		EntityItem eitem = new EntityItem(world, 0d, 0d, 0d, stack);
		eitem.hoverStart = 0f;
		
		GL11.glTranslated(x, y, z);
		boolean render3d = false;
		if(stack.getItem() instanceof ItemBlock)
		{
			ItemBlock ib = (ItemBlock) stack.getItem();
			render3d = RenderBlocks.renderItemIn3d(ib.field_150939_a.getRenderType());
		}
		if(!render3d)
		{
			GL11.glTranslated(0d, 0d, -0.24d * scale);
			GL11.glRotatef(90f, 1f, 0f, 0f);
		}
		GL11.glScalef(scale, scale, scale);
		RenderManager.instance.renderEntityWithPosYaw(eitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glPopMatrix();
	}


}

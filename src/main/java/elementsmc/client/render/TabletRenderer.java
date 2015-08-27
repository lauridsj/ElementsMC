package elementsmc.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;

import org.lwjgl.opengl.GL11;

import elementsmc.common.item.ItemTablet;

public class TabletRenderer implements IItemRenderer
{

	public static TabletRenderer instance = new TabletRenderer();;
	public static GroupObject tabletModel;
	private static float texOffset = 0F;

	static
	{
		tabletModel = new GroupObject("tablet", GL11.GL_TRIANGLES);
		float angle = (float) Math.toRadians(51.43D);
		Vec3 vec = Vec3.createVectorHelper(0d, 0.2d, 0.5d);
		Vec3 vec2 = Vec3.createVectorHelper(0d, 0.2d, 0.5d);
		vec2.rotateAroundY(angle);
		tabletModel.faces.add(createFace(
				0f, 0.6255f, 0f,
				(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
				0.5f, 0.5f,
				(0.5f + (float) vec.xCoord), 0.5f + (float) vec.zCoord,
				(0.5f + (float) vec2.xCoord), 0.5f + (float) vec2.zCoord));
		tabletModel.faces.add(createFace(
				0f, 0.688f, 0f,
				(float) vec.xCoord, 0.688f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
				0.5f, 0.5f,
				(0.5f + (float) vec.xCoord), 0.5f + (float) vec.zCoord,
				(0.5f + (float) vec2.xCoord), 0.5f + (float) vec2.zCoord));
		tabletModel.faces.add(createFace(
				(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
				(float) vec.xCoord, 0.688f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
				0f, 0f,
				0f, 0.0625f,
				0.5f, 0.0625f));
		tabletModel.faces.add(createFace(
				(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
				(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
				0f, 0f,
				0.5f, 0.0625f,
				0.5f, 0f));
		for(int i = 0; i < 6; i++)
		{
			vec.rotateAroundY(angle);
			vec2.rotateAroundY(angle);
			//texOffset += 0.0625f;
			tabletModel.faces.add(createFace(
					0f, 0.6255f, 0f,
					(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
					0.5f, 0.5f,
					(0.5f + (float) vec.xCoord), 0.5f + (float) vec.zCoord,
					(0.5f + (float) vec2.xCoord), 0.5f + (float) vec2.zCoord));
			tabletModel.faces.add(createFace(
					0f, 0.688f, 0f,
					(float) vec.xCoord, 0.688f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
					0.5f, 0.5f,
					(0.5f + (float) vec.xCoord), 0.5f + (float) vec.zCoord,
					(0.5f + (float) vec2.xCoord), 0.5f + (float) vec2.zCoord));
			tabletModel.faces.add(createFace(
					(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
					(float) vec.xCoord, 0.688f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
					0f, 0f,
					0f, 0.0625f,
					0.5f, 0.0625f));
			tabletModel.faces.add(createFace(
					(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
					(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
					0f, 0f,
					0.5f, 0.0625f,
					0.5f, 0f));
		}
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if(item.getItem() instanceof ItemTablet)
		{
			ItemTablet tablet = (ItemTablet) item.getItem();
			Minecraft.getMinecraft().getTextureManager().bindTexture(tablet.getResource(item));
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);
			if(type == ItemRenderType.ENTITY)
			{
				GL11.glTranslatef(0f, -0.625f, 0f);
			}
			else if(type == ItemRenderType.INVENTORY)
			{
				GL11.glRotatef(20f, 1f, 0f, 0f);
				GL11.glRotatef(-20f, 0f, 0f, 1f);
				GL11.glScalef(1.5f, 1.5f, 1.5f);
				GL11.glTranslatef(0.45f, 0.35f, 0.5f);			
			}
			else
			{
				GL11.glTranslatef(0.5f, 0.125f, 0.5f);
			}
			tabletModel.render();
			GL11.glPopMatrix();
		}

	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType arg0, ItemStack arg1, ItemRendererHelper arg2)
	{
		return true;
	}

	private static Face createFace(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float tx1, float ty1,
			float tx2, float ty2, float tx3, float ty3)
	{
		Face f = new Face();
		f.vertices = new Vertex[] { new Vertex(x1, y1, z1), new Vertex(x2, y2, z2), new Vertex(x3, y3, z3) };
		f.textureCoordinates = new TextureCoordinate[] { new TextureCoordinate(tx1 + texOffset, ty1), new TextureCoordinate(tx2 + texOffset, ty2),
				new TextureCoordinate(tx3 + texOffset, ty3) };
		return f;
	}

	private static Face createFace(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4,
			float tx1, float ty1, float tx2, float ty2, float tx3, float ty3, float tx4, float ty4)
	{
		Face f = new Face();
		f.vertices = new Vertex[] { new Vertex(x1, y1, z1), new Vertex(x2, y2, z2), new Vertex(x3, y3, z3), new Vertex(x4, y4, z4) };
		f.textureCoordinates = new TextureCoordinate[] { new TextureCoordinate(tx1 + texOffset, ty1), new TextureCoordinate(tx2 + texOffset, ty2),
				new TextureCoordinate(tx3 + texOffset, ty3), new TextureCoordinate(tx4 + texOffset, ty4) };
		return f;
	}

}

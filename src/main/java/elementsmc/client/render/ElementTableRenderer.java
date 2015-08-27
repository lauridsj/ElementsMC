package elementsmc.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TileElementTable;

public class ElementTableRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{

	private GroupObject modelMk1;
	private GroupObject modelPodest;
	private GroupObject modelMk2;
	private static final ResourceLocation resource = new ResourceLocation("elementsmc:textures/blocks/elementTable.png");
	private static ModelPodest podest = new ModelPodest();
	// private static final ResourceLocation resource2 = new
	// ResourceLocation("elementsmc:textures/models/elementTableMk2.png");
	private float texOffset = 0F;

	public ElementTableRenderer()
	{
		modelMk1 = new GroupObject("elementTable", GL11.GL_TRIANGLES);
		float angle = (float) Math.toRadians(51.43D);
		Vec3 vec = Vec3.createVectorHelper(0d, 0.2d, 0.5d);
		Vec3 vec2 = Vec3.createVectorHelper(0d, 0.2d, 0.5d);
		vec2.rotateAroundY(angle);
		modelMk1.faces.add(this.createFace(
				0f, 0.6255f, 0f,
				(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
				0.5f * 0.0625f, 0.5f,
				(0.5f + (float) vec.xCoord) * 0.0625f, 0.5f + (float) vec.zCoord,
				(0.5f + (float) vec2.xCoord) * 0.0625f, 0.5f + (float) vec2.zCoord));
		modelMk1.faces.add(this.createFace(
				0f, 0.688f, 0f,
				(float) vec.xCoord, 0.688f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
				0.5f * 0.0625f, 0.5f,
				(0.5f + (float) vec.xCoord) * 0.0625f, 0.5f + (float) vec.zCoord,
				(0.5f + (float) vec2.xCoord) * 0.0625f, 0.5f + (float) vec2.zCoord));
		modelMk1.faces.add(this.createFace(
				(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
				(float) vec.xCoord, 0.688f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
				0f, 0f,
				0f, 0.0625f,
				0.03125f, 0.0625f));
		modelMk1.faces.add(this.createFace(
				(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
				(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
				(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
				0f, 0f,
				0.03125f, 0.0625f,
				0.03125f, 0f));
		for(int i = 0; i < 6; i++)
		{
			vec.rotateAroundY(angle);
			vec2.rotateAroundY(angle);
			texOffset += 0.0625f;
			modelMk1.faces.add(this.createFace(
					0f, 0.6255f, 0f,
					(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
					0.5f * 0.0625f, 0.5f,
					(0.5f + (float) vec.xCoord) * 0.0625f, 0.5f + (float) vec.zCoord,
					(0.5f + (float) vec2.xCoord) * 0.0625f, 0.5f + (float) vec2.zCoord));
			modelMk1.faces.add(this.createFace(
					0f, 0.688f, 0f,
					(float) vec.xCoord, 0.688f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
					0.5f * 0.0625f, 0.5f,
					(0.5f + (float) vec.xCoord) * 0.0625f, 0.5f + (float) vec.zCoord,
					(0.5f + (float) vec2.xCoord) * 0.0625f, 0.5f + (float) vec2.zCoord));
			modelMk1.faces.add(this.createFace(
					(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
					(float) vec.xCoord, 0.688f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
					0f, 0f,
					0f, 0.0625f,
					0.03125f, 0.0625f));
			modelMk1.faces.add(this.createFace(
					(float) vec.xCoord, 0.6255f, (float) vec.zCoord,
					(float) vec2.xCoord, 0.688f, (float) vec2.zCoord,
					(float) vec2.xCoord, 0.6255f, (float) vec2.zCoord,
					0f, 0f,
					0.03125f, 0.0625f,
					0.03125f, 0f));
		}
		/*
		 * vec.rotateAroundY(angle); vec2.rotateAroundY(angle); texOffset +=
		 * 0.125f; modelMk1.faces.add(this.createFace(0f, 0.6255f, 0f,
		 * (float)vec.xCoord, 0.6255f, (float)vec.zCoord, (float)vec2.xCoord,
		 * 0.6255f, (float)vec2.zCoord, 0.5f * 0.125f, 0.5f, (0.5f +
		 * (float)vec.xCoord) * 0.125f, 0.5f + (float)vec.zCoord, (0.5f +
		 * (float)vec2.xCoord) * 0.125f, 0.5f + (float)vec2.zCoord));
		 * vec.rotateAroundY(angle); vec2.rotateAroundY(angle); texOffset +=
		 * 0.125f; modelMk1.faces.add(this.createFace(0f, 0.6255f, 0f,
		 * (float)vec.xCoord, 0.6255f, (float)vec.zCoord, (float)vec2.xCoord,
		 * 0.6255f, (float)vec2.zCoord, 0.5f * 0.125f, 0.5f, (0.5f +
		 * (float)vec.xCoord) * 0.125f, 0.5f + (float)vec.zCoord, (0.5f +
		 * (float)vec2.xCoord) * 0.125f, 0.5f + (float)vec2.zCoord));
		 * vec.rotateAroundY(angle); vec2.rotateAroundY(angle); texOffset +=
		 * 0.125f; modelMk1.faces.add(this.createFace(0f, 0.6255f, 0f,
		 * (float)vec.xCoord, 0.6255f, (float)vec.zCoord, (float)vec2.xCoord,
		 * 0.6255f, (float)vec2.zCoord, 0.5f * 0.125f, 0.5f, (0.5f +
		 * (float)vec.xCoord) * 0.125f, 0.5f + (float)vec.zCoord, (0.5f +
		 * (float)vec2.xCoord) * 0.125f, 0.5f + (float)vec2.zCoord));
		 * vec.rotateAroundY(angle); vec2.rotateAroundY(angle); texOffset +=
		 * 0.125f; modelMk1.faces.add(this.createFace(0f, 0.6255f, 0f,
		 * (float)vec.xCoord, 0.6255f, (float)vec.zCoord, (float)vec2.xCoord,
		 * 0.6255f, (float)vec2.zCoord, 0.5f * 0.125f, 0.5f, (0.5f +
		 * (float)vec.xCoord) * 0.125f, 0.5f + (float)vec.zCoord, (0.5f +
		 * (float)vec2.xCoord) * 0.125f, 0.5f + (float)vec2.zCoord));
		 * vec.rotateAroundY(angle); vec2.rotateAroundY(angle); texOffset +=
		 * 0.125f; modelMk1.faces.add(this.createFace(0f, 0.6255f, 0f,
		 * (float)vec.xCoord, 0.6255f, (float)vec.zCoord, (float)vec2.xCoord,
		 * 0.6255f, (float)vec2.zCoord, 0.5f * 0.125f, 0.5f, (0.5f +
		 * (float)vec.xCoord) * 0.125f, 0.5f + (float)vec.zCoord, (0.5f +
		 * (float)vec2.xCoord) * 0.125f, 0.5f + (float)vec2.zCoord));
		 */

		modelPodest = new GroupObject("elementTable2", GL11.GL_QUADS);
		texOffset = 0.9375f;
		modelPodest.faces.add(this.createFace(-0.0625f, 0f, -0.0625f, -0.0625f, 0f, 0.0625f, -0.0625f, 0.5625f, 0.0625f, -0.0625f, 0.5625f, -0.0625f,
				0.4375f * 0.0625f, 0f, 0.5625f * 0.0625f, 0f, 0.5625f * 0.0625f, 0.5625f, 0.4375f * 0.0625f, 0.5625f));
		modelPodest.faces.add(this.createFace(0.0625f, 0f, -0.0625f, 0.0625f, 0f, 0.0625f, 0.0625f, 0.5625f, 0.0625f, 0.0625f, 0.5625f, -0.0625f,
				0.4375f * 0.0625f, 0f, 0.5625f * 0.0625f, 0f, 0.5625f * 0.0625f, 0.5625f, 0.4375f * 0.0625f, 0.5625f));
		modelPodest.faces.add(this.createFace(-0.0625f, 0f, -0.0625f, 0.0625f, 0f, -0.0625f, 0.0625f, 0.5625f, -0.0625f, -0.0625f, 0.5625f, -0.0625f,
				0.4375f * 0.0625f, 0f, 0.5625f * 0.0625f, 0f, 0.5625f * 0.0625f, 0.5625f, 0.4375f * 0.0625f, 0.5625f));
		modelPodest.faces.add(this.createFace(-0.0625f, 0f, 0.0625f, 0.0625f, 0f, 0.0625f, 0.0625f, 0.5625f, 0.0625f, -0.0625f, 0.5625f, 0.0625f,
				0.4375f * 0.0625f, 0f, 0.5625f * 0.0625f, 0f, 0.5625f * 0.0625f, 0.5625f, 0.4375f * 0.0625f, 0.5625f));

		modelPodest.faces.add(this.createFace(-0.0625f, 0f, -0.0625f, 0.0625f, 0f, -0.0625f, 0.0625f, 0f, 0.0625f, -0.0625f, 0f, 0.0625f,
				0.4375f * 0.0625f, 0.4375f, 0.5625f * 0.0625f, 0.4375f, 0.5625f * 0.0625f, 0.5625f, 0.4375f * 0.0625f, 0.5625f));
		modelPodest.faces.add(this.createFace(-0.0625f, 0.5625f, -0.0625f, 0.0625f, 0.5625f, -0.0625f, 0.0625f, 0.5625f, 0.0625f, -0.0625f, 0.5625f,
				0.0625f, 0.4375f * 0.0625f, 0.4375f, 0.5625f * 0.0625f, 0.4375f, 0.5625f * 0.0625f, 0.5625f, 0.4375f * 0.0625f, 0.5625f));

		texOffset = 0f;
		modelMk2 = new GroupObject("elementTable3", GL11.GL_TRIANGLES);
		angle = (float) Math.toRadians(30D);
		vec = Vec3.createVectorHelper(0d, 0.2d, 0.5d);
		vec2 = Vec3.createVectorHelper(0d, 0.2d, 0.5d);
		vec2.rotateAroundY(angle);
		modelMk2.faces.add(this.createFace(0f, 0.6255f, 0f, (float) vec.xCoord, 0.6255f, (float) vec.zCoord, (float) vec2.xCoord, 0.6255f,
				(float) vec2.zCoord, 0.5f * 0.0625f, 0.5f, (0.5f + (float) vec.xCoord) * 0.0625f, 0.5f + (float) vec.zCoord,
				(0.5f + (float) vec2.xCoord) * 0.0625f, 0.5f + (float) vec2.zCoord));
		for(int i = 0; i < 11; i++)
		{
			vec.rotateAroundY(angle);
			vec2.rotateAroundY(angle);
			texOffset += 0.0625f;
			modelMk2.faces.add(this.createFace(0f, 0.6255f, 0f, (float) vec.xCoord, 0.6255f, (float) vec.zCoord, (float) vec2.xCoord, 0.6255f,
					(float) vec2.zCoord, 0.5f * 0.0625f, 0.5f, (0.5f + (float) vec.xCoord) * 0.0625f, 0.5f + (float) vec.zCoord,
					(0.5f + (float) vec2.xCoord) * 0.0625f, 0.5f + (float) vec2.zCoord));
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks)
	{

		if(tileentity instanceof TileElementTable)
		{
			this.bindTexture(resource);
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			int i = (int) ((System.currentTimeMillis() / 10L) % 360L);
			GL11.glTranslatef((float) x + 0.5f, (float) y + (0.0625f * (float) Math.sin(Math.toRadians(i))), (float) z + 0.5f);
			GL11.glRotatef((System.currentTimeMillis() / 20L) % 360L, 0f, 1f, 0f);
			modelMk1.render();
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);

			GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5625f, (float) z + 0.5f);
			float f = 0.1f;
			GL11.glScalef(f, -f, -f);
			//modelPodest.render();
			podest.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
			GL11.glPopMatrix();
		}
		/*
		 * else if(tileentity instanceof TileEntityElementTableMk2) {
		 * this.bindTexture(resource2); GL11.glPushMatrix();
		 * GL11.glDisable(GL11.GL_CULL_FACE); GL11.glDisable(GL11.GL_LIGHTING);
		 * int i = (int) ((System.currentTimeMillis() / 10L) % 360L);
		 * GL11.glTranslatef((float) d0 + 0.5f, (float) d1 + (0.0625f * (float)
		 * Math.sin(Math.toRadians(i))), (float) d2 + 0.5f);
		 * GL11.glRotatef((System.currentTimeMillis() / 20L) % 360L, 0f, 1f,
		 * 0f); modelMk2.render(); GL11.glPopMatrix();
		 * 
		 * GL11.glPushMatrix(); GL11.glDisable(GL11.GL_CULL_FACE);
		 * GL11.glDisable(GL11.GL_LIGHTING);
		 * 
		 * GL11.glTranslatef((float) d0 + 0.5f, (float) d1, (float) d2 + 0.5f);
		 * modelPodest.render(); GL11.glPopMatrix(); }
		 */

	}

	private Face createFace(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float tx1, float ty1,
			float tx2, float ty2, float tx3, float ty3)
	{
		Face f = new Face();
		f.vertices = new Vertex[] { new Vertex(x1, y1, z1), new Vertex(x2, y2, z2), new Vertex(x3, y3, z3) };
		f.textureCoordinates = new TextureCoordinate[] { new TextureCoordinate(tx1 + texOffset, ty1), new TextureCoordinate(tx2 + texOffset, ty2),
				new TextureCoordinate(tx3 + texOffset, ty3) };
		return f;
	}

	private Face createFace(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4,
			float tx1, float ty1, float tx2, float ty2, float tx3, float ty3, float tx4, float ty4)
	{
		Face f = new Face();
		f.vertices = new Vertex[] { new Vertex(x1, y1, z1), new Vertex(x2, y2, z2), new Vertex(x3, y3, z3), new Vertex(x4, y4, z4) };
		f.textureCoordinates = new TextureCoordinate[] { new TextureCoordinate(tx1 + texOffset, ty1), new TextureCoordinate(tx2 + texOffset, ty2),
				new TextureCoordinate(tx3 + texOffset, ty3), new TextureCoordinate(tx4 + texOffset, ty4) };
		return f;
	}

	private void setColor(int r, int g, int b)
	{
		float f1 = (float) (r) / 255.0F;
		float f2 = (float) (g) / 255.0F;
		float f3 = (float) (b) / 255.0F;
		GL11.glColor4f(f1, f2, f3, 1.0F);
	}

	@Override
	public int getRenderId()
	{
		return ElementsMC.elementTableRenderID;
	}

	@Override
	public void renderInventoryBlock(Block arg0, int arg1, int arg2, RenderBlocks arg3)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScalef(1.2f, 1.2f, 1.2f);
		GL11.glTranslatef(0.5f, -0.0625f, 0.5f);
		modelMk1.render();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScalef(1.2f, 1.2f, 1.2f);

		GL11.glTranslatef(0.5f, 0.5625f,  0.5f);
		float f = 0.1f;
		GL11.glScalef(f, -f, -f);
		//modelPodest.render();
		podest.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess arg0, int arg1, int arg2, int arg3, Block arg4, int arg5, RenderBlocks arg6)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int arg0)
	{
		return true;
	}

}

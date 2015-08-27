package elementsmc.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elementsmc.client.util.Animation;
import elementsmc.client.util.RenderHelper;
import elementsmc.common.elements.Element;
import elementsmc.common.tileentity.TileInfuser;
import elementsmc.common.util.Coords4;

public class InfuserRenderer extends TileEntitySpecialRenderer implements
		ISimpleBlockRenderingHandler {

	public static GroupObject outerRing = createRing(0.375d, 0.4375d, 0.0625d);
	public static GroupObject innerRing = createRing(0.25d, 0.3125d, 0.0625d);
	public static ResourceLocation resourceGold = new ResourceLocation("elementsmc:textures/blocks/goldPodest.png");
	public static ResourceLocation resourceInfuser = new ResourceLocation("elementsmc:textures/blocks/infuser.png");
	public static ModelInfuser model = new ModelInfuser();
	
	public static GroupObject createRing(double innerRadius, double outerRadius, double height)
	{
		GroupObject obj = new GroupObject("ring", GL11.GL_QUADS);
		float angle = (float) Math.toRadians(51.43D);
		double outerLength = 2d * outerRadius * Math.sin(angle / 2f);
		double innerLength = 2d * innerRadius * Math.sin(angle / 2f);
		Vec3 outerVec1 = Vec3.createVectorHelper(0d, 0d, outerRadius);
		Vec3 outerVec2 = Vec3.createVectorHelper(0d, 0d, outerRadius);
		Vec3 innerVec1 = Vec3.createVectorHelper(0d, 0d, innerRadius);
		Vec3 innerVec2 = Vec3.createVectorHelper(0d, 0d, innerRadius);
		outerVec2.rotateAroundY(angle);
		innerVec2.rotateAroundY(angle);
		for(int i = 0; i < 7; i++)
		{
			obj.faces.add(createFace(
					innerVec1.xCoord, 0, innerVec1.zCoord,
					innerVec2.xCoord, 0, innerVec2.zCoord,
					outerVec2.xCoord, 0, outerVec2.zCoord,
					outerVec1.xCoord, 0, outerVec1.zCoord,
					innerVec1.xCoord, innerVec1.zCoord,
					innerVec2.xCoord, innerVec2.zCoord,
					outerVec2.xCoord, outerVec2.zCoord,
					outerVec1.xCoord, outerVec1.zCoord
					)); //bottom
			obj.faces.add(createFace(
					innerVec1.xCoord, height, innerVec1.zCoord,
					innerVec2.xCoord, height, innerVec2.zCoord,
					outerVec2.xCoord, height, outerVec2.zCoord,
					outerVec1.xCoord, height, outerVec1.zCoord,
					innerVec1.xCoord, innerVec1.zCoord,
					innerVec2.xCoord, innerVec2.zCoord,
					outerVec2.xCoord, outerVec2.zCoord,
					outerVec1.xCoord, outerVec1.zCoord
					)); //top
			obj.faces.add(createFace(
					innerVec1.xCoord, 0, innerVec1.zCoord,
					innerVec2.xCoord, 0, innerVec2.zCoord,
					innerVec2.xCoord, height, innerVec2.zCoord,
					innerVec1.xCoord, height, innerVec1.zCoord,
					0, height,
					innerLength, height,
					innerLength, 0,
					0, 0
					)); //inner
			obj.faces.add(createFace(
					outerVec1.xCoord, 0, outerVec1.zCoord,
					outerVec2.xCoord, 0, outerVec2.zCoord,
					outerVec2.xCoord, height, outerVec2.zCoord,
					outerVec1.xCoord, height, outerVec1.zCoord,
					0, height,
					outerLength, height,
					outerLength, 0,
					0, 0
					)); //outer
			outerVec1.rotateAroundY(angle);
			innerVec1.rotateAroundY(angle);
			outerVec2.rotateAroundY(angle);
			innerVec2.rotateAroundY(angle);
		}
		return obj;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceInfuser);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslated(0.5, 0.0625, 0.5);
		float f = 0.1f;
		GL11.glScalef(f, -f, -f);
		model.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
		GL11.glPopMatrix();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceGold);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslated(0.5, 0.625, 0.53125);
		GL11.glRotatef(-90f, 1, 0, 0);
		//GL11.glScalef(f, -f, -f);
		innerRing.render();
		GL11.glPopMatrix();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceGold);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslated(0.5, 0.625, 0.53125);
		GL11.glRotatef(-90f, 1, 0, 0);
		//GL11.glScalef(f, -f, -f);
		outerRing.render();
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return 0;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tpf)
	{
		TileInfuser tei = (TileInfuser) te;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glScalef(0.1f, -0.1f, -0.1f);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceInfuser);
		model.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5d, y + 0.5625d, z + 0.53125d);
		GL11.glRotatef(-90f, 1, 0, 0);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceGold);
		Animation.apply(Coords4.fromTileEntity(te), "inner");
		innerRing.render();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5d, y + 0.5625d, z + 0.53125d);
		GL11.glRotatef(-90f, 1, 0, 0);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceGold);
		Animation.apply(Coords4.fromTileEntity(te), "outer");
		outerRing.render();
		GL11.glPopMatrix();
		
		if(tei.getStackInSlot(0) != null)
		{
			GL11.glPushMatrix();
			ItemStack stack2 = tei.getStackInSlot(0).copy();
			stack2.stackSize = 1;
			EntityItem eitem = new EntityItem(te.getWorldObj(), 0d, 0d, 0d, stack2);		
			eitem.hoverStart = 0f;
			
			int i = (int) ((System.currentTimeMillis() / 10L) % 360L);
			GL11.glTranslated(x + 0.5, y + 0.45 + (0.03125f * (float) Math.sin(Math.toRadians(i))), z + 0.5);
			GL11.glRotatef((System.currentTimeMillis() / 20L) % 360L, 0f, 1f, 0f);
			GL11.glScalef(0.6f, 0.6f, 0.6f);
			RenderManager.instance.renderEntityWithPosYaw(eitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(x + 0.125, y + 0.126, z + 0.8125);
		GL11.glRotated(-251, 1, 0, 0);
		for(int i = 0; i < 7; i++)
		{
			Element e = Element.values()[i];
			double d1 = 0.046875 + (0.09375 * i);
			double d2 = 0.046875;				
			RenderHelper.drawTexturedQuad(d1, d2, d1 + 0.09375, d2 + 0.09375, 0, e.loc, 0, 0, 1, 1);
		}
		GL11.glPopMatrix();
	}

	private static Face createFace(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4,
			double tx1, double ty1, double tx2, double ty2, double tx3, double ty3, double tx4, double ty4)
	{
		Face f = new Face();
		f.vertices = new Vertex[] { new Vertex((float)x1, (float)y1, (float)z1), new Vertex((float)x2, (float)y2, (float)z2), new Vertex((float)x3, (float)y3, (float)z3), new Vertex((float)x4, (float)y4, (float)z4) };
		f.textureCoordinates = new TextureCoordinate[] { new TextureCoordinate((float)tx1, (float)ty1), new TextureCoordinate((float)tx2, (float)ty2),
				new TextureCoordinate((float)tx3, (float)ty3), new TextureCoordinate((float)tx4, (float)ty4) };
		return f;
	}
	
}

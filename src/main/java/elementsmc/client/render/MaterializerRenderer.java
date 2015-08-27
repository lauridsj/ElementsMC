package elementsmc.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TileMaterializer;

public class MaterializerRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{

	private static final ResourceLocation resource = new ResourceLocation("elementsmc:textures/blocks/materializer.png");
	private static final ModelMaterializer model = new ModelMaterializer();

	@Override
	public int getRenderId()
	{
		return ElementsMC.materializerRenderID;
	}

	@Override
	public void renderInventoryBlock(Block arg0, int arg1, int arg2, RenderBlocks arg3)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslated(0.5, 0.125, 0.5);
		float f = 0.1f;
		GL11.glScalef(f, -f, -f);
		model.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
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

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks)
	{
		TileMaterializer mat = (TileMaterializer) te;
		this.bindTexture(resource);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslated(x + 0.5, y + 0.125, z + 0.5);
		float f = 0.1f;
		GL11.glScalef(f, -f, -f);
		model.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
		GL11.glPopMatrix();
		if(mat.getStackInSlot(0) != null)
		{
			GL11.glPushMatrix();
			ItemStack stack = mat.getStackInSlot(0).copy();
			stack.stackSize = 1;
			EntityItem eitem = new EntityItem(te.getWorldObj(), 0d, 0d, 0d, stack);		
			eitem.hoverStart = 0f;
			GL11.glTranslated(x + 0.5, y + 0.15625, z + 0.5);
			GL11.glRotatef((System.currentTimeMillis() / 20L) % 360L, 0f, 1f, 0f);
            RenderManager.instance.renderEntityWithPosYaw(eitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            GL11.glPopMatrix();
		}
	}

}

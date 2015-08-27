package elementsmc.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TileIncinerator;

public class IncineratorRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{

	private static final ResourceLocation resource = new ResourceLocation("elementsmc:textures/blocks/incinerator.png");
	private static ModelIncinerator model = new ModelIncinerator();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks)
	{
		//System.out.println("Rendering at " + tile.xCoord + "; " +  tile.yCoord + "; " + tile.zCoord);
		this.bindTexture(resource);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslated(x + 0.5, y + 0.0625, z + 0.5);
		float f = 0.1f;
		GL11.glScalef(f, -f, -f);
		model.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
		GL11.glPopMatrix();
		TileIncinerator inc = (TileIncinerator) tile;
		if(inc.getStackInSlot(0) != null)
		{
			GL11.glPushMatrix();
			ItemStack stack = inc.getStackInSlot(0).copy();
			stack.stackSize = 1;
			EntityItem eitem = new EntityItem(inc.getWorldObj(), 0d, 0d, 0d, stack);
			eitem.hoverStart = 0f;
			GL11.glTranslated(x + 0.5, y + 0.21, z + 0.5);
			boolean render3d = false;
			if(stack.getItem() instanceof ItemBlock)
			{
				ItemBlock ib = (ItemBlock) stack.getItem();
				render3d = RenderBlocks.renderItemIn3d(ib.field_150939_a.getRenderType());
			}
			if(!render3d)
			{
				GL11.glTranslated(0d, 0d, -0.24d);
				GL11.glRotatef(90f, 1f, 0f, 0f);
			}
			RenderManager.instance.renderEntityWithPosYaw(eitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glPopMatrix();
		}
	}

	@Override
	public int getRenderId()
	{
		return ElementsMC.incineratorRenderID;
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
		GL11.glTranslated(0.5, 0.0625, 0.5);
		float f = 0.1f;
		GL11.glScalef(f, -f, -f);
		model.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int meta, RenderBlocks rb)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int arg0)
	{
		return true;
	}

}

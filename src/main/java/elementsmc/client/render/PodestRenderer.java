package elementsmc.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elementsmc.client.util.Animation;
import elementsmc.client.util.RenderHelper;
import elementsmc.common.block.BlockPodest;
import elementsmc.common.elements.Element;
import elementsmc.common.item.ItemTablet;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.TilePodest;
import elementsmc.common.util.Coords4;
import elementsmc.common.util.GenericHelper;

public class PodestRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{

	private static ModelPodest modelPodest = new ModelPodest();
	private static float texOffset = 0F;

	@Override
	public int getRenderId()
	{
		return ElementsMC.podestRenderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int arg2, RenderBlocks arg3)
	{
		if(block instanceof BlockPodest)
		{
			BlockPodest podest = (BlockPodest) block;
			Minecraft.getMinecraft().getTextureManager().bindTexture(podest.getResource(meta));
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glScalef(2f, 2f, 2f);

			GL11.glTranslatef(0.5f, 0.6f,  0.5f);
			
			float f = 0.1f;
			GL11.glScalef(f, -f, -f);
			modelPodest.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
			GL11.glPopMatrix();
		}
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
		if(te.blockType instanceof BlockPodest)
		{
			BlockPodest podest = (BlockPodest) te.blockType;
			Minecraft.getMinecraft().getTextureManager().bindTexture(podest.getResource(te.blockMetadata));
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5625f, (float) z + 0.5f);
			float f = 0.1f;
			GL11.glScalef(f, -f, -f);
			//modelPodest.render();
			modelPodest.render(null, 0f, 0f, 0f, 0f, 0f, 0.625f);
			GL11.glPopMatrix();

			TilePodest tep = (TilePodest) te;
			ItemStack stack = tep.getStackInSlot(7);
			if(stack != null && stack.getItem() instanceof ItemTablet)
			{
				ItemTablet tbl = (ItemTablet) stack.getItem();
				Minecraft.getMinecraft().getTextureManager().bindTexture(tbl.getResource(stack));
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_LIGHTING);

				GL11.glTranslatef((float) x + 0.5f, (float) y - 0.0625f, (float) z + 0.5f);
				Animation ani = Animation.getAnimation(Coords4.fromTileEntity(tep), "rotate");
				if(ani != null)
				{
					ani.apply();
				}
				TabletRenderer.tabletModel.render();
				GL11.glPopMatrix();

				float angle = (float) Math.toRadians(51.43D);
				Vec3 vec = Vec3.createVectorHelper(0, 0, 0.3);
				vec.rotateAroundY((angle / 2f));
				if(ani != null)
				{
					vec.rotateAroundY((float) Math.toRadians(ani.getRotationAngleY()));
				}
				for(int i = 0; i < 7; i++)
				{
					if(tep.getStackInSlot(i) != null)
					{

						RenderHelper.drawLyingItem(tep.getStackInSlot(i), tep.getWorldObj(), x + 0.5 + vec.xCoord, y + 0.638, z + 0.5 + vec.zCoord, 0.6f);	
					}
					vec.rotateAroundY(angle);
				}

				if(tep.getStackInSlot(8) != null)
				{
					GL11.glPushMatrix();
					ItemStack stack2 = tep.getStackInSlot(8).copy();
					stack2.stackSize = 1;
					EntityItem eitem = new EntityItem(te.getWorldObj(), 0d, 0d, 0d, stack2);		
					eitem.hoverStart = 0f;
					int i = (int) ((System.currentTimeMillis() / 10L) % 360L);
					GL11.glTranslated(x + 0.5, y + 0.8 + (0.0625f * (float) Math.sin(Math.toRadians(i))), z + 0.5);
					GL11.glRotatef((System.currentTimeMillis() / 20L) % 360L, 0f, 1f, 0f);
					RenderManager.instance.renderEntityWithPosYaw(eitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
					GL11.glPopMatrix();
				}

				if(tep.producingTime != 0)
				{
					int i = GenericHelper.RNG.nextInt(30);
					for(int j = 0; j < i; j++)
					{
						double dx = (GenericHelper.RNG.nextDouble() * 0.8) + 0.1;
						double dy = 0.65;
						double dz = (GenericHelper.RNG.nextDouble() * 0.8) + 0.1;
						double d = GenericHelper.getDistanceSquared(dx, dy, dz, 0.5, 0.65, 0.5);
						if(d <= 0.25)
						{
							Vec3 dir = Vec3.createVectorHelper(dx - 0.5, 0, dz - 0.5);
							dir.normalize();
							dir.xCoord *= 0.08f;
							dir.zCoord *= 0.08f;
							System.out.println(dir);
							dir.rotateAroundY((float)Math.PI / 2f);
							
							Element e = Element.values()[GenericHelper.RNG.nextInt(7)];
							EntityReddustFX fx = new EntityReddustFX(tep.getWorldObj(), tep.xCoord + dx, tep.yCoord + dy, tep.zCoord + dz, 0.5f, (float)e.red / 256f, (float)e.green / 256f, (float)e.blue / 256f);
						 	fx.setVelocity(dir.xCoord, 0, dir.zCoord);
						 
							Minecraft.getMinecraft().effectRenderer.addEffect(fx);
						}
					}
				}
			}
		}
	}

}

package elementsmc.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import elementsmc.common.tileentity.ContainerIncinerator;
import elementsmc.common.tileentity.TileIncinerator;

public class GuiIncinerator extends GuiContainer
{

	private static final ResourceLocation resource = new ResourceLocation("elementsmc:textures/gui/incinerator.png");
	public ContainerIncinerator container;

	public GuiIncinerator(ContainerIncinerator con)
	{
		super(con);
		this.container = con;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = StatCollector.translateToLocal(container.tileEntity.getInventoryName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(resource);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if(this.container.clientBurnTime > 0)
		{
			int i = Math.round(((float)this.container.clientBurnTime / (float)TileIncinerator.TICKS_PER_OPERATION) * 14f);
			this.drawTexturedModalRect(k + 80, l + 52 + i, 176, i, 14, 14 - i);
		}

	}
}

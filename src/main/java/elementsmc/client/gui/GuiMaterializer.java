package elementsmc.client.gui;

import java.util.Arrays;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import elementsmc.common.main.EMConstants;
import elementsmc.common.main.ElementsMC;
import elementsmc.common.tileentity.ContainerMaterializer;
import elementsmc.common.tileentity.TileMaterializer;
import elementsmc.common.util.StreamHelper;

public class GuiMaterializer extends GuiContainer
{
	private static final ResourceLocation resource = new ResourceLocation("elementsmc:textures/gui/materializer.png");
	public ContainerMaterializer container;
	public int scrolling = 0;

	public GuiMaterializer(ContainerMaterializer con)
	{
		super(con);
		this.container = con;
		this.xSize = 176;
		this.ySize = 189;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		String s = StatCollector.translateToLocal(container.tile.getInventoryName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 4, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 93, 4210752);
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		int start = scrolling * 6;
		int xStart = 8;
		int yStart = 13;
		for(int i = 0; i < 30; i++)
		{
			if(container.clientProducables.size() > start + i)
			{
				int x = xStart + ((i % 6) * 16);
				int y = yStart + (((i - (i % 6)) / 6) * 16);
				ItemStack item = container.clientProducables.get(start + i);
				this.drawItem(item, x, y, null);
				if(mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16)
				{
					this.drawHoveringText(Arrays.asList(new String[] {item.getDisplayName()}), mouseX, mouseY, this.fontRendererObj);
				}
			}
		}
		if(container.clientProducingItem != null)
		{
			this.fontRendererObj.drawString(StatCollector.translateToLocal("container.materializer.producing"), 120, 64, 4210752);
			this.drawItem(container.clientProducingItem, 120, 77, null);
			int x = 120 + k;
			int y = 77 + l;
			if(mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16)
			{
				this.drawHoveringText(Arrays.asList(new String[] {container.clientProducingItem.getDisplayName()}), mouseX - k, mouseY - l, this.fontRendererObj);
			}
			x = 140 + k;
			y = 76 + l;
			if(mouseX >= x && mouseX <= x + 18 && mouseY >= y && mouseY <= y + 18)
			{
				this.drawHoveringText(Arrays.asList(new String[] {StatCollector.translateToLocal("container.materializer.stop")}), mouseX - k, mouseY - l, this.fontRendererObj);
			}
		}
		for(int i = 0; i < 30; i++)
		{
			if(container.clientProducables.size() > start + i)
			{
				int x = xStart + ((i % 6) * 16) + k;
				int y = yStart + (((i - (i % 6)) / 6) * 16) + l;
				ItemStack item = container.clientProducables.get(start + i);
				if(mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16)
				{
					this.drawHoveringText(Arrays.asList(new String[] {item.getDisplayName()}), mouseX - k, mouseY - l, this.fontRendererObj);
					break;
				}
			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(resource);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if(container.clientProducingTime > 0)
		{
			int i = Math.round(((float)this.container.clientProducingTime / (float)TileMaterializer.TICKS_PER_OPERATION) * 22f);
			this.drawTexturedModalRect(k + 124, l + 45, 176, 15, i, 17);
		}
		int maxScroll = (container.clientProducables.size() / 6) - 4;
		float f = ((float)scrolling / (float)maxScroll) * 65f;
		this.drawTexturedModalRect(k + 107, l + 13 + Math.round(f), 176, 0, 9, 15);
		if(container.clientProducingItem != null)
		{
			this.drawTexturedModalRect(k + 140, l + 76, 176, 32, 18, 18);
		}
	}

	private void drawItem(ItemStack item, int x, int y, String str)
	{

		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if (item != null) font = item.getItem().getFontRenderer(item);
		if (font == null) font = fontRendererObj;
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), item, x, y);
		itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), item, x, y , str);
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
	}

	protected void mouseClickMove(int mouseX, int mouseY, int button, long time)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		int x = mouseX - k;
		int y = mouseY - l;
		int maxScroll = (container.clientProducables.size() / 6) - 4;
		if(maxScroll > 0 && button == 0 && x > 106 && x < 116 && y > 12 && y < 93)
		{
			float f = ((float)(y - 12) / 65f) * maxScroll;
			this.scrolling = Math.round(f);

		}
	}

	protected void mouseClicked(int mouseX, int mouseY, int btn)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		int x = mouseX - k - 8;
		int y = mouseY - l - 13;
		if(x >= 0 && x < 96 && y >= 0 && y < 80)
		{
			int itemX = (x - (x % 16)) / 16;
			int itemY = (y - (y % 16)) / 16;
			int i = (itemY * 6) + itemX + (scrolling * 6);
			if(i < container.clientProducables.size())
			{
				ItemStack item = container.clientProducables.get(i);
				StreamHelper.newOutputStream();
				StreamHelper.writeInt(EMConstants.MATERIALIZER_ITEM_CHOOSE);
				StreamHelper.writeInt(container.tile.xCoord);
				StreamHelper.writeInt(container.tile.yCoord);
				StreamHelper.writeInt(container.tile.zCoord);
				StreamHelper.writeInt(Item.getIdFromItem(item.getItem()));
				StreamHelper.writeInt(item.getItemDamage());
				ElementsMC.networkChannel.sendToServer(StreamHelper.getPacket());
				container.tile.producingItem = item.copy();
			}
		}
		x = mouseX - k;
		y = mouseY - l;
		if(container.clientProducingItem != null && x >= 140 && x <= 158 && y >= 76 && y <= 94)
		{
			StreamHelper.newOutputStream();
			StreamHelper.writeInt(EMConstants.MATERIALIZER_ITEM_CHOOSE);
			StreamHelper.writeInt(container.tile.xCoord);
			StreamHelper.writeInt(container.tile.yCoord);
			StreamHelper.writeInt(container.tile.zCoord);
			StreamHelper.writeInt(0);
			StreamHelper.writeInt(0);
			ElementsMC.networkChannel.sendToServer(StreamHelper.getPacket());
			container.tile.producingItem = null;

		}
		super.mouseClicked(mouseX, mouseY, btn);
	}


}

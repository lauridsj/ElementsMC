package elementsmc.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.Vec3;
import elementsmc.client.util.RenderHelper;
import elementsmc.common.elements.Element;
import elementsmc.common.tileentity.ContainerElementTable;
import elementsmc.common.util.GenericHelper;

public class GuiElementTable extends GuiContainer
{

	public ContainerElementTable container;
	public int essences = 7;

	public GuiElementTable(ContainerElementTable con)
	{
		super(con);
		this.container = con;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2)
	{
		float f = (2f * (float) Math.PI) / ((float) essences);
		int k = this.width / 2;
		int l = this.height / 2;

		Vec3 vec = Vec3.createVectorHelper(0, -120, 0);
		Vec3 vec2 = Vec3.createVectorHelper(0, -120, 0);
		Vec3 vec3 = Vec3.createVectorHelper(0, -80, 0);
		vec2.rotateAroundZ(f);
		vec3.rotateAroundZ(f / 2f);

		for(int i = 0; i < essences; i++)
		{
			Element e = Element.values()[i];
			RenderHelper.drawLine(k, l, (double) zLevel + 1, vec.xCoord + k, vec.yCoord + l, (double) zLevel + 1, 2f, 0, 0, 0, 255);
			RenderHelper.drawLine(vec.xCoord + k, vec.yCoord + l, (double) zLevel + 1, vec2.xCoord + k, vec2.yCoord + l, (double) zLevel + 1, 2f, 0,
					0, 0, 255);
			RenderHelper.drawTriangle(k, l, (double) zLevel, vec.xCoord + k, vec.yCoord + l, (double) zLevel, vec2.xCoord + k, vec2.yCoord + l,
					(double) zLevel, e.red, e.green, e.blue, 192);

			RenderHelper.drawTexturedQuad(k + (int) Math.round(vec3.xCoord) - 16, l + (int) Math.round(vec3.yCoord) - 16,
					k + (int) Math.round(vec3.xCoord) + 16, l + (int) Math.round(vec3.yCoord) + 16, this.zLevel + 1, e.loc, 0, 0, 1, 1);
			// RenderHelper.drawTexturedModalRect(k +
			// (int)Math.round(vec3.xCoord) - 32, l +
			// (int)Math.round(vec3.yCoord) - 32, 0, 0, 64, 64, this.zLevel + 1,
			// 64, 64);
			String str = String.valueOf(container.essencesClient[i]);
			this.fontRendererObj.drawString(str, k + (int) Math.round(vec3.xCoord) - (this.fontRendererObj.getStringWidth(str) / 2),
					l + (int) Math.round(vec3.yCoord) + 18, GenericHelper.getColorFromRGB(240, 240, 240));
			vec.rotateAroundZ(f);
			vec2.rotateAroundZ(f);
			vec3.rotateAroundZ(f);
		}
	}

	public Vec3 getRotatedVec(float angle, double length)
	{
		Vec3 vec = Vec3.createVectorHelper(length, 0, 0);
		vec.rotateAroundZ(angle);
		return vec;
	}

	public void drawWorldBackground(int p_146270_1_)
	{

	}

}

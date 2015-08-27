package elementsmc.common.elements;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public enum Element
{

	FIRE("fire", 255, 105, 0, 0),

	EARTH("earth", 0, 127, 13, 0),

	WATER("water", 0, 149, 254, 0),

	AIR("air", 254, 230, 108, 0),

	NETHER("nether", 160, 0, 0, 0),

	MAGIC("magic", 255, 0, 219, 0),

	END("end", 65, 0, 95, 0),

	NATURE("nature", 154, 205, 50, 1),

	LIFE("life", 255, 106, 106, 1),

	ICE("ice", 240, 255, 255, 1),

	LIGHT("light", 255, 165, 0, 1),

	ELECTRICITY("electricity", 0, 255, 255, 1);

	public final int red;
	public final int green;
	public final int blue;
	public final String elementName;
	public final int mk;
	public ResourceLocation loc;

	private Element(String name, int red, int green, int blue, int mk)
	{
		this.elementName = name;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.mk = mk;
		this.loc = new ResourceLocation("elementsmc:textures/elements/" + this.elementName + ".png");
	}

	public String getLocalizedName()
	{
		return StatCollector.translateToLocal("element." + this.elementName);
	}

}

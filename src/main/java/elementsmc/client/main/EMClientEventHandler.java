package elementsmc.client.main;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

public class EMClientEventHandler
{

	@SubscribeEvent
	public void onDrawBlockHighlight(DrawBlockHighlightEvent event)
	{
		//System.out.println(event.target.hitVec);
	}
	
}

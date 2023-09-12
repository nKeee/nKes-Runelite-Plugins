package nKesPlugins;

import nKesFishing.nKesFishingPlugin;
import nKesFishing.nKesFishingPlugin;
import nKesInventorynotifier.InventoryNotifierPlugin;
import nKesInventorytags.InventoryTagsPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class nKesPlugins
{
	public static void main(String[] args) throws Exception
	{
		//Testing only, only one plugin can be loaded this way

		//ExternalPluginManager.loadBuiltin((nKesObjectIndicatorsPlugin.class));
		//ExternalPluginManager.loadBuiltin(nKesAgilityPlugin.class);
		//ExternalPluginManager.loadBuiltin(nKesNpcIndicatorsPlugin.class);
		//ExternalPluginManager.loadBuiltin(nKesScreenMarkerPlugin.class);
		//ExternalPluginManager.loadBuiltin(nKesIdleNotifierPlugin.class);
		//ExternalPluginManager.loadBuiltin(nKesGroundMarkerPlugin.class);
		//ExternalPluginManager.loadBuiltin(InventoryTagsPlugin.class);
		ExternalPluginManager.loadBuiltin(nKesFishingPlugin.class);

		RuneLite.main(args);
	}
}
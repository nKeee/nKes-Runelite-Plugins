package nKesPlugins;

import nKesFishing.nKesFishingPlugin;
import nKesBlackjack.nKesBlackjackPlugin;
import nKesInventorynotifier.InventoryNotifierPlugin;
import nKesInventorytags.nKesInventoryTagsPlugin;
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
		//ExternalPluginManager.loadBuiltin(nKesFishingPlugin.class);
		//ExternalPluginManager.loadBuiltin(nKesBlackjackPlugin.class);

		RuneLite.main(args);
	}
}
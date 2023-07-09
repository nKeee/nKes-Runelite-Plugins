package nKesPlugins;

import nKesAgility.nKesAgilityPlugin;
import nKesIdleAlert.nKesAfkAlertPlugin;
import nKesIdlenotifier.nKesIdleNotifierPlugin;
import nKesNpchighlight.nKesNpcIndicatorsPlugin;
import nKesObjectindicators.nKesObjectIndicatorsPlugin;
import nKesScreenmarkers.nKesScreenMarkerPlugin;
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
		ExternalPluginManager.loadBuiltin(nKesIdleNotifierPlugin.class);
		//ExternalPluginManager.loadBuiltin(nKesAfkAlertPlugin.class);

		RuneLite.main(args);
	}
}
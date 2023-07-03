package com.example;
import nKesNpchighlight.nKesNpcIndicatorsPlugin;
import nKesScreenmarkers.nKesScreenMarkerPlugin;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExamplePluginTest
{
	public static void main(String[] args) throws Exception
	{
		//Testing only, only one plugin can be loaded this way

		//ExternalPluginManager.loadBuiltin(nKesNpcIndicatorsPlugin.class);
		//ExternalPluginManager.loadBuiltin(ExamplePlugin.class);
		//ExternalPluginManager.loadBuiltin(nKesScreenMarkerPlugin.class);

		RuneLite.main(args);
	}
}
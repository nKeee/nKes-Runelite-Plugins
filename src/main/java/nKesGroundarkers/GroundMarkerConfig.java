/*
 * Copyright (c) 2018, TheLonelyDev <https://github.com/TheLonelyDev>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package nKesGroundarkers;

import java.awt.Color;

import net.runelite.client.config.*;

@ConfigGroup(GroundMarkerConfig.GROUND_MARKER_CONFIG_GROUP)
public interface GroundMarkerConfig extends Config
{
	String GROUND_MARKER_CONFIG_GROUP = "nkesgroundMarker";
	String SHOW_IMPORT_EXPORT_KEY_NAME = "showImportExport";

	@Alpha
	@ConfigItem(
		keyName = "markerColor",
		name = "Tile color",
		description = "Configures the color of marked tile"
	)
	default Color markerColor()
	{
		return Color.YELLOW;
	}

	@ConfigItem(
		keyName = "rememberTileColors",
		name = "Remember color per tile",
		description = "Color tiles using the color from time of placement"
	)
	default boolean rememberTileColors()
	{
		return false;
	}

	@ConfigItem(
		keyName = "drawOnMinimap",
		name = "Draw tiles on minimap",
		description = "Configures whether marked tiles should be drawn on minimap"
	)
	default boolean drawTileOnMinimmap()
	{
		return false;
	}

	@ConfigItem(
		keyName = SHOW_IMPORT_EXPORT_KEY_NAME,
		name = "Show Import/Export/Clear options",
		description = "Show the Import, Export, and Clear options on the world map right-click menu"
	)
	default boolean showImportExport()
	{
		return true;
	}

	@ConfigItem(
		keyName = "borderWidth",
		name = "Border Width",
		description = "Width of the marked tile border"
	)
	default double borderWidth()
	{
		return 2;
	}

	@ConfigItem(
		keyName = "fillOpacity",
		name = "Fill Opacity",
		description = "Opacity of the tile fill color"
	)
	@Range(
		max = 255
	)
	default int fillOpacity()
	{
		return 50;
	}

	@ConfigSection(
			name = "AHK Dot options",
			description = "Dot customization",
			position = 3
	) String dot = "dot";
	@Alpha
	@ConfigItem(
			keyName = "dot",
			name = "Dot color",
			description = "Color of the AHK dot",
			position = 1,
			section = dot
	) default Color getDotColor() { return new Color(255, 0, 0, 255); }
	@ConfigItem(
			keyName = "dotsize",
			name = "Dot Size",
			description = "Make the dot bigger or smaller if you're experiencing problems with color detection",
			position = 2,
			section = dot
	)
	@Range(
			min = 0,
			max = 10
	)
	default int dotSize()
	{
		return 2;
	}
	@ConfigItem(
			keyName = "deviation",
			name = "Deviation",
			description = "Change the dispersion of dots",
			position = 3,
			section = dot
	)
	@Range(
			min = 1,
			max = 10
	)
	default int deviation()
	{
		return 2;
	}
}

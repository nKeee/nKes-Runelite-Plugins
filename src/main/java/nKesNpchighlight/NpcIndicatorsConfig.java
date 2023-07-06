/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package nKesNpchighlight;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup(NpcIndicatorsConfig.GROUP)
public interface NpcIndicatorsConfig extends Config
{
	String GROUP = "npcindicatorsforahk";

	@ConfigSection(
		name = "Render style",
		description = "The render style of NPC highlighting",
		position = 0
	)
	String renderStyleSection = "renderStyleSection";

	@ConfigItem(
		position = 0,
		keyName = "highlightHull",
		name = "Highlight hull",
		description = "Configures whether or not NPC should be highlighted by hull",
		section = renderStyleSection
	)
	default boolean highlightHull()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "highlightTile",
		name = "Highlight tile",
		description = "Configures whether or not NPC should be highlighted by tile",
		section = renderStyleSection
	)
	default boolean highlightTile()
	{
		return false;
	}

	@ConfigItem(
		position = 2,
		keyName = "highlightTrueTile",
		name = "Highlight true tile",
		description = "Configures whether or not NPC should be highlighted by true tile",
		section = renderStyleSection
	)
	default boolean highlightTrueTile()
	{
		return false;
	}

	@ConfigItem(
		position = 3,
		keyName = "highlightSouthWestTile",
		name = "Highlight south west tile",
		description = "Configures whether or not NPC should be highlighted by south western tile",
		section = renderStyleSection
	)
	default boolean highlightSouthWestTile()
	{
		return false;
	}

	@ConfigItem(
		position = 4,
		keyName = "highlightSouthWestTrueTile",
		name = "Highlight south west true tile",
		description = "Configures whether or not NPC should be highlighted by south western true tile",
		section = renderStyleSection
	)
	default boolean highlightSouthWestTrueTile()
	{
		return false;
	}

	@ConfigItem(
		position = 5,
		keyName = "highlightOutline",
		name = "Highlight outline",
		description = "Configures whether or not the model of the NPC should be highlighted by outline",
		section = renderStyleSection
	)
	default boolean highlightOutline()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		position = 6,
		keyName = "npcColor",
		name = "Highlight Color",
		description = "Color of the NPC highlight border, menu, and text",
		section = renderStyleSection
	)
	default Color highlightColor()
	{
		return Color.CYAN;
	}

	@Alpha
	@ConfigItem(
		position = 7,
		keyName = "fillColor",
		name = "Fill Color",
		description = "Color of the NPC highlight fill",
		section = renderStyleSection
	)
	default Color fillColor()
	{
		return new Color(0, 255, 255, 20);
	}

	@ConfigItem(
		position = 8,
		keyName = "borderWidth",
		name = "Border Width",
		description = "Width of the highlighted NPC border",
		section = renderStyleSection
	)
	default double borderWidth()
	{
		return 2;
	}

	@ConfigItem(
		position = 9,
		keyName = "outlineFeather",
		name = "Outline feather",
		description = "Specify between 0-4 how much of the model outline should be faded",
		section = renderStyleSection
	)
	@Range(
		min = 0,
		max = 4
	)
	default int outlineFeather()
	{
		return 0;
	}

	@ConfigSection(
			name = "NPC Options",
			description = "Dot customization",
			position = 2
	) String npcoptions = "npcoptions";

	@ConfigItem(
		position = 10,
		keyName = "npcToHighlight",
		name = "NPCs to Highlight",
		description = "List of NPC names to highlight. Format: (NPC), (NPC)",
			section = npcoptions
	)
	default String getNpcToHighlight()
	{
		return "";
	}

	@ConfigItem(
		keyName = "npcToHighlight",
		name = "",
		description = "",
			section = npcoptions
	)
	void setNpcToHighlight(String npcsToHighlight);

	@ConfigItem(
		position = 11,
		keyName = "drawNames",
		name = "Draw names above NPC",
		description = "Configures whether or not NPC names should be drawn above the NPC",
			section = npcoptions
	)
	default boolean drawNames()
	{
		return false;
	}

	@ConfigItem(
		position = 12,
		keyName = "drawMinimapNames",
		name = "Draw names on minimap",
		description = "Configures whether or not NPC names should be drawn on the minimap",
			section = npcoptions
	)
	default boolean drawMinimapNames()
	{
		return false;
	}

	@ConfigItem(
		position = 13,
		keyName = "highlightMenuNames",
		name = "Highlight menu names",
		description = "Highlight NPC names in right click menu",
			section = npcoptions
	)
	default boolean highlightMenuNames()
	{
		return false;
	}

	@ConfigItem(
		position = 14,
		keyName = "ignoreDeadNpcs",
		name = "Ignore dead NPCs",
		description = "Prevents highlighting NPCs after they are dead",
			section = npcoptions
	)
	default boolean ignoreDeadNpcs()
	{
		return true;
	}

	@ConfigItem(
		position = 15,
		keyName = "deadNpcMenuColor",
		name = "Dead NPC menu color",
		description = "Color of the NPC menus for dead NPCs",
			section = npcoptions
	)
	Color deadNpcMenuColor();

	@ConfigItem(
		position = 16,
		keyName = "showRespawnTimer",
		name = "Show respawn timer",
		description = "Show respawn timer of tagged NPCs",
			section = npcoptions
	)
	default boolean showRespawnTimer()
	{
		return false;
	}

	@ConfigItem(
		position = 17,
		keyName = "ignorePets",
		name = "Ignore pets",
		description = "Excludes pets from being highlighted",
			section = npcoptions
	)
	default boolean ignorePets()
	{
		return true;
	}

	@ConfigSection(
			name = "AHK Dot Options",
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
}
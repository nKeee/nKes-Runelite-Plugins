package nKesAgility;

import net.runelite.client.config.*;

import java.awt.Color;

@ConfigGroup("nKesrooftops")
public interface RooftopsConfig extends Config {
	@ConfigSection(
		name = "Obstacles",
		description = "Obstacles",
		position = 1
	) String obstacles = "obstacles";

		@Alpha
		@ConfigItem(
			keyName = "obstacle_next",
			name = "Next",
			description = "Color of the next obstacle.",
			position = 1,
			section = obstacles
		) default Color getObstacleNextColor() { return new Color(0, 255, 0, 80); }

		@Alpha
		@ConfigItem(
			keyName = "obstacle_next_unavailable",
			name = "Next unavailable",
			description = "Color of the next obstacle.",
			position = 2,
			section = obstacles
		) default Color getObstacleNextUnavailableColor() { return new Color(200, 255, 0, 80); }

		@Alpha
		@ConfigItem(
			keyName = "obstacle_unavailable",
			name = "Other",
			description = "Color of other obstacles.",
			position = 3,
			section = obstacles
		) default Color getObstacleUnavailableColor() { return new Color(255, 150, 0, 70); }

		@Alpha
		@ConfigItem(
			keyName = "obstacle_stop",
			name = "Stop",
			description = "Color of obstacle that should not be used, because Mark of grace is on the ground.",
			position = 4,
			section = obstacles
		) default Color getObstacleStopColor() { return new Color(255, 0, 0, 80); }

	@ConfigSection(
		name = "Marks of graces",
		description = "Marks of graces",
		position = 2
	) String marks_of_graces = "marks_of_graces";

		@Alpha
		@ConfigItem(
			keyName = "mark_of_grace",
			name = "Mark of grace",
			description = "Color of the Mark of grace.",
			position = 1,
			section = marks_of_graces
		) default Color getMarkOfGraceColor() { return new Color(0, 255, 0, 80); }

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

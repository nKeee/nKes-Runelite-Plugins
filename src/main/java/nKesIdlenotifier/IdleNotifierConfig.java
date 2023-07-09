/*
 * Copyright (c) 2017, Devin French <https://github.com/devinfrench>
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
package nKesIdlenotifier;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("nKesidlenotifier")
public interface IdleNotifierConfig extends Config
{
	@ConfigItem(
		keyName = "animationidle",
		name = "Idle Animation Notifications",
		description = "Configures if idle animation notifications are enabled",
		position = 1
	)
	default boolean animationIdle()
	{
		return true;
	}

	@ConfigItem(
		keyName = "interactionidle",
		name = "Idle Interaction Notifications",
		description = "Configures if idle interaction notifications are enabled e.g. combat, fishing",
		position = 2
	)
	default boolean interactionIdle()
	{
		return true;
	}

	@ConfigItem(
		keyName = "movementidle",
		name = "Idle Movement Notifications",
		description = "Configures if idle movement notifications are enabled e.g. running, walking",
		position = 3
	)
	default boolean movementIdle()
	{
		return false;
	}

	@ConfigItem(
			keyName = "timeoutMovement",
			name = "Movement delay",
			description = "The notification delay after the player hasnt moved for certain time",
			position = 6
	)
	@Units(Units.MILLISECONDS)
	default int getIdleNotificationDelayMovement()
	{
		return 5000;
	}

	@ConfigItem(
			keyName = "timeoutAnimation",
			name = "Animation delay",
			description = "The notification delay after the player hasnt animated for certain time",
			position = 7
	)
	@Units(Units.MILLISECONDS)
	default int getIdleNotificationDelayAnimation()
	{
		return 5000;
	}

	@ConfigItem(
			keyName = "timeoutInteraction",
			name = "Interaction delay",
			description = "The notification delay after the player hasnt animated for certain time",
			position = 8
	)
	@Units(Units.MILLISECONDS)
	default int getIdleNotificationDelayInteraction()
	{
		return 5000;
	}

	@Alpha
	@ConfigItem(
			keyName = "boxcolorIdle",
			name = "Idle box color",
			description = "Color of the box when you're idle",
			position = 9
	) default Color getIdleColor() { return new Color(255, 0, 0, 255); }

	@Alpha
	@ConfigItem(
			keyName = "boxcolorNotidle",
			name = "Active box color",
			description = "Color of the box when you're not idle",
			position = 9
	) default Color getActiveColor() { return new Color(0, 255, 0, 255); }

}

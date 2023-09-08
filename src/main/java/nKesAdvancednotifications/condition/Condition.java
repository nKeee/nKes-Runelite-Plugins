package nKesAdvancednotifications.condition;

import nKesAdvancednotifications.AdvancedNotificationsPlugin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class Condition
{
	@Getter
	@Setter
	private transient AdvancedNotificationsPlugin plugin;

	public Condition(AdvancedNotificationsPlugin plugin)
	{
		this.plugin = plugin;
	}

	public abstract boolean isFulfilled();

	public abstract Condition clone();
}

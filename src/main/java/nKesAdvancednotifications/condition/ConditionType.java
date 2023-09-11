package nKesAdvancednotifications.condition;

import nKesAdvancednotifications.AdvancedNotificationsPlugin;
import nKesAdvancednotifications.notification.Notification;
import lombok.Getter;

public class ConditionType<T extends Condition>
{
	public interface Provider<T extends Condition>
	{
		T provide(AdvancedNotificationsPlugin plugin);
	}

	private final Provider<T> provider;
	@Getter
	private final Class<T> targetClass;

	public ConditionType(Provider<T> provider, Class<T> targetClass)
	{
		this.provider = provider;
		this.targetClass = targetClass;
	}

	public T provide(AdvancedNotificationsPlugin plugin)
	{
		return provider.provide(plugin);
	}
}

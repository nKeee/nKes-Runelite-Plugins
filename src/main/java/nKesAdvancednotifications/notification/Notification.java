package nKesAdvancednotifications.notification;

import nKesAdvancednotifications.AdvancedNotificationsPlugin;
import nKesAdvancednotifications.DraggableContainer;
import nKesAdvancednotifications.condition.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;


@NoArgsConstructor
public abstract class Notification
{
	@Getter
	@Setter
	private transient AdvancedNotificationsPlugin plugin;

	@Getter
	@Setter
	private boolean enabled = true;

	@Getter
	@Setter
	private transient boolean configuring = false;

	@Getter
	@Setter
	private Condition condition = null;

	private Duration dur = Duration.ofMillis(500);
	private Instant inst = Instant.now();

	@Getter
	private final transient DraggableContainer<Condition> conditionContainer = new DraggableContainer<Condition>() {

		@Override
		public List<Condition> getDraggableItems() {
			return Collections.singletonList(condition);
		}

		@Override
		public Notification getRoot() {
			return Notification.this;
		}
	};

	public Notification(AdvancedNotificationsPlugin plugin)
	{
		this.plugin = plugin;
	}

	public void tryNotify(Object event)
	{
		if (!enabled || (condition != null && !condition.isFulfilled())) {
			return;
		}
		notify(event);
	}

	protected abstract void notify(Object event);

	protected void doNotification(String message)
	{
		plugin.getNotifier().notify(message);
	}


	//only called from empty/itemnotifications, timer doesnt work ATM, but overall logic works
	void enableBox(){
		plugin.displayBox = true;
		inst = Instant.now();
	}
	//only called from empty/itemnotifications, timer doesnt work ATM, but overall logic works
	void displayBoxTimer(){
		if (Instant.now().compareTo(inst.plus(dur)) >= 0){
			plugin.displayBox = false;
		}
	}

	public abstract Notification clone();
}

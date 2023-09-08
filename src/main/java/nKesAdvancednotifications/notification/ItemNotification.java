package nKesAdvancednotifications.notification;

import nKesAdvancednotifications.AdvancedNotificationsPlugin;
import nKesAdvancednotifications.InventoryEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ItemNotification extends Notification
{
	@Getter
	@Setter
	private String item = "Coins";
	@Getter
	@Setter
	private InventoryComparator.Pointer comparator = new InventoryComparator.Pointer(InventoryComparator.COMPARATORS[0]);
	@Getter
	@Setter
	private int comparatorParam = 0;

	public ItemNotification(AdvancedNotificationsPlugin plugin)
	{
		super(plugin);
	}

	@Override
	public void notify(Object event)
	{
		if (!(event instanceof InventoryEvent)) return;

		InventoryEvent e = (InventoryEvent)event;

		if (getPlugin().getItemManager().getItemComposition(e.getItemID()).getName().equalsIgnoreCase(item)
			&& comparator.object.shouldNotify(e.getPreviousCount(), e.getCount(), comparatorParam))
		{
			enableBox();
			doNotification(comparator.object.notification(item, comparatorParam));
		}
		displayBoxTimer();
	}

	@Override
	public ItemNotification clone()
	{
		ItemNotification n = new ItemNotification(getPlugin());
		n.item = item;
		n.comparator = new InventoryComparator.Pointer(comparator.object);
		n.comparatorParam = comparatorParam;
		return n;
	}
}

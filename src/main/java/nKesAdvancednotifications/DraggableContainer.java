package nKesAdvancednotifications;

import nKesAdvancednotifications.notification.Notification;

import java.util.List;

public interface DraggableContainer<T>
{
	List<T> getDraggableItems();
	default Notification getRoot() { return null; }
}

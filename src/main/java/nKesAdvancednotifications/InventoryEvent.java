package nKesAdvancednotifications;

import lombok.Value;

@Value
public class InventoryEvent
{
	private final int itemID;
	private final int count;
	private final int previousCount;
}

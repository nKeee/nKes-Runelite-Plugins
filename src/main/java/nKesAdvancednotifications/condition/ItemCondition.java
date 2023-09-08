package nKesAdvancednotifications.condition;

import nKesAdvancednotifications.AdvancedNotificationsPlugin;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.InventoryID;

import java.util.Arrays;

public class ItemCondition extends Condition
{
	@Getter
	@Setter
	private String item = "Coins";
	@Getter
	@Setter
	private InventoryChecker.Pointer checker = new InventoryChecker.Pointer(InventoryChecker.CHECKERS[0]);
	@Getter
	@Setter
	private int checkerParam = 0;

	public ItemCondition(AdvancedNotificationsPlugin plugin)
	{
		super(plugin);
	}

	@Override
	public boolean isFulfilled()
	{
		return checker.object.isFulfilled(
			Arrays.stream(getPlugin().getClient().getItemContainer(InventoryID.INVENTORY).getItems())
				.filter(i -> getPlugin().getItemManager().getItemComposition(i.getId()).getName().equalsIgnoreCase(item))
				.reduce(0, (a, i) -> a + i.getQuantity(), Integer::sum),
			checkerParam
		);
	}

	@Override
	public ItemCondition clone()
	{
		ItemCondition c = new ItemCondition(getPlugin());
		c.item = item;
		c.checker = new InventoryChecker.Pointer(checker.object);
		c.checkerParam = checkerParam;
		return c;
	}
}

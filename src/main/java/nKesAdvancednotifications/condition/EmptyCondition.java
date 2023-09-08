package nKesAdvancednotifications.condition;

import nKesAdvancednotifications.AdvancedNotificationsPlugin;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemID;

import java.util.Arrays;

public class EmptyCondition extends Condition
{
	@Getter
	@Setter
	private InventoryChecker.Pointer checker = new InventoryChecker.Pointer(InventoryChecker.CHECKERS[0]);
	@Getter
	@Setter
	private int checkerParam = 0;

	public EmptyCondition(AdvancedNotificationsPlugin plugin)
	{
		super(plugin);
	}

	@Override
	public boolean isFulfilled()
	{
		return checker.object.isFulfilled(
			Arrays.stream(getPlugin().getClient().getItemContainer(InventoryID.INVENTORY).getItems())
				.filter(i -> i.getId() == ItemID._3RD_AGE_AMULET)
				.reduce(0, (a, i) -> a + i.getQuantity(), Integer::sum),
			checkerParam
		);
	}

	@Override
	public Condition clone() {
		EmptyCondition n = new EmptyCondition(getPlugin());
		n.checker = new InventoryChecker.Pointer(checker.object);
		n.checkerParam = checkerParam;
		return n;
	}
}

package nKesInventorynotifier;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.Notifier;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ImageUtil;
import java.awt.image.BufferedImage;
import java.util.Arrays;

@Slf4j
@PluginDescriptor(
		name = "nKe's Inventory Notifier"
)
public class InventoryNotifierPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private InventoryNotifierOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Notifier notifier;

	private static final BufferedImage INVENTORY_IMAGE;
	private static final BufferedImage INVENTORY_FULL_IMAGE;
	private BufferedImage CURRENT_IMAGE = INVENTORY_IMAGE;

	private static final int INVENTORY_SIZE = 28;

	@Getter
	private InventoryNotifierInfoBox inventoryNotifierInfoBox;

	static
	{
		INVENTORY_IMAGE = ImageUtil.loadImageResource(InventoryNotifierPlugin.class, "inventory_icon.png");
		INVENTORY_FULL_IMAGE = ImageUtil.loadImageResource(InventoryNotifierPlugin.class, "inventory_full_icon.png");
	}

	@Override
	protected void startUp() throws Exception
	{
			addInfoBox();
	}

	@Override
	protected void shutDown() throws Exception
	{
			removeInfoBox();
	}

	private void addInfoBox()
	{
		inventoryNotifierInfoBox = new InventoryNotifierInfoBox(CURRENT_IMAGE, this);
		updateOverlays();
		infoBoxManager.addInfoBox(inventoryNotifierInfoBox);
	}

	private void removeInfoBox()
	{
		infoBoxManager.removeInfoBox(inventoryNotifierInfoBox);
		inventoryNotifierInfoBox = null;
	}

	private void updateOverlays() {
		String text = String.valueOf(openInventorySpaces());
		if (openInventorySpaces() == 0) {
			CURRENT_IMAGE = INVENTORY_FULL_IMAGE;
			inventoryNotifierInfoBox.setText("");
			notifier.notify("Your inventory is full.");
		} else {
			CURRENT_IMAGE = INVENTORY_IMAGE;
			inventoryNotifierInfoBox.setText(text);
		}
	}

	private int openInventorySpaces()
	{
		ItemContainer container = client.getItemContainer(InventoryID.INVENTORY);
		Item[] items = container == null ? new Item[0] : container.getItems();
		int usedSpaces = (int) Arrays.stream(items).filter(p -> p.getId() != -1).count();
		return INVENTORY_SIZE - usedSpaces;
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getContainerId() == InventoryID.INVENTORY.getId())
		{
			updateOverlays();
			removeInfoBox();
			addInfoBox();
		}
	}
}

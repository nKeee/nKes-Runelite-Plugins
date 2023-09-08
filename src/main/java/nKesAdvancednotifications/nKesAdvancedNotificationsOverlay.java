package nKesAdvancednotifications;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class nKesAdvancedNotificationsOverlay extends Overlay {
    private final Client client;
    private final AdvancedNotificationsPlugin plugin;


    @Inject
    public nKesAdvancedNotificationsOverlay( Client client, AdvancedNotificationsPlugin plugin) {

        this.client = client;
        this.plugin = plugin;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Rectangle2D r = new Rectangle2D.Double(50,0,50,50);

        if (plugin.displayBox==true){
            graphics.setColor(Color.orange);
        }
        if (plugin.displayBox==false) {
            graphics.setColor(Color.red);
        }

        graphics.fill(r);
        return null;
    }
}

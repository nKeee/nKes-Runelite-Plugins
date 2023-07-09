package nKesIdlenotifier;

import net.runelite.api.Client;
import net.runelite.api.Player;
import javax.inject.Inject;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.time.Duration;

class nKesIdleNotifierOverlay extends Overlay {
    private final Client client;
    private final IdleNotifierConfig config;
    private final nKesIdleNotifierPlugin plugin;

    @Inject
    public nKesIdleNotifierOverlay( IdleNotifierConfig config,  Client client,  nKesIdleNotifierPlugin plugin) {
        this.config = config;
        this.client = client;
        this.plugin = plugin;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }
    @Override
    public Dimension render(Graphics2D graphics) {
        Rectangle2D r = new Rectangle2D.Double(0,0,50,50);

        if (plugin.moving || plugin.interacting){
            graphics.setColor(config.getActiveColor());
        }
        else {
            graphics.setColor(config.getIdleColor());
        }
        graphics.fill(r);
        return null;
    }
}

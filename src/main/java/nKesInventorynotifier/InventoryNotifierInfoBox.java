package nKesInventorynotifier;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBox;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryNotifierInfoBox extends InfoBox
{
    private String _text;

    InventoryNotifierInfoBox(BufferedImage image, Plugin plugin)
    {
        super(image, plugin);
        setTooltip("Color of full inventory: FF00EE");
    }

    @Override
    public String getText() {
        return _text;
    }

    @Override
    public Color getTextColor() {
        return Color.WHITE;
    }

    public void setText(String text)
    {
        _text = text;
    }
}

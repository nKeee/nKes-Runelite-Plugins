package nKesBlackjack;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ClientTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;

@PluginDescriptor(
        name = "nKe's Blackjack",
        description = "Blackjacking made easy",
        tags = {"swap","swapper","menu","entry","menu entry swapper","blackjack","nke","ahk","thieving"}
)
public class nKesBlackjackPlugin extends Plugin
{
    private static final String SUCCESS_BLACKJACK = "You smack the bandit over the head and render them unconscious.";
    private static final String FAILED_BLACKJACK = "Your blow only glances off the bandit's head.";
    private boolean pickpocketOnAggro;
    private boolean random;
    private long nextKnockOutTick = 0;

    @Inject
    private Client client;
    @Inject
    private nKesBlackjackConfig config;

    @Provides
    nKesBlackjackConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(nKesBlackjackConfig.class);
    }

    @Subscribe
    private void onConfigChanged(ConfigChanged event)
    {
        if (event.getGroup().equals("nkesblackjack"))
        {
            this.random = config.random();
        }
    }

    @Subscribe
    public void onClientTick(ClientTick clientTick)
    {
        if (client.getGameState() != GameState.LOGGED_IN || client.isMenuOpen() || client.isKeyPressed(KeyCode.KC_SHIFT))
        {
            return;
        }

        MenuEntry[] menuEntries = client.getMenuEntries();
        int useIndex = -1;
        int topIndex = menuEntries.length - 1;
        String swapTarget;

        if (client.getTickCount() >= nextKnockOutTick)
        {
            swapTarget = "Knock-Out";
        }
        else{
            swapTarget = "Pickpocket";
        }

        for (int i = 0; i < topIndex; i++)
        {
            if (Text.removeTags(menuEntries[i].getOption()).equals(swapTarget))
            {
                useIndex = i;
                break;
            }
        }

        if (useIndex == -1)
        {
            return;
        }

        MenuEntry entry1 = menuEntries[useIndex];
        MenuEntry entry2 = menuEntries[topIndex];

        menuEntries[useIndex] = entry2;
        menuEntries[topIndex] = entry1;

        client.setMenuEntries(menuEntries);
    }
    @Subscribe
    private void onChatMessage(ChatMessage event)
    {
        final String msg = event.getMessage();

        if (event.getType() == ChatMessageType.SPAM && (msg.equals(SUCCESS_BLACKJACK) || (msg.equals(FAILED_BLACKJACK) && this.pickpocketOnAggro)))
        {
            final int ticks = this.random ? RandomUtils.nextInt(3, 4) : 4;
            nextKnockOutTick = client.getTickCount() + ticks;
        }
    }

}
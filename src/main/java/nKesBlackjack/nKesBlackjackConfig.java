package nKesBlackjack;

import net.runelite.client.config.*;

@ConfigGroup(nKesBlackjackConfig.GROUP)
public interface nKesBlackjackConfig extends Config
{
    String GROUP = "nkesblackjack";

    @ConfigItem(
            keyName = "random",
            name = "Random pickpocket miss",
            description = "Randomly miss 1 pickpocket every so often.",
            position = 1
    )
    default boolean random()
    {
        return false;
    }
}
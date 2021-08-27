package org.battleplugins.arena.arena;

import net.kyori.adventure.text.Component;
import org.battleplugins.arena.message.MessageHandler;
import org.bukkit.OfflinePlayer;

/**
 * A {@link MessageHandler} capable of sending messages
 * for {@link Arena}s. Can be extended and changed by
 * Arenas, unlike the base MessageHandler class.
 *
 * @author Redned
 */
public class ArenaMessageHandler extends MessageHandler {

    private Arena arena;

    public ArenaMessageHandler(Arena arena, ConfigurationNode node, String... sections) {
        super(node, sections);

        this.arena = arena;
    }

    @Override
    public Component getFormattedMessage(String key) {
        Component superResult = super.getFormattedMessage(key);
        superResult = superResult.replaceText(config -> config.matchLiteral("%arena_name%").replacement(arena.getName()));
        superResult = superResult.replaceText(config -> config.matchLiteral("%arena_command%").replacement(arena.getName().toLowerCase()));
        return superResult;
    }

    @Override
    public Component getFormattedMessage(OfflinePlayer player, String key) {
        Component superResult = super.getFormattedMessage(player, key);
        superResult = superResult.replaceText(config -> config.matchLiteral("%arena_name%").replacement(arena.getName()));
        superResult = superResult.replaceText(config -> config.matchLiteral("%arena_command%").replacement(arena.getName().toLowerCase()));
        return superResult;
    }

    @Override
    public Component getPlaceholderMessage(OfflinePlayer player, Component message) {
        Component superResult = super.getPlaceholderMessage(player, message);
        superResult = superResult.replaceText(config -> config.matchLiteral("%arena_name%").replacement(arena.getName()));
        superResult = superResult.replaceText(config -> config.matchLiteral("%arena_command%").replacement(arena.getName().toLowerCase()));
        return superResult;
    }
}

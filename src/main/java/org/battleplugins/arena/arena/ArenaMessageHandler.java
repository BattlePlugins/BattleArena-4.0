package org.battleplugins.arena.arena;

import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.api.entity.living.player.OfflinePlayer;
import org.battleplugins.arena.message.MessageHandler;

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
    public String getFormattedMessage(String key) {
        String superResult = super.getFormattedMessage(key);
        superResult = superResult.replace("%arena_name%", arena.getName());
        superResult = superResult.replace("%arena_command%", arena.getName().toLowerCase());
        return superResult;
    }

    @Override
    public String getFormattedMessage(OfflinePlayer player, String key) {
        String superResult = super.getFormattedMessage(player, key);
        superResult = superResult.replace("%arena_name%", arena.getName());
        superResult = superResult.replace("%arena_command%", arena.getName().toLowerCase());
        return superResult;
    }

    @Override
    public String getPlaceholderMessage(OfflinePlayer player, String message) {
        String superResult = super.getPlaceholderMessage(player, message);
        superResult = superResult.replace("%arena_name%", arena.getName());
        superResult = superResult.replace("%arena_command%", arena.getName().toLowerCase());
        return superResult;
    }
}

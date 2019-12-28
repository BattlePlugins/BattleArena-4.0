package org.battleplugins.arena.message;

import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.api.entity.living.player.OfflinePlayer;
import org.battleplugins.api.entity.living.player.Player;

/**
 * Main message manager for BattleArena.
 *
 * @author Redned
 */
public class MessageManager {

    private MessageHandler mainHandler;

    public MessageManager(ConfigurationNode node) {
        this.mainHandler = new MessageHandler(node);
    }

    /**
     * Returns a message with the given key/path
     *
     * @param key the name (key) of the message in the config
     * @return a message with the given key/path
     */
    public String getMessage(String key) {
        return mainHandler.getMessage(key);
    }

    /**
     * Returns a message with the given key/path which
     * is formatted with color replacements and the
     * prefix
     *
     * @param key the name (key) of the message in the config
     * @return a message with the given key/path
     */
    public String getFormattedMessage(String key) {
        return mainHandler.getFormattedMessage(key);
    }

    /**
     * Returns a message with the given key/path and
     * variable replacement for the specified player
     *
     * @param player the player to perform the variable replacement on
     * @param key the name (key) of the message in the config
     * @return a message with the given key/path
     */
    public String getMessage(OfflinePlayer player, String key) {
        return mainHandler.getMessage(player, key);
    }

    /**
     * Returns a message with the given key/path and
     * variable replacement for the specified player,
     * which is also formatted with color replacements
     * and prefix
     *
     * @param player the player to perform the variable replacement on
     * @param key the name (key) of the message in the config
     * @return a message with the given key/path
     */
    public String getFormattedMessage(OfflinePlayer player, String key) {
        return mainHandler.getFormattedMessage(player, key);
    }

    /**
     * Returns the message with variables replaced
     *
     * @param player the player to base variable replacement from
     * @param message the message to send
     * @return the message with variables replaced
     */
    public String getPlaceholderMessage(OfflinePlayer player, String message) {
        return mainHandler.getPlaceholderMessage(player, message);
    }

    /**
     * Sends a message to the specified player with
     * the variables replaced
     *
     * @param player the player to send the message to
     * @param message the message to send
     */
    public void sendMessage(Player player, String message) {
        mainHandler.sendMessage(player, message);
    }
}

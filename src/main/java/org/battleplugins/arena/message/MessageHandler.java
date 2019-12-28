package org.battleplugins.arena.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import mc.alk.battlecore.message.MessageController;

import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.api.entity.living.player.OfflinePlayer;
import org.battleplugins.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * A handler containing messages and methods to send
 * them with various different options.
 *
 * @author Redned
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MessageHandler {

    private Map<String, String> messages = new HashMap<>();

    protected MessageHandler(ConfigurationNode node) {
        for (String str : node.getCollectionValue(String.class)) {
            messages.put(str, node.getNode(str).getValue(String.class));
        }
    }

    /**
     * Returns a message with the given key/path
     *
     * @param key the name (key) of the message in the config
     * @return a message with the given key/path
     */
    public String getMessage(String key) {
        return messages.get(key);
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
        return MessageController.colorChat(messages.get("prefix") + MessageController.colorChat(messages.get(key)));
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
        return getPlaceholderMessage(player, messages.get(key));
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
        return MessageController.colorChat(messages.get("prefix") + getPlaceholderMessage(player, messages.get(key)));
    }

    /**
     * Returns the message with variables replaced
     *
     * @param player the player to base variable replacement from
     * @param message the message to send
     * @return the message with variables replaced
     */
    public String getPlaceholderMessage(OfflinePlayer player, String message) {
        message = message.replace("%player_name%", player.getName());
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            message = message.replace("%" + entry + "%", entry.getValue());
        }
        return MessageController.colorChat(message);
    }

    /**
     * Sends a message to the specified player with
     * the variables replaced
     *
     * @param player the player to send the message to
     * @param message the message to send
     */
    public void sendMessage(Player player, String message) {
        player.sendMessage(getPlaceholderMessage(player, message));
    }
}

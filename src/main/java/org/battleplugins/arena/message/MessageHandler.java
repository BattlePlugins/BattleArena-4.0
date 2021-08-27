package org.battleplugins.arena.message;

import org.battleplugins.arena.util.Log;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * A handler containing messages and methods to send
 * them with various different options.
 *
 * @author Redned
 */
public class MessageHandler {

    private final Component prefix;
    private final Map<String, Component> messages = new HashMap<>();

    protected MessageHandler(ConfigurationNode node, String... sections) {
        if (sections.length == 0) {
            Map<Object, ? extends ConfigurationNode> mapResult = node.getChildrenMap();
            for (Map.Entry<Object, ? extends ConfigurationNode> entry : mapResult.entrySet()) {
                messages.put((String) entry.getKey(), MiniMessage.get().parse(mapResult.get(entry.getKey()).getString()));
            }
        } else {
            for (String section : sections) {
                ConfigurationNode sectionNode = node.getNode(section);
                Map<Object, ? extends ConfigurationNode> mapResult = sectionNode.getChildrenMap();
                for (Map.Entry<Object, ? extends ConfigurationNode> entry : mapResult.entrySet()) {
                    messages.put((String) entry.getKey(), MiniMessage.get().parse(mapResult.get(entry.getKey()).getString()));
                }
            }
        }
        this.prefix = messages.get("prefix");
        Log.info("Loaded messages " + messages.keySet());
    }

    /**
     * Returns a message with the given key/path
     *
     * @param key the name (key) of the message in the config
     * @return a message with the given key/path
     */
    public Component getMessage(String key) {
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
    public Component getFormattedMessage(String key) {
        return this.prefix.append(messages.get(key));
    }

    /**
     * Returns a message with the given key/path and
     * variable replacement for the specified player
     *
     * @param player the player to perform the variable replacement on
     * @param key the name (key) of the message in the config
     * @return a message with the given key/path
     */
    public Component getMessage(OfflinePlayer player, String key) {
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
    public Component getFormattedMessage(OfflinePlayer player, String key) {
        return this.prefix.append(getPlaceholderMessage(player, messages.get(key)));
    }

    /**
     * Returns the message with variables replaced
     *
     * @param player the player to base variable replacement from
     * @param message the message to send
     * @return the message with variables replaced
     */
    public Component getPlaceholderMessage(OfflinePlayer player, Component message) {
        message = message.replaceText(config -> config.matchLiteral("%player_name%").replacement(player.getName()).build());
        for (Map.Entry<String, Component> entry : messages.entrySet()) {
            message = message.replaceText(config -> config.matchLiteral("%" + entry.getKey() + "%").replacement(entry.getValue()).build());
        }
        return message;
    }

    /**
     * Sends a message to the specified player with
     * the variables replaced
     *
     * @param player the player to send the message to
     * @param message the message to send
     */
    public void sendMessage(Player player, Component message) {
        player.sendMessage(getPlaceholderMessage(player, message));
    }
}

package org.battleplugins.arena.arena.team;

import org.battleplugins.api.configuration.Configuration;
import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.api.inventory.item.ItemStack;
import org.battleplugins.api.inventory.item.ItemTypes;
import org.battleplugins.api.message.MessageStyle;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.configuration.item.ItemReader;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Manages and stores default teams.
 *
 * @author Redned
 */
public class ArenaTeamManager {

    private BattleArena plugin;

    private Map<String, ArenaTeam> defaultTeams;

    public ArenaTeamManager(BattleArena plugin) {
        this.plugin = plugin;
        this.defaultTeams = new LinkedHashMap<>();

        Configuration teamConfig = plugin.getConfigManager().getTeamsConfig();
        for (String key : teamConfig.getNode("teams").getCollectionValue(String.class)) {
            ConfigurationNode node = teamConfig.getNode("teams").getNode(key);
            ArenaTeam team = new ArenaTeam(node.getNode("name").getValue(String.class),
                    MessageStyle.getByChar(node.getNode("teamColor").getValue(String.class).replace("&", "")),
                    this.parseColor(node.getNode("armorColor").getValue(String.class)),
                    ItemReader.readItem(node.getNode("item").getValue(String.class)).orElse(ItemStack.builder().type(ItemTypes.AIR).build()),
                    -1);
            defaultTeams.put(node.getNode(team.getName()).getValue(String.class), team);
        }
    }

    /**
     * Returns a map of the default teams
     *
     * Key: the name of the team
     * Value: the ArenaTeam object
     *
     * @return a map of the default teams
     */
    public Map<String, ArenaTeam> getDefaultTeams() {
        return defaultTeams;
    }

    /**
     * Constructs a team with the given name. If
     * a team is not registered, a {@link NullPointerException}
     * is thrown
     *
     * @param name the name of the team
     * @param maxPlayers the maximum amount of players that can join
     * @return a newly constructed team with the given name
     */
    public ArenaTeam constructTeam(String name, int maxPlayers) {
        ArenaTeam team = defaultTeams.get(name).clone();
        team.maxPlayers = maxPlayers;
        return team;
    }

    private Color parseColor(String color) {
        // Check if the color is octal, decimal or hexadecimal
        if (!color.contains(",")) {
            try {
                return Color.decode(color);
            } catch (Exception ex) {
                return new Color(0, 0, 0);
            }
        }
        // Must be rgb, so check for that
        StringTokenizer tokenizer = new StringTokenizer(color, ",");
        int r = Integer.parseInt(tokenizer.nextToken());
        int g = Integer.parseInt(tokenizer.nextToken());
        int b = Integer.parseInt(tokenizer.nextToken());
        return new Color(r, g, b);
    }
}

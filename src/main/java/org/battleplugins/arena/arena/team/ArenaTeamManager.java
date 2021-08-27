package org.battleplugins.arena.arena.team;

import net.kyori.adventure.text.format.TextColor;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.util.Log;
import org.bukkit.configuration.Configuration;

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
        Map<Object, ? extends ConfigurationNode> mapResult = teamConfig.getNode("teams").getChildrenMap();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : mapResult.entrySet()) {
            ConfigurationNode node = mapResult.get(entry.getKey());
            ArenaTeam team = new ArenaTeam((String) node.getKey(),
                    node.getNode("name").getString(),
                    TextColor.fromHexString(node.getNode("teamColor").getString().replace("&", "")),
                    this.parseColor(node.getNode("armorColor").getString()),
                    ItemReader.readItem(node.getNode("item").getString()).orElse(ItemStack.builder().type(ItemTypes.AIR).build()),
                    -1);
            defaultTeams.put(team.getId(), team);
        }
        Log.info("Loaded teams " + defaultTeams.keySet());
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

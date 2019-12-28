package org.battleplugins.arena.arena.team;

import lombok.AccessLevel;
import lombok.Getter;

import org.battleplugins.api.configuration.Configuration;
import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.arena.BattleArena;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages and stores default teams.
 *
 * @author Redned
 */
@Getter
public class ArenaTeamManager {

    @Getter(AccessLevel.NONE)
    private BattleArena plugin;

    /**
     * A map of the default teams
     *
     * Key: the name of the team
     * Value: the ArenaTeam object
     *
     * @return a map of the default teams
     */
    private Map<String, ArenaTeam> defaultTeams;

    public ArenaTeamManager(BattleArena plugin) {
        this.plugin = plugin;
        this.defaultTeams = new LinkedHashMap<>();

        Configuration teamConfig = plugin.getConfigManager().getTeamsConfig();
        for (String key : teamConfig.getNode("teams").getCollectionValue(String.class)) {
            ConfigurationNode node = teamConfig.getNode("teams").getNode(key);
            ArenaTeam team = new ArenaTeam(node.getNode("name").getValue(String.class),
                    node.getNode("teamColor").getValue(String.class),
                    node.getNode("armorColor").getValue(String.class),
                    node.getNode("item").getValue(String.class), -1);
            defaultTeams.put(node.getNode(team.getName()).getValue(String.class), team);
        }
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
}

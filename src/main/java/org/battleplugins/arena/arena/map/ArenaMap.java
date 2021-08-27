package org.battleplugins.arena.arena.map;

import com.google.common.base.Preconditions;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.team.ArenaTeam;
import org.battleplugins.arena.match.Match;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The physical map associated with a {@link Match}.
 *
 * @author Redned
 */
public class ArenaMap {

    private String id;
    private String name;

    private Arena arena;

    private final Map<ArenaTeam, List<Location>> spawns = new HashMap<>();

    private ArenaMap() {
    }

    /**
     * Returns the id of this map
     *
     * @return the id of this map
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the name of the map
     *
     * @return the name of the map
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the arena for this map
     *
     * @return the arena for this map
     */
    public Arena getArena() {
        return this.arena;
    }

    /**
     * Returns a map of spawn spawns associated with
     * this map.
     *
     * Key: the team the location is associated with
     * Value: a list of location associated with the team
     *
     * @return a map of spawn locations for this map
     */
    public Map<ArenaTeam, List<Location>> getSpawns() {
        return this.spawns;
    }

    /**
     * Returns a new map builder
     *
     * @return a new map builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ArenaMap map = new ArenaMap();

        private final Map<String, List<Location>> spawns = new HashMap<>();

        public Builder id(String id) {
            this.map.id = id;
            return this;
        }

        public Builder name(String name) {
            this.map.name = name;
            return this;
        }

        public Builder spawns(String team, Location... spawns) {
            return this.spawns(team, Arrays.asList(spawns));
        }

        public Builder spawns(String team, List<Location> spawns) {
            List<Location> mapSpawns = this.spawns.getOrDefault(team, new ArrayList<>());
            mapSpawns.addAll(spawns);
            this.spawns.put(team, mapSpawns);
            return this;
        }

        public Builder spawns(Map<String, List<Location>> spawns) {
            this.spawns.putAll(spawns);
            return this;
        }

        public Builder arena(Arena arena) {
            this.map.arena = arena;
            return this;
        }

        public ArenaMap build() {
            Preconditions.checkNotNull(this.map.id, "Map ID cannot be null");
            Preconditions.checkNotNull(this.map.name, "Map name cannot be null");
            Preconditions.checkNotNull(this.map.arena, "Map Arena cannot be null");

            // TODO: Add per-arena team support
            for (Map.Entry<String, List<Location>> spawnEntry : this.spawns.entrySet()) {
                this.map.spawns.put(BattleArena.getInstance().getArenaManager().getTeamManager().getDefaultTeams().get(spawnEntry.getKey()), spawnEntry.getValue());
            }
            return this.map;
        }
    }
}

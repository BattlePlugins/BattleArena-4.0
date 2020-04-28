package org.battleplugins.arena.arena.map;

import org.battleplugins.api.world.Location;
import org.battleplugins.arena.arena.team.ArenaTeam;
import org.battleplugins.arena.match.Match;

import java.util.HashMap;
import java.util.Map;

/**
 * The physical map associated with a {@link Match}.
 *
 * @author Redned
 */
public class ArenaMap {

    private String name;

    private Map<ArenaTeam, Location> locations;

    public ArenaMap(String name) {
        this(name, new HashMap<>());
    }

    public ArenaMap(String name, Map<ArenaTeam, Location> locations) {
        this.name = name;
        this.locations = locations;
    }

    /**
     * Returns the name of the map
     *
     * @return the name of the map
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a map of spawn locations associated with
     * this map.
     *
     * Key: the team the location is associated with
     * Value: the location
     *
     * @return a map of spawn locations for this map
     */
    public Map<ArenaTeam, Location> getLocations() {
        return locations;
    }
}

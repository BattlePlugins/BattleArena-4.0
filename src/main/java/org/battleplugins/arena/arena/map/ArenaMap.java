package org.battleplugins.arena.arena.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
@Getter
@AllArgsConstructor
public class ArenaMap {

    /**
     * The name of the map
     *
     * @return the name of the map
     */
    private String name;

    /**
     * The map of spawn locations associated with
     * this map.
     *
     * Key: the team the location is associated with
     * Value: the location
     *
     * @return a map of spawn locations for this map
     */
    private Map<ArenaTeam, Location> locations = new HashMap<>();
}

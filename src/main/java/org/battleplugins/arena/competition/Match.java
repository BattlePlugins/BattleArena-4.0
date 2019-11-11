package org.battleplugins.arena.competition;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;

/**
 * Represents an active {@link Arena} in the form of a match.
 * 
 * A match has a waiting queue, with options regarding it generally
 * defined inside a configuration file. These are started when enough
 * when enough people are in the queue as defined in the configuration
 * file associated with the {@link Arena}.
 * 
 * @author Redned
 */
public class Match extends Competition {

    public Match(BattleArena plugin, Arena arena) {
        super(plugin, arena);
    }
}

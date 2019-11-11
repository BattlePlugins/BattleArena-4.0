package org.battleplugins.arena.competition;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;

/**
 * Represents an active {@link Arena} in the form of an event.
 * 
 * Events are similar to matches, but need to be started by
 * an administrator. By default, these can be started with the
 * 'start' subcommand associated with it and there is a join 
 * phase in which players are able to join it. 
 * 
 * Events can however be extended and expanded upon, similarly
 * to how {@link Tournament} works.
 * 
 * @author Redned
 */
public class Event extends Competition {

    public Event(BattleArena plugin, Arena arena) {
        super(plugin, arena);
    }
}

package org.battleplugins.arena.match;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;

/**
 * Represents an active {@link Arena} in the form of a tournament.
 * 
 * A tournament represents an {@link Event} that has multiple 
 * different phases and stages. These can be configured to run
 * for any {@link Match} or {@link Event}, but have to be
 * manually started by an administrator.
 * 
 * @author Redned
 */
public class Tournament extends Event {

    private Match parent;

    public Tournament(BattleArena plugin, Match parent) {
        super(plugin, parent.getArena());

        this.parent = parent;
    }
}

package org.battleplugins.arena.arena;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import mc.alk.battlecore.executor.CustomCommandExecutor;

import org.battleplugins.arena.competition.Event;
import org.battleplugins.arena.competition.Match;
import org.battleplugins.arena.competition.Tournament;
import org.battleplugins.arena.competition.victorycondition.VictoryCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Main arena class. Custom arenas need to extend this class in order
 * to be properly added into the arena registry.
 * 
 * This is not the same as the physical arena in which the competition
 * is currently taking place in. The physical competition can either be
 * a {@link Match}, {@link Event} or {@link Tournament}. One of these exists
 * for each actively running competition. 
 * 
 * @author Redned
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Arena {

    /**
     * The name of the arena
     * 
     * @return the name of the arena
     */
    protected String name;

    /**
     * The arena type
     * 
     * @return the arena type
     */
    protected ArenaType arenaType;
    
    /**
     * The command executor for this arena
     * 
     * @return the command executor for this arena
     */
    private CustomCommandExecutor executor;

    /**
     * The list of victory conditions for this arena
     *
     * @return the list of victory conditions for this arena
     */
    private List<VictoryCondition> victoryConditions = new ArrayList<>();
}

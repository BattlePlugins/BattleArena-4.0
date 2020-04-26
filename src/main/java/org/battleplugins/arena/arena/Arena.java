package org.battleplugins.arena.arena;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import mc.alk.battlecore.executor.CustomCommandExecutor;

import org.battleplugins.arena.arena.map.ArenaMap;
import org.battleplugins.arena.match.Match;
import org.battleplugins.arena.match.Event;
import org.battleplugins.arena.match.Tournament;
import org.battleplugins.arena.match.victorycondition.VictoryCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Main arena class. Custom arenas need to extend this class in order
 * to be properly added into the arena registry.
 * 
 * This is not the same as the physical arena in which the match
 * is currently taking place in. The physical match can either be
 * a {@link Match}, {@link Event} or {@link Tournament}. One of these exists
 * for each actively running match.
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
     * The command executor for this arena
     * 
     * @return the command executor for this arena
     */
    private CustomCommandExecutor executor;

    /**
     * A list of matches active with this Arena
     *
     * @return a list of match with this Arena
     */
    private List<Match> matches = new ArrayList<>();

    /**
     * The list of victory conditions for this arena
     *
     * @return the list of victory conditions for this arena
     */
    private List<VictoryCondition> victoryConditions = new ArrayList<>();

    /**
     * The message handler to use for this arena
     *
     * @return the message handler to use for this arena
     */
    private ArenaMessageHandler messageHandler;

    /**
     * A list of available maps 
     */
    private List<ArenaMap> availableMaps;
}

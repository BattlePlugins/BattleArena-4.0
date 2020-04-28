package org.battleplugins.arena.arena;

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
public class Arena {

    protected String name;

    private CustomCommandExecutor executor;

    private List<Match> matches = new ArrayList<>();
    private List<VictoryCondition> victoryConditions = new ArrayList<>();

    private ArenaMessageHandler messageHandler;

    private List<ArenaMap> availableMaps = new ArrayList<>();

    Arena() {
    }

    /**
     * Returns the name of the arena
     *
     * @return the name of the arena
     */
    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the command executor for this arena
     *
     * @return the command executor for this arena
     */
    public CustomCommandExecutor getExecutor() {
        return executor;
    }

    void setExecutor(CustomCommandExecutor executor) {
        this.executor = executor;
    }

    /**
     * Returns a list of matches active with this Arena
     *
     * @return a list of match with this Arena
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * Returns the list of victory conditions for this arena
     *
     * @return the list of victory conditions for this arena
     */
    public List<VictoryCondition> getVictoryConditions() {
        return victoryConditions;
    }

    /**
     * Returns the message handler to use for this arena
     *
     * @return the message handler to use for this arena
     */
    public ArenaMessageHandler getMessageHandler() {
        return messageHandler;
    }

    void setMessageHandler(ArenaMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Returns a list of available maps
     *
     * @return a list of available maps
     */
    public List<ArenaMap> getAvailableMaps() {
        return availableMaps;
    }
}

package org.battleplugins.arena.match.state.option;

import org.battleplugins.arena.match.Match;
import org.bukkit.entity.Player;

/**
 * Represents a state option for a match.
 * 
 * @author Redned
 */
public abstract class StateOption {

    private String name;

    public StateOption(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the state option
     *
     * @return the name of the state option
     */
    public String getName() {
        return name;
    }

    /**
     * Calls the state option
     * 
     * @param player the player to run this option on
     * @param match the match
     */
    public abstract void runOption(Player player, Match match);
}

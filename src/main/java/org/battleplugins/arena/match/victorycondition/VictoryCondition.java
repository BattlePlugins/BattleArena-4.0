package org.battleplugins.arena.match.victorycondition;

import org.battleplugins.arena.match.Match;

/**
 * Represents a victory condition for a match.
 * 
 * @author Redned
 */
public abstract class VictoryCondition {

    private String name;

    public VictoryCondition(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the victory condition
     *
     * @return the name of the victory condition
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the victory condition was met
     * 
     * @param match the match
     */
    public abstract boolean checkCondition(Match match);
}

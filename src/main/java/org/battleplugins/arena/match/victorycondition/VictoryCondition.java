package org.battleplugins.arena.match.victorycondition;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.battleplugins.arena.match.Match;

/**
 * Represents a victory condition for a match.
 * 
 * @author Redned
 */
@Getter
@RequiredArgsConstructor
public abstract class VictoryCondition {

    /**
     * The name of the victory condition
     * 
     * @return the name of the victory condition
     */
    @NonNull
    private String name;
    
    /**
     * Checks if the victory condition was met
     * 
     * @param match the match
     */
    public abstract boolean checkCondition(Match match);
}

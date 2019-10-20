package org.battleplugins.arena.competition.victorycondition;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.battleplugins.arena.competition.Competition;

/**
 * Represents a victory condition for a competition.
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
     * @param competition the competition
     */
    public abstract boolean checkCondition(Competition competition);
}

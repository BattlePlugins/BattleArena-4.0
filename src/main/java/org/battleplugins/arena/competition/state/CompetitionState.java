package org.battleplugins.arena.competition.state;

import lombok.Builder;
import lombok.Getter;

/**
 * A state in a competition.
 * 
 * @author Redned
 */
@Builder 
@Getter
public class CompetitionState {
    
    /**
     * The name of the competition state
     * 
     * @return the name of the competition state
     */
    private String name;

    /**
     * Aliases of the competition state
     * 
     * @return aliases of the competition states
     */
    private String[] aliases;
    
    protected CompetitionState(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        
        CompetitionStates.competitionStates.add(this);
    }
}

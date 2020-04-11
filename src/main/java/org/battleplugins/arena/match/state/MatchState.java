package org.battleplugins.arena.match.state;

import lombok.Builder;
import lombok.Getter;

/**
 * A state in a match.
 * 
 * @author Redned
 */
@Builder 
@Getter
public class MatchState {
    
    /**
     * The name of the match state
     * 
     * @return the name of the match state
     */
    private String name;

    /**
     * Aliases of the match state
     * 
     * @return aliases of the match states
     */
    private String[] aliases;
    
    protected MatchState(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        
        MatchStates.matchStates.add(this);
    }
}

package org.battleplugins.arena.match.state.option;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.arena.match.Match;

/**
 * Represents a state option for a match.
 * 
 * @author Redned
 */
@Getter
@RequiredArgsConstructor
public abstract class StateOption {

    /**
     * The name of the state option
     * 
     * @return the name of the state option
     */
    @NonNull
    private String name;
    
    /**
     * Calls the state option
     * 
     * @param player the player to run this option on
     * @param match the match
     */
    public abstract void runOption(Player player, Match match);
}

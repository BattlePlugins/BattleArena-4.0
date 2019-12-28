package org.battleplugins.arena.competition.state.option;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.arena.competition.Competition;

/**
 * Represents a state option for a competition.
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
     * @param competition the competition
     */
    public abstract void runOption(Player player, Competition competition);
}

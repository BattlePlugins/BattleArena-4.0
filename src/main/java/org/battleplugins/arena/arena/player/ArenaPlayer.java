package org.battleplugins.arena.arena.player;

import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mc.alk.mc.MCPlayer;

import org.battleplugins.arena.competition.Competition;

/**
 * Represents a player in a competition.
 * 
 * @author Redned
 */
@Getter
@RequiredArgsConstructor
public class ArenaPlayer {

    /**
     * The current competition the player is in
     */
    private Competition currentCompetition;
    
    /**
     * The current team the player is on
     */
    private ArenaTeam currentTeam;
    
    /**
     * If the player is ready (clicked block or has voted for a map)
     * 
     * @param ready if the player is ready
     * @return if the player is ready
     */
    @Setter
    private boolean ready = false;
    
    /**
     * If the player is remaining in the competition
     * 
     * @param remainingInCompetition if the player is remaining in the competition
     * @return if the player is remaining in the competition
     */
    @Setter
    private boolean remainingInCompetition = false;
    
    /**
     * The amount of kills the player has
     * 
     * @param kills the amount of kills the player has
     * @return the amount of kills the player has
     */
    @Setter
    private int kills = 0;
    
    /**
     * The amount of deaths the player has
     * 
     * @param deaths the amount of deaths the player has
     * @return the amount of deaths the player has
     */
    @Setter
    private int deaths = 0;
    
    /**
     * The player associated with this instance
     *
     * @return the player associated with this instance
     */
    @NonNull
    private MCPlayer player;
    
    /**
     * Returns if the player is in a competition
     * 
     * @return if the player is in a competition
     */
    public boolean isInCompetition() {
        return currentCompetition != null;
    }
    
    /**
     * Returns the current competition the player is in
     * 
     * @return the current competition the player is in
     */
    public Optional<Competition> getCurrentCompetition() {
        return Optional.ofNullable(currentCompetition);
    }
    
    /**
     * Returns the current team the player is on
     * 
     * @return the current team the player is on
     */
    public Optional<ArenaTeam> getCurrentTeam() {
        return Optional.ofNullable(currentTeam);
    }
    
    /**
     * Sets the current competition the player is in, set to null
     * if they are leaving a competition
     * 
     * @param competition the competition to put the player in
     */
    public void setCurrentCompetition(Competition competition) {
        this.currentCompetition = competition;
        if (competition == null) {
            this.currentTeam = null;
            this.ready = false;
        }
    }
}

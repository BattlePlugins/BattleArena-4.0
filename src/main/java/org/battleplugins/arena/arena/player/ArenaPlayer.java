package org.battleplugins.arena.arena.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.arena.arena.classes.ArenaClass;
import org.battleplugins.arena.arena.team.ArenaTeam;
import org.battleplugins.arena.match.Match;

import java.util.Optional;

/**
 * Represents a player in a match.
 * 
 * @author Redned
 */
@Getter
@RequiredArgsConstructor
public class ArenaPlayer {

    /**
     * The current match the player is in
     */
    private Match currentMatch;
    
    /**
     * The current team the player is on
     */
    @Setter
    private ArenaTeam currentTeam;

    /**
     * The class the player currently has
     */
    private ArenaClass currentClass;

    /**
     * If the player is ready (clicked block or has voted for a map)
     * 
     * @param ready if the player is ready
     * @return if the player is ready
     */
    @Setter
    private boolean ready = false;
    
    /**
     * If the player is remaining in the match
     * 
     * @param remainingInMatch if the player is remaining in the match
     * @return if the player is remaining in match
     */
    @Setter
    private boolean remainingInMatch = false;
    
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
    private Player player;
    
    /**
     * Returns if the player is in a match
     * 
     * @return if the player is in a match
     */
    public boolean isInMatch() {
        return currentMatch != null;
    }

    /**
     * Returns if the player has a class
     *
     * @return if the player has a class
     */
    public boolean hasClass() { return currentClass != null; }
    
    /**
     * Returns the current match the player is in
     * 
     * @return the current match the player is in
     */
    public Optional<Match> getCurrentMatch() {
        return Optional.ofNullable(currentMatch);
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
     * Sets the current match the player is in, set to null
     * if they are leaving a match
     * 
     * @param match the match to put the player in
     */
    public void setCurrentMatch(Match match) {
        this.currentMatch = match;
        if (match == null) {
            this.currentTeam = null;
            this.ready = false;
        }
    }

    /**
     * Set the current class for this player
     *
     * @param newClass List of new items for the class
     */
    public void setCurrentClass(ArenaClass newClass) {
        this.currentClass = newClass;
    }
}

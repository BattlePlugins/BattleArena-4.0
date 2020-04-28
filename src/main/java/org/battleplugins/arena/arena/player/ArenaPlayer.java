package org.battleplugins.arena.arena.player;

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
public class ArenaPlayer {

    private Player player;

    private Match currentMatch;
    private ArenaTeam currentTeam;
    private ArenaClass currentClass;

    private boolean ready = false;
    private boolean remainingInMatch = false;

    private int kills = 0;
    private int deaths = 0;

    public ArenaPlayer(Player player) {
        this.player = player;
    }

    /**
     * Returns if the player is in a match
     *
     * @return if the player is in a match
     */
    public boolean isInMatch() {
        return currentMatch != null;
    }

    /**
     * Returns the current match the player is in
     *
     * @return the current match the player is in
     */
    public Optional<Match> getCurrentMatch() {
        return Optional.ofNullable(currentMatch);
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
     * Returns the current team the player is on
     *
     * @return the current team the player is on
     */
    public Optional<ArenaTeam> getCurrentTeam() {
        return Optional.ofNullable(currentTeam);
    }

    /**
     * Sets the current team the player is on
     *
     * @param currentTeam the team to set the player on
     */
    public void setCurrentTeam(ArenaTeam currentTeam) {
        this.currentTeam = currentTeam;
    }

    /**
     * Returns if the player has a class
     *
     * @return if the player has a class
     */
    public boolean hasClass() {
        return currentClass != null;
    }

    /**
     * Returns the class the player currently has
     *
     * @return the class the player currently has
     */
    public ArenaClass getCurrentClass() {
        return currentClass;
    }

    /**
     * Set the current class for this player
     *
     * @param newClass List of new items for the class
     */
    public void setCurrentClass(ArenaClass newClass) {
        this.currentClass = newClass;
    }

    /**
     * Returns if the player is ready (clicked block or has voted for a map)
     *
     * @return if the player is ready
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Sets if the player is ready (clicked block or has voted for a map)
     *
     * @param ready if the player is ready
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Returns if the player is remaining in the match
     *
     * @return if the player is remaining in the match
     */
    public boolean isRemainingInMatch() {
        return remainingInMatch;
    }

    /**
     * Sets if the player is remaining in the match
     *
     * @param remainingInMatch if the player is remaining in the match
     */
    public void setRemainingInMatch(boolean remainingInMatch) {
        this.remainingInMatch = remainingInMatch;
    }

    /**
     * Returns the amount of kills the player has for their
     * current match
     *
     * @return the amount of kills the player has
     */
    public int getKills() {
        return kills;
    }

    /**
     * Sets the amount of kills the player has for their current match
     *
     * @param kills the amount of kills the player has
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * Returns the amount of deaths the player has for their
     * current match
     *
     * @return the amount of deaths the player has
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Sets the amount of deaths the player has for their current match
     *
     * @param deaths the amount of deaths the player has
     */
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    /**
     * Returns the player associated with this instance
     *
     * @return the player associated with this instance
     */
    public Player getPlayer() {
        return player;
    }
}

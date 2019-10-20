package org.battleplugins.arena.competition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.player.ArenaPlayer;
import org.battleplugins.arena.arena.player.ArenaTeam;
import org.battleplugins.arena.competition.state.CompetitionState;
import org.battleplugins.arena.competition.state.CompetitionStates;

/**
 * Represents an active {@link Arena}. One of these is present for
 * each physical arena. Can either be an {@link Event}, {@link Match}
 * or {@link Tournament}.
 * 
 * The behavior for this class changes slightly when players are allowed
 * to vote for maps. Rather than assigning a competition for each map upon
 * the plugin being enabled, instead a set amount of competitions is 
 * created as defined in the Arena configuration. 
 * 
 * @author Redned
 */
@Getter
@RequiredArgsConstructor
public abstract class Competition {

    /**
     * The {@link Arena} associated with the competition
     * 
     * @return the {@link Arena} associated with the competition
     */
    @NonNull 
    protected final Arena arena;
    
    /**
     * The current state of the competition
     * 
     * @return the current state of the competition
     */
    protected CompetitionState state = CompetitionStates.NONE;
    
    /**
     * The teams in the competition
     * 
     * @return the teams in the competition
     */
    protected List<ArenaTeam> teams = new ArrayList<>();
    
    /**
     * Returns a list of all the players in the competition
     * 
     * @return a list of all the players in the competition
     */
    public List<ArenaPlayer> getPlayers() {
        List<ArenaPlayer> players = new ArrayList<>();
        teams.forEach(team -> players.addAll(team.getPlayers()));
        return players;
    }
    
    /**
     * Returns a list of the remaining players
     * 
     * @return a list of the remaining players
     */
    public List<ArenaPlayer> getRemainingPlayers() {
        return getPlayers().stream().filter(ArenaPlayer::isRemainingInCompetition).collect(Collectors.toList());
    }
    
    /**
     * Returns a list of remaining teams
     * 
     * @return a list of remaining teams
     */
    public List<ArenaTeam> getRemainingTeams() {
        return getTeams().stream().filter(ArenaTeam::isRemainingInCompetition).collect(Collectors.toList());
    }
    
    /**
     * Returns if this class is the same as the competition class given
     * 
     * @param competitionClass the competition class
     * @return if this class is the same as the competition class given
     */
    public <T extends Competition> boolean is(Class<T> competitionClass) {
        return competitionClass.isInstance(this);
    }
    
    /**
     * Returns this class casted to the given competition class
     * 
     * @param competitionClass the competition class
     * @return this class casted to the given competition class
     */
    public <T extends Competition> T as(Class<T> competitionClass) {
        return is(competitionClass) ? competitionClass.cast(this) : null;
    }
}

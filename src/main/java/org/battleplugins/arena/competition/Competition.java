package org.battleplugins.arena.competition;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.player.ArenaPlayer;
import org.battleplugins.arena.arena.player.ArenaTeam;
import org.battleplugins.arena.competition.state.CompetitionState;
import org.battleplugins.arena.competition.state.CompetitionStates;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
     * Key: the name of the team
     * Value: the arena team
     *
     * @return the teams in the competition
     */
    protected Map<String, ArenaTeam> teams = new LinkedHashMap<>();

    /**
     * The name of this competition, usually
     * the name of the map associated with it. Will return
     * empty if the competition is not assigned to a
     * map (yet)
     */
    private String name;

    /**
     * Returns a list of all the players in the competition
     * 
     * @return a list of all the players in the competition
     */
    public List<ArenaPlayer> getPlayers() {
        List<ArenaPlayer> players = new ArrayList<>();
        teams.forEach((teamName, team) -> players.addAll(team.getPlayers()));
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
        return getTeams().values().stream().filter(ArenaTeam::isRemainingInCompetition).collect(Collectors.toList());
    }

    /**
     * The name of this competition, usually
     * the name of the map associated with it. Will return
     * empty if the competition is not assigned to a
     * map (yet).
     *
     * @return the name of this competition
     */
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    /**
     * Returns if this competition is open
     *
     * @return if this competition is open
     */
    public boolean isOpen() {
        if (state.equals(CompetitionStates.ON_PRE_START))
            return true;

        if (state.equals(CompetitionStates.ON_START))
            return true;

        if (state.equals(CompetitionStates.ON_BEGINNING))
            return true;

        if (state.equals(CompetitionStates.NONE))
            return true;

        return false;
    }

    /**
     * Adds a player to the competition
     *
     * @param player the player to add to the competition
     * @param teamName the name of the team to add the player to
     */
    public void addPlayer(ArenaPlayer player, @Nullable String teamName) {
        player.setCurrentCompetition(this);
        player.setDeaths(0);
        player.setKills(0);

        if (teams.containsKey(teamName)) {
            teams.get(teamName).getPlayers().add(player);
        } else {
            if (teams.isEmpty()) {
                // TODO: Pull team names from a separate config
                ArenaTeam team = new ArenaTeam(teamName == null ? "Red" : teamName);
                team.getPlayers().add(player);
                player.setCurrentTeam(team);
                teams.put(teamName, team);
            } else {
                Random random = ThreadLocalRandom.current();
                ArenaTeam team = teams.values().toArray(new ArenaTeam[0])[random.nextInt(teams.size())];
                team.getPlayers().add(player);
                player.setCurrentTeam(team);
            }
        }
    }

    /**
     * Removes a player from the competition
     *
     * @param player the player to remove from the competition
     * @param teamName the name of the team to remove the player from
     */
    public void removePlayer(ArenaPlayer player, String teamName) {
        player.setCurrentTeam(null);
        player.setCurrentCompetition(null);
        player.setReady(false);

        if (!getPlayers().contains(player))
            return;

        if (teams.containsKey(teamName)) {
            teams.get(teamName).getPlayers().remove(player);
        } else {
            for (ArenaTeam team : teams.values()) {
                if (team.getPlayers().contains(player)) {
                    team.getPlayers().remove(player);
                }
            }
        }
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

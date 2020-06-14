package org.battleplugins.arena.match;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.map.ArenaMap;
import org.battleplugins.arena.arena.player.ArenaPlayer;
import org.battleplugins.arena.arena.team.ArenaTeam;
import org.battleplugins.arena.match.state.MatchState;
import org.battleplugins.arena.match.state.MatchStates;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Represents an active {@link Arena}. One of these is present for
 * each physical arena.
 * 
 * The behavior for this class changes slightly when players are allowed
 * to vote for maps. Rather than assigning a match for each map upon
 * the plugin being enabled, instead a set amount of matches is
 * created as defined in the Arena configuration. 
 * 
 * @author Redned
 */
public class Match {

    private final BattleArena plugin;

    protected final Arena arena;
    protected MatchState state = MatchStates.NONE;

    private ArenaMap map;

    protected Map<String, ArenaTeam> teams = new LinkedHashMap<>();

    private AtomicInteger nextTeamIndex = new AtomicInteger();

    public Match(BattleArena plugin, Arena arena) {
        this(plugin, arena, null);
    }

    public Match(BattleArena plugin, Arena arena, ArenaMap map) {
        this.plugin = plugin;
        this.arena = arena;
        this.map = map;
    }

    /**
     * Returns the {@link Arena} associated with the march
     *
     * @return the arena associated with the match
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Returns the current {@link MatchState}
     *
     * @return the current match state
     */
    public MatchState getState() {
        return state;
    }

    /**
     * Returns the map for this match. Empty if a
     * map has not yet been selected
     *
     * @return the map for this match
     */
    public Optional<ArenaMap> getMap() {
        return Optional.ofNullable(map);
    }

    /**
     * Returns the teams in the match
     *
     * Key: the name of the team
     * Value: the arena team
     *
     * @return the teams in the match
     */
    public Map<String, ArenaTeam> getTeams() {
        return teams;
    }

    /**
     * Returns a list of all the players in the match
     * 
     * @return a list of all the players in the match
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
        return getPlayers().stream().filter(ArenaPlayer::isRemainingInMatch).collect(Collectors.toList());
    }
    
    /**
     * Returns a list of remaining teams
     * 
     * @return a list of remaining teams
     */
    public List<ArenaTeam> getRemainingTeams() {
        return getTeams().values().stream().filter(ArenaTeam::isRemainingInMatch).collect(Collectors.toList());
    }

    /**
     * Returns if this match is open
     *
     * @return if this match is open
     */
    public boolean isOpen() {
        if (state.equals(MatchStates.ON_PRE_START))
            return true;

        if (state.equals(MatchStates.ON_START))
            return true;

        if (state.equals(MatchStates.ON_BEGINNING))
            return true;

        return state.equals(MatchStates.NONE);
    }

    /**
     * Adds a player to the match
     *
     * @param player the player to add to the match
     * @param teamName the name of the team to add the player to
     */
    public void addPlayer(ArenaPlayer player, @Nullable String teamName) {
        player.setCurrentMatch(this);
        player.setDeaths(0);
        player.setKills(0);

        // If the team is already registered
        if (teams.containsKey(teamName)) {
            ArenaTeam team = teams.get(teamName);
            // If team is full, create a new team. -1 indicates that the team can never "fill up"
            if (team.getPlayers().size() >= team.getMaxPlayers() && team.getMaxPlayers() != -1) {
                addToAvailableTeam(player);
            } else {
                teams.get(teamName).getPlayers().add(player);
                player.setCurrentTeam(team);
            }
        } else {
            // If the teams are empty and a new one needs to be made
            if (teams.isEmpty()) {
                String finalTeamName = teamName;
                if (teamName == null) {
                    finalTeamName = plugin.getArenaManager().getTeamManager().getDefaultTeams().keySet().toArray(new String[0])[nextTeamIndex.getAndIncrement()];
                }
                // TODO: Set team max players from match file
                ArenaTeam team = plugin.getArenaManager().getTeamManager().constructTeam(finalTeamName, -1);
                team.getPlayers().add(player);
                player.setCurrentTeam(team);
                teams.put(finalTeamName, team);
            } else {
                Random random = ThreadLocalRandom.current();
                ArenaTeam team = teams.values().toArray(new ArenaTeam[0])[random.nextInt(teams.size())];
                if (team.getPlayers().size() >= team.getMaxPlayers() && team.getMaxPlayers() != -1) {
                    addToAvailableTeam(player);
                }
                team.getPlayers().add(player);
                player.setCurrentTeam(team);
            }
        }
    }

    /**
     * Removes a player from the match
     *
     * @param player the player to remove from the match
     */
    public void removePlayer(ArenaPlayer player) {
        player.setCurrentTeam(null);
        player.setCurrentMatch(null);
        player.setReady(false);

        if (!getPlayers().contains(player))
            return;

        for (ArenaTeam team : teams.values()) {
            team.getPlayers().remove(player);
            return;
        }
    }

    /**
     * Returns if this class is the same as the match class given
     * 
     * @param matchClass the match class
     * @return if this class is the same as the match class given
     */
    public <T extends Match> boolean is(Class<T> matchClass) {
        return matchClass.isInstance(this);
    }
    
    /**
     * Returns this class casted to the given match class
     * 
     * @param matchClass the match class
     * @return this class casted to the given match class
     */
    public <T extends Match> T as(Class<T> matchClass) {
        return is(matchClass) ? matchClass.cast(this) : null;
    }

    /**
     * Adds a player to the next available team. This method may
     * be called if the team they're trying to join is full or if the
     * current teams are empty
     *
     * In an unlikely scenario, a player may try and join a match
     * where this method may end up being called due to the aforementioned
     * scenarios, with the exception being that there is not enough teams
     * created. This will result in the player being added to a team
     * that is already "full." While this is unlikely, it has been
     * added in order to prevent countless bugs that existed in the legacy
     * version of BattleArena.
     *
     * @param player the player to add to the next available team
     */
    private void addToAvailableTeam(ArenaPlayer player) {
        // TODO: Implement max team amounts
        int nextTeamIndex = this.nextTeamIndex.getAndIncrement();
        if (plugin.getArenaManager().getTeamManager().getDefaultTeams().size() >= nextTeamIndex) {
            plugin.getLogger().warning("Team size has been exceeded for arena " + arena.getName() + ". Filling up full teams instead.");
            nextTeamIndex = ThreadLocalRandom.current().nextInt(plugin.getArenaManager().getTeamManager().getDefaultTeams().size());
        }

        String nextTeamName = plugin.getArenaManager().getTeamManager().getDefaultTeams().keySet().toArray(new String[0])[nextTeamIndex];
        ArenaTeam nextTeam = plugin.getArenaManager().getTeamManager().constructTeam(nextTeamName, -1);
        nextTeam.getPlayers().add(player);
        player.setCurrentTeam(nextTeam);
        teams.put(nextTeamName, nextTeam);
    }
}

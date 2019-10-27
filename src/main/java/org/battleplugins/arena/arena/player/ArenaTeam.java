package org.battleplugins.arena.arena.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ArenaTeam {

    /**
     * The name of the team
     * 
     * @return the name of the team
     */
    @NonNull
    private String name;
    
    /**
     * The players in the team
     * 
     * @return the players in the team
     */
    private Set<ArenaPlayer> players = new HashSet<>();
    
    /**
     * Returns if the team is remaining in the competition
     * 
     * @return if the team is remaining in the competition
     */
    public boolean isRemainingInCompetition() {
        if (players.isEmpty())
            return false;
        
        for (ArenaPlayer player : players) {
            if (player.isInCompetition())
                return true;
        }
        
        return false;
    }
}

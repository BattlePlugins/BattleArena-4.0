package org.battleplugins.arena.arena.player;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
    private List<ArenaPlayer> players = new ArrayList<>();
    
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

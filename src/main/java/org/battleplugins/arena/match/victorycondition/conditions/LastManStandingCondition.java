package org.battleplugins.arena.match.victorycondition.conditions;

import org.battleplugins.arena.match.Match;
import org.battleplugins.arena.match.victorycondition.VictoryCondition;

/**
 * A victory condition called when there is only
 * a set amount of players or teams left in the game.
 *
 * @author Redned
 */
public class LastManStandingCondition extends VictoryCondition {

    private int peopleStanding = 1;

    private boolean accountTeams = true;
    
    public LastManStandingCondition(String name) {
        super(name);
    }

    @Override
    public boolean checkCondition(Match match) {
        if (accountTeams && match.getRemainingTeams().size() == 1)
            return true;

        return match.getRemainingPlayers().size() == peopleStanding;
    }
}

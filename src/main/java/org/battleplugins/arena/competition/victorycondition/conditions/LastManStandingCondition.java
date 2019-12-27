package org.battleplugins.arena.competition.victorycondition.conditions;

import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.configuration.ConfigProperty;
import org.battleplugins.arena.competition.victorycondition.VictoryCondition;

/**
 * A victory condition called when there is only
 * a set amount of players or teams left in the game.
 *
 * @author Redned
 */
public class LastManStandingCondition extends VictoryCondition {

    @ConfigProperty
    private int peopleStanding = 1;
    
    @ConfigProperty
    private boolean accountTeams = true;
    
    public LastManStandingCondition(String name) {
        super(name);
    }

    @Override
    public boolean checkCondition(Competition competition) {
        if (accountTeams && competition.getRemainingTeams().size() == 1)
            return true;

        return competition.getRemainingPlayers().size() == peopleStanding;
    }
}

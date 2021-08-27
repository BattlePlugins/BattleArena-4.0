package org.battleplugins.arena.match.victorycondition;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.match.victorycondition.conditions.*;

/**
 * Manages and stores default victory conditions.
 * 
 * @author Redned
 */
public class VictoryConditionManager{

    private static final String CONFIG_CONDITION_NAME = "conditionName";

    private BattleArena plugin;

    public VictoryConditionManager(BattleArena plugin) {
        this.plugin = plugin;
    }
}

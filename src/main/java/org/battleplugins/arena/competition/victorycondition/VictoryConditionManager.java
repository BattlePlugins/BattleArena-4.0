package org.battleplugins.arena.competition.victorycondition;

import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.competition.victorycondition.conditions.*;
import org.battleplugins.arena.configuration.ConfigPropertyManager;

import java.util.Map;
import java.util.Optional;

/**
 * Manages and stores default victory conditions.
 * 
 * @author Redned
 */
public class VictoryConditionManager extends ConfigPropertyManager<VictoryCondition> {

    private static final String CONFIG_CONDITION_NAME = "conditionName";

    private BattleArena plugin;

    public VictoryConditionManager(BattleArena plugin) {
        this.plugin = plugin;

        properties.put("LastManStanding", LastManStandingCondition.class);
    }

    /**
     * Returns a victory condition from the given configuration node.
     * Overloaded method of {@link #getProperty(String, ConfigurationNode)}
     * for easier understanding.
     *
     * @param node the node to get the condition from
     * @return a victory condition from the given configuration node
     */
    public Optional<VictoryCondition> getVictoryCondition(ConfigurationNode node) {
        return getProperty(CONFIG_CONDITION_NAME, node);
    }

    /**
     * A map of all the victory conditions. Everything in this
     * map is empty and is solely used to assign a victory condition to
     * a class. To get a VictoryCondition from a configuration path, use
     * the {@link #getVictoryCondition(ConfigurationNode)} method.
     *
     * Key: the name of the victory conditions
     * Value: the victory condition class
     *
     * @return a map of all the victory conditions
     */
    public Map<String, Class<? extends VictoryCondition>> getVictoryConditions() {
        return properties;
    }
}

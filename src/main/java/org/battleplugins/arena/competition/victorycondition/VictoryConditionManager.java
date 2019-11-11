package org.battleplugins.arena.competition.victorycondition;

import java.util.Map;
import java.util.Optional;

import mc.alk.battlecore.configuration.ConfigurationSection;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.competition.victorycondition.conditions.*;
import org.battleplugins.arena.config.ConfigPropertyManager;

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
     * Returns a victory condition from the given configuration section.
     * Overloaded method of {@link #getProperty(String, ConfigurationSection)}
     * for easier understanding.
     *
     * @param section the section to get the condition from
     * @return a victory condition from the given configuration section
     */
    public Optional<VictoryCondition> getVictoryCondition(ConfigurationSection section) {
        return getProperty(CONFIG_CONDITION_NAME, section);
    }

    /**
     * A map of all the victory conditions. Everything in this
     * map is empty and is solely used to assign a victory condition to
     * a class. To get a VictoryCondition from a configuration path, use
     * the {@link #getVictoryCondition(ConfigurationSection)} method.
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

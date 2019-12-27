package org.battleplugins.arena.competition.state.option;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.competition.state.option.options.*;
import org.battleplugins.arena.configuration.ConfigPropertyManager;

import java.util.Map;
import java.util.Optional;

/**
 * Manages and stores default state options.
 * 
 * @author Redned
 */
public class StateOptionManager extends ConfigPropertyManager<StateOption> {

    private BattleArena plugin;

    public StateOptionManager(BattleArena plugin) {
        this.plugin = plugin;

        properties.put("clearInventory", ClearInventoryOption.class);
        properties.put("changeGamemode", ChangeGamemodeStateOption.class);
        properties.put("giveEffects", GiveEffectsOption.class);
    }

    /**
     * Returns a state option from the given string.
     * Overloaded method of {@link #getProperty(String)}
     * for easier understanding.
     *
     * @param value the string to get the state option from
     * @return a state option from the given string
     */
    public Optional<StateOption> getStateOption(String value) {
        return getProperty(value);
    }

    /**
     * Returns a map of all the state options. Everything in this
     * map is empty and is solely used to assign a state option
     * to a class. To get a StateOption from a string, use the
     * {@link #getProperty(String)} method.
     *
     * Key: the name of the state option
     * Value: the state option class
     *
     * @return a map of all the state options
     */
    public Map<String, Class<? extends StateOption>> getStateOptions() {
        return properties;
    }
}

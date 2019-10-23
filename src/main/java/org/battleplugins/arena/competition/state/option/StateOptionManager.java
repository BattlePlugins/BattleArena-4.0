package org.battleplugins.arena.competition.state.option;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.competition.state.option.options.ClearInventoryOption;
import org.battleplugins.arena.competition.state.option.options.GiveEffectsOption;
import org.battleplugins.arena.configuration.ConfigProperty;

/**
 * Manages and stores default state options.
 * 
 * @author Redned
 */
public class StateOptionManager {

    private BattleArena plugin;

    /**
     * A map of all the state options. Everything in this
     * map is empty and is solely used to assign a state option
     * to a class. To get a StateOption from a string, use the
     * {@link #getStateOption(String)} method.
     * 
     * Key: the name of the state option
     * Value: the state option class
     * 
     * @return a map of all the state options
     */
    private Map<String, Class<? extends StateOption>> stateOptions = new HashMap<>();
    
    public StateOptionManager(BattleArena plugin) {
        this.plugin = plugin;

        stateOptions.put("clearInventory", ClearInventoryOption.class);
        stateOptions.put("giveEffects", GiveEffectsOption.class);
    }
    
    /**
     * Returns a state option with the given option string
     * 
     * @param option the option string
     * @return a state option with the given option string
     */
    public Optional<StateOption> getStateOption(String option) {
        String optionName = option.split("\\{")[0];
        if (!stateOptions.containsKey(optionName)) {
            plugin.getLogger().warning("State option " + option + " is not valid! Refer to the documentation for a full list of state options!");
            return Optional.empty();
        }
        
        Map<String, String> properties = new HashMap<>();
        if (option.contains("{")) {
            String optionStr = option.split("\\{")[1].trim();
            for (String property : optionStr.split(",")) {
                properties.put(property.split("=")[0], property.split("=")[1]);
            }
        }
            
        try { 
            Class<? extends StateOption> stateOptionClass = stateOptions.get(optionName);
            Constructor<? extends StateOption> stateOptionConstructor = stateOptionClass.getConstructor(String.class);
            stateOptionConstructor.setAccessible(true);
            
            StateOption stateOption = stateOptionConstructor.newInstance(optionName);
            for (Field field : stateOptionClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ConfigProperty.class))
                    continue;
                
                ConfigProperty stateProperty = field.getAnnotation(ConfigProperty.class);
                String propertyName = stateProperty.value().isEmpty() ? field.getName() : stateProperty.value();
                
                String property = properties.get(propertyName);
                if (property == null && stateProperty.required()) {
                    plugin.getLogger().warning("State option " + option + " is missing a required option, (" + propertyName + ")! Please make sure your competition file is correct!");
                    continue;
                }
                
                setPropertyValue(stateOption, field, propertyName);
                return Optional.of(stateOption);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            
        return Optional.empty();
    }
    
    private void setPropertyValue(StateOption option, Field field, String value) {
        field.setAccessible(true);
        try {
            field.set(option, field.getType().cast(value));
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            plugin.getLogger().warning("Failed to set state property " + value + " to field " + field.getName() + "!");
            ex.printStackTrace();
        } 
    }
}

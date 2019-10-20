package org.battleplugins.arena.competition.victorycondition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import mc.alk.battlecore.configuration.ConfigurationSection;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.configuration.ConfigProperty;
import org.battleplugins.arena.competition.victorycondition.conditions.LastManStandingCondition;

/**
 * Manages and stores default victory conditions.
 * 
 * @author Redned
 */
public class VictoryConditionManager {

    private BattleArena plugin;

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
    private Map<String, Class<? extends VictoryCondition>> victoryConditions = new HashMap<>();
    
    public VictoryConditionManager(BattleArena plugin) {
        this.plugin = plugin;
        
        victoryConditions.put("LastManStanding", LastManStandingCondition.class);
    }
    
    /**
     * Returns a victory condition with the given option string
     * 
     * @param section the configuration section
     * @return a victory condition with the given configuration path
     */
    public Optional<VictoryCondition> getVictoryCondition(ConfigurationSection section) {
        if (section == null) {
            plugin.getLogger().warning("Configuration section for victory condition " + section + " is not valid! Refer to the documentation for how to set up victory conditions!");
            return Optional.empty();
        }
        
        Map<String, String> properties = new HashMap<>();
        for (String key : section.getSections().getKeys(false)) {
            properties.put(key, section.getString(key));
        }
            
        String conditionName = properties.get("condition");
        if (!properties.containsKey(conditionName)) {
            plugin.getLogger().warning("No condition was defined in section " + section + "! Refer to the documentation for a list of victory conditions.");
            return Optional.empty();
        }
        
        try { 
            Class<? extends VictoryCondition> victoryConditionClass = victoryConditions.get(conditionName);
            Constructor<? extends VictoryCondition> victoryConditionConstructor = victoryConditionClass.getConstructor(String.class);
            victoryConditionConstructor.setAccessible(true);
            
            VictoryCondition victoryCondition = victoryConditionConstructor.newInstance(conditionName);
            for (Field field : victoryConditionClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ConfigProperty.class))
                    continue;
                
                ConfigProperty victoryProperty = field.getAnnotation(ConfigProperty.class);
                String propertyName = victoryProperty.value().isEmpty() ? field.getName() : victoryProperty.value();
                
                String property = properties.get(propertyName);
                if (property == null && victoryProperty.required()) {
                    plugin.getLogger().warning("Victory condition " + conditionName + " is missing a required option, (" + propertyName + ")! Please make sure your competition file is correct!");
                    continue;
                }
                
                setPropertyValue(victoryCondition, field, propertyName);
                return Optional.of(victoryCondition);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            
        return Optional.empty();
    }
    
    private void setPropertyValue(VictoryCondition option, Field field, String value) {
        field.setAccessible(true);
        try {
            field.set(option, field.getType().cast(value));
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            plugin.getLogger().warning("Failed to set victory condition " + value + " to field " + field.getName() + "!");
            ex.printStackTrace();
        } 
    }
}

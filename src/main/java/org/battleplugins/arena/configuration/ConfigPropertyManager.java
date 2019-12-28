package org.battleplugins.arena.configuration;

import mc.alk.battlecore.util.Log;

import org.battleplugins.api.configuration.ConfigurationNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Configuration property manager. Mainly used for
 * retrieving values from classes annotated with the
 * {@link ConfigProperty} annotation.
 *
 * @author Redned
 */
public class ConfigPropertyManager<T> {

    /**
     * A map of properties to keep in memory
     *
     * Key: the name of the property
     * Value: the property class
     *
     * @return a map of registries to keep in memory
     */
    protected Map<String, Class<? extends T>> properties = new HashMap<>();

    /**
     * Returns a property from the given configuration node
     *
     * @param rootProperty the root property (main key)
     * @param node the configuration node to get the property from
     * @return a property from the given configuration node
     */
    protected Optional<T> getProperty(String rootProperty, ConfigurationNode node) {
        if (node == null) {
            return Optional.empty();
        }

        Map<String, String> properties = new HashMap<>();
        for (String key : node.getCollectionValue(String.class)) {
            properties.put(key, node.getNode(key).getValue(String.class));
        }

        if (!properties.containsKey(rootProperty)) {
            Log.warn("No root property was defined in node " + node + "! Please make sure everything is correct and up to date.");
            return Optional.empty();
        }

        try {
            Class<? extends T> valueClass = this.properties.get(rootProperty);
            Constructor<? extends T> valueConstructor = valueClass.getConstructor(String.class);
            valueConstructor.setAccessible(true);

            T propertyValue = valueConstructor.newInstance(rootProperty);
            for (Field field : valueClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ConfigProperty.class))
                    continue;

                ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
                String configPropertyName = configProperty.value().isEmpty() ? field.getName() : configProperty.value();

                String property = properties.get(configPropertyName);
                if (property == null && configProperty.required()) {
                    Log.warn("Property value " + propertyValue + " is missing a required option, (" + configPropertyName + ")! Please make sure your configuration is correct!");
                    return Optional.empty();
                }

                setPropertyValue(propertyValue, field, property);
                return Optional.of(propertyValue);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Returns a property from the given string
     *
     * Format: (propertyName){(property)=(value),(property)=(value)...}
     * Example: giveEffect{effect=speed,duration=100,amplifier=2}
     *
     * @param value the string to parse the property from
     * @return a property from the given string
     */
    protected Optional<T> getProperty(String value) {
        String propertyName = value.split("\\{")[0];
        if (!properties.containsKey(propertyName)) {
            Log.warn("Value " + value + " is not a valid property! Valid properties: " + properties.keySet() + "!");
            return Optional.empty();
        }

        Map<String, String> properties = new HashMap<>();
        if (value.contains("{")) {
            String valueStr = value.split("\\{")[1].trim();
            for (String property : valueStr.split(",")) {
                properties.put(property.split("=")[0], property.split("=")[1]);
            }
        }

        try {
            Class<? extends T> valueClass = this.properties.get(propertyName);
            Constructor<? extends T> valueConstructor = valueClass.getConstructor(String.class);
            valueConstructor.setAccessible(true);

            T propertyValue = valueConstructor.newInstance(propertyName);
            for (Field field : valueClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ConfigProperty.class))
                    continue;

                ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
                String configPropertyName = configProperty.value().isEmpty() ? field.getName() : configProperty.value();

                String property = properties.get(configPropertyName);
                if (property == null && configProperty.required()) {
                    Log.warn("Property value " + value + " is missing a required option, (" + propertyName + ")! Please make sure your configuration is correct!");
                    return Optional.empty();
                }

                setPropertyValue(propertyValue, field, property);
                return Optional.of(propertyValue);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Sets the value of the property specified
     *
     * @param property the property (object) to get the field from
     * @param field the field to modify
     * @param value the value to set for the field
     */
    protected void setPropertyValue(T property, Field field, String value) {
        field.setAccessible(true);
        try {
            field.set(property, field.getType().cast(value));
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Log.warn("Failed to set value of property " + value + " to field " + field.getName() + "!");
            ex.printStackTrace();
        }
    }
}

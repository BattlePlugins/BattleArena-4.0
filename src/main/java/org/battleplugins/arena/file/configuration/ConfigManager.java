package org.battleplugins.arena.file.configuration;

import mc.alk.battlecore.util.Log;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.file.FileManager;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Manages configuration files used across BattleArena.
 *
 * @author Redned
 */
public class ConfigManager extends FileManager {

    private Configuration config;
    private Configuration messagesConfig;
    private Configuration teamsConfig;
    private Configuration classConfig;

    public ConfigManager(BattleArena plugin) {
        super(plugin);

        loadConfigs();
    }

    /**
     * Returns the main config.yml for BattleArena
     *
     * @return the main config.yml
     */
    public Configuration getConfig() {
        return this.config;
    }

    /**
     * Returns the messages.yml config file for BattleArena
     *
     * @return the messages.yml config file
     */
    public Configuration getMessagesConfig() {
        return this.messagesConfig;
    }

    /**
     * Returns the teams.yml config file for BattleArena
     *
     * @return the teams.yml config file
     */
    public Configuration getTeamsConfig() {
        return this.teamsConfig;
    }

    /**
     * Returns the classes.yml config file for BattleArena
     *
     * @return the classes.yml config file
     */
    public Configuration getClassConfig() {
        return classConfig;
    }

    /**
     * Loads all the configs necessary for BattleArena
     */
    public void loadConfigs() {
        try {
            if (Files.notExists(plugin.getDataFolder())) {
                Files.createDirectory(plugin.getDataFolder());
            }
            config = loadConfig(plugin.getDataFolder(), "", "config.yml");
            messagesConfig = loadConfig(plugin.getDataFolder(), "", "messages.yml");
            teamsConfig = loadConfig(plugin.getDataFolder(), "", "teams.yml");
            classConfig = loadConfig(plugin.getDataFolder(), "", "classes.yml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reloads all the config files for BattleArena
     */
    public void reloadConfigs() {
        reloadConfig(config);
        reloadConfig(messagesConfig);
        reloadConfig(teamsConfig);
        reloadConfig(classConfig);
    }

    /**
     * Reloads a config file
     *
     * @param config the config to reload
     */
    public void reloadConfig(Configuration config) {
        try {
            config.save();
            config.reload();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves all the config files for BattleArena
     */
    public void saveConfigs() {
        try {
            config.save();
            messagesConfig.save();
            teamsConfig.save();
            classConfig.save();
        } catch (IOException ex) {
            Log.warn("Failed to save config files!");
            ex.printStackTrace();
        }
    }
}

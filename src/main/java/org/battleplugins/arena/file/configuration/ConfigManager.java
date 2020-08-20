package org.battleplugins.arena.file.configuration;

import mc.alk.battlecore.util.Log;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.file.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Manages configuration files used across BattleArena.
 *
 * @author Redned
 */
public class ConfigManager extends FileManager {

    private Configuration messagesConfig;
    private Configuration teamsConfig;
    private Configuration classConfig;

    public ConfigManager(BattleArena plugin) {
        super(plugin);

        for (String allowedExtension : ALLOWED_EXTENSIONS) {
            if (Files.exists(Paths.get(plugin.getDataFolder().toString(), "config" + allowedExtension))) {
                lastUsedExtension = allowedExtension;
                break;
            }
        }
        // No config has been made yet.. default to yml
        if (lastUsedExtension == null) {
            lastUsedExtension = ".yml";
        }
        try {
            this.config = loadConfig(plugin.getDataFolder(), "", "config");
            if (!lastUsedExtension.endsWith(this.config.getNode("configStorage").getString())) {
                convertTo = this.config.getNode("configStorage").getString().toLowerCase();
                this.plugin.getLogger().info("Old config files found... converting.");

                this.config = loadConfig(plugin.getDataFolder(), "", "config");
            }
        } catch (Exception ex) {
            this.plugin.getLogger().warning("Failed to create or load main configuration!!");
            ex.printStackTrace();
        }

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
            messagesConfig = loadConfig(plugin.getDataFolder(), "", "messages");
            teamsConfig = loadConfig(plugin.getDataFolder(), "", "teams");
            classConfig = loadConfig(plugin.getDataFolder(), "", "classes");
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

package org.battleplugins.arena.configuration;

import lombok.AccessLevel;
import lombok.Getter;

import mc.alk.battlecore.util.FileUtil;
import mc.alk.battlecore.util.Log;

import org.battleplugins.api.configuration.Configuration;
import org.battleplugins.api.configuration.provider.YAMLConfigurationProvider;
import org.battleplugins.arena.BattleArena;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Manages configuration files used across BattleArena.
 *
 * @author Redned
 */
@Getter
public class ConfigManager {

    @Getter(AccessLevel.NONE)
    private BattleArena plugin;

    /**
     * The main config.yml for BattleArena
     *
     * @return the main config.yml
     */
    private Configuration config;

    /**
     * The messages.yml config file for BattleArena
     *
     * @return the messages.yml config file
     */
    private Configuration messagesConfig;

    /**
     * The teams.yml config file for BattleArena
     *
     * @return the teams.yml config file
     */
    private Configuration teamsConfig;

    public ConfigManager(BattleArena plugin) {
        this.plugin = plugin;

        loadConfigs();
    }

    /**
     * Loads all the configs necessary for BattleArena
     */
    public void loadConfigs() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        config = loadConfig(plugin.getDataFolder(), "", "config.yml");
        messagesConfig = loadConfig(plugin.getDataFolder(), "", "messages.yml");
        teamsConfig = loadConfig(plugin.getDataFolder(), "", "teams.yml");
    }

    /**
     * Loads a config file, attempts to load from
     * jar if not found
     *
     * @param directory the file of the config
     * @param resourceDir the directory of the resource
     * @param resource the resource to load from
     * @return the configuration object of the file
     */
    public Configuration loadConfig(File directory, String resourceDir, String resource) {
        File configFile = new File(directory, resource);
        if (!configFile.exists()) {
            try {
                InputStream stream = plugin.getClass().getResourceAsStream("/" + resourceDir + resource);
                if (stream == null) {
                    configFile.createNewFile();
                    Log.debug("Did not find " + resource + " in the jar, so creating an empty file instead.");
                } else {
                    FileUtil.writeFile(configFile, plugin.getClass().getResourceAsStream("/" + resourceDir + resource));
                }
            } catch (IOException ex) {
                Log.err("Could not create " + resource + " config file!");
                ex.printStackTrace();
            }
        }

        return Configuration.builder()
                .file(configFile)
                .provider(YAMLConfigurationProvider.class)
                .build();
    }

    /**
     * Reloads all the config files for BattleArena
     */
    public void reloadConfigs() {
        reloadConfig(config);
        reloadConfig(messagesConfig);
        reloadConfig(teamsConfig);
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

package org.battleplugins.arena.configuration;

import mc.alk.battlecore.util.Log;

import org.battleplugins.api.configuration.Configuration;
import org.battleplugins.api.configuration.provider.YAMLConfigurationProvider;
import org.battleplugins.arena.BattleArena;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages configuration files used across BattleArena.
 *
 * @author Redned
 */
public class ConfigManager {

    private BattleArena plugin;

    private Configuration config;
    private Configuration messagesConfig;
    private Configuration teamsConfig;
    private Configuration classConfig;

    public ConfigManager(BattleArena plugin) {
        this.plugin = plugin;

        loadConfigs();
    }

    /**
     * Returns the main config.yml for BattleArena
     *
     * @return the main config.yml
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Returns the messages.yml config file for BattleArena
     *
     * @return the messages.yml config file
     */
    public Configuration getMessagesConfig() {
        return messagesConfig;
    }

    /**
     * Returns the teams.yml config file for BattleArena
     *
     * @return the teams.yml config file
     */
    public Configuration getTeamsConfig() {
        return teamsConfig;
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
        if (Files.notExists(plugin.getDataFolder())) {
            try {
                Files.createDirectory(plugin.getDataFolder());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        config = loadConfig(plugin.getDataFolder(), "", "config.yml");
        messagesConfig = loadConfig(plugin.getDataFolder(), "", "messages.yml");
        teamsConfig = loadConfig(plugin.getDataFolder(), "", "teams.yml");
        classConfig = loadConfig(plugin.getDataFolder(), "", "classes.yml");
    }

    /**
     * Loads a config file, attempts to load from
     * jar if not found
     *
     * @param path the path of the config
     * @param resourceDir the directory of the resource
     * @param resource the resource to load from
     * @return the configuration object of the file
     */
    public Configuration loadConfig(Path path, String resourceDir, String resource) {
        Path configPath = Paths.get(path.toString(), resource);
        if (Files.notExists(configPath)) {
            try {
                URI uri = plugin.getClass().getResource("/" + resourceDir + resource).toURI();
                this.createFileSystem(uri);

                Path resourcePath = Paths.get(uri);
                Files.createDirectories(configPath.getParent());
                Files.copy(resourcePath, configPath);
            } catch (IOException | URISyntaxException ex) {
                Log.err("Could not create " + resource + " config file!");
                ex.printStackTrace();
            }
        }

        return Configuration.builder()
                .path(configPath)
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
            ex.printStackTrace();
        }
    }

    private void createFileSystem(URI uri) throws IOException {
        try {
            FileSystems.getFileSystem(uri);
        } catch (FileSystemNotFoundException ex) {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            FileSystems.newFileSystem(uri, env);
        }
    }
}

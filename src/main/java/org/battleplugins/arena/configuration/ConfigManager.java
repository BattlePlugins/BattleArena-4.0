package org.battleplugins.arena.configuration;

import lombok.AccessLevel;
import lombok.Getter;

import mc.alk.battlecore.util.Log;

import org.battleplugins.api.configuration.Configuration;
import org.battleplugins.api.configuration.provider.YAMLConfigurationProvider;
import org.battleplugins.arena.BattleArena;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;

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

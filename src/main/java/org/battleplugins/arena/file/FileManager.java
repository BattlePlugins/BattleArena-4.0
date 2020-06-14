package org.battleplugins.arena.file;

import mc.alk.battlecore.util.Log;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.file.configuration.Configuration;
import org.spongepowered.configurate.yaml.YAMLConfigurationLoader;

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
 * Base file manager for BattleArena.
 *
 * @author Redned
 */
public class FileManager {

    protected BattleArena plugin;

    public FileManager(BattleArena plugin) {
        this.plugin = plugin;
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
    public Configuration loadConfig(Path path, String resourceDir, String resource) throws IOException {
        Path configPath = Paths.get(path.toString(), resource);
        if (Files.notExists(configPath)) {
            try {
                URI uri = this.plugin.getClass().getResource("/" + resourceDir + resource).toURI();
                this.createFileSystem(uri);

                Path resourcePath = Paths.get(uri);
                Files.createDirectories(configPath.getParent());
                Files.copy(resourcePath, configPath);
            } catch (URISyntaxException ex) {
                Log.err("Could not create " + resource + " config file!");
                ex.printStackTrace();
            }
        }

        YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder().setPath(configPath).build();
        return new Configuration(loader, loader.load());
    }

    protected void createFileSystem(URI uri) throws IOException {
        try {
            FileSystems.getFileSystem(uri);
        } catch (FileSystemNotFoundException ex) {
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            FileSystems.newFileSystem(uri, env);
        }
    }
}

package org.battleplugins.arena.file;

import mc.alk.battlecore.util.Log;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.file.configuration.Configuration;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.json.JSONConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.xml.XMLConfigurationLoader;
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

    protected static final String[] ALLOWED_EXTENSIONS = {".conf", ".json", ".xml", ".yml"};

    protected static String lastUsedExtension;
    protected static String convertTo;

    protected BattleArena plugin;

    protected Configuration config;

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
        Path configPath = Paths.get(path.toString(), resource + lastUsedExtension);
        if (Files.notExists(configPath)) {
            try {
                URI uri = this.plugin.getClass().getResource("/" + resourceDir + resource + ".yml").toURI();
                this.createFileSystem(uri);

                Path resourcePath = Paths.get(uri);
                Files.createDirectories(configPath.getParent());
                Files.copy(resourcePath, configPath);
            } catch (URISyntaxException ex) {
                Log.err("Could not create " + resource + " config file!");
                ex.printStackTrace();
            }
        }

        ConfigurationLoader<?> loader;
        switch (lastUsedExtension) {
            case ".conf":
                loader = HoconConfigurationLoader.builder().setPath(configPath).build();
                break;
            case ".json":
                loader = JSONConfigurationLoader.builder().setPath(configPath).build();
                break;
            case ".xml":
                loader = XMLConfigurationLoader.builder().setPath(configPath).build();
                break;
            case ".yml":
                loader = YAMLConfigurationLoader.builder().setPath(configPath).build();
                break;
            default:
                throw new RuntimeException("Failed to find config parser for format " + lastUsedExtension + "!");
        }

        if (convertTo != null && !lastUsedExtension.substring(1).equals(convertTo)) {
            ConfigurationNode node = loader.load();
            Path newPath = Paths.get(path.toString(), resource + "." + convertTo);
            Files.createDirectories(newPath.getParent());
            Files.createFile(newPath);
            switch (convertTo) {
                case "conf":
                    loader = HoconConfigurationLoader.builder().setPath(newPath).build();
                    break;
                case "json":
                    loader = JSONConfigurationLoader.builder().setPath(newPath).build();
                    break;
                case "xml":
                    loader = XMLConfigurationLoader.builder().setPath(newPath).build();
                    break;
                case "yml":
                    loader = YAMLConfigurationLoader.builder().setPath(newPath).build();;
                    break;
                default:
                    throw new RuntimeException("Failed to find config parser for format " + convertTo + " when converting files!");
            }
            loader.save(node);
            Files.delete(configPath);
        }
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

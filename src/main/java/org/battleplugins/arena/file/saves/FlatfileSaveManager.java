package org.battleplugins.arena.file.saves;

import mc.alk.battlecore.util.Log;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.map.ArenaMap;
import org.battleplugins.arena.file.FileManager;
import org.battleplugins.arena.file.configuration.Configuration;
import org.battleplugins.arena.file.reader.storage.map.FlatfileMapStorageReader;
import org.spongepowered.configurate.ConfigurationNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Manages flatfile saves used across BattleArena.
 *
 * @author Redned
 */
public class FlatfileSaveManager extends FileManager {

    private Configuration mapSaves;

    public FlatfileSaveManager(BattleArena plugin) {
        super(plugin);

        loadMaps();
    }

    /**
     * Loads the maps save file
     */
    public void loadMaps() {
        try {
            Path mapsPath = Paths.get(this.plugin.getDataFolder().toString(), "maps", "maps.yml");
            if (Files.notExists(mapsPath)) {
                Files.createDirectories(mapsPath.getParent());
                Files.createFile(mapsPath);
            }
            this.mapSaves = loadConfig(mapsPath.getParent(), "", "maps.yml");
            Map<Object, ? extends ConfigurationNode> mapResult = this.mapSaves.getChildrenMap();
            for (Map.Entry<Object, ? extends ConfigurationNode> entry : mapResult.entrySet()) {
                Log.debug("Loading map " + entry.getKey());
                this.plugin.getArenaManager().getLoadedMaps().add(FlatfileMapStorageReader.get().read(entry.getValue()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the currently loaded maps to file
     */
    public void saveMaps() {
        try {
            for (ArenaMap map : this.plugin.getArenaManager().getLoadedMaps()) {
                Log.debug("Saving map " + map.getId());
                FlatfileMapStorageReader.get().write(map, this.mapSaves.getNode(map.getId()));
            }
            this.mapSaves.save();
        } catch (IOException ex) {
            Log.warn("Failed to save maps!");
            ex.printStackTrace();
        }
    }
}

package org.battleplugins.arena.file.reader.storage.map;

import mc.alk.battlecore.util.LocationUtil;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.map.ArenaMap;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map storage reader for reading maps from flatfile.
 *
 * @author Redned
 */
public class FlatfileMapStorageReader extends MapStorageReader<ConfigurationNode> {

    private static final FlatfileMapStorageReader INSTANCE = new FlatfileMapStorageReader();

    @Override
    public ArenaMap read(ConfigurationNode node) {
        ArenaMap.Builder mapBuilder = ArenaMap.builder();
        mapBuilder.id((String) node.getKey());
        mapBuilder.name(node.getNode("name").getString());
        Map<Object, ? extends ConfigurationNode> mapResult = node.getNode("spawns").getChildrenMap();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : mapResult.entrySet()) {
            String team = (String) entry.getKey();
            Collection<String> spawns = mapResult.get(team).getChildrenList().stream().map(ConfigurationNode::getString).collect(Collectors.toList());
            mapBuilder.spawns(team, spawns.stream().map(LocationUtil::fromString).collect(Collectors.toList()));
        }
        Arena arena = BattleArena.getInstance().getArenaManager().getArenas().get(node.getNode("arenaType").getString());
        if (arena == null) {
            throw new IllegalArgumentException(String.format("Could not find arena with name %s!", node.getNode("arenaType")));
        }
        mapBuilder.arena(arena);
        return mapBuilder.build();
    }

    @Override
    public boolean write(ArenaMap map, ConfigurationNode node) {
        node.getNode("name").setValue(map.getName());
        node.getNode("spawns").setValue(map.getSpawns()
                .entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey().getId(),
                        entry.getValue().stream()
                                .map(LocationUtil::toString)
                                .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue)
                )
        );
        node.getNode("arenaType").setValue(map.getArena().getName());
        return true;
    }

    /**
     * Returns the current instance of this map reader
     *
     * @return the current instance of this map reader
     */
    public static FlatfileMapStorageReader get() {
        return INSTANCE;
    }
}

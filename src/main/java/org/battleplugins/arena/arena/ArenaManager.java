package org.battleplugins.arena.arena;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import mc.alk.battlecore.executor.CustomCommandExecutor;

import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.player.ArenaPlayer;
import org.battleplugins.arena.arena.team.ArenaTeamManager;
import org.battleplugins.arena.executor.ArenaExecutor;
import org.battleplugins.command.Command;
import org.battleplugins.entity.living.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains all the necessary arena info and handles
 * registration of arenas. This is also injected into the platform's
 * service provider API so developers can easily access this class.
 * 
 * Custom arena extensions loaded from the 'arenas' subfolder do not
 * need to reference this class in order to register arenas, but 
 * developers wanting to register arenas inside of a plugin itself will
 * need to call {@link #registerArena(String, String, Class, CustomCommandExecutor)}.
 * 
 * @author Redned
 */
@Getter
@RequiredArgsConstructor
public class ArenaManager {

    @NonNull 
    @Getter(AccessLevel.NONE)
    private BattleArena plugin;

    /**
     * The team manager
     *
     * @return the team manager
     */
    private ArenaTeamManager teamManager = new ArenaTeamManager(plugin);

    /**
     * A map of all the arenas
     * 
     * Key: the name of the arena
     * Value: the arena 
     * 
     * @return a map of all the arenas
     */
    private Map<String, Arena> arenas = new HashMap<>();

    /**
     * A map of all the ArenaPlayers online
     *
     * Key: the name of the ArenaPlayer
     * Value: the arena player
     *
     * @return a map of all the ArenaPlayers online
     */
    private Map<String, ArenaPlayer> arenaPlayers = new HashMap<>();

    /**
     * Registers an arena into BattleArena
     * 
     * @param name the name of the arena
     * @param command the command to register this arena with
     * @param arenaClass the class of the arena to register
     */
    public void registerArena(String name, String command, Class<? extends Arena> arenaClass) {
        registerArena(name, command, getArenaFactory(arenaClass));
    }
    
    /**
     * Registers an arena into BattleArena
     * 
     * @param name the name of the arena
     * @param command the command to register this arena with
     * @param arenaFactory the arena factory containing the arena
     */
    public void registerArena(String name, String command, ArenaFactory arenaFactory) {
        registerArena(name, command, arenaFactory, null);
    }
    
    /**
     * Registers an arena into BattleArena
     * 
     * @param name the name of the arena
     * @param command the command to register this arena with
     * @param arenaClass the class of the arena to register
     * @param executor the command executor for this arena
     */
    public void registerArena(String name, String command, Class<? extends Arena> arenaClass, CustomCommandExecutor executor) {
        registerArena(name, command, getArenaFactory(arenaClass), executor);
    }
    
    /**
     * Registers an arena into BattleArena
     * 
     * @param name the name of the arena
     * @param command the command to register this arena with
     * @param arenaFactory the arena factory containing the arena
     * @param executor the command executor for this arena
     */
    public void registerArena(String name, String command, ArenaFactory arenaFactory, CustomCommandExecutor executor) {
        if (arenaFactory == null) {
            throw new NullPointerException("Arena factory for " + name + " was null, failed to register arena!");
        }

        Arena arena = arenaFactory.newArena();
        arena.setName(name);
        if (arena.getExecutor() == null) {
            arena.setExecutor(new ArenaExecutor(plugin, arena));
        }
        
        if (executor != null) {
            arena.getExecutor().addMethods(executor, executor.getClass().getMethods());
            plugin.registerCommand(new Command(command, "Main command for " + arena.getName() + ".", "battlearena." + command, new ArrayList<>()), executor);
        }
    }

    /**
     * Returns an ArenaPlayer from the given player
     *
     * @param player the player to get the ArenaPlayer for
     * @return an ArenaPlayer from the given player
     */
    public ArenaPlayer getArenaPlayer(Player player) {
        return arenaPlayers.computeIfAbsent(player.getName(), arenaPlayer -> new ArenaPlayer(player));
    }

    private ArenaFactory getArenaFactory(Class<? extends Arena> arenaClass) {
        if (arenaClass == null)
            return null;

        return () -> {
            try {
                return arenaClass.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                plugin.getLogger().error("An error occurred when trying to register arena class " + arenaClass.getSimpleName());
                plugin.getLogger().error("If your arena class has a custom constructor, it must also have a default one as well.");
                plugin.getLogger().error("If you want to use custom constructors, you can create a new ArenaFactory. Refer to the API documentation for more info.");
                ex.printStackTrace();
            }
            return null;
        };
    }
}

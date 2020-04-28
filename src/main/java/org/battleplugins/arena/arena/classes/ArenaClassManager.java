package org.battleplugins.arena.arena.classes;

import mc.alk.battlecore.util.Log;

import org.battleplugins.api.inventory.item.ItemStack;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.player.ArenaPlayer;

import java.util.Map;

/**
 * Represents a class that facilitates most any action related
 * to class giving, detection or registration.
 *
 * @author Zach443
 */
public class ArenaClassManager {

    /** Instance of the plugin for config, etc **/
    private BattleArena plugin;

    /** Map of all our ArenaClasses **/
    private Map<String, ArenaClass> arenaClasses;

    public ArenaClassManager(BattleArena plugin) {
        this.plugin = plugin;
    }

    /**
     * Add a new class to the Manager
     *
     * @param newClass Class to add to the Manager
     */
    public void addClass(ArenaClass newClass) {
        arenaClasses.put(newClass.getName(), newClass);
    }

    /**
     * Gives a player a class
     *
     * @param player ArenaPlayer you want to give a class
     * @param className Name of the class you want to give
     */
    public void giveClass(ArenaPlayer player, String className) {
        ArenaClass classToGive;
        if (arenaClasses.get(className) != null) {
            classToGive = arenaClasses.get(className);

            player.setCurrentClass(classToGive);

            for (ItemStack item : classToGive.getItems()) {
                player.getPlayer().getInventory().addItem(item);
            }
        } else {
            Log.warn("Class [" + className + "] not found!");
        }
    }
}

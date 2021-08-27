package org.battleplugins.arena.arena.classes;

import org.battleplugins.arena.match.Match;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Represents a class used in {@link Match}es or any
 * extensions of them.
 *
 * @author Zach443
 */
public class ArenaClass {

    /** Name of the Class **/
    private String name;

    /** All the items for the class **/
    private List<ItemStack> items;

    public ArenaClass(String name, List<ItemStack> items) {
        this.name = name;
        this.items = items;
    }

    /**
     * Set the name of the class
     *
     * @param newName The new name for the class
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Set the items for the class
     *
     * @param newItems List of new items for the class
     */
    public void setItems(List<ItemStack> newItems) {
        this.items = newItems;
    }

    /**
     * Returns the name of the class
     *
     * @return the name of the class
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns a list of all the items in the class
     *
     * @return a list of items in the class
     */
    public List<ItemStack> getItems() {
        return this.items;
    }
}

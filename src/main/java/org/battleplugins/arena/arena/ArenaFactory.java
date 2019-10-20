package org.battleplugins.arena.arena;

/**
 * Factory for creating a new Arena.
 * 
 * @author Europia79
 */
public interface ArenaFactory {

    /**
     * Creates a new arena
     * 
     * @return a new arena
     */
    Arena newArena();

    /**
     * Default ArenaFactory
     */
    ArenaFactory DEFAULT = Arena::new;
}

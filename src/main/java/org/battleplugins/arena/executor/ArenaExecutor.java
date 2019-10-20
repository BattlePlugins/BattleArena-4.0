package org.battleplugins.arena.executor;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import mc.alk.battlecore.executor.CustomCommandExecutor;

import org.battleplugins.arena.arena.Arena;

/**
 * Main arena executor for individual arenas. One of these
 * is assigned to each {@link Arena}. Custom arenas that specify a 
 * different executor use this in addition to the one that was given.
 * 
 * @author Redned
 */
@AllArgsConstructor
public class ArenaExecutor extends CustomCommandExecutor {

    @NonNull
    private Arena arena;
}

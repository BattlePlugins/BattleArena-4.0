package org.battleplugins.arena.match.state.option.options;

import org.battleplugins.arena.match.Match;
import org.battleplugins.arena.match.state.option.StateOption;
import org.bukkit.entity.Player;

/**
 * State option that clears inventories.
 *
 * @author Redned
 */
public class ClearInventoryOption extends StateOption {

    public ClearInventoryOption(String name) {
        super(name);
    }

    @Override
    public void runOption(Player player, Match match) {
        player.getInventory().clear();
    }
}
package org.battleplugins.arena.competition.state.option.options;

import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.competition.state.option.StateOption;
import org.battleplugins.entity.living.player.Player;

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
    public void runOption(Player player, Competition competition) {
        player.getInventory().clear();
    }
}
package org.battleplugins.arena.competition.state.option.options;

import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.competition.state.option.StateOption;

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
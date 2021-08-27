package org.battleplugins.arena.match.state.option.options;

import org.battleplugins.arena.util.Log;
import org.battleplugins.arena.match.Match;
import org.battleplugins.arena.match.state.option.StateOption;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * State option for changing a player's gamemode.
 *
 * @author Redned
 */
public class ChangeGamemodeStateOption extends StateOption {

    private String mode;

    public ChangeGamemodeStateOption(String name) {
        super(name);
    }

    @Override
    public void runOption(Player player, Match match) {
        GameMode gameMode = null;
        for (GameMode value : GameModes.values()) {
            if (value.getIdentifier().getKey().equalsIgnoreCase(mode)) {
                gameMode = value;
                break;
            }
        }

        if (gameMode == null) {
            Log.warn("Gamemode " + mode + " could not be found!");
            return;
        }

        player.setGameMode(gameMode);
    }

    /**
     * Returns the gamemode
     *
     * @return the gamemode
     */
    public String getMode() {
        return mode;
    }
}

package org.battleplugins.arena.competition.state.option.options;

import lombok.Getter;

import mc.alk.battlecore.util.Log;

import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.competition.state.option.StateOption;
import org.battleplugins.arena.configuration.ConfigProperty;
import org.battleplugins.entity.living.player.Player;
import org.battleplugins.entity.living.player.gamemode.GameMode;
import org.battleplugins.entity.living.player.gamemode.GameModes;

/**
 * State option for changing a player's gamemode.
 *
 * @author Redned
 */
@Getter
public class ChangeGamemodeStateOption extends StateOption {

    @ConfigProperty(required = true)
    private String mode;

    public ChangeGamemodeStateOption(String name) {
        super(name);
    }

    @Override
    public void runOption(Player player, Competition competition) {
        GameMode gameMode = null;
        for (GameMode value : GameModes.values()) {
            if (value.getKey().getKey().equalsIgnoreCase(mode)) {
                gameMode = value;
                break;
            }
        }

        if (gameMode == null) {
            Log.debug("Gamemode " + mode + " could not be found!");
            return;
        }

        player.setGameMode(gameMode);
    }
}

package org.battleplugins.arena.competition.state.option.options;

import lombok.Getter;

import mc.alk.mc.MCPlayer;
import mc.alk.mc.entity.player.MCGameMode;

import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.competition.state.option.StateOption;
import org.battleplugins.arena.config.ConfigProperty;
import org.jline.utils.Log;

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
    public void runOption(MCPlayer player, Competition competition) {
        MCGameMode gameMode;
        try {
            gameMode = MCGameMode.valueOf(mode);
        } catch (Exception ex) {
            // Debug here since this may be thrown a lot
            Log.debug("Gamemode " + mode + " could not be found!");
            return;
        }

        player.setGameMode(gameMode);
    }
}

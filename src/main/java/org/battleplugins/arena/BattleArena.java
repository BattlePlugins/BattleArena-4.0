package org.battleplugins.arena;

import lombok.Getter;

import mc.alk.battlecore.BattlePlugin;

import org.battleplugins.api.configuration.Configuration;
import org.battleplugins.api.plugin.PluginProperties;
import org.battleplugins.arena.arena.ArenaManager;
import org.battleplugins.arena.match.state.option.StateOptionManager;
import org.battleplugins.arena.match.victorycondition.VictoryConditionManager;
import org.battleplugins.arena.configuration.ConfigManager;
import org.battleplugins.arena.message.MessageManager;

/**
 * Overall main class for the BattleArena plugin.
 * 
 * @author Redned
 */
@Getter
@PluginProperties(id = "battlearena", authors = "BattlePlugins", name = "BattleArena", version = "$VERSION", description = "$DESCRIPTION", url = "$URL")
public class BattleArena extends BattlePlugin {

    private ConfigManager configManager;
    private MessageManager messageManager;

    private ArenaManager arenaManager;
    private StateOptionManager stateOptionManager;
    private VictoryConditionManager victoryConditionManager;

    @Override
    public void onEnable() {
        super.onEnable();

        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(configManager.getMessagesConfig().getNode("messages"));

        this.arenaManager = new ArenaManager(this);
        this.stateOptionManager = new StateOptionManager(this);
        this.victoryConditionManager = new VictoryConditionManager(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Returns the config for BattleArena
     *
     * @return the config for BattleArena
     */
    public Configuration getConfig() {
        return configManager.getConfig();
    }
}

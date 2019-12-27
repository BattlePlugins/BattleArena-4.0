package org.battleplugins.arena;

import lombok.Getter;

import mc.alk.battlecore.BattlePlugin;

import org.battleplugins.arena.arena.ArenaManager;
import org.battleplugins.arena.competition.state.option.StateOptionManager;
import org.battleplugins.arena.competition.victorycondition.VictoryConditionManager;
import org.battleplugins.arena.configuration.ConfigManager;
import org.battleplugins.configuration.Configuration;
import org.battleplugins.plugin.PluginProperties;

/**
 * Overall main class for the BattleArena plugin.
 * 
 * @author Redned
 */
@Getter
@PluginProperties(id = "battlearena", authors = "BattlePlugins", name = ArenaInfo.NAME, version = ArenaInfo.VERSION, description = ArenaInfo.DESCRIPTION, url = ArenaInfo.URL)
public class BattleArena extends BattlePlugin {

    private ConfigManager configManager;

    private ArenaManager arenaManager;
    private StateOptionManager stateOptionManager;
    private VictoryConditionManager victoryConditionManager;

    @Override
    public void onEnable() {
        super.onEnable();

        this.configManager = new ConfigManager(this);

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

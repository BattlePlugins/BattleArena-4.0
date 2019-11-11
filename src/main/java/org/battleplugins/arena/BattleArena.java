package org.battleplugins.arena;

import lombok.Getter;

import mc.alk.battlecore.configuration.Configuration;
import mc.alk.mc.plugin.MCPlugin;
import mc.alk.mc.plugin.PluginProperties;

import org.battleplugins.arena.arena.ArenaManager;
import org.battleplugins.arena.competition.state.option.StateOptionManager;
import org.battleplugins.arena.competition.victorycondition.VictoryConditionManager;
import org.battleplugins.arena.config.ConfigManager;

/**
 * Overall main class for the BattleArena plugin.
 * 
 * @author Redned
 */
@Getter
@PluginProperties(id = "battlearena", authors = "BattlePlugins", name = ArenaInfo.NAME, version = ArenaInfo.VERSION, description = ArenaInfo.DESCRIPTION, url = ArenaInfo.URL)
public class BattleArena extends MCPlugin {

    private ConfigManager configManager;

    private ArenaManager arenaManager;
    private StateOptionManager stateOptionManager;
    private VictoryConditionManager victoryConditionManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);

        this.arenaManager = new ArenaManager(this);
        this.stateOptionManager = new StateOptionManager(this);
        this.victoryConditionManager = new VictoryConditionManager(this);
    }

    @Override
    public void onDisable() {
        
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

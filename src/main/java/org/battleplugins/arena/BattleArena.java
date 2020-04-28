package org.battleplugins.arena;

import mc.alk.battlecore.BattlePlugin;

import org.battleplugins.api.configuration.Configuration;
import org.battleplugins.api.plugin.PluginProperties;
import org.battleplugins.arena.arena.ArenaFactory;
import org.battleplugins.arena.arena.ArenaManager;
import org.battleplugins.arena.arena.classes.ArenaClassManager;
import org.battleplugins.arena.match.state.option.StateOptionManager;
import org.battleplugins.arena.match.victorycondition.VictoryConditionManager;
import org.battleplugins.arena.configuration.ConfigManager;
import org.battleplugins.arena.message.MessageManager;

/**
 * Overall main class for the BattleArena plugin.
 * 
 * @author Redned
 */
@PluginProperties(id = "battlearena", authors = "BattlePlugins", name = "BattleArena", version = "$VERSION", description = "$DESCRIPTION", url = "$URL")
public class BattleArena extends BattlePlugin {

    private ConfigManager configManager;
    private MessageManager messageManager;

    private ArenaManager arenaManager;
    private StateOptionManager stateOptionManager;
    private VictoryConditionManager victoryConditionManager;
    private ArenaClassManager classManager;

    @Override
    public void onEnable() {
        super.onEnable();

        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(configManager.getMessagesConfig().getNode("messages"));

        this.arenaManager = new ArenaManager(this);
        this.stateOptionManager = new StateOptionManager(this);
        this.victoryConditionManager = new VictoryConditionManager(this);
        this.classManager = new ArenaClassManager(this);

        this.arenaManager.registerArena("Arena", "arena", ArenaFactory.DEFAULT);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Returns the {@link ConfigManager} used by BattleArena
     *
     * @return the {@link ConfigManager} used by BattleArena
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * Returns the {@link MessageManager} used by BattleArena
     *
     * @return the {@link MessageManager} used by BattleArena
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * Returns the {@link ArenaManager} used by BattleArena
     *
     * @return the {@link ArenaManager} used by BattleArena
     */
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    /**
     * Returns the {@link StateOptionManager} used by BattleArena
     *
     * @return the {@link StateOptionManager} used by BattleArena
     */
    public StateOptionManager getStateOptionManager() {
        return stateOptionManager;
    }

    /**
     * Returns the {@link VictoryConditionManager} used by BattleArena
     *
     * @return the {@link VictoryConditionManager} used by BattleArena
     */
    public VictoryConditionManager getVictoryConditionManager() {
        return victoryConditionManager;
    }

    /**
     * Returns the {@link ArenaClassManager} used by BattleArena
     *
     * @return the {@link ArenaClassManager} used by BattleArena
     */
    public ArenaClassManager getClassManager() {
        return classManager;
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

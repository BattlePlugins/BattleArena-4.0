package org.battleplugins.arena;

import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.ArenaManager;
import org.battleplugins.arena.arena.classes.ArenaClassManager;
import org.battleplugins.arena.arena.map.ArenaMap;
import org.battleplugins.arena.match.state.option.StateOptionManager;
import org.battleplugins.arena.match.victorycondition.VictoryConditionManager;
import org.battleplugins.arena.message.MessageManager;
import org.battleplugins.arena.util.Log;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Overall main class for the BattleArena plugin.
 * 
 * @author Redned
 * Gutted by Zach443
 */
public class BattleArena extends JavaPlugin {

    private static BattleArena INSTANCE;

    private MessageManager messageManager;

    private ArenaManager arenaManager;
    private StateOptionManager stateOptionManager;
    private VictoryConditionManager victoryConditionManager;
    private ArenaClassManager classManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        super.onEnable();

        //TODO: Fix debug stuff
        //Log.setDebug(this.getConfig().getNode("debugMode").getBoolean());
        //this.getLogger().setDebug(true);

        this.messageManager = new MessageManager(this.configManager.getMessagesConfig().getNode("messages"));

        this.arenaManager = new ArenaManager(this);
        this.stateOptionManager = new StateOptionManager(this);
        this.victoryConditionManager = new VictoryConditionManager(this);
        this.classManager = new ArenaClassManager(this);

        this.arenaManager.registerArena("Arena", "arena", Arena.DEFAULT_FACTORY);
        if (!this.getConfig().getNode("defaultArenaOptions", " createMatchesOnDemand").getBoolean()) {
            for (ArenaMap map : this.arenaManager.getLoadedMaps()) {
                this.arenaManager.createMatchForMap(map.getArena(), map, true);
                Log.info("Constructed match for map " + map.getId());
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
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
     * Returns the current BattleArena instance
     *
     * @return the current BattleArena instance
     */
    public static BattleArena getInstance() {
        return INSTANCE;
    }
}

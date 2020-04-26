import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.player.ArenaPlayer;

import java.util.Map;

public class ArenaClassManager {

    /** Instance of the plugin for config, etc **/
    private BattleArena plugin;

    /** Map of all our ArenaClasses **/
    private Map<String, ArenaClass> arenaClasses;

    public ArenaClassManager(BattleArena plugin) {
        this.plugin = plugin;


    }

    public void giveClass(ArenaPlayer player, String pClass) {

    }
}

package org.battleplugins.arena.executor;

import mc.alk.battlecore.executor.CustomCommandExecutor;

import org.battleplugins.api.command.Command;
import org.battleplugins.api.command.CommandSender;
import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.api.message.MessageStyle;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.player.ArenaPlayer;
import org.battleplugins.arena.arena.team.ArenaTeam;
import org.battleplugins.arena.match.Match;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main arena executor for individual arenas. One of these
 * is assigned to each {@link Arena}. Custom arenas that specify a 
 * different executor use this in addition to the one that was given.
 * 
 * @author Redned
 */
public class ArenaExecutor extends CustomCommandExecutor {

    private BattleArena plugin;

    private Arena arena;

    public ArenaExecutor(BattleArena plugin, Arena arena) {
        this.plugin = plugin;
        this.arena = arena;
    }
    @MCCommand(cmds = {"create", "new"}, order = 1)
    public void newCommand(Player player, String name) {
        if (plugin.getArenaManager().getLoadedMaps().stream().anyMatch(map -> map.getName().equalsIgnoreCase(name))) {
            player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "mapAlreadyExists"));
            return;
        }

        // TODO: Custom match types & dont make new ones here (temporary)
        plugin.getArenaManager().createNewMap(name);
        arena.getMatches().add(new Match(plugin, arena));
        player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "createdNewMap").replace("%map_name%", name));
    }

    @MCCommand(cmds = {"join", "j"}, order = 2, max = 1)
    public void joinCommand(Player player) {
        this.joinCommand(player, "");
    }

    @MCCommand(cmds = {"join", "j"}, order = 3)
    public void joinCommand(Player player, String name) {
        ArenaPlayer arenaPlayer = plugin.getArenaManager().getArenaPlayer(player);
        if (arenaPlayer.isInMatch()) {
            player.sendMessage(arena.getMessageHandler().getFormattedMessage("alreadyInMatch"));
            return;
        }

        boolean nameNull = name == null || name.isEmpty();
        Match match = null;
        for (Match arenaMatch : arena.getMatches()) {
            if (arenaMatch.getMap().isPresent()) {
                if (arenaMatch.getMap().get().getName().equalsIgnoreCase(name)) {
                    match = arenaMatch;
                }
            } else if (nameNull) {
                match = arenaMatch;
                break;
            }
        }

        if (match == null) {
            player.sendMessage(MessageStyle.RED + arena.getMessageHandler().getFormattedMessage(nameNull ? "noOpenMatches" : "matchDoesNotExist"));
            return;
        }

        match.addPlayer(arenaPlayer, null);
        player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "joinedMatch"));
    }

    @MCCommand(cmds = {"leave", "l"}, order = 4)
    public void leaveCommand(Player player) {
        ArenaPlayer arenaPlayer = plugin.getArenaManager().getArenaPlayer(player);
        if (!arenaPlayer.isInMatch()) {
            player.sendMessage(arena.getMessageHandler().getFormattedMessage("notInMatch"));
            return;
        }

        Optional<ArenaTeam> opArenaTeam = arenaPlayer.getCurrentTeam();
        arenaPlayer.getCurrentMatch().ifPresent(match -> match.removePlayer(arenaPlayer));
        player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "leftMatch"));
    }

    @Override
    protected Object verifyArg(CommandSender sender, Class<?> clazz, Command command, String[] args, int curIndex, AtomicInteger numUsedStrings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ArenaPlayer arenaPlayer = plugin.getArenaManager().getArenaPlayer(player);
            if (clazz.isAssignableFrom(Match.class)) {
                return arenaPlayer.getCurrentMatch().orElseThrow(()
                        -> new IllegalArgumentException(arena.getMessageHandler().getFormattedMessage("notInMatch")));
            }
        }

        return super.verifyArg(sender, clazz, command, args, curIndex, numUsedStrings);
    }
}

package org.battleplugins.arena.executor;

import lombok.AllArgsConstructor;
import lombok.NonNull;

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
@AllArgsConstructor
public class ArenaExecutor extends CustomCommandExecutor {

    @NonNull
    private BattleArena plugin;

    @NonNull
    private Arena arena;

    @MCCommand(cmds = {"join", "j"})
    public void joinCommand(Player player, String name) {
        ArenaPlayer arenaPlayer = plugin.getArenaManager().getArenaPlayer(player);
        if (arenaPlayer.isInMatch()) {
            player.sendMessage(arena.getMessageHandler().getFormattedMessage("alreadyInMatch"));
            return;
        }

        boolean nameNull = name == null || name.isEmpty();
        Match match = null;
        for (Match arenaMatch : arena.getMatches()) {
            if (arenaMatch.getName().isPresent() && arenaMatch.getName().get().equalsIgnoreCase(name)) {
                match = arenaMatch;
            }

            if (!arenaMatch.getName().isPresent() && nameNull) {
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

    @MCCommand(cmds = {"leave", "l"})
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

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
import org.battleplugins.arena.competition.Competition;

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

    @MCCommand(cmds = {"j", "join"})
    public void joinCommand(Player player, String name) {
        ArenaPlayer arenaPlayer = plugin.getArenaManager().getArenaPlayer(player);
        if (arenaPlayer.isInCompetition()) {
            player.sendMessage(arena.getMessageHandler().getFormattedMessage("alreadyInCompetition"));
            return;
        }

        boolean nameNull = name == null || name.isEmpty();
        Competition competition = null;
        for (Competition arenaComp : arena.getCompetitions()) {
            if (arenaComp.getName().isPresent() && arenaComp.getName().get().equalsIgnoreCase(name)) {
                competition = arenaComp;
            }

            if (!arenaComp.getName().isPresent() && nameNull) {
                competition = arenaComp;
                break;
            }
        }

        if (competition == null) {
            player.sendMessage(MessageStyle.RED + arena.getMessageHandler().getFormattedMessage(nameNull ? "noOpenCompetitions" : "competitionDoesNotExist"));
            return;
        }

        competition.addPlayer(arenaPlayer, null);
        player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "joinedCompetition"));
    }

    @MCCommand(cmds = {"l", "leave"})
    public void leaveCommand(Player player) {
        ArenaPlayer arenaPlayer = plugin.getArenaManager().getArenaPlayer(player);
        if (!arenaPlayer.isInCompetition()) {
            player.sendMessage(arena.getMessageHandler().getFormattedMessage("notInCompetition"));
            return;
        }

        Optional<ArenaTeam> opArenaTeam = arenaPlayer.getCurrentTeam();
        if (opArenaTeam.isPresent()) {
            arenaPlayer.getCurrentCompetition().get().removePlayer(arenaPlayer);
        } else {
            arenaPlayer.getCurrentCompetition().get().removePlayer(arenaPlayer);
        }

        player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "leftCompetition"));
    }

    @Override
    protected Object verifyArg(CommandSender sender, Class<?> clazz, Command command, String[] args, int curIndex, AtomicInteger numUsedStrings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ArenaPlayer arenaPlayer = plugin.getArenaManager().getArenaPlayer(player);
            if (clazz.isAssignableFrom(Competition.class)) {
                return arenaPlayer.getCurrentCompetition().orElseThrow(()
                        -> new IllegalArgumentException(arena.getMessageHandler().getFormattedMessage("notInCompetition")));
            }
        }

        return super.verifyArg(sender, clazz, command, args, curIndex, numUsedStrings);
    }
}

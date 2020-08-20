package org.battleplugins.arena.executor;

import mc.alk.battlecore.executor.CustomCommandExecutor;

import org.battleplugins.api.command.Command;
import org.battleplugins.api.command.CommandSender;
import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.api.message.MessageStyle;
import org.battleplugins.arena.BattleArena;
import org.battleplugins.arena.arena.Arena;
import org.battleplugins.arena.arena.map.ArenaMap;
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
    public void newCommand(Player player, String id) {
        if (plugin.getArenaManager().getLoadedMaps().stream().anyMatch(map -> map.getName().equalsIgnoreCase(id))) {
            player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "mapAlreadyExists"));
            return;
        }

        ArenaMap arenaMap = plugin.getArenaManager().loadMap(id, this.arena);
        if (!this.plugin.getConfig().getNode("defaultArenaOptions", "createMatchesOnDemand").getBoolean()) {
            this.plugin.getArenaManager().createMatchForMap(this.arena, arenaMap, true);
        }
        player.sendMessage(arena.getMessageHandler().getFormattedMessage(player, "createdNewMap").replace("%map_name%", arenaMap.getName()));
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
                if (arenaMatch.getMap().get().getId().equalsIgnoreCase(name)) {
                    match = arenaMatch;
                    break;
                }
            } else if (nameNull) {
                match = arenaMatch;
                break;
            }
        }

        if (match == null && nameNull && !this.arena.getMatches().isEmpty()) {
            match = arena.getMatches().get(0);
        }

        if (match == null) {
            if (this.plugin.getConfig().getNode("defaultArenaOptions", "createMatchesOnDemand").getBoolean()) {
                // TODO: Check arena options for if on-demand matches for certain maps can be made
                if (nameNull) {
                    this.arena.getMatches().add(new Match(this.plugin, this.arena));
                } else if (this.arena.getAvailableMaps().containsKey(name)) {
                    this.plugin.getArenaManager().createMatchForMap(this.arena, this.arena.getAvailableMaps().get(name), true);
                } else {
                    player.sendMessage(MessageStyle.RED + arena.getMessageHandler().getFormattedMessage("matchDoesNotExist"));
                    return;
                }
                this.joinCommand(player, name);
            } else {
                player.sendMessage(MessageStyle.RED + arena.getMessageHandler().getFormattedMessage(nameNull ? "noOpenMatches" : "matchDoesNotExist"));
            }
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

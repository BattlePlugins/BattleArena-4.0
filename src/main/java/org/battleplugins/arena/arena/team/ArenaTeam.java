package org.battleplugins.arena.arena.team;

import org.battleplugins.api.inventory.item.ItemStack;
import org.battleplugins.api.message.MessageStyle;
import org.battleplugins.arena.arena.player.ArenaPlayer;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a team in a match.
 *
 * @author Redned
 */
public class ArenaTeam implements Cloneable {

    private String name;

    private MessageStyle teamColor;
    private Color armorColor;

    private ItemStack item;

    protected int maxPlayers;

    /**
     * The players in the team
     * 
     * @return the players in the team
     */
    private Set<ArenaPlayer> players = new HashSet<>();

    public ArenaTeam(String name, MessageStyle teamColor, Color armorColor, ItemStack item, int maxPlayers) {
        this.name = name;
        this.teamColor = teamColor;
        this.armorColor = armorColor;
        this.item = item;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns the name of this team
     *
     * @return the name of this team
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the color of the team
     *
     * @return the color of the team
     */
    public MessageStyle getTeamColor() {
        return teamColor;
    }

    /**
     * Returns the color of the armor
     *
     * @return the color of the armor
     */
    public Color getArmorColor() {
        return armorColor;
    }

    /**
     * Returns the {@link ItemStack} that represents this team,
     * usually an item in the inventory that all team members
     * get when they become part of the team (largely dependent
     * on the gamemode)
     *
     * @return the itemstack that represents this team
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Returns the maximum amount of players this
     * team can have
     *
     * @return the maximum amount of players this team can have
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Returns the {@link ArenaPlayer}s on this team
     *
     * @return the players on this team
     */
    public Set<ArenaPlayer> getPlayers() {
        return players;
    }

    /**
     * Returns if the team is remaining in the match
     * 
     * @return if the team is remaining in the match
     */
    public boolean isRemainingInMatch() {
        if (players.isEmpty())
            return false;
        
        for (ArenaPlayer player : players) {
            if (player.isInMatch())
                return true;
        }
        
        return false;
    }

    /**
     * Clones the arena team
     *
     * @return the cloned team
     */
    protected ArenaTeam clone() {
        return new ArenaTeam(name, teamColor, armorColor, item, maxPlayers);
    }
}

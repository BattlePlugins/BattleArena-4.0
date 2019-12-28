package org.battleplugins.arena.arena.team;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.battleplugins.api.message.MessageStyle;
import org.battleplugins.arena.arena.player.ArenaPlayer;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Represents a team in a competition.
 *
 * @author Redned
 */
@Getter
@RequiredArgsConstructor
public class ArenaTeam implements Cloneable {

    /**
     * The name of the team
     * 
     * @return the name of the team
     */
    @NonNull
    private String name;

    /**
     * The color of the team
     */
    @NonNull
    private String teamColor;

    /**
     * The armor color of the team
     */
    @NonNull
    private String armorColor;

    /**
     * The item of the team
     *
     * @return the item of the team
     */
    @NonNull
    private String item;

    @NonNull
    protected int maxPlayers;

    /**
     * The players in the team
     * 
     * @return the players in the team
     */
    private Set<ArenaPlayer> players = new HashSet<>();
    
    /**
     * Returns if the team is remaining in the competition
     * 
     * @return if the team is remaining in the competition
     */
    public boolean isRemainingInCompetition() {
        if (players.isEmpty())
            return false;
        
        for (ArenaPlayer player : players) {
            if (player.isInCompetition())
                return true;
        }
        
        return false;
    }

    /**
     * Returns the color of the team
     *
     * @return the color of the team
     */
    public MessageStyle getTeamColor() {
        return MessageStyle.getByChar(teamColor.replace("&", ""));
    }

    /**
     * Returns the color of the armor
     *
     * @return the color of the armor
     */
    public Color getArmorColor() {
        // Check if the color is octal, decimal or hexadecimal
        if (!armorColor.contains(",")) {
            try {
                return Color.decode(armorColor);
            } catch (Exception ex) {
                return new Color(0, 0, 0);
            }
        }
        // Must be rgb, so check for that
        StringTokenizer tokenizer = new StringTokenizer(armorColor, ",");
        int r = Integer.parseInt(tokenizer.nextToken());
        int g = Integer.parseInt(tokenizer.nextToken());
        int b = Integer.parseInt(tokenizer.nextToken());
        return new Color(r, g, b);
    }

    /**
     * Clones the arena team.
     *
     * @return the cloned team
     */
    protected ArenaTeam clone() {
        return new ArenaTeam(name, teamColor, armorColor, item, maxPlayers);
    }
}

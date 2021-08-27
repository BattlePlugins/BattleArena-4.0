package org.battleplugins.arena.match.state.option.options;

import org.battleplugins.arena.match.Match;
import org.battleplugins.arena.match.state.option.StateOption;
import org.bukkit.entity.Player;

/**
 * State option that gives effects to a player.
 * 
 * @author Redned
 */
public class GiveEffectsOption extends StateOption {

    private String effect;

    private int duration;

    private int amplifier;
    
    public GiveEffectsOption(String name) {
        super(name);
    }

    @Override
    public void runOption(Player player, Match match) {
        
    }

    /**
     * Returns the effect set
     *
     * @return the effect set
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Returns the duration to set the effect for
     *
     * @return the duration to set the effect for
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the amplifier to set for the effect
     *
     * @return the amplifier to set for the effect
     */
    public int getAmplifier() {
        return amplifier;
    }
}

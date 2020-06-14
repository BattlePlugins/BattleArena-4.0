package org.battleplugins.arena.match.state.option.options;

import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.arena.match.Match;
import org.battleplugins.arena.match.state.option.StateOption;
import org.battleplugins.arena.file.configuration.ConfigProperty;

/**
 * State option that gives effects to a player.
 * 
 * @author Redned
 */
public class GiveEffectsOption extends StateOption {

    @ConfigProperty(required = true)
    private String effect;
    
    @ConfigProperty
    private int duration;
    
    @ConfigProperty
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

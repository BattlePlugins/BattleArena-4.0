package org.battleplugins.arena.competition.state.option.options;

import lombok.Getter;

import mc.alk.mc.MCPlayer;

import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.competition.state.option.StateOption;
import org.battleplugins.arena.config.ConfigProperty;

/**
 * State option that gives effects to a player.
 * 
 * @author Redned
 */
@Getter
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
    public void runOption(MCPlayer player, Competition competition) {
        
    }
}

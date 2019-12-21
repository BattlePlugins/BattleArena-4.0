package org.battleplugins.arena.competition.state.option.options;

import lombok.Getter;

import org.battleplugins.arena.competition.Competition;
import org.battleplugins.arena.competition.state.option.StateOption;
import org.battleplugins.arena.config.ConfigProperty;
import org.battleplugins.entity.living.player.Player;

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
    public void runOption(Player player, Competition competition) {
        
    }
}

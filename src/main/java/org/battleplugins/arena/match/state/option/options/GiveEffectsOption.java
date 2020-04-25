package org.battleplugins.arena.match.state.option.options;

import lombok.Getter;

import org.battleplugins.api.entity.living.player.Player;
import org.battleplugins.arena.match.Match;
import org.battleplugins.arena.match.state.option.StateOption;
import org.battleplugins.arena.configuration.ConfigProperty;

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
    public void runOption(Player player, Match match) {
        
    }
}
package org.battleplugins.arena.match.state;

import java.util.HashSet;
import java.util.Set;

/**
 * A class containing all the {@link MatchState}s.

 * Developers wanting to create their own match states can simply
 * use the {@link MatchState#builder()} method and it will automatically
 * be added to the set of allowed match states.
 * 
 * @author Redned
 */
public class MatchStates {

    static final Set<MatchState> matchStates = new HashSet<>();
    
    public static final MatchState DEFAULTS = MatchState.builder().name("defaults").build();
    public static final MatchState NONE = MatchState.builder().name("none").build();
    public static final MatchState ON_ENTER = MatchState.builder().name("onEnter").build();
    public static final MatchState ON_LEAVE = MatchState.builder().name("onLeave").build();
    public static final MatchState ON_CREATE = MatchState.builder().name("onCreate").build();
    public static final MatchState ON_JOIN = MatchState.builder().name("onJoin").build();
    public static final MatchState ON_OPEN = MatchState.builder().name("onOpen").build();
    public static final MatchState ON_BEGINNING = MatchState.builder().name("onBeginning").build();
    public static final MatchState ON_PRE_START = MatchState.builder().name("onPreStart").build();
    public static final MatchState ON_START = MatchState.builder().name("onStart").build();
    public static final MatchState ON_VICTORY = MatchState.builder().name("onVictory").build();
    public static final MatchState ON_COMPLETE = MatchState.builder().name("onComplete").build();
    public static final MatchState ON_CANCEL = MatchState.builder().name("onCancel").build();
    public static final MatchState ON_FINISH = MatchState.builder().name("onFinish").build();
    public static final MatchState ON_SPAWN = MatchState.builder().name("onSpawn").build();
    public static final MatchState ON_DEATH = MatchState.builder().name("onDeath").build();
    public static final MatchState ON_KILL = MatchState.builder().name("onKill").build();
    public static final MatchState ON_WIN = MatchState.builder().name("onWin").aliases(new String[]{"winners"}).build();
    public static final MatchState ON_DRAW = MatchState.builder().name("onDraw").build();
    public static final MatchState ON_LOSE = MatchState.builder().name("onLose").aliases(new String[]{"losers"}).build();
    public static final MatchState ON_TIME_EXPIRE = MatchState.builder().name("onTimeExpire").build();
    public static final MatchState ON_COUNTDOWN = MatchState.builder().name("onCountdown").build();
    public static final MatchState ON_QUEUE_ENTER = MatchState.builder().name("onQueueEnter").build();
    public static final MatchState PRE_REQS = MatchState.builder().name("preReqs").build();
    
    /**
     * Returns a set of all the registered match states
     * 
     * @return a set of all the registered match states
     */
    public static Set<MatchState> values() {
        return matchStates;
    }
}

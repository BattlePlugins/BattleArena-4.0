package org.battleplugins.arena.competition.state;

import java.util.HashSet;
import java.util.Set;

/**
 * A class containing all the general competition states.
 * For more specific states for matches and events, see 
 * {@link MatchState} for match-specific states and {@link EventState}
 * for event-specific states.
 * 
 * Developers wanting to create their own competition states can simply
 * use the {@link CompetitionState#builder()} method and it will automatically
 * be added to the set of allowed competition states.
 * 
 * @author Redned
 */

public class CompetitionStates {

    static final Set<CompetitionState> competitionStates = new HashSet<>();
    
    public static final CompetitionState DEFAULTS = CompetitionState.builder().name("defaults").build();
    public static final CompetitionState NONE = CompetitionState.builder().name("none").build();
    public static final CompetitionState ON_ENTER = CompetitionState.builder().name("onEnter").build();
    public static final CompetitionState ON_LEAVE = CompetitionState.builder().name("onLeave").build();
    public static final CompetitionState ON_CREATE = CompetitionState.builder().name("onCreate").build();
    public static final CompetitionState ON_JOIN = CompetitionState.builder().name("onJoin").build();
    public static final CompetitionState ON_OPEN = CompetitionState.builder().name("onOpen").build();
    public static final CompetitionState ON_BEGINNING = CompetitionState.builder().name("onBeginning").build();
    public static final CompetitionState ON_PRE_START = CompetitionState.builder().name("onPreStart").build();
    public static final CompetitionState ON_START = CompetitionState.builder().name("onStart").build();
    public static final CompetitionState ON_VICTORY = CompetitionState.builder().name("onVictory").build();
    public static final CompetitionState ON_COMPLETE = CompetitionState.builder().name("onComplete").build();
    public static final CompetitionState ON_CANCEL = CompetitionState.builder().name("onCancel").build();
    public static final CompetitionState ON_FINISH = CompetitionState.builder().name("onFinish").build();
    public static final CompetitionState ON_SPAWN = CompetitionState.builder().name("onSpawn").build();
    public static final CompetitionState ON_DEATH = CompetitionState.builder().name("onDeath").build();
    public static final CompetitionState ON_KILL = CompetitionState.builder().name("onKill").build();
    public static final CompetitionState ON_WIN = CompetitionState.builder().name("onWin").aliases(new String[]{"winners"}).build();
    public static final CompetitionState ON_DRAW = CompetitionState.builder().name("onDraw").build();
    public static final CompetitionState ON_LOSE = CompetitionState.builder().name("onLose").aliases(new String[]{"losers"}).build();
    public static final CompetitionState ON_TIME_EXPIRE = CompetitionState.builder().name("onTimeExpire").build();
    public static final CompetitionState ON_COUNTDOWN = CompetitionState.builder().name("onCountdown").build();
    public static final CompetitionState ON_QUEUE_ENTER = CompetitionState.builder().name("onQueueEnter").build();
    public static final CompetitionState PRE_REQS = CompetitionState.builder().name("preReqs").build();
    
    /**
     * Returns a set of all the registered competition states
     * 
     * @return a set of all the registered competition states
     */
    public static Set<CompetitionState> values() {
        return competitionStates;
    }
}

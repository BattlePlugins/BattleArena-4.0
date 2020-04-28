package org.battleplugins.arena.match.state;

/**
 * A state in a match.
 * 
 * @author Redned
 */
public class MatchState {

    private String name;
    private String[] aliases;
    
    protected MatchState(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        
        MatchStates.matchStates.add(this);
    }

    /**
     * Returns the name of the match state
     *
     * @return the name of the match state
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the aliases of the match state
     *
     * @return the aliases of the match states
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Returns a new match state builder
     *
     * @return a new match state builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String[] aliases;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder aliases(String... aliases) {
            this.aliases = aliases;
            return this;
        }

        public MatchState build() {
            return new MatchState(name, aliases);
        }
    }
}

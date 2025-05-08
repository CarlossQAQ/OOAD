package accommodation.domain.state;

import accommodation.domain.model.Accommodation;

/**
 * Factory class for creating StateContext objects
 */
public class StateContextFactory {
    
    /**
     * Create a new StateContext with initial vacant state
     * @param accommodation The accommodation to associate with the context
     * @return A new StateContext
     */
    public static StateContext createContext(Accommodation accommodation) {
        return new StateContext(accommodation);
    }
} 
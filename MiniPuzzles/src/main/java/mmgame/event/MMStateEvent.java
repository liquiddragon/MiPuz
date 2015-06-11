package mmgame.event;

import java.util.EventObject;

/**
 * This is the MMGame custom event for game state operations.
 */
public class MMStateEvent extends EventObject {

    private final MMStates.State mmState;

    /**
     * This is default event constructor storing custom event information.
     *
     * @param source of event
     * @param mmState state information
     */
    public MMStateEvent(Object source, MMStates.State mmState) {
        super(source);
        this.mmState = mmState;
    }

    /**
     * This method allows recipient to obtain state that was passed to it.
     *
     * @return state information
     */
    public MMStates.State getState() {
        return mmState;
    }
}

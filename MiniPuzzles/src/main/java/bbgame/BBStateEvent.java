package bbgame;

import java.util.EventObject;

/**
 * This is the BBGame custom event for game state operations.
 */
public class BBStateEvent extends EventObject {

    private final BBStates.State bbState;

    /**
     * This is default event constructor that passes state information to
     * listeners.
     *
     * @param source of event
     * @param bbState state information
     */
    public BBStateEvent(Object source, BBStates.State bbState) {
        super(source);
        this.bbState = bbState;
    }

    /**
     * This method allows recipient to obtain state that was passed to it.
     *
     * @return state information
     */
    public BBStates.State getState() {
        return bbState;
    }
}

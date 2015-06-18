/**
 * BBGame custom state event.
 */
package bbgame.event;

import java.util.EventObject;

/**
 * BBGame custom event for game state operations.
 */
public class BBStateEvent extends EventObject {

    private final BBStates.State bbState;

    /**
     * Construct custom event with given state.
     *
     * @param source of event
     * @param bbState state information
     */
    public BBStateEvent(Object source, BBStates.State bbState) {
        super(source);
        this.bbState = bbState;
    }

    /**
     * Obtain state that this event represents.
     *
     * @return state information
     */
    public BBStates.State getState() {
        return bbState;
    }
}

/**
 * MMGame custom state event.
 */
package mmgame.event;

import java.util.EventObject;

/**
 * MMGame custom event for game state operations.
 */
public class MMStateEvent extends EventObject {

    private final MMStates.State mmState;

    /**
     * Construct custom event with given state.
     *
     * @param source of event
     * @param mmState state information
     */
    public MMStateEvent(Object source, MMStates.State mmState) {
        super(source);
        this.mmState = mmState;
    }

    /**
     * Obtain state that this event represents.
     *
     * @return state information
     */
    public MMStates.State getState() {
        return mmState;
    }
}

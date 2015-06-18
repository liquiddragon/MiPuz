/**
 * MMGame custom timer event.
 */
package mmgame.event;

import java.util.EventObject;

/**
 * MMGame custom event for game timer operations.
 */
public class MMTimerEvent extends EventObject {

    private final int id;

    /**
     * Construct custom event with given timer ID.
     *
     * @param source of event
     * @param id timer event ID
     */
    public MMTimerEvent(Object source, int id) {
        super(source);
        this.id = id;
    }

    /**
     * Obtain ID that this event represents.
     *
     * @return timer ID
     */
    public int getID() {
        return id;
    }
}

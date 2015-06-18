/**
 * BBGame state event listener interface.
 */
package bbgame.event;

/**
 * Interface for listeners interested in the BBGame states.
 */
public interface BBStateListener {

    /**
     * Listener interface.
     *
     * @param event state information
     */
    public void bbStateReceived(BBStateEvent event);
}

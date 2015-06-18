/**
 * MMGame state event listener interface.
 */
package mmgame.event;

/**
 * Interface for listeners interested in the MMGame states.
 */
public interface MMStateListener {

    /**
     * Listener interface.
     *
     * @param event state information
     */
    public void mmStateReceived(MMStateEvent event);
}

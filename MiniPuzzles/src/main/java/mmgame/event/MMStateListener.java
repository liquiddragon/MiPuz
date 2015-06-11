package mmgame.event;

/**
 * This is state interface for listeners interested in the MMGame states.
 */
public interface MMStateListener {

    /**
     * This method allow listener to receive game state information.
     *
     * @param event state information
     */
    public void mmStateReceived(MMStateEvent event);
}

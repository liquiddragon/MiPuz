package bbgame.event;

/**
 * This is state interface for listeners interested in the BBGame states.
 */
public interface BBStateListener {

    /**
     * This method allow listener to receive game state information.
     *
     * @param event state information
     */
    public void bbStateReceived(BBStateEvent event);
}

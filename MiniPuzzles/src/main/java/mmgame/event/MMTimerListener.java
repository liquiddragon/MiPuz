/**
 * MMGame timer event listener interface.
 */
package mmgame.event;

/**
 * Interface for listeners interested in the MMGame timers.
 */
public interface MMTimerListener {

    /**
     * Listener interface.
     *
     * @param event state information
     */
    public void mmTimerReceived(MMTimerEvent event);
}

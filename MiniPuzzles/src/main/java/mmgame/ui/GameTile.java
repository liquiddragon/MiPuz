/**
 * This is MMGame GameTile GUI custom object.
 */
package mmgame.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import mmgame.event.MMTimerEvent;
import mmgame.event.MMTimerListener;

/**
 * This class creates game tile by extending JToggleButton and adding custom
 * feature for it.
 */
public class GameTile extends JToggleButton implements ActionListener {

    private final int blinkTimerLength = 500;
    private final MMTimerListener mmTimerListener;
    private int blinkTime;
    private Timer blinker;

    /**
     * Creates GameTile using given icons as default, pressed and select as well
     * as registering event listener.
     *
     * @param primaryIcon default icon
     * @param secondaryIcon pressed and selected state icon
     * @param mmListener MMTimerEvent listener
     */
    public GameTile(ImageIcon primaryIcon, ImageIcon secondaryIcon, MMTimerListener mmListener) {
        super(primaryIcon);
        super.setPressedIcon(secondaryIcon);
        super.setSelectedIcon(secondaryIcon);
        mmTimerListener = mmListener;
        blinkTime = blinkTimerLength;
        createTimer();
    }

    /**
     * Simulates GameTile selection and deselection using blink time.
     */
    public void blink() {
        super.setSelected(true);
        blinker.restart();
    }

    /**
     * Returns blink time.
     *
     * @return time in milliseconds
     */
    public int getBlinkTime() {
        return blinkTime;
    }

    /**
     * Sets blink time.
     *
     * @param blinkTime time in milliseconds
     */
    public void setBlinkTime(int blinkTime) {
        this.blinkTime = blinkTime;
        createTimer();
    }

    private void createTimer() {
        blinker = new Timer(blinkTime, this);
        blinker.setRepeats(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.setSelected(false);
        //System.out.print("GT event.." + System.currentTimeMillis() + "...");
        mmTimerListener.mmTimerReceived(new MMTimerEvent(this, 1));
    }
}

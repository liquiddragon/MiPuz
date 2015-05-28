package bbgame;

import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import javax.swing.ImageIcon;

/**
 * This is a BBGame for the framework.
 */
public class BBGame implements Game {

    private final GameInfo gi;
    private final ImageIcon gicon;
    private BBEngine engine;

    public BBGame() {
        this.gicon = new ImageIcon("src/main/java/bbgame/resources/Binary-icon.png");
        this.gi = new GameInfo("BBGame", "This is a BB game", this.gicon);
    }

    /**
     * This method provides GameInfo object of this game.
     * 
     * @return GameInfo object
     */
    @Override
    public GameInfo retrieveGameInfo() {
        return this.gi;
    }

    /**
     * This method performs game initialisation procedures.
     * 
     * @return Whether initialisation was successful or not
     */
    @Override
    public boolean initGame() {
        this.engine = new BBEngine();
        return true;
    }

    /**
     * This method runs the game for one full cycle.
     */
    @Override
    public void runGame() {
        BBEngine.CheckResult result;

        long value = this.engine.obtainNewGuess();

        do {
            //Obtain input

            // As UI is missing convert value to 'guess' to exit the loop
            result = this.engine.checkGuess(Long.toBinaryString(value));
        } while (result != BBEngine.CheckResult.CORRECT);
    }

    /**
     * This method will perform game cleanup, if any.
     */
    @Override
    public void cleanUpGame() {
    }
}

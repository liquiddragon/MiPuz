package bbgame;

import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import javax.swing.ImageIcon;

/**
 * This is a test class for interface and framework operations.
 */
public class BBGame implements Game {

    private final GameInfo gi;
    private final ImageIcon gicon;
    
    public BBGame() {
        this.gicon = new ImageIcon("src/main/java/bbgame/resources/Binary-icon.png");
        this.gi = new GameInfo("BBGame", "This is a test class for framework and its operations", this.gicon);
    }
    
    @Override
    public GameInfo retrieveGameInfo() {
        return this.gi;
    }

    @Override
    public boolean initGame() {
        return true;
    }

    @Override
    public void runGame() {
    }

    @Override
    public void cleanUpGame() {
    }
}

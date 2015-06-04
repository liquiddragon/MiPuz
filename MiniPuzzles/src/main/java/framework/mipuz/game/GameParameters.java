package framework.mipuz.game;

import javax.swing.JPanel;

/**
 * This class is container for game to obtain required information from
 * framework for it to operate in it.
 */
public class GameParameters {

    private JPanel gameDisplay;
    private GameEnd gameEnd;

    public GameParameters() {
        gameDisplay = null;
        gameEnd = null;
    }

    public JPanel getGameDisplay() {
        return gameDisplay;
    }

    public void setGameDisplay(JPanel gameDisplay) {
        this.gameDisplay = gameDisplay;
    }

    public GameEnd getGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(GameEnd gameEnd) {
        this.gameEnd = gameEnd;
    }
}

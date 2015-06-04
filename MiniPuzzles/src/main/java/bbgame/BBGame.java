package bbgame;

import bbgame.logic.BBEngine;
import bbgame.ui.BBGameUI;
import bbgame.ui.BBStartUI;
import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * This is a BBGame for the framework.
 */
public class BBGame implements Game, BBStateListener {

    private final GameInfo gi;
    private final ImageIcon gicon;
    private BBEngine engine;
    private GameParameters gameParams;
    private BBStartUI bbStartUI;
    private BBGameUI bbGameUI;

    /**
     * This is default constructor.
     */
    public BBGame() {
        gicon = new ImageIcon("src/main/java/bbgame/resources/Binary-icon.png");
        gi = new GameInfo("BBGame", "This is a BB game", gicon);
    }

    /**
     * This method provides GameInfo object of this game.
     *
     * @return GameInfo object
     */
    @Override
    public GameInfo retrieveGameInfo() {
        return gi;
    }

    /**
     * This method performs game initialisation procedures.
     *
     * @param gameParams Framework parameters for the game
     * @return Whether initialisation was successful or not
     */
    @Override
    public boolean initGame(GameParameters gameParams) {
        this.gameParams = gameParams;
        engine = new BBEngine();
        bbStartUI = new BBStartUI(gameParams.getGameDisplay(), this);
        return true;
    }

    /**
     * This method starts the game.
     */
    @Override
    public void runGame() {
        bbStartUI.askGameLevel();
    }

    /**
     * This method will perform game cleanup, if any.
     */
    @Override
    public void cleanUpGame() {
    }

    /**
     * This method handles BBGame main state operations.
     *
     * @param event to be handled
     */
    @Override
    public void bbStateReceived(BBStateEvent event) {
        switch (event.getState()) {
            case RETURN_TO_MENU:
                bbStartUI.removeStartUI();
                gameParams.getGameEnd().finished();
                break;

            case RUN:
                BBEngine.GameLevel gl = bbStartUI.getSelection();
                engine.setGameLevel(gl);
                bbStartUI.removeStartUI();

                // Functionality test displayed during development as BBGame
                // does not provide return back to menu as of yet.
                JOptionPane.showMessageDialog(null, "Running...", "MiPuz", JOptionPane.INFORMATION_MESSAGE);
                /*
                bbGameUI
                        = new BBGameUI(gameParams.getGameDisplay(), engine);
                bbGameUI.runGame();
                */
                gameParams.getGameEnd().finished();
                break;
        }
    }
}

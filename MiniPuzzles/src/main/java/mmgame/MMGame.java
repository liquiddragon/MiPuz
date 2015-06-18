/**
 * This is MMGame shell.
 */
package mmgame;

import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import javax.swing.ImageIcon;
import mmgame.event.MMStateEvent;
import mmgame.event.MMStateListener;
import mmgame.logic.MMEngine;
import mmgame.ui.MMGameUI;
import mmgame.ui.MMStartUI;

/**
 * This is a MMGame main controller.
 */
public class MMGame implements Game, MMStateListener {

    private final GameInfo gi;
    private final ImageIcon gicon;
    private GameParameters gameParams;
    private MMEngine engine;
    private MMStartUI mmStartUI;
    private MMGameUI mmGameUI;

    /**
     * Create MMGame shell.
     */
    public MMGame() {
        gicon = new ImageIcon(getClass().getResource("/MM-icon.png"));
        gi = new GameInfo("MMGame", "This is a MM game", gicon);
    }

    /**
     * Provide GameInfo object of this game.
     *
     * @return GameInfo object
     */
    @Override
    public GameInfo retrieveGameInfo() {
        return gi;
    }

    /**
     * Perform game initialisation procedures.
     *
     * @param gameParams Framework parameters for the game
     * @return Whether initialisation was successful or not
     */
    @Override
    public boolean initGame(GameParameters gameParams) {
        this.gameParams = gameParams;
        engine = new MMEngine();
        mmStartUI = new MMStartUI(gameParams.getGameDisplay(), this);

        return true;
    }

    /**
     * Start the game.
     */
    @Override
    public void runGame() {
        mmStartUI.askGameLevel();
    }

    /**
     * Perform game cleanup, if any.
     */
    @Override
    public void cleanUpGame() {
        // None required so far
    }

    /**
     * Handle MMGame main state operations.
     *
     * @param event to be handled
     */
    @Override
    public void mmStateReceived(MMStateEvent event) {
        switch (event.getState()) {
            case RETURN_TO_MENU:
                mmStartUI.removeStartUI();
                gameParams.getGameEnd().finished();
                break;

            case RUN:
                mmStartUI.removeStartUI();

                MMEngine.GameLevel gl = mmStartUI.getSelection();
                engine.setGameLevel(gl);

                mmGameUI = new MMGameUI(gameParams.getGameDisplay(),
                        engine, this);
                mmGameUI.runGame();
                break;

            case GAME_OVER:
                mmGameUI.removeGameUI();
                mmStartUI.askGameLevel();
                break;
        }
    }
}

package mmgame;

import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import javax.swing.ImageIcon;
import mmgame.event.MMStateEvent;
import mmgame.event.MMStateListener;
import mmgame.logic.MMEngine;
import mmgame.ui.MMStartUI;

/**
 * This is a MMGame for the framework.
 */
public class MMGame implements Game, MMStateListener {

    private final GameInfo gi;
    private final ImageIcon gicon;
    private GameParameters gameParams;
    private MMEngine engine;
    private MMStartUI mmStartUI;

    /**
     * This is default constructor.
     */
    public MMGame() {
        gicon = new ImageIcon("src/main/java/mmgame/resources/MM-icon.png");
        gi = new GameInfo("MMGame", "This is a MM game", gicon);
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

    @Override
    public boolean initGame(GameParameters gameParams) {
        this.gameParams = gameParams;
        engine = new MMEngine();
        mmStartUI = new MMStartUI(gameParams.getGameDisplay(), this);

        return true;
    }

    @Override
    public void runGame() {
        mmStartUI.askGameLevel();
    }

    @Override
    public void cleanUpGame() {
        
    }

    /**
     * This method handles MMGame main state operations.
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
                break;

            case GAME_OVER:
                break;
        }
    }
}

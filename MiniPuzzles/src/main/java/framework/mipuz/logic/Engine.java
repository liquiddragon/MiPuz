package framework.mipuz.logic;

import bbgame.BBGame;
import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import java.util.Iterator;
import mmgame.MMGame;

/**
 * This class handles framework central logic.
 *
 */
public class Engine {

    private final Games games;

    /**
     * This is framework engine default constructor.
     */
    public Engine() {
        games = new Games();

        loadGames();
    }

    /**
     * This method creates and add games into game manager.
     *
     * The game classes are actually created by this method and stored in a
     * list. This means that most of normal constructor operations should be
     * placed in initGame method.
     */
    private void loadGames() {
        games.addGame(new BBGame());
        games.addGame(new MMGame());
    }

    /**
     * This method returns number of games currently available.
     *
     * @return number of game available
     */
    public int numberOfGames() {
        return games.numberOfEntries();
    }

    /**
     * This method provides Iterator access to all games managed by game
     * manager.
     *
     * @return Iterator to game list
     */
    public Iterator listGameInfos() {
        return games.listGameInfos().iterator();
    }

    /**
     * This method handles running of selected game.
     *
     * @param gi GameInfo object of requested game to be played
     * @param gameParams parameters for the game to use to function in given
     * framework
     */
    public void playGame(GameInfo gi, GameParameters gameParams) {
        Game game = games.retrieveGame(gi);

        if (game != null) {
            if (game.initGame(gameParams) == true) {
                game.runGame();
            }
            game.cleanUpGame();
        }
    }
}

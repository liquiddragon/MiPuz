package framework.mipuz.logic;

import bbgame.BBGame;
import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import java.util.Iterator;

/**
 * This class handles framework central logic.
 *
 */
public class Engine {

    private final Games games;

    public Engine() {
        this.games = new Games();

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
        this.games.addGame(new BBGame());
    }

    /**
     * This method returns number of games currently available.
     *
     * @return number of game available
     */
    public int numberOfGames() {
        return this.games.numberOfEntries();
    }

    /**
     * This method provides Iterator access to all games managed by game
     * manager.
     *
     * @return Iterator to game list
     */
    public Iterator listGameInfos() {
        return this.games.listGameInfos().iterator();
    }

    /**
     * This method handles running of selected game.
     *
     * @param gi GameInfo object of requested game to be played
     */
    public void playGame(GameInfo gi) {
        Game game = this.games.retrieveGame(gi);

        if (game != null) {
            if (game.initGame() == true) {
                game.runGame();
            }
            game.cleanUpGame();
        }
    }
}

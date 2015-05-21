package framework.mipuz.logic;

import bbgame.BBGame;
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
     */
    private void loadGames() {
        this.games.addGame(new BBGame().retrieveGameInfo());
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
    public Iterator listGames() {
        return this.games.listGames().iterator();
    }
}

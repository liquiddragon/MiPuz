/**
 * Manager for games that framework hosts.
 */
package framework.mipuz.logic;

import framework.mipuz.game.Game;
import java.util.ArrayList;
import java.util.List;
import framework.mipuz.game.GameInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * Manage games implementing Game interface.
 *
 */
public class Games {

    private final List<GameInfo> gamesInfo;
    private final Map<Integer, Game> games;

    /**
     * Construct framework game manager.
     */
    public Games() {
        gamesInfo = new ArrayList<>();
        games = new HashMap<>();
    }

    /**
     * Adds new game into manager.
     *
     * @param game to be added
     */
    public void addGame(Game game) {
        gamesInfo.add(game.retrieveGameInfo());
        games.put(obtainStorageKey(game.retrieveGameInfo()), game);
    }

    /**
     * Provides list of all games that are managed.
     *
     * @return list of all games
     */
    public List<GameInfo> listGameInfos() {
        return gamesInfo;
    }

    /**
     * Provides number of entries that are managed currently.
     *
     * @return number of game entries
     */
    public int numberOfEntries() {
        return gamesInfo.size();
    }

    /**
     * Retrieve requested game object.
     *
     * @param gi GameInfo for requested game
     * @return Game if it was found, otherwise null
     */
    public Game retrieveGame(GameInfo gi) {
        Game game = null;
        if (games.containsKey(obtainStorageKey(gi))) {
            game = games.get(obtainStorageKey(gi));
        }

        return game;
    }

    /**
     * Obtain unique ID for game. Presently this is done by obtaining hash code
     * of game short name.
     *
     * @param gi GameInfo object of the game
     * @return unique ID for GameInfo object
     */
    private int obtainStorageKey(GameInfo gi) {
        return gi.getShortName().hashCode();
    }
}

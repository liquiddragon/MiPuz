package framework.mipuz.logic;

import java.util.ArrayList;
import java.util.List;
import framework.mipuz.game.GameInfo;

/**
 * This class manages games implementing Game interface.
 *
 */
public class Games {

    private final List<GameInfo> games;

    public Games() {
        this.games = new ArrayList<>();
    }

    /**
     * Adds new game into manager.
     *
     * @param gi game to be added
     */
    public void addGame(GameInfo gi) {
        this.games.add(gi);
    }

    /**
     * Provides list of all games that are managed.
     *
     * @return list of all games
     */
    public List<GameInfo> listGames() {
        return this.games;
    }

    /**
     * Provides number of entries that are managed currently.
     *
     * @return number of game entries
     */
    public int numberOfEntries() {
        return this.games.size();
    }
}

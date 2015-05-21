package framework.mipuz.game;

public interface Game {

    /**
     * Returns an GameInfo object that provides information about a game.
     *
     * @return The game information
     */
    public GameInfo retrieveGameInfo();

    /**
     * Game initialisation method that is called by framework to allow game to
     * make its initialisations required before the game is being run.
     *
     * @return value that indicates whether initialisation was successful or
     * not. If not then the game is not attempted to execute.
     */
    public boolean initGame();

    /**
     * The game execution method called by framework.
     */
    public void runGame();

    /**
     * Game cleaning method that framework calls when it is preparing to shut
     * down application or to clean the game away. This method is called always
     * if initGame method is called.
     */
    public void cleanUpGame();
}

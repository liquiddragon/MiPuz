package framework.mipuz.logic;

import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Games class tests.
 */
public class GamesTest {

    private GTest game;
    private Games games;

    /**
     * Test that new class instantiation creates class variables.
     */
    @Test
    public void classInstantiationTest() {
        games = new Games();

        Field gamesField = obtainGamesClassPrivateField("games");

        try {
            gamesField.setAccessible(true);
            assertNotNull(gamesField.get(games));
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test adding game object to list.
     */
    @Test
    public void addGameTest() {
        game = new GTest();
        game.setGameInfo(new GameInfo("Test 1", "Test 1", null));

        games = new Games();
        games.addGame(game);

        List<GameInfo> lgi = games.listGameInfos();

        checkListGamesResult(1);
    }

    /**
     * Test adding game info object afterwards to list works.
     */
    @Test
    public void addGameTestAfterwards() {
        games = addGamesToList(new GameInfo("Test 1", "Test 1", null));

        List<GameInfo> lgi = games.listGameInfos();
        checkListGamesResult(1);

        game = new GTest();
        game.setGameInfo(new GameInfo("Test 2", "Test 2", null));
        games.addGame(game);
        checkListGamesResult(2);
    }

    /**
     * Test listing games provides all items.
     */
    @Test
    public void listGamesTest() {
        games = addGamesToList(new GameInfo("Test 1", "Test 1", null),
                new GameInfo("Test 2", "Test 2", null));

        checkListGamesResult(2);
    }

    /**
     * Test obtaining number of entries with few items works.
     */
    @Test
    public void numberOfEntriesIsTwoTest() {
        games = addGamesToList(new GameInfo("Test 1", "Test 1", null),
                new GameInfo("Test 2", "Test 2", null));

        assertEquals(2, games.numberOfEntries());
    }

    /**
     * Test obtaining number of entries without any items works.
     */
    @Test
    public void numberOfEntriesWOEntriesTest() {
        games = new Games();

        assertEquals(0, games.numberOfEntries());
    }

    /**
     * Test storage key generating method.
     */
    @Test
    public void obtainStorageKeyTest() {
        game = new GTest();
        game.setGameInfo(new GameInfo("Test 1", "Test 1", null));

        games = new Games();
        games.addGame(game);

        Field gamesField = obtainGamesClassPrivateField("games");

        try {
            gamesField.setAccessible(true);
            Map<?, ?> gs = ((Map) gamesField.get(games));
            assertNotNull(gs.get("Test 1".hashCode()));
            assertEquals(1, gs.size());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Helper method to obtaining access to private field in Games class for
     * testing purposes.
     *
     * @param fieldName Name of field for which access is wanted
     * @return Reference to Games class private field, if successful, or null,
     * if unsuccessful
     */
    private Field obtainGamesClassPrivateField(String fieldName) {
        Class gamesClass = Games.class;
        Field field = null;

        try {
            field = gamesClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println("Missing field: " + e.toString());
        } catch (SecurityException e) {
            System.out.println(e.toString());
        }
        return field;
    }

    /**
     * Helper method for checking games list results in tests.
     */
    private void checkListGamesResult(int nrOfEntries) {
        assertEquals(nrOfEntries, games.numberOfEntries());

        int i = 1;
        for (GameInfo gi : games.listGameInfos()) {
            assertEquals("Test " + i, gi.getShortName());
            assertEquals("Test " + i, gi.getDescription());
            assertNull(gi.getIcon());
            i++;
        }
    }

    /**
     * Helper method to add GameInfo objects to Games class test object.
     *
     * @param gi GameInfo entries to be added to the list
     * @return Reference to Games object
     */
    private Games addGamesToList(GameInfo... gi) {
        Games gamesTest = new Games();

        for (GameInfo gie : gi) {
            game = new GTest();
            game.setGameInfo(gie);
            gamesTest.addGame(game);
        }
        return gamesTest;
    }

    /**
     * This is private helper class for testing purposes.
     */
    private class GTest implements Game {

        private GameInfo gi;
        public boolean initCalled;
        public boolean runGameCalled;
        public boolean cleanUpGameCalled;
        public boolean initIsSuccesful;

        public GTest() {
            initCalled = false;
            runGameCalled = false;
            cleanUpGameCalled = false;
            initIsSuccesful = true;
        }

        public void setGameInfo(GameInfo gif) {
            gi = gif;
        }

        public GameInfo getGameInfo() {
            return gi;
        }

        @Override
        public GameInfo retrieveGameInfo() {
            return gi;
        }

        @Override
        public boolean initGame(GameParameters gameParams) {
            initCalled = true;
            return initIsSuccesful;
        }

        @Override
        public void runGame() {
            runGameCalled = true;
        }

        @Override
        public void cleanUpGame() {
            cleanUpGameCalled = true;
        }
    }
}

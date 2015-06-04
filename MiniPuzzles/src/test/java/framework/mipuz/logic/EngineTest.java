package framework.mipuz.logic;

import framework.mipuz.game.Game;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Engine class tests.
 */
public class EngineTest {

    private Engine engine;
    private List<GTest> gtestObjects;

    @Before
    public void setUp() {
        gtestObjects = new ArrayList<>();
    }

    /**
     * Test that new class instantiation creates class variables.
     */
    @Test
    public void classInstantiationTest() {
        engine = new Engine();

        Field gamesField = obtainEngineClassPrivateField("games");
        if (gamesField != null) {
            try {
                gamesField.setAccessible(true);
                assertNotNull(gamesField.get(engine));
                assertEquals(1, engine.numberOfGames());
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument: " + e.toString());
            } catch (SecurityException | IllegalAccessException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Test obtaining number of entries with few items works.
     */
    @Test
    public void numberOfGamesFewItemsTest() {
        engine = new Engine();
        Games gamesTest = addGamesToList(new GameInfo("Test 1", "Test 1", null),
                new GameInfo("Test 2", "Test 2", null));

        Field gamesField = obtainEngineClassPrivateField("games");
        if (gamesField != null) {
            try {
                gamesField.setAccessible(true);
                gamesField.set(engine, gamesTest);

                assertNotNull(gamesField.get(engine));
                assertEquals(2, engine.numberOfGames());
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument: " + e.toString());
            } catch (SecurityException | IllegalAccessException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Test obtaining number of entries without items.
     */
    @Test
    public void numberOfGamesWOEntriesTest() {
        engine = new Engine();
        Games gamesTest = new Games();

        Field gamesField = obtainEngineClassPrivateField("games");
        if (gamesField != null) {
            try {
                gamesField.setAccessible(true);
                gamesField.set(engine, gamesTest);

                assertNotNull(gamesField.get(engine));
                assertEquals(0, engine.numberOfGames());
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument: " + e.toString());
            } catch (SecurityException | IllegalAccessException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Test game field being null.
     */
    @Test
    public void gameFieldIsNullTest() {
        engine = new Engine();
        Games gamesTest = null;

        Field gamesField = obtainEngineClassPrivateField("games");
        if (gamesField != null) {
            try {
                gamesField.setAccessible(true);
                gamesField.set(engine, gamesTest);

                assertNull(gamesField.get(engine));
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument: " + e.toString());
            } catch (SecurityException | IllegalAccessException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Test iterator of engine class.
     */
    @Test
    public void listGamesTest() {
        engine = new Engine();
        Games gamesTest = addGamesToList(new GameInfo("Test 1", "Test 1", null),
                new GameInfo("Test 2", "Test 2", null));

        Field gamesField = obtainEngineClassPrivateField("games");
        if (gamesField != null) {
            try {
                gamesField.setAccessible(true);
                gamesField.set(engine, gamesTest);

                checkIteratorResult(new GameInfo("Test 1", "Test 1", null),
                        new GameInfo("Test 2", "Test 2", null));

            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument: " + e.toString());
            } catch (SecurityException | IllegalAccessException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Test that playGame method works as expected.
     */
    @Test
    public void playGameTest() {
        engine = new Engine();

        Games gamesTest = addGamesToList(new GameInfo("Test 1", "Test 1", null));
        Field gamesField = obtainEngineClassPrivateField("games");
        if (gamesField != null) {
            try {
                gamesField.setAccessible(true);
                gamesField.set(engine, gamesTest);
                assertNotNull(gamesField.get(engine));
                assertEquals(1, engine.numberOfGames());
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument: " + e.toString());
            } catch (SecurityException | IllegalAccessException e) {
                System.out.println(e.toString());
            }
        }

        Iterator itr = engine.listGameInfos();
        assertNotNull(itr);
        GameInfo gi = (GameInfo) itr.next();
        assertNotNull(gi);
        engine.playGame(gi, null);

        GTest gtestGame = gtestObjects.get(0);
        assertTrue(gtestGame.initCalled);
        assertTrue(gtestGame.runGameCalled);
        assertTrue(gtestGame.cleanUpGameCalled);
    }

    /**
     * Helper method to obtaining access to private field in Engine class for
     * testing purposes.
     *
     * @param fieldName Name of field for which access is wanted
     * @return Reference to Engine class private field, if successful, or null,
     * if unsuccessful
     */
    private Field obtainEngineClassPrivateField(String fieldName) {
        Class engineClass = Engine.class;
        Field field = null;

        try {
            field = engineClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println("Missing field: " + e.toString());
        } catch (SecurityException e) {
            System.out.println(e.toString());
        }
        return field;
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
            GTest game = new GTest();
            game.setGameInfo(gie);
            gamesTest.addGame(game);

            gtestObjects.add(game);
        }
        return gamesTest;
    }

    /**
     * Helper method for checking iterator operation in tests.
     *
     * @param gameInfo GameInfo objects expected to be retrieved via iterator
     */
    private void checkIteratorResult(GameInfo... gameInfo) {
        int found = 0;
        Iterator itr = engine.listGameInfos();

        while (itr.hasNext()) {
            GameInfo gi = (GameInfo) itr.next();

            for (GameInfo gie : gameInfo) {
                if (gi.getShortName().equals(gie.getShortName()) == true) {
                    found++;
                }
            }
        }

        assertEquals(gameInfo.length, found);
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

package framework.mipuz.logic;

import framework.mipuz.game.GameInfo;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Games class tests.
 */
public class GamesTest {

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
     * Test adding game info object to list.
     */
    @Test
    public void addGameTest() {
        games = new Games();

        games.addGame(new GameInfo("Test 1", "Test 1", null));

        List<GameInfo> lgi = games.listGames();

        checkListGamesResult(1);
    }

    /**
     * Test adding game info object afterwards to list works.
     */
    @Test
    public void addGameTestAfterwards() {
        games = addGamesToList(new GameInfo("Test 1", "Test 1", null));

        List<GameInfo> lgi = games.listGames();
        checkListGamesResult(1);

        games.addGame(new GameInfo("Test 2", "Test 2", null));
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
        for (GameInfo gi : games.listGames()) {
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
            gamesTest.addGame(gie);
        }
        return gamesTest;
    }
}

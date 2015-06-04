package bbgame;

import bbgame.logic.BBEngine;
import bbgame.ui.BBStartUI;
import framework.mipuz.game.GameEnd;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import java.lang.reflect.Field;
import javax.swing.JPanel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for BBGame class.
 */
public class BBGameTest {

    private BBGame bbGame;

    public BBGameTest() {
    }

    /**
     * Test that new class instantiation creates class variables.
     */
    @Test
    public void classInstantiationTest() {
        bbGame = new BBGame();

        Field giField = obtainBBGameClassPrivateField("gi");
        Field giconField = obtainBBGameClassPrivateField("gicon");

        try {
            giField.setAccessible(true);
            giconField.setAccessible(true);
            assertNotNull(giField.get(bbGame));
            assertNotNull(giconField.get(bbGame));
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test retrieving of GameInfo.
     */
    @Test
    public void retrieveGameInfoTest() {
        bbGame = new BBGame();

        GameInfo gi = bbGame.retrieveGameInfo();
        assertNotNull(gi);

        Field giField = obtainBBGameClassPrivateField("gi");
        try {
            giField.setAccessible(true);
            GameInfo privateGI = (GameInfo) giField.get(bbGame);
            assertEquals(privateGI.getShortName(), gi.getShortName());
            assertEquals(privateGI.getDescription(), gi.getDescription());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test that game initialisation works.
     */
    @Test
    public void initGameTest() {
        bbGame = new BBGame();

        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(null);
        Boolean status = bbGame.initGame(gparams);

        assertTrue(status);

        Field engineField = obtainBBGameClassPrivateField("engine");
        Field bbStartUIField = obtainBBGameClassPrivateField("bbStartUI");
        Field gameParamsField = obtainBBGameClassPrivateField("gameParams");
        try {
            engineField.setAccessible(true);
            bbStartUIField.setAccessible(true);
            gameParamsField.setAccessible(true);
            assertNotNull(engineField.get(bbGame));
            assertNotNull(bbStartUIField.get(bbGame));
            assertNotNull(gameParamsField.get(bbGame));
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test that running game works.
     */
    @Test
    public void runGameTest() {
        bbGame = new BBGame();

        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(null);

        assertTrue(bbGame.initGame(gparams));

        Field bbStartUIField = obtainBBGameClassPrivateField("bbStartUI");
        JPanel testPanel = new JPanel();
        BBStartUI gtest = new BBStartUI(testPanel, null);

        try {
            bbStartUIField.setAccessible(true);
            bbStartUIField.set(bbGame, gtest);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        bbGame.runGame();

        assertEquals(1, testPanel.getComponentCount());
    }

    /**
     * Preliminary test for game clean up.
     */
    @Test
    public void cleanUpGameTest() {
        bbGame = new BBGame();

        bbGame.cleanUpGame();
    }

    /**
     * Test event handler return to menu.
     */
    @Test
    public void bbStateReceivedReturnToMenuTest() {
        bbGame = new BBGame();

        GTest test = new GTest();
        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(test);

        assertTrue(bbGame.initGame(gparams));

        Field bbStartUIField = obtainBBGameClassPrivateField("bbStartUI");
        JPanel testPanel = new JPanel();
        BBStartUI gtest = new BBStartUI(testPanel, null);

        try {
            bbStartUIField.setAccessible(true);
            bbStartUIField.set(bbGame, gtest);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        bbGame.runGame();

        BBStateEvent bbevent = new BBStateEvent(this, BBStates.State.RETURN_TO_MENU);
        bbGame.bbStateReceived(bbevent);
        assertEquals(0, testPanel.getComponentCount());
        assertTrue(test.finishedCalled);
    }

    /**
     * Test event handler run.
     */
    @Test
    public void bbStateReceivedRunTest() {
        bbGame = new BBGame();

        GTest test = new GTest();
        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(test);

        assertTrue(bbGame.initGame(gparams));

        Field bbStartUIField = obtainBBGameClassPrivateField("bbStartUI");
        JPanel testPanel = new JPanel();
        BBStartUI gtest = new BBStartUI(testPanel, null);

        try {
            bbStartUIField.setAccessible(true);
            bbStartUIField.set(bbGame, gtest);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        bbGame.runGame();

        BBStateEvent bbevent = new BBStateEvent(this, BBStates.State.RUN);
        bbGame.bbStateReceived(bbevent);
        assertEquals(0, testPanel.getComponentCount());
        assertTrue(test.finishedCalled);

        Field engineField = obtainBBGameClassPrivateField("engine");
        try {
            engineField.setAccessible(true);
            BBEngine bbEngine = (BBEngine) engineField.get(bbGame);
            assertEquals(BBEngine.GameLevel.EASY, bbEngine.getGameLevel());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Helper method to obtaining access to private field in BBGame class for
     * testing purposes.
     *
     * @param fieldName Name of field for which access is wanted
     * @return Reference to BGame class private field, if successful, or null,
     * if unsuccessful
     */
    private Field obtainBBGameClassPrivateField(String fieldName) {
        Class bbGameClass = BBGame.class;
        Field field = null;

        try {
            field = bbGameClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println("Missing field: " + e.toString());
        } catch (SecurityException e) {
            System.out.println(e.toString());
        }
        return field;
    }

    private class GTest implements GameEnd {

        public boolean finishedCalled = false;

        @Override
        public void finished() {
            finishedCalled = true;
        }
    }
}

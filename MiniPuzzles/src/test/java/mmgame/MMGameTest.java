package mmgame;

import mmgame.event.MMStateEvent;
import mmgame.event.MMStates;
import mmgame.logic.MMEngine;
import framework.mipuz.game.GameEnd;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import java.lang.reflect.Field;
import javax.swing.JPanel;
import mmgame.ui.MMStartUI;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for MMGame class.
 */
public class MMGameTest {

    private MMGame mmGame;

    public MMGameTest() {
    }

    /**
     * Test that new class instantiation creates class variables.
     */
    @Test
    public void classInstantiationTest() {
        mmGame = new MMGame();

        Field giField = obtainMMGameClassPrivateField("gi");
        Field giconField = obtainMMGameClassPrivateField("gicon");

        try {
            giField.setAccessible(true);
            giconField.setAccessible(true);
            assertNotNull(giField.get(mmGame));
            assertNotNull(giconField.get(mmGame));
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
        mmGame = new MMGame();

        GameInfo gi = mmGame.retrieveGameInfo();
        assertNotNull(gi);

        Field giField = obtainMMGameClassPrivateField("gi");
        try {
            giField.setAccessible(true);
            GameInfo privateGI = (GameInfo) giField.get(mmGame);
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
        mmGame = new MMGame();

        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(null);
        Boolean status = mmGame.initGame(gparams);

        assertTrue(status);

        Field engineField = obtainMMGameClassPrivateField("engine");
        Field mmStartUIField = obtainMMGameClassPrivateField("mmStartUI");
        Field gameParamsField = obtainMMGameClassPrivateField("gameParams");
        try {
            engineField.setAccessible(true);
            mmStartUIField.setAccessible(true);
            gameParamsField.setAccessible(true);
            assertNotNull(engineField.get(mmGame));
            assertNotNull(mmStartUIField.get(mmGame));
            assertNotNull(gameParamsField.get(mmGame));
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
        mmGame = new MMGame();

        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(null);

        assertTrue(mmGame.initGame(gparams));

        Field mmStartUIField = obtainMMGameClassPrivateField("mmStartUI");
        JPanel testPanel = new JPanel();
        MMStartUI gtest = new MMStartUI(testPanel, null);

        try {
            mmStartUIField.setAccessible(true);
            mmStartUIField.set(mmGame, gtest);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        mmGame.runGame();

        assertEquals(3, testPanel.getComponentCount());
    }

    /**
     * Preliminary test for game clean up.
     */
    @Test
    public void cleanUpGameTest() {
        mmGame = new MMGame();

        mmGame.cleanUpGame();
    }

    /**
     * Test event handler return to menu.
     */
    @Test
    public void mmStateReceivedReturnToMenuTest() {
        mmGame = new MMGame();

        GTest test = new GTest();
        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(test);

        assertTrue(mmGame.initGame(gparams));

        Field mmStartUIField = obtainMMGameClassPrivateField("mmStartUI");
        JPanel testPanel = new JPanel();
        MMStartUI gtest = new MMStartUI(testPanel, null);

        try {
            mmStartUIField.setAccessible(true);
            mmStartUIField.set(mmGame, gtest);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        mmGame.runGame();

        MMStateEvent mmevent = new MMStateEvent(this, MMStates.State.RETURN_TO_MENU);
        mmGame.mmStateReceived(mmevent);
        assertEquals(0, testPanel.getComponentCount());
        assertTrue(test.finishedCalled);
    }

    /**
     * Test event handler run.
     */
    @Test
    public void mmStateReceivedRunTest() {
        mmGame = new MMGame();

        GTest test = new GTest();
        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(test);

        assertTrue(mmGame.initGame(gparams));

        Field mmStartUIField = obtainMMGameClassPrivateField("mmStartUI");
        JPanel testPanel = new JPanel();
        MMStartUI gtest = new MMStartUI(testPanel, null);

        try {
            mmStartUIField.setAccessible(true);
            mmStartUIField.set(mmGame, gtest);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        mmGame.runGame();

        MMStateEvent mmevent = new MMStateEvent(this, MMStates.State.RUN);
        mmGame.mmStateReceived(mmevent);
        assertEquals(0, testPanel.getComponentCount());

        Field engineField = obtainMMGameClassPrivateField("engine");
        try {
            engineField.setAccessible(true);
            MMEngine mmEngine = (MMEngine) engineField.get(mmGame);
            assertEquals(MMEngine.GameLevel.EASY, mmEngine.getGameLevel());
            assertEquals(1, MMEngine.GameLevel.EASY.getLevelValue());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test event handler game over.
     */
    @Test
    public void mmStateReceivedGameOverTest() {
        mmGame = new MMGame();

        GTest test = new GTest();
        GameParameters gparams = new GameParameters();
        gparams.setGameDisplay(new JPanel());
        gparams.setGameEnd(test);

        assertTrue(mmGame.initGame(gparams));

        Field mmStartUIField = obtainMMGameClassPrivateField("mmStartUI");
        JPanel testPanel = new JPanel();
        MMStartUI gtest = new MMStartUI(testPanel, null);

        try {
            mmStartUIField.setAccessible(true);
            mmStartUIField.set(mmGame, gtest);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        mmGame.runGame();

        MMStateEvent mmevent = new MMStateEvent(this, MMStates.State.RUN);
        mmGame.mmStateReceived(mmevent);

        mmevent = new MMStateEvent(this, MMStates.State.GAME_OVER);
        mmGame.mmStateReceived(mmevent);
        assertEquals(3, testPanel.getComponentCount());
    }

    /**
     * Helper method to obtaining access to private field in MMGame class for
     * testing purposes.
     *
     * @param fieldName Name of field for which access is wanted
     * @return Reference to BGame class private field, if successful, or null,
     * if unsuccessful
     */
    private Field obtainMMGameClassPrivateField(String fieldName) {
        Class mmGameClass = MMGame.class;
        Field field = null;

        try {
            field = mmGameClass.getDeclaredField(fieldName);
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

package bbgame;

import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

public class BBEngineTest {

    private BBEngine bbengine;

    public BBEngineTest() {
    }

    /**
     * Test that new class instantiation creates class variables.
     */
    @Test
    public void classInstantiationTest() {
        bbengine = new BBEngine();

        Field randomizerField = obtainBBEngineClassPrivateField("randomizer");
        try {
            randomizerField.setAccessible(true);
            assertNotNull(randomizerField.get(bbengine));
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        Field gameLevelField = obtainBBEngineClassPrivateField("gameLevel");
        try {
            gameLevelField.setAccessible(true);
            assertNotNull(gameLevelField.get(bbengine));
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test that game level is set properly during construction and getGameLevel
     * method works.
     */
    @Test
    public void getGameLevelTest() {
        bbengine = new BBEngine();

        assertEquals(BBEngine.GameLevel.EASY, bbengine.getGameLevel());
    }

    /**
     * Test that setGameLevel method works.
     */
    @Test
    public void setGameLevelTest() {
        bbengine = new BBEngine();

        bbengine.setGameLevel(BBEngine.GameLevel.MEDIUM);
        assertEquals(BBEngine.GameLevel.MEDIUM, bbengine.getGameLevel());
    }

    /**
     * Test that obtainNewGuess method returns some proper value on easy level.
     */
    @Test
    public void obtainNewGuessOnEasyLevelTest() {
        bbengine = new BBEngine();

        long guessValue = bbengine.obtainNewGuess();

        assertTrue(0 <= guessValue && guessValue <= BBEngine.GameLevel.EASY.getLimit());
    }

    /**
     * Test that obtainNewGuess method returns some proper value on medium
     * level.
     */
    @Test
    public void obtainNewGuessOnMediumLevelTest() {
        bbengine = new BBEngine();

        bbengine.setGameLevel(BBEngine.GameLevel.MEDIUM);
        long guessValue = bbengine.obtainNewGuess();

        assertTrue(0 <= guessValue && guessValue <= BBEngine.GameLevel.MEDIUM.getLimit());
    }

    /**
     * Test that obtainNewGuess method returns some proper value on hard level.
     */
    @Test
    public void obtainNewGuessOnHardLevelTest() {
        bbengine = new BBEngine();

        bbengine.setGameLevel(BBEngine.GameLevel.HARD);
        long guessValue = bbengine.obtainNewGuess();

        assertTrue(0 <= guessValue && guessValue <= BBEngine.GameLevel.HARD.getLimit());
    }

    /**
     * Test that obtainLastGuess method return previously returned value of
     * obtainNewGuess method.
     */
    @Test
    public void obtainLastGuessTest() {
        bbengine = new BBEngine();

        long guessValue = bbengine.obtainNewGuess();

        assertEquals(guessValue, bbengine.obtainLastGuess());
    }

    /**
     * Test that checkGuess method works with correct input but incorrect value.
     */
    @Test
    public void checkGuessWithIncorrectValueTest() {
        bbengine = new BBEngine();

        long guessValue = bbengine.obtainNewGuess();

        assertEquals(BBEngine.CheckResult.INCORRECT,
                bbengine.checkGuess(Long.toBinaryString(guessValue + 1)));
    }

    /**
     * Test that checkGuess method works with correct input and correct value.
     */
    @Test
    public void checkGuessWithCorrectValueTest() {
        bbengine = new BBEngine();

        long guessValue = bbengine.obtainNewGuess();

        assertEquals(BBEngine.CheckResult.CORRECT,
                bbengine.checkGuess(Long.toBinaryString(guessValue)));
    }

    /**
     * Test that checkGuess method handles incorrect input properly.
     */
    @Test
    public void checkGuessWithIncorrectInputTest() {
        bbengine = new BBEngine();

        bbengine.obtainNewGuess();

        assertEquals(BBEngine.CheckResult.INVALID_INPUT,
                bbengine.checkGuess("TEST"));
    }

    /**
     * Test that checkGuess method handles uninitialised state correctly.
     */
    @Test
    public void checkGuessWhenUninitialisedTest() {
        bbengine = new BBEngine();

        assertEquals(BBEngine.CheckResult.INVALID_INPUT,
                bbengine.checkGuess("1"));
    }

    /**
     * Helper method to obtaining access to private field in BBEngine class for
     * testing purposes.
     *
     * @param fieldName Name of field for which access is wanted
     * @return Reference to BBEngine class private field, if successful, or
     * null, if unsuccessful
     */
    private Field obtainBBEngineClassPrivateField(String fieldName) {
        Class bbengineClass = BBEngine.class;
        Field field = null;

        try {
            field = bbengineClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println("Missing field: " + e.toString());
        } catch (SecurityException e) {
            System.out.println(e.toString());
        }

        return field;
    }

}

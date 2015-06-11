package mmgame.logic;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author esaharju
 */
public class MMEngineTest {

    private MMEngine mmengine;

    /**
     * Test that new class instantiation creates class variables.
     */
    @Test
    public void classInstantiationTest() {
        mmengine = new MMEngine();

        Field randomizerField = obtainMMEngineClassPrivateField("randomizer");
        testFieldForNotNull(randomizerField);

        Field gameLevelField = obtainMMEngineClassPrivateField("gameLevel");
        testFieldForNotNull(gameLevelField);

        Field gameNumbersField = obtainMMEngineClassPrivateField("gameNumbers");
        testFieldForNotNull(gameNumbersField);
    }

    /**
     * Test that game level is set properly during construction and getGameLevel
     * method works.
     */
    @Test
    public void getGameLevelTest() {
        mmengine = new MMEngine();

        assertEquals(MMEngine.GameLevel.EASY, mmengine.getGameLevel());
    }

    /**
     * Test that setGameLevel method works.
     */
    @Test
    public void setGameLevelTest() {
        mmengine = new MMEngine();

        mmengine.setGameLevel(MMEngine.GameLevel.MEDIUM);
        assertEquals(MMEngine.GameLevel.MEDIUM, mmengine.getGameLevel());
    }

    /**
     * Test that starting new game populates newly randomised numbers properly.
     */
    @Test
    public void startNewGameTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        Field gameNumbersField = obtainMMEngineClassPrivateField("gameNumbers");
        testFieldForNotNull(gameNumbersField);
        try {
            gameNumbersField.setAccessible(true);
            List<Integer> testNumbers = (List<Integer>) gameNumbersField.get(mmengine);
            assertEquals(MMEngine.GameLevel.EASY.getCount(), testNumbers.size());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test that starting new game empties earlier numbers if present.
     */
    @Test
    public void startNewGameAgainTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        mmengine.setGameLevel(MMEngine.GameLevel.MEDIUM);
        mmengine.startNewGame();

        Field gameNumbersField = obtainMMEngineClassPrivateField("gameNumbers");
        testFieldForNotNull(gameNumbersField);
        try {
            gameNumbersField.setAccessible(true);
            List<Integer> testNumbers = (List<Integer>) gameNumbersField.get(mmengine);
            assertEquals(MMEngine.GameLevel.MEDIUM.getCount(), testNumbers.size());
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Test that obtaining randomised numbers works.
     */
    @Test
    public void obtainNumbersTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        assertEquals(MMEngine.GameLevel.EASY.getCount(),
                mmengine.obtainNumbers(MMEngine.GameLevel.EASY.getCount()).size());
    }

    /**
     * Test that obtaining randomised numbers works even if requested more than
     * were originally provided.
     */
    @Test
    public void obtainNumbersTooManyTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        assertEquals(MMEngine.GameLevel.EASY.getCount(),
                mmengine.obtainNumbers(MMEngine.GameLevel.MEDIUM.getCount()).size());
    }

    /**
     * Test that obtaining randomised numbers works even if requested less than
     * were originally provided.
     */
    @Test
    public void obtainNumbersLessThanTest() {
        mmengine = new MMEngine();

        mmengine.setGameLevel(MMEngine.GameLevel.HARD);
        mmengine.startNewGame();

        assertEquals(MMEngine.GameLevel.HARD.getCount() - 1,
                mmengine.obtainNumbers(MMEngine.GameLevel.HARD.getCount() - 1).size());
    }

    /**
     * Test that obtaining randomised numbers works even if requested more than
     * were originally provided.
     */
    @Test
    public void obtainNumbersMoreThanTest() {
        mmengine = new MMEngine();

        mmengine.setGameLevel(MMEngine.GameLevel.HARD);
        mmengine.startNewGame();

        assertEquals(MMEngine.GameLevel.HARD.getCount(),
                mmengine.obtainNumbers(MMEngine.GameLevel.HARD.getCount() + 1).size());
    }

    /**
     * Test that obtaining randomised numbers works for hard level.
     */
    @Test
    public void obtainNumbersForHardTest() {
        mmengine = new MMEngine();

        mmengine.setGameLevel(MMEngine.GameLevel.MEDIUM);
        mmengine.startNewGame();

        assertEquals(MMEngine.GameLevel.MEDIUM.getCount(),
                mmengine.obtainNumbers(MMEngine.GameLevel.HARD.getCount()).size());
    }

    /**
     * Test that validation works for all numbers.
     */
    @Test
    public void checkGuessAllNumbersTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        MMEngine.CheckResult checkResult = mmengine.checkGuess(
                mmengine.obtainNumbers(MMEngine.GameLevel.EASY.getCount()),
                MMEngine.GameLevel.EASY.getCount());

        assertEquals(MMEngine.CheckResult.CORRECT, checkResult);

        checkResult = mmengine.checkGuess(
                mmengine.obtainNumbers(MMEngine.GameLevel.EASY.getCount()),
                0);

        assertEquals(MMEngine.CheckResult.CORRECT, checkResult);
    }

    /**
     * Test that validation fails when incorrect amount is provided vs
     * requested.
     */
    @Test
    public void checkGuessLessThanAllNumbersTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        List<Integer> numbers = mmengine.obtainNumbers(MMEngine.GameLevel.EASY.getCount() - 1);
        MMEngine.CheckResult checkResult = mmengine.checkGuess(
                numbers, 0);

        assertEquals(MMEngine.CheckResult.INCORRECT, checkResult);
    }

    /**
     * Test that validation succeeds when partial check is performed.
     */
    @Test
    public void checkGuessPartialTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        List<Integer> numbers = mmengine.obtainNumbers(MMEngine.GameLevel.EASY.getCount() - 2);
        MMEngine.CheckResult checkResult = mmengine.checkGuess(
                numbers, numbers.size());

        assertEquals(MMEngine.CheckResult.CORRECT, checkResult);
    }

    /**
     * Test that validation works for incorrect guess.
     */
    @Test
    public void checkIncorrectGuessTest() {
        mmengine = new MMEngine();

        mmengine.startNewGame();

        List<Integer> numbers = mmengine.obtainNumbers(MMEngine.GameLevel.EASY.getCount());
        numbers.set(1, Integer.SIZE);

        MMEngine.CheckResult checkResult = mmengine.checkGuess(
                numbers, 0);

        assertEquals(MMEngine.CheckResult.INCORRECT, checkResult);
    }

    /**
     * Helper method to obtaining access to private field in MMEngine class for
     * testing purposes.
     *
     * @param fieldName Name of field for which access is wanted
     * @return Reference to MMEngine class private field, if successful, or
     * null, if unsuccessful
     */
    private Field obtainMMEngineClassPrivateField(String fieldName) {
        Class mmengineClass = MMEngine.class;
        Field field = null;

        try {
            field = mmengineClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println("Missing field: " + e.toString());
        } catch (SecurityException e) {
            System.out.println(e.toString());
        }

        return field;
    }

    /**
     * Helper method for testing if given private field is non-null on test
     * created MMEngine instance.
     *
     * @param field reference to field being tested
     */
    private void testFieldForNotNull(Field field) {
        try {
            field.setAccessible(true);
            assertNotNull(field.get(mmengine));
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.toString());
        } catch (SecurityException | IllegalAccessException e) {
            System.out.println(e.toString());
        }
    }
}

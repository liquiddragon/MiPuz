package mmgame.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class contains MMGame logic.
 *
 */
public class MMEngine {

    private final Random randomizer;
    private final List<Integer> gameNumbers;
    private GameLevel gameLevel;

    /**
     * Game levels used for this game.
     */
    public enum GameLevel {

        EASY(6, 1), MEDIUM(8, 2), HARD(10, 3);

        private final int itemCount;
        private final int numericalValue;

        private GameLevel(int count, int value) {
            itemCount = count;
            numericalValue = value;
        }

        public int getCount() {
            return itemCount;
        }

        public int getLevelValue() {
            return numericalValue;
        }
    }

    /**
     * Results of check performed on provided guess.
     */
    public enum CheckResult {

        CORRECT, INCORRECT, NOT_INITIALIZED
    }

    /**
     * This is MMEngine default constructor.
     */
    public MMEngine() {
        randomizer = new Random();
        gameLevel = GameLevel.EASY;
        gameNumbers = new ArrayList<>();
    }

    /**
     * This method provides current game level.
     *
     * @return GameLevel value
     */
    public GameLevel getGameLevel() {
        return gameLevel;
    }

    /**
     * This method sets game level.
     *
     * @param gameLevel GameLevel to be taken into use
     */
    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * This method initialises new game.
     */
    public void startNewGame() {
        if (!gameNumbers.isEmpty()) {
            gameNumbers.clear();
        }

        fillNumbers();
    }

    /**
     * This method returns requested amount of randomised numbers.
     *
     * @param count how many numbers are requested
     * @return List of numbers requested
     */
    public List<Integer> obtainNumbers(int count) {
        List<Integer> numbers = new ArrayList<>();

        if (count > getNumberCount()) {
            count = gameNumbers.size();
        }

        for (int i = 0; i < count; i++) {
            numbers.add(gameNumbers.get(i));
        }

        return numbers;
    }

    /**
     * This method checks guess correctness. If partial is above zero then only
     * given entries are checked. If partial is zero then all given entries are
     * checked.
     *
     * @param numbers to be checked
     * @param partial check given number of numbers or all, if zero
     * @return result of validation
     */
    public CheckResult checkGuess(List<Integer> numbers, int partial) {
        CheckResult result = CheckResult.NOT_INITIALIZED;

        if (!gameNumbers.isEmpty()) {
            if (partial == 0 && numbers.size() != gameNumbers.size()) {
                result = CheckResult.INCORRECT;
            } else {
                result = CheckResult.CORRECT;

                if (partial == 0) {
                    partial = gameNumbers.size();
                }

                for (int i = 0; i < partial; i++) {
                    if (!numbers.get(i).equals(gameNumbers.get(i))) {
                        result = CheckResult.INCORRECT;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * This method creates an array containing GameLevel required amount of
     * numbers in random order.
     */
    private void fillNumbers() {
        List<Integer> chooseFrom = new ArrayList<>();
        int numbersNeeded = getNumberCount();

        for (int i = 0; i < numbersNeeded; i++) {
            chooseFrom.add(i);
        }

        while (!chooseFrom.isEmpty()) {
            int index = randomizer.nextInt(chooseFrom.size());
            gameNumbers.add(chooseFrom.get(index));
            chooseFrom.remove(index);
        }
    }

    /**
     * This method returns how many numbers are required per game level.
     *
     * @return Amount of numbers or zero if GameLevel is not set
     */
    private int getNumberCount() {
        int count = 0;

        switch (gameLevel) {
            case EASY:
                count = GameLevel.EASY.getCount();
                break;

            case MEDIUM:
                count = GameLevel.MEDIUM.getCount();
                break;

            case HARD:
                count = GameLevel.HARD.getCount();
                break;
        }

        return count;
    }
}

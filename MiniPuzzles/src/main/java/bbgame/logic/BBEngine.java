package bbgame.logic;

import java.util.Random;

/**
 * This class contains BBGame logic.
 *
 */
public class BBEngine {

    private final long VALUE_NOT_INITIALIZED = -1;
    private GameLevel gameLevel;
    private final Random randomizer;
    private long valueToBeGuessed;

    /**
     * Game levels used for this game and related value limits.
     */
    public enum GameLevel {

        EASY(256), MEDIUM(65536), HARD(4294967296L);
        private final long upperLimit;

        private GameLevel(long limit) {
            upperLimit = limit;
        }

        public long getLimit() {
            return upperLimit;
        }
    };

    /**
     * Results of check performed on provided guess.
     */
    public enum CheckResult {

        CORRECT, INCORRECT, INVALID_INPUT
    }

    /**
     * This is BBEngine default constructor.
     */
    public BBEngine() {
        randomizer = new Random();
        gameLevel = GameLevel.EASY;
        valueToBeGuessed = VALUE_NOT_INITIALIZED;
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
     * This method provides new value for guessing.
     *
     * @return value to be guessed
     */
    public long obtainNewGuess() {
        setUpGuess();
        return valueToBeGuessed;
    }

    /**
     * This method provides previously generated value.
     *
     * @return previously generated guess value
     */
    public long obtainLastGuess() {
        return valueToBeGuessed;
    }

    /**
     * This method validates guess against generated value. If obtainNewGuess
     * method has not been called this method returns INVALID_INPUT as a result.
     *
     * @param guess to be validated
     * @return result of validation
     */
    public CheckResult checkGuess(String guess) {
        CheckResult result;

        if (valueToBeGuessed == VALUE_NOT_INITIALIZED) {
            result = CheckResult.INVALID_INPUT;
        } else {
            try {
                long guessNr;
                guessNr = Integer.parseInt(guess, 2);

                if (guessNr == valueToBeGuessed) {
                    result = CheckResult.CORRECT;
                } else {
                    result = CheckResult.INCORRECT;
                }
            } catch (NumberFormatException e) {
                result = CheckResult.INVALID_INPUT;
            }
        }
        return result;
    }

    /**
     * This method generates random value based on game level.
     */
    private void setUpGuess() {
        long scaler = 0;
        valueToBeGuessed = Math.abs(randomizer.nextLong());

        switch (gameLevel) {
            case EASY:
                scaler = BBEngine.GameLevel.EASY.getLimit();
                break;

            case MEDIUM:
                scaler = BBEngine.GameLevel.MEDIUM.getLimit();
                break;

            case HARD:
                scaler = BBEngine.GameLevel.HARD.getLimit();
                break;
        }
        valueToBeGuessed %= scaler;
    }
}

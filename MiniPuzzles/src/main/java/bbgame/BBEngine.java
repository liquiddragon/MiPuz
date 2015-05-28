package bbgame;

import java.util.Random;

/**
 * This class is BBGame engine containing game logic.
 *
 */
public class BBEngine {

    private final long VALUE_NOT_INITIALIZED = -1;
    private GameLevel gameLevel;
    private final Random randomizer;
    private long valueToBeGuessed;

    /**
     * Game level used for this game and its value limits.
     */
    public enum GameLevel {

        EASY(256), MEDIUM(65536), HARD(2147483648L);
        private final long upperLimit;

        private GameLevel(long limit) {
            upperLimit = limit;
        }

        public long getLimit() {
            return upperLimit;
        }
    };

    /**
     * Result of check performed on provided guess.
     */
    public enum CheckResult {

        CORRECT, INCORRECT, INVALID_INPUT
    }

    public BBEngine() {
        this.randomizer = new Random();
        this.gameLevel = GameLevel.EASY;
        this.valueToBeGuessed = VALUE_NOT_INITIALIZED;
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
        return this.valueToBeGuessed;
    }

    /**
     * This method provides previously obtained value anew.
     *
     * @return previously generated guess value
     */
    public long obtainLastGuess() {
        return this.valueToBeGuessed;
    }

    /**
     * This method validates guess against generated random value. If
     * obtainNewGuess method has not been called this method returns
     * INVALID_INPUT as a result.
     *
     * @param guess to be validated
     * @return result of validation
     */
    public CheckResult checkGuess(String guess) {
        CheckResult result;

        if (this.valueToBeGuessed == VALUE_NOT_INITIALIZED) {
            result = CheckResult.INVALID_INPUT;
        } else {
            try {
                long guessNr;
                guessNr = Integer.parseInt(guess, 2);

                if (guessNr == this.valueToBeGuessed) {
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
        this.valueToBeGuessed = Math.abs(this.randomizer.nextLong());

        switch (this.gameLevel) {
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
        this.valueToBeGuessed %= scaler;
    }
}

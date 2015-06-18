/**
 * BBGame engine for running the game.
 */
package bbgame.logic;

import java.util.Random;

/**
 * BBGame logic.
 *
 */
public class BBEngine {

    private final long valueNotInitialized = -1;
    private final Random randomizer;
    private final int radixBaseTwo = 2;
    private GameLevel gameLevel;
    private long valueToBeGuessed;

    /**
     * Game levels used for game and related value limits.
     */
    public enum GameLevel {

        /**
         * Easy level.
         */
        EASY(256, 8),
        /**
         * Medium level.
         */
        MEDIUM(65536, 16),
        /**
         * Hard level.
         */
        HARD(4294967296L, 32);
        private final long upperLimit;
        private final int nrOfBits;

        private GameLevel(long limit, int bits) {
            upperLimit = limit;
            nrOfBits = bits;
        }

        /**
         * Get maximum value limit corresponding to game level
         *
         * @return value limit
         */
        public long getLimit() {
            return upperLimit;
        }

        /**
         * Get number of bits used corresponding to game level
         *
         * @return number of bits
         */
        public int getBits() {
            return nrOfBits;
        }
    };

    /**
     * Results of check performed on provided guess.
     */
    public enum CheckResult {

        /**
         * Guess was correct.
         */
        CORRECT,
        /**
         * Guess was incorrect.
         */
        INCORRECT,
        /**
         * Game was not initialised or input was incorrect.
         */
        INVALID_INPUT
    }

    /**
     * Construct BBEngine.
     */
    public BBEngine() {
        randomizer = new Random();
        gameLevel = GameLevel.EASY;
        valueToBeGuessed = valueNotInitialized;
    }

    /**
     * Provide current game level.
     *
     * @return GameLevel value
     */
    public GameLevel getGameLevel() {
        return gameLevel;
    }

    /**
     * Set game level.
     *
     * @param gameLevel GameLevel to be taken into use
     */
    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * Provide new value to be guessed by player.
     *
     * @return value to be guessed
     */
    public long obtainNewGuess() {
        setUpGuess();
        return valueToBeGuessed;
    }

    /**
     * Provide previously generated value.
     *
     * @return previously generated guess value
     */
    public long obtainLastGuess() {
        return valueToBeGuessed;
    }

    /**
     * Validates player guess against generated value. If obtainNewGuess method
     * has not been called this method returns INVALID_INPUT as a result.
     *
     * @param guess to be validated
     * @return result of validation
     */
    public CheckResult checkGuess(String guess) {
        CheckResult result = CheckResult.INCORRECT;

        if (valueToBeGuessed == valueNotInitialized) {
            result = CheckResult.INVALID_INPUT;
        } else {
            try {
                long guessNr = Integer.parseInt(guess, radixBaseTwo);

                if (guessNr == valueToBeGuessed) {
                    result = CheckResult.CORRECT;
                }
            } catch (NumberFormatException e) {
                result = CheckResult.INVALID_INPUT;
            }
        }
        return result;
    }

    /**
     * Helper method for generating random value based on game level.
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

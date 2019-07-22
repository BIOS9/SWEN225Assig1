class Turn {
    public static final DICE_COUNT = 2;
    public static final DICE_SIDES = 6;

    private final int diceRoll;
    private Player player;
    private Queue<Cell> moves;
    private Suggestion suggestion;

    /**
     * Checks if the turn is valid
     * @return
     */
    public boolean isValid() {

    }

    /**
     * Uses dice constants to generate a random number.
     * @return integer value representing dice roll
     */
    public int rollDice() {
        // Use dice constants to generate random dice roll
    }
}
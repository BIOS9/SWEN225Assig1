package game;
import java.util.*;

class Turn {
    public static final int DIE_COUNT = 2;
    public static final int DIE_SIDES = 6;

    private final int diceRoll;
    private Player player;
    private Queue<Cell> moves;
    private Suggestion suggestion;

    /**
     * Constructor
     * Rolls dice, configures variables
     * @param player Player who is the owner of this turn
     */
    public Turn(Player player) {
        diceRoll = rollDice();
        this.player = player;
    }

    /**
     * Checks if the turn is valid
     * @return
     */
    public boolean isValid() {
        // Checks if physical move is valid, number of cells moved, is stepping over other player, are cells conencted, etc
        // Checks if player can make suggestion/accusation eg hasAccused
        // Checks if suggestion/accusation is valid
    }

    /**
     * Uses dice constants to generate a random number.
     * @return integer value representing dice roll
     */
    public int rollDice() {
        Random rng = new Random();
        int roll = 0;

        for(int die = 0; die < DIE_COUNT; ++die)
            roll += rng.nextInt(DIE_SIDES) + 1; // Add 1 because rng gives 0-exclusive DIE_SIDES, dice don't have 0

        return roll;
    }
}
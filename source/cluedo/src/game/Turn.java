package game;

import javafx.util.Pair;

import java.util.Random;
/** 
 * 
 * Processes the players turn, checking validity and delegating any suggestions or assumptions
 * to the suggestion class.
 * 
 * Collaborates with Player and Suggestion.
 * @author abbey
 *
 */
class Turn {
	
	//Turn Attributes
    private int diceRoll;
    
    //Turn Associations
    private Player player;

    /**
     * Constructor
     * 
     * Rolls dice, configures variables
     * @param player Player who is the owner of this turn
     */
    public Turn(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    /**
     * Uses dice constants to generate a random number.
     * @return integer value representing dice roll
     */
    public Pair<Integer, Integer> rollDice() {
        Random rng = new Random();

        int roll1 = rng.nextInt(6) + 1; // Add 1 because rng gives 0-exclusive 6, dice don't have 0
        int roll2 = rng.nextInt(6) + 1;

        diceRoll = roll1 + roll2;
        return new Pair<>(roll1, roll2);
    }
}
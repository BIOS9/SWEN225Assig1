package game;
import game.board.Cell;

import java.util.*;
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
    public static final int DIE_COUNT = 2;
    public static final int DIE_SIDES = 6;

    private final int diceRoll;
    
    //Turn Associations
    private Player player;

    /**
     * Constructor
     * 
     * Rolls dice, configures variables
     * @param player Player who is the owner of this turn
     */
    public Turn(Player player) {
        diceRoll = rollDice();
        this.player = player;
        
        //Tell thwqe player how much they rolled
        //ask them where they want to move
        //If they move into a room they can make a suggestion/acusation
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
    public static int rollDice() {
        Random rng = new Random();
        int roll = 0;

        for(int die = 0; die < DIE_COUNT; ++die)
            roll += rng.nextInt(DIE_SIDES) + 1; // Add 1 because rng gives 0-exclusive DIE_SIDES, dice don't have 0

        return roll;
    }
}
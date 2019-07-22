package game;

import java.util.List;

/**
 * Responsible for running the game and managing the active game objects.
 * 
 * Collaborates directly with Players, Cards and Characters.
 * @author abbey
 *
 */
class CluedoGame {
    int playerTurnIndex = 0; // Whos turn it is
    List<Character> characters;

    /**
     * Entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {

    }

    /**
     * Initialises the game state, players and board.
     */
    public void initGame() {
        // Generate players
        // Generate graph
        // add starting positions from graph to player pos's
    }

    /**
     * Generates the cards, selects a solution and deals the cards to players
     */
    public void initCards() {
        // Create three stacks of cards, one for each card type
        // Get random card of each type to make solution
        // Shuffle remaining cars into one stack
        // Deal cards to players
    }
}
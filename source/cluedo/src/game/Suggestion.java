package game;

/**
 * 
 * Responsible for taking a pplayers 3 suggestion or acusation cards and checking that they are 1 of each
 * type (Room, Character, Weapon).  
 * 
 * Collaborates with Turn, Card and Player
 * 
 * @author abbey
 *
 */
public class Suggestion {
	
	//Suggestion Attributes
	private boolean isAcusation = false; 
	
	//Suggestion Associations
    private game.cards.Room room;
    private game.cards.Character character;
    private game.cards.Weapon weapon;
    Player player;
    
    
    public Suggestion (game.cards.Room room, game.cards.Character character, game.cards.Weapon weapon, Player player, boolean isAcusation) {
    	this.room = room;
    	this.character = character;
    	this.weapon = weapon;
    	this.player = player;
    	this.isAcusation = isAcusation;
    }   
    
    public Suggestion (game.cards.Room room, game.cards.Character character, game.cards.Weapon weapon) {
    	this.room = room;
    	this.character = character;
    	this.weapon = weapon;
    }
    
}

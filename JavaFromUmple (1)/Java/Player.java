/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;

// line 82 "model.ump"
// line 116 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private boolean hasAcused;

  //Player Associations
  private List<Card> hand;
  private List<Turn> turn;
  private Game game;
  private Cell cell;
  private Character character;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(boolean aHasAcused, Game aGame, Cell aCell, Character aCharacter)
  {
    hasAcused = aHasAcused;
    hand = new ArrayList<Card>();
    turn = new ArrayList<Turn>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aCell == null || aCell.getOccupied() != null)
    {
      throw new RuntimeException("Unable to create Player due to aCell. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cell = aCell;
    boolean didAddCharacter = setCharacter(aCharacter);
    if (!didAddCharacter)
    {
      throw new RuntimeException("Unable to create player due to character. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Player(boolean aHasAcused, Game aGame, Room aRoomForCell, Cell aNorthForCell, Cell aSouthForCell, Cell aEastForCell, Cell aWestForCell, Turn aTurnForCell, Character aCharacter)
  {
    hasAcused = aHasAcused;
    hand = new ArrayList<Card>();
    turn = new ArrayList<Turn>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cell = new Cell(aRoomForCell, aNorthForCell, aSouthForCell, aEastForCell, aWestForCell, this, aTurnForCell);
    boolean didAddCharacter = setCharacter(aCharacter);
    if (!didAddCharacter)
    {
      throw new RuntimeException("Unable to create player due to character. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHasAcused(boolean aHasAcused)
  {
    boolean wasSet = false;
    hasAcused = aHasAcused;
    wasSet = true;
    return wasSet;
  }

  public boolean getHasAcused()
  {
    return hasAcused;
  }
  /* Code from template association_GetMany */
  public Card getHand(int index)
  {
    Card aHand = hand.get(index);
    return aHand;
  }

  public List<Card> getHand()
  {
    List<Card> newHand = Collections.unmodifiableList(hand);
    return newHand;
  }

  public int numberOfHand()
  {
    int number = hand.size();
    return number;
  }

  public boolean hasHand()
  {
    boolean has = hand.size() > 0;
    return has;
  }

  public int indexOfHand(Card aHand)
  {
    int index = hand.indexOf(aHand);
    return index;
  }
  /* Code from template association_GetMany */
  public Turn getTurn(int index)
  {
    Turn aTurn = turn.get(index);
    return aTurn;
  }

  public List<Turn> getTurn()
  {
    List<Turn> newTurn = Collections.unmodifiableList(turn);
    return newTurn;
  }

  public int numberOfTurn()
  {
    int number = turn.size();
    return number;
  }

  public boolean hasTurn()
  {
    boolean has = turn.size() > 0;
    return has;
  }

  public int indexOfTurn(Turn aTurn)
  {
    int index = turn.indexOf(aTurn);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Cell getCell()
  {
    return cell;
  }
  /* Code from template association_GetOne */
  public Character getCharacter()
  {
    return character;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHand()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addHand(Card aHand)
  {
    boolean wasAdded = false;
    if (hand.contains(aHand)) { return false; }
    Player existingPlayer = aHand.getPlayer();
    if (existingPlayer == null)
    {
      aHand.setPlayer(this);
    }
    else if (!this.equals(existingPlayer))
    {
      existingPlayer.removeHand(aHand);
      addHand(aHand);
    }
    else
    {
      hand.add(aHand);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHand(Card aHand)
  {
    boolean wasRemoved = false;
    if (hand.contains(aHand))
    {
      hand.remove(aHand);
      aHand.setPlayer(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHandAt(Card aHand, int index)
  {  
    boolean wasAdded = false;
    if(addHand(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHandAt(Card aHand, int index)
  {
    boolean wasAdded = false;
    if(hand.contains(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHandAt(aHand, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTurn()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Turn addTurn(final aDiceRoll)
  {
    return new Turn(aDiceRoll, this);
  }

  public boolean addTurn(Turn aTurn)
  {
    boolean wasAdded = false;
    if (turn.contains(aTurn)) { return false; }
    Player existingPlayer = aTurn.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aTurn.setPlayer(this);
    }
    else
    {
      turn.add(aTurn);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTurn(Turn aTurn)
  {
    boolean wasRemoved = false;
    //Unable to remove aTurn, as it must always have a player
    if (!this.equals(aTurn.getPlayer()))
    {
      turn.remove(aTurn);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTurnAt(Turn aTurn, int index)
  {  
    boolean wasAdded = false;
    if(addTurn(aTurn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTurn()) { index = numberOfTurn() - 1; }
      turn.remove(aTurn);
      turn.add(index, aTurn);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTurnAt(Turn aTurn, int index)
  {
    boolean wasAdded = false;
    if(turn.contains(aTurn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTurn()) { index = numberOfTurn() - 1; }
      turn.remove(aTurn);
      turn.add(index, aTurn);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTurnAt(aTurn, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (6)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCharacter(Character aNewCharacter)
  {
    boolean wasSet = false;
    if (aNewCharacter == null)
    {
      //Unable to setCharacter to null, as player must always be associated to a character
      return wasSet;
    }
    
    Player existingPlayer = aNewCharacter.getPlayer();
    if (existingPlayer != null && !equals(existingPlayer))
    {
      //Unable to setCharacter, the current character already has a player, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Character anOldCharacter = character;
    character = aNewCharacter;
    character.setPlayer(this);

    if (anOldCharacter != null)
    {
      anOldCharacter.setPlayer(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while( !hand.isEmpty() )
    {
      hand.get(0).setPlayer(null);
    }
    for(int i=turn.size(); i > 0; i--)
    {
      Turn aTurn = turn.get(i - 1);
      aTurn.delete();
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
    Cell existingCell = cell;
    cell = null;
    if (existingCell != null)
    {
      existingCell.delete();
    }
    Character existingCharacter = character;
    character = null;
    if (existingCharacter != null)
    {
      existingCharacter.setPlayer(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "hasAcused" + ":" + getHasAcused()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cell = "+(getCell()!=null?Integer.toHexString(System.identityHashCode(getCell())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "character = "+(getCharacter()!=null?Integer.toHexString(System.identityHashCode(getCharacter())):"null");
  }
}
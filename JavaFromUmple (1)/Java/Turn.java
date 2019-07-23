/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;

// line 45 "model.ump"
// line 148 "model.ump"
public class Turn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Turn Attributes
  private final diceCount;
  private final diceNum;
  private final diceRoll;

  //Turn Associations
  private List<Cell> cells;
  private suggestion suggestion;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Turn(final aDiceRoll, Player aPlayer)
  {
    diceCount = 2;
    diceNum = 6;
    diceRoll = aDiceRoll;
    cells = new ArrayList<Cell>();
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create turn due to player. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDiceCount(final aDiceCount)
  {
    boolean wasSet = false;
    diceCount = aDiceCount;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiceNum(final aDiceNum)
  {
    boolean wasSet = false;
    diceNum = aDiceNum;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiceRoll(final aDiceRoll)
  {
    boolean wasSet = false;
    diceRoll = aDiceRoll;
    wasSet = true;
    return wasSet;
  }

  public final getDiceCount()
  {
    return diceCount;
  }

  public final getDiceNum()
  {
    return diceNum;
  }

  public final getDiceRoll()
  {
    return diceRoll;
  }
  /* Code from template association_GetMany */
  public Cell getCell(int index)
  {
    Cell aCell = cells.get(index);
    return aCell;
  }

  public List<Cell> getCells()
  {
    List<Cell> newCells = Collections.unmodifiableList(cells);
    return newCells;
  }

  public int numberOfCells()
  {
    int number = cells.size();
    return number;
  }

  public boolean hasCells()
  {
    boolean has = cells.size() > 0;
    return has;
  }

  public int indexOfCell(Cell aCell)
  {
    int index = cells.indexOf(aCell);
    return index;
  }
  /* Code from template association_GetOne */
  public suggestion getSuggestion()
  {
    return suggestion;
  }

  public boolean hasSuggestion()
  {
    boolean has = suggestion != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCells()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Cell addCell(Room aRoom, Cell aNorth, Cell aSouth, Cell aEast, Cell aWest, Player aOccupied)
  {
    return new Cell(aRoom, aNorth, aSouth, aEast, aWest, aOccupied, this);
  }

  public boolean addCell(Cell aCell)
  {
    boolean wasAdded = false;
    if (cells.contains(aCell)) { return false; }
    Turn existingTurn = aCell.getTurn();
    boolean isNewTurn = existingTurn != null && !this.equals(existingTurn);
    if (isNewTurn)
    {
      aCell.setTurn(this);
    }
    else
    {
      cells.add(aCell);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCell(Cell aCell)
  {
    boolean wasRemoved = false;
    //Unable to remove aCell, as it must always have a turn
    if (!this.equals(aCell.getTurn()))
    {
      cells.remove(aCell);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCellAt(Cell aCell, int index)
  {  
    boolean wasAdded = false;
    if(addCell(aCell))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCells()) { index = numberOfCells() - 1; }
      cells.remove(aCell);
      cells.add(index, aCell);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCellAt(Cell aCell, int index)
  {
    boolean wasAdded = false;
    if(cells.contains(aCell))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCells()) { index = numberOfCells() - 1; }
      cells.remove(aCell);
      cells.add(index, aCell);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCellAt(aCell, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setSuggestion(suggestion aNewSuggestion)
  {
    boolean wasSet = false;
    if (suggestion != null && !suggestion.equals(aNewSuggestion) && equals(suggestion.getTurn()))
    {
      //Unable to setSuggestion, as existing suggestion would become an orphan
      return wasSet;
    }

    suggestion = aNewSuggestion;
    Turn anOldTurn = aNewSuggestion != null ? aNewSuggestion.getTurn() : null;

    if (!this.equals(anOldTurn))
    {
      if (anOldTurn != null)
      {
        anOldTurn.suggestion = null;
      }
      if (suggestion != null)
      {
        suggestion.setTurn(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    if (aPlayer == null)
    {
      return wasSet;
    }

    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removeTurn(this);
    }
    player.addTurn(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=cells.size(); i > 0; i--)
    {
      Cell aCell = cells.get(i - 1);
      aCell.delete();
    }
    suggestion existingSuggestion = suggestion;
    suggestion = null;
    if (existingSuggestion != null)
    {
      existingSuggestion.delete();
    }
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removeTurn(this);
    }
  }

  // line 54 "model.ump"
   public int rollDice(){
    
  }

  // line 56 "model.ump"
   public boolean isValid(){
    
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "diceCount" + "=" + (getDiceCount() != null ? !getDiceCount().equals(this)  ? getDiceCount().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "diceNum" + "=" + (getDiceNum() != null ? !getDiceNum().equals(this)  ? getDiceNum().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "diceRoll" + "=" + (getDiceRoll() != null ? !getDiceRoll().equals(this)  ? getDiceRoll().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "suggestion = "+(getSuggestion()!=null?Integer.toHexString(System.identityHashCode(getSuggestion())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}
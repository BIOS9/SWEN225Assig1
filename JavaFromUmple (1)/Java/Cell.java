/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 21 "model.ump"
// line 93 "model.ump"
public class Cell
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cell Attributes
  private Room room;
  private Cell north;
  private Cell south;
  private Cell east;
  private Cell west;

  //Cell Associations
  private Player occupied;
  private Turn turn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cell(Room aRoom, Cell aNorth, Cell aSouth, Cell aEast, Cell aWest, Player aOccupied, Turn aTurn)
  {
    room = aRoom;
    north = aNorth;
    south = aSouth;
    east = aEast;
    west = aWest;
    if (aOccupied == null || aOccupied.getCell() != null)
    {
      throw new RuntimeException("Unable to create Cell due to aOccupied. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    occupied = aOccupied;
    boolean didAddTurn = setTurn(aTurn);
    if (!didAddTurn)
    {
      throw new RuntimeException("Unable to create cell due to turn. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Cell(Room aRoom, Cell aNorth, Cell aSouth, Cell aEast, Cell aWest, boolean aHasAcusedForOccupied, Game aGameForOccupied, Character aCharacterForOccupied, Turn aTurn)
  {
    room = aRoom;
    north = aNorth;
    south = aSouth;
    east = aEast;
    west = aWest;
    occupied = new Player(aHasAcusedForOccupied, aGameForOccupied, this, aCharacterForOccupied);
    boolean didAddTurn = setTurn(aTurn);
    if (!didAddTurn)
    {
      throw new RuntimeException("Unable to create cell due to turn. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoom(Room aRoom)
  {
    boolean wasSet = false;
    room = aRoom;
    wasSet = true;
    return wasSet;
  }

  public boolean setNorth(Cell aNorth)
  {
    boolean wasSet = false;
    north = aNorth;
    wasSet = true;
    return wasSet;
  }

  public boolean setSouth(Cell aSouth)
  {
    boolean wasSet = false;
    south = aSouth;
    wasSet = true;
    return wasSet;
  }

  public boolean setEast(Cell aEast)
  {
    boolean wasSet = false;
    east = aEast;
    wasSet = true;
    return wasSet;
  }

  public boolean setWest(Cell aWest)
  {
    boolean wasSet = false;
    west = aWest;
    wasSet = true;
    return wasSet;
  }

  public Room getRoom()
  {
    return room;
  }

  public Cell getNorth()
  {
    return north;
  }

  public Cell getSouth()
  {
    return south;
  }

  public Cell getEast()
  {
    return east;
  }

  public Cell getWest()
  {
    return west;
  }
  /* Code from template association_GetOne */
  public Player getOccupied()
  {
    return occupied;
  }
  /* Code from template association_GetOne */
  public Turn getTurn()
  {
    return turn;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTurn(Turn aTurn)
  {
    boolean wasSet = false;
    if (aTurn == null)
    {
      return wasSet;
    }

    Turn existingTurn = turn;
    turn = aTurn;
    if (existingTurn != null && !existingTurn.equals(aTurn))
    {
      existingTurn.removeCell(this);
    }
    turn.addCell(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Player existingOccupied = occupied;
    occupied = null;
    if (existingOccupied != null)
    {
      existingOccupied.delete();
    }
    Turn placeholderTurn = turn;
    this.turn = null;
    if(placeholderTurn != null)
    {
      placeholderTurn.removeCell(this);
    }
  }

  // line 30 "model.ump"
   public boolean isRoom(Room room){
    
  }

  // line 32 "model.ump"
   public boolean isOccupied(){
    
  }

  // line 34 "model.ump"
   public Player getOccupant(){
    
  }

  // line 36 "model.ump"
   public boolean isConnect(Cell c){
    
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "room" + "=" + (getRoom() != null ? !getRoom().equals(this)  ? getRoom().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "north" + "=" + (getNorth() != null ? !getNorth().equals(this)  ? getNorth().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "south" + "=" + (getSouth() != null ? !getSouth().equals(this)  ? getSouth().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "east" + "=" + (getEast() != null ? !getEast().equals(this)  ? getEast().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "west" + "=" + (getWest() != null ? !getWest().equals(this)  ? getWest().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "occupied = "+(getOccupied()!=null?Integer.toHexString(System.identityHashCode(getOccupied())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "turn = "+(getTurn()!=null?Integer.toHexString(System.identityHashCode(getTurn())):"null");
  }
}
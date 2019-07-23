/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 66 "model.ump"
// line 105 "model.ump"
public class Character extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Character Attributes
  private String name;
  private Cell location;
  private boolean hasGuessed;

  //Character Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Character(Game aGame, String aName, Cell aLocation, boolean aHasGuessed)
  {
    super(aGame);
    name = aName;
    location = aLocation;
    hasGuessed = aHasGuessed;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLocation(Cell aLocation)
  {
    boolean wasSet = false;
    location = aLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setHasGuessed(boolean aHasGuessed)
  {
    boolean wasSet = false;
    hasGuessed = aHasGuessed;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Cell getLocation()
  {
    return location;
  }

  public boolean getHasGuessed()
  {
    return hasGuessed;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHasGuessed()
  {
    return hasGuessed;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (player != null && !player.equals(aNewPlayer) && equals(player.getCharacter()))
    {
      //Unable to setPlayer, as existing player would become an orphan
      return wasSet;
    }

    player = aNewPlayer;
    Character anOldCharacter = aNewPlayer != null ? aNewPlayer.getCharacter() : null;

    if (!this.equals(anOldCharacter))
    {
      if (anOldCharacter != null)
      {
        anOldCharacter.player = null;
      }
      if (player != null)
      {
        player.setCharacter(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "hasGuessed" + ":" + getHasGuessed()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "location" + "=" + (getLocation() != null ? !getLocation().equals(this)  ? getLocation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}
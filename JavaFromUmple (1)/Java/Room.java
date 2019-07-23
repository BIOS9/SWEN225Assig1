/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 76 "model.ump"
// line 111 "model.ump"
public class Room extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private String name;
  private Cell centre;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(Game aGame, String aName, Cell aCentre)
  {
    super(aGame);
    name = aName;
    centre = aCentre;
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

  public boolean setCentre(Cell aCentre)
  {
    boolean wasSet = false;
    centre = aCentre;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Cell getCentre()
  {
    return centre;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "centre" + "=" + (getCentre() != null ? !getCentre().equals(this)  ? getCentre().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
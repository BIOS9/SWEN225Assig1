/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 61 "model.ump"
// line 100 "model.ump"
public class Weapon extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Attributes
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(Game aGame, String aName)
  {
    super(aGame);
    name = aName;
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

  public String getName()
  {
    return name;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;

// line 141 "model.ump"
// line 155 "model.ump"
public class suggestion
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //suggestion Associations
  private List<Card> cards;
  private Turn turn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public suggestion(Turn aTurn, Card... allCards)
  {
    cards = new ArrayList<Card>();
    boolean didAddCards = setCards(allCards);
    if (!didAddCards)
    {
      throw new RuntimeException("Unable to create suggestion, must have 3 cards. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTurn = setTurn(aTurn);
    if (!didAddTurn)
    {
      throw new RuntimeException("Unable to create suggestion due to turn. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Card getCard(int index)
  {
    Card aCard = cards.get(index);
    return aCard;
  }

  public List<Card> getCards()
  {
    List<Card> newCards = Collections.unmodifiableList(cards);
    return newCards;
  }

  public int numberOfCards()
  {
    int number = cards.size();
    return number;
  }

  public boolean hasCards()
  {
    boolean has = cards.size() > 0;
    return has;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }
  /* Code from template association_GetOne */
  public Turn getTurn()
  {
    return turn;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfCards()
  {
    return 3;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfCards()
  {
    return 3;
  }
  /* Code from template association_SetNToOptionalOne */
  public boolean setCards(Card... newCards)
  {
    boolean wasSet = false;
    ArrayList<Card> checkNewCards = new ArrayList<Card>();
    for (Card aCard : newCards)
    {
      if (checkNewCards.contains(aCard))
      {
        return wasSet;
      }
      else if (aCard.getSuggestion() != null && !this.equals(aCard.getSuggestion()))
      {
        return wasSet;
      }
      checkNewCards.add(aCard);
    }

    if (checkNewCards.size() != minimumNumberOfCards())
    {
      return wasSet;
    }

    cards.removeAll(checkNewCards);
    
    for (Card orphan : cards)
    {
      setSuggestion(orphan, null);
    }
    cards.clear();
    for (Card aCard : newCards)
    {
      setSuggestion(aCard, this);
      cards.add(aCard);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setSuggestion(Card aCard, suggestion aSuggestion)
  {
    try
    {
      java.lang.reflect.Field mentorField = aCard.getClass().getDeclaredField("suggestion");
      mentorField.setAccessible(true);
      mentorField.set(aCard, aSuggestion);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aSuggestion to aCard", e);
    }
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTurn(Turn aNewTurn)
  {
    boolean wasSet = false;
    if (aNewTurn == null)
    {
      //Unable to setTurn to null, as suggestion must always be associated to a turn
      return wasSet;
    }
    
    suggestion existingSuggestion = aNewTurn.getSuggestion();
    if (existingSuggestion != null && !equals(existingSuggestion))
    {
      //Unable to setTurn, the current turn already has a suggestion, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Turn anOldTurn = turn;
    turn = aNewTurn;
    turn.setSuggestion(this);

    if (anOldTurn != null)
    {
      anOldTurn.setSuggestion(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(Card aCard : cards)
    {
      setSuggestion(aCard,null);
    }
    cards.clear();
    Turn existingTurn = turn;
    turn = null;
    if (existingTurn != null)
    {
      existingTurn.setSuggestion(null);
    }
  }

  // line 143 "model.ump"
   public boolean isValid(){
    
  }

}
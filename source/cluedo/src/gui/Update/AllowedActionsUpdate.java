package gui.Update;

/**
 * Represents the update package for allowed actions to be passed to the MVC.
 * @author abbey
 *
 */
public class AllowedActionsUpdate extends GameUpdate {
    public final boolean allowFinishTurn;
    public final boolean allowSuggestion;
    public final boolean allowAccusation;
    
    /**
     * Constructs a new allowed actions update .
     * @param allowFinishTurn
     * @param allowSuggestion
     * @param allowAccusation
     */
    public AllowedActionsUpdate(boolean allowFinishTurn, boolean allowSuggestion, boolean allowAccusation) {
        this.allowFinishTurn = allowFinishTurn;
        this.allowSuggestion = allowSuggestion;
        this.allowAccusation = allowAccusation;
    }
}

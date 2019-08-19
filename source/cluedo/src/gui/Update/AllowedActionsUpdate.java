package gui.Update;

public class AllowedActionsUpdate extends GameUpdate {
    public final boolean allowFinishTurn;
    public final boolean allowSuggestion;
    public final boolean allowAccusation;

    public AllowedActionsUpdate(boolean allowFinishTurn, boolean allowSuggestion, boolean allowAccusation) {
        this.allowFinishTurn = allowFinishTurn;
        this.allowSuggestion = allowSuggestion;
        this.allowAccusation = allowAccusation;
    }
}

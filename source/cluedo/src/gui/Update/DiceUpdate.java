package gui.Update;

public class DiceUpdate extends GameUpdate {
    public final int FirstDie;
    public final int SecondDie;

    public DiceUpdate(int firstDie, int secondDie) {
        this.FirstDie = firstDie;
        this.SecondDie = secondDie;
    }
}

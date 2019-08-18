package gui;

import game.Player;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Collection;

public class PlayerSetupWindow extends JDialog {
    public static final String WINDOW_TITLE = "Cluedo Player Setup";
    Collection<game.cards.Character> characters;
    Collection<game.cards.Character> chosenCharacters;
    public Player player;

    public PlayerSetupWindow(Collection<game.cards.Character> characters, Collection<game.cards.Character> chosenCharacters, JFrame parent) {
        super(parent, WINDOW_TITLE, ModalityType.DOCUMENT_MODAL);

        this.chosenCharacters = chosenCharacters;
        this.characters = characters;

        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Please put your name next to your character.");
        label.setHorizontalAlignment(JLabel.CENTER);
        container.add(label);

        JButton submitButton = new JButton("OK");
        submitButton.addActionListener(e -> {
            player = new Player(characters.iterator().next());
            setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        container.add(submitButton);

        // Finalize and display the window
        add(container);
        pack();
        setVisible(true);
    }


}

package gui;

import game.Player;
import game.cards.Character;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerSetupWindow extends JDialog {
    public static final String WINDOW_TITLE = "Cluedo";
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 20;
    Collection<game.cards.Character> characters;
    Set<game.cards.Character> chosenCharacters;
    Set<String> chosenNames;
    JTextField nameField;
    String chosenCharacterName;
    public Player player;

    public PlayerSetupWindow(Collection<game.cards.Character> characters, Set<game.cards.Character> chosenCharacters, Set<String> chosenNames, JFrame parent) {
        super(parent, WINDOW_TITLE, ModalityType.DOCUMENT_MODAL);

        this.chosenNames = chosenNames;
        this.chosenCharacters = chosenCharacters;
        this.characters = characters;

        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Player name:");
        container.add(label);

        nameField = new JTextField();
        container.add(nameField);

        ButtonGroup charButtonGroup = new ButtonGroup();

        for(game.cards.Character c : characters) {
            JRadioButton charButton = new JRadioButton(c.getName());
            charButton.addActionListener(this::characterButtonListener);
            charButtonGroup.add(charButton);

            if(chosenCharacters.contains(c))
                charButton.setEnabled(false);

            container.add(charButton);
        }


        JButton submitButton = new JButton("OK");
        submitButton.addActionListener(this::okButtonListener);
        container.add(submitButton);

        // Finalize and display the window
        add(container);
        pack();
        setVisible(true);
    }

    private void characterButtonListener(ActionEvent e) {
        JRadioButton charButton = (JRadioButton)e.getSource();
        chosenCharacterName = charButton.getText();
    }

    private void okButtonListener(ActionEvent e) {
        String playerName = nameField.getText();

        if(playerName.length() < MIN_NAME_LENGTH) {
            JOptionPane.showMessageDialog(this, "Player name must be " + MIN_NAME_LENGTH + " or more characters", "Cluedo", JOptionPane.WARNING_MESSAGE);
            return;
        } else if(playerName.length() > MAX_NAME_LENGTH) {
            JOptionPane.showMessageDialog(this, "Player name must be " + MAX_NAME_LENGTH + " or less characters", "Cluedo", JOptionPane.WARNING_MESSAGE);
            return;
        } else if(chosenNames.contains(playerName)) {
            JOptionPane.showMessageDialog(this, "That name has already been used", "Cluedo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ensure a character has been chose
        if(chosenCharacterName == null) {
            JOptionPane.showMessageDialog(this, "Please choose a character", "Cluedo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Optional<Character> character = characters.stream().filter(x -> x.getName().equals(chosenCharacterName)).findFirst();
        if(character.isPresent()) {
            player = new Player(character.get(), playerName);
            close();
        }
    }

    private void close() {
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

}

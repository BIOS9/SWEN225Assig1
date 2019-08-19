package gui;

import game.Player;
import game.cards.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class PlayerSetupWindow extends JDialog implements KeyListener {
    public static final String WINDOW_TITLE = "Cluedo";
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 20;
    Collection<game.cards.Character> characters;
    Set<game.cards.Character> chosenCharacters;
    Set<String> chosenNames;
    JTextField nameField;
    String chosenCharacterName;
    public Player player;

    // Default names to enter
    static final List<String> DEFAULT_NAMES = new ArrayList<String>() {{
        add("Mufasa");
        add("Simba");
        add("Scar");
        add("Nala");
        add("Pumbaa");
        add("Sarabi");
    }};

    /**
     * Constructor for the player setup window
     * @param characters Characters to let the player choose from
     * @param chosenCharacters Character that have already been chosen that a player cannot choose
     * @param chosenNames Names that have already been chosen so that a player cannot choose
     * @param parent The parent window of the JDialog
     */
    public PlayerSetupWindow(Collection<game.cards.Character> characters, Set<game.cards.Character> chosenCharacters, Set<String> chosenNames, JFrame parent) {
        super(parent, WINDOW_TITLE, ModalityType.DOCUMENT_MODAL);

        this.chosenNames = chosenNames;
        this.chosenCharacters = chosenCharacters;
        this.characters = characters;

        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Player name:");
        constraints.anchor = GridBagConstraints.WEST;
        container.add(label, constraints);

        // Adds name field
        nameField = new JTextField();
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        container.add(nameField, constraints);


        // Setup default name
        for(String name : DEFAULT_NAMES) {
            if(chosenNames.contains(name)) continue;

            nameField.setText(name);
            break;
        }

        int currentY = 2; // 2 because there are 2 elements above
        boolean selected = false;

        // Add the radio buttons
        ButtonGroup charButtonGroup = new ButtonGroup();
        for(game.cards.Character c : characters) {
            JRadioButton charButton = new JRadioButton(c.getName());
            nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
            charButton.addActionListener(this::characterButtonListener);
            charButtonGroup.add(charButton);

            if(chosenCharacters.contains(c))
                charButton.setEnabled(false);
            else if(!selected) {
                charButton.setSelected(true);
                chosenCharacterName = c.getName();
                selected = true;
            }

            constraints.gridy = currentY;
            container.add(charButton, constraints);
            ++currentY;
        }


        // Add submit button
        JButton submitButton = new JButton("OK");
        submitButton.addActionListener(this::okButtonListener);
        constraints.gridy = currentY;
        container.add(submitButton, constraints);

        addKeyListener(this);
        nameField.addKeyListener(this);

        // Finalize and display the window
        add(container);
        pack();
        setVisible(true);
    }

    /**
     * Listener for when the player clicks a character radio button
     */
    private void characterButtonListener(ActionEvent e) {
        JRadioButton charButton = (JRadioButton)e.getSource();
        chosenCharacterName = charButton.getText();
    }

    /**
     * Listener for when the player clicks the OK button
     */
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

    /**
     * Closes the window, doesn't dispose of the object but hides the window from the player
     * The window is not disposed so that other parts of the program can retrieve the generated player
     * object before disposing of the window after it closes.
     */
    private void close() {
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) // Handles enter pressed
            okButtonListener(null);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

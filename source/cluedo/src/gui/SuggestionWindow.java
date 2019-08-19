package gui;

import game.Player;
import game.Suggestion;
import game.cards.Character;
import game.cards.Room;
import game.cards.Weapon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Optional;

public class SuggestionWindow extends JDialog {
    public static final String WINDOW_TITLE = "Cluedo";
    Collection<game.cards.Character> characters;
    Collection<Room> rooms;
    Collection<Weapon> weapons;
    Room forceRoom;
    boolean isAccusation;
    game.cards.Character selectedCharacter;
    game.cards.Room selectedRoom;
    game.cards.Weapon selectedWeapon;
    Player player;

    public SuggestionWindow(Collection<game.cards.Character> characters, Collection<Room> rooms, Collection<Weapon> weapons, Player player, Room forceRoom, JFrame parent) {
        super(parent, WINDOW_TITLE, Dialog.ModalityType.DOCUMENT_MODAL);
        if(forceRoom == null)
            isAccusation = true;
        else
            isAccusation = false;

        this.characters = characters;
        this.rooms = rooms;
        this.weapons = weapons;
        this.forceRoom = forceRoom;
        this.player = player;

        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        constraints.insets = new Insets(5, 25, 0, 5);
        constraints.gridwidth = 3;
        container.add(new JLabel("Please choose your cards."), constraints);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10, 5, 0, 0);
        constraints.gridy = 1;
        container.add(new JLabel("Character:"), constraints);

        int currentY = 2;
        boolean selected = false;

        // Add the character buttons
        constraints.insets = new Insets(0, 0, 0, 20);
        ButtonGroup charButtonGroup = new ButtonGroup();
        for(game.cards.Character c : characters) {
            JRadioButton charButton = new JRadioButton(c.getName());
            charButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            charButton.addActionListener(e -> {
                Optional<Character> character  = characters.stream().filter(x -> x.getName().equals(charButton.getText())).findFirst();
                selectedCharacter = character.orElse(null);
            });
            charButtonGroup.add(charButton);

            if(!selected) {
                charButton.setSelected(true);
                selectedCharacter = c;
                selected = true;
            }

            constraints.gridy = currentY;
            container.add(charButton, constraints);
            ++currentY;
        }

        selected = false;


        constraints.insets = new Insets(10, 25, 0, 0);
        constraints.gridy = 1;
        constraints.gridx = 1;
        container.add(new JLabel("Weapon:"), constraints);

        currentY = 2;

        // Add the character buttons
        constraints.insets = new Insets(0, 20, 0, 20);
        ButtonGroup weaponButtonGroup = new ButtonGroup();
        for(game.cards.Weapon w : weapons) {
            JRadioButton weaponButton = new JRadioButton(w.getName());
            weaponButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            weaponButton.addActionListener(e -> {
                Optional<Weapon> weapon  = weapons.stream().filter(x -> x.getName().equals(weaponButton.getText())).findFirst();
                selectedWeapon = weapon.orElse(null);
            });
            weaponButtonGroup.add(weaponButton);

            if(!selected) {
                weaponButton.setSelected(true);
                selectedWeapon = w;
                selected = true;
            }

            constraints.gridy = currentY;
            container.add(weaponButton, constraints);
            ++currentY;
        }

        selected = false;

        constraints.insets = new Insets(10, 25, 0, 0);
        constraints.gridy = 1;
        constraints.gridx = 2;
        container.add(new JLabel("Room:"), constraints);

        currentY = 2;

        // Add rooms
        constraints.insets = new Insets(0, 20, 0, 10);
        ButtonGroup roomButtonGroup = new ButtonGroup();
        for(game.cards.Room r : rooms) {
            JRadioButton roomButton = new JRadioButton(r.getName());
            roomButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            roomButton.addActionListener(e -> {
                Optional<Room> room  = rooms.stream().filter(x -> x.getName().equals(roomButton.getText())).findFirst();
                selectedRoom = room.orElse(null);
            });
            roomButtonGroup.add(roomButton);

            // Force selection of the current room if this is a suggestion
            if(!isAccusation) {
                selectedRoom = forceRoom;
                if(r.equals(forceRoom))
                    roomButton.setSelected(true);
                roomButton.setEnabled(false);
            } else if(!selected) {
                roomButton.setSelected(true);
                selectedRoom = r;
                selected = true;
            }

            constraints.gridy = currentY;
            container.add(roomButton, constraints);
            ++currentY;
        }

        // Add submit button
        JButton submitButton = new JButton("OK");
        submitButton.addActionListener(e -> close());
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.gridy = currentY;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        container.add(submitButton, constraints);

        // Finalize and display the window
        add(container);
        pack();
        setVisible(true);
    }

    public SuggestionWindow(Collection<game.cards.Character> characters, Collection<Room> rooms, Collection<Weapon> weapons, Player player, JFrame parent) {
        this(characters, rooms, weapons, player, null, parent);
    }

    public Suggestion getSuggestion() {
        return new Suggestion(selectedRoom, selectedCharacter, selectedWeapon, player, isAccusation);
    }

    /**
     * Closes the window, doesn't dispose of the object but hides the window from the player
     * The window is not disposed so that other parts of the program can retrieve the generated suggestion
     * object before disposing of the window after it closes.
     */
    private void close() {
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}

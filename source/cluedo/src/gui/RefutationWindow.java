package gui;

import game.Player;
import game.Suggestion;
import game.cards.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RefutationWindow extends JDialog {
    public static final String WINDOW_TITLE = "Cluedo";
    public Card refutation;

    public RefutationWindow(Player player, Suggestion suggestion, JFrame parent) {
        super(parent, WINDOW_TITLE, ModalityType.DOCUMENT_MODAL);

        // Get the cards that the player has that can be used for the refutation
        List<Card> playerRefutableCards = player.getHand().stream().filter(suggestion::containsCard).collect(Collectors.toList());

        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 0, 5);
        container.add(new JLabel(playerRefutableCards.isEmpty() ? player.getPlayerName() + " you have no refutation cards, continue when you're ready." : player.getPlayerName() + " please choose a refutation card."), constraints);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10, 5, 0, 0);
        constraints.gridy = 1;
        container.add(new JLabel("Valid refutation cards:"), constraints);

        int currentY = 2;
        boolean selected = false;

        // Add the card buttons
        constraints.insets = new Insets(0, 0, 0, 0);
        ButtonGroup charButtonGroup = new ButtonGroup();
        for (Card c : playerRefutableCards) {
            JRadioButton cardButton = new JRadioButton(c.getName());
            cardButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            cardButton.addActionListener(e -> {
                Optional<Card> card = playerRefutableCards.stream().filter(x -> x.getName().equals(cardButton.getText())).findFirst();
                refutation = card.orElse(null);
            });
            charButtonGroup.add(cardButton);

            if (!selected) {
                cardButton.setSelected(true);
                refutation = c;
                selected = true;
            }

            constraints.gridy = currentY;
            container.add(cardButton, constraints);
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

    /**
     * Closes the window, doesn't dispose of the object but hides the window from the player
     * The window is not disposed so that other parts of the program can retrieve the generated card
     * object before disposing of the window after it closes.
     */
    private void close() {
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}

package gui;

import game.CluedoGame;
import gui.request.PlayerCountRequest;
import gui.request.PlayerRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class GameWindow extends JFrame implements Observer, ActionListener {
    public static final String WINDOW_TITLE = "Cluedo";
    public static final int WINDOW_INITIAL_WIDTH = 800;
    public static final int WINDOW_INITIAL_HEIGHT = 800;
    public static final String DICE_BACKGROUND_IMAGE = "images/felt.jpg";

    private CluedoGame game = null;

    private JLabel diceBox, cardBox, playerBox, boardBox, messageBox, infoBox;


    public GameWindow() {
        super(WINDOW_TITLE);
        setSize(WINDOW_INITIAL_WIDTH, WINDOW_INITIAL_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildWindow();

        // Finalize and display the window with the game
        pack();
        setVisible(true);
        //newGame();
    }

    /**
     * Defines layout of the window and adds all the elements
     */
    private void buildWindow() {
        buildMenuBar();

        JPanel container = new JPanel(new GridBagLayout()); // The main container that holds all the elements
        buildDiceBox(container);
        buildCardBox(container);
        buildBoardBox(container);
        buildPlayerBox(container);
        buildInfoBox(container);

        container.setOpaque(true);
        container.setBackground(Color.black);

        add(container);
    }

    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener((e) -> newGame());
        menu.add(newGameItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((e) -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        menu.add(exitItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void buildInfoBox(JPanel container) {
        infoBox = new JLabel();
        infoBox.setPreferredSize(new Dimension(150, 150));
        infoBox.setOpaque(true);
        infoBox.setBackground(Color.magenta);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 3;

        container.add(infoBox, c);
    }

    private void buildPlayerBox(JPanel container) {
        playerBox = new JLabel();
        playerBox.setPreferredSize(new Dimension(200, 100));
        playerBox.setOpaque(true);
        playerBox.setBackground(Color.orange);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 0;
        c.weighty = 1;

        container.add(playerBox, c);
    }

    private void buildDiceBox(JPanel container) {
        diceBox = new JLabel();
        diceBox.setPreferredSize(new Dimension(200, 100));
        diceBox.setOpaque(true);
        diceBox.setBackground(Color.blue);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 1;

        container.add(diceBox, c);
    }

    private void buildCardBox(JPanel container) {
        cardBox = new JLabel();
        cardBox.setOpaque(true);
        cardBox.setBackground(Color.red);
        cardBox.setPreferredSize(new Dimension(100, 150));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;

        c.anchor = GridBagConstraints.WEST;

        container.add(cardBox, c);

    }

    private void buildBoardBox(JPanel container) {
        JPanel boardContainer = new JPanel();
        boardContainer.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        messageBox = new JLabel("Hello, here is a message!");
        messageBox.setPreferredSize(new Dimension(200, 20));
        messageBox.setOpaque(true);
        messageBox.setBackground(Color.cyan);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        boardContainer.add(messageBox, c);

        boardBox = new JLabel();
        boardBox.setOpaque(true);
        boardBox.setBackground(Color.green);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridy = 0;
        boardContainer.add(boardBox, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 2;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        container.add(boardContainer, c);
    }

    private void newGame() {
        if(game != null)
            game.deleteObserver(this);
        game = new CluedoGame();
        game.addObserver(this);
        game.startGame();
    }

    private void request(PlayerRequest request) {
        if(request instanceof PlayerCountRequest) {
            PlayerCountRequest playerCountRequest = (PlayerCountRequest)request;
            int result = askPlayerCount(playerCountRequest);
            playerCountRequest.setResponse(result); // Return the result to the game
        }
    }

    private int askPlayerCount(PlayerCountRequest request) {
        while (true) {
            String result = JOptionPane.showInputDialog("How many players are playing? " + request.conditions);
            try {
                int playerCount = Integer.parseInt(result);
                if(request.isInputValid(playerCount)) // Only exit if the input is valid
                    return playerCount;
            } catch (Exception ex) {}
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg != null) {
            if (arg instanceof PlayerRequest) {
                request((PlayerRequest)arg);
            }
        }
    }
}

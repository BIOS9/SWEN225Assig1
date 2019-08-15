package gui;

import game.CluedoGame;
import gui.Update.DiceUpdate;
import gui.request.PlayerCountRequest;
import gui.request.PlayerRequest;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

        public class GameWindow extends JFrame implements Observer, ActionListener {
            public static final String WINDOW_TITLE = "Cluedo";
            public static final int
                    WINDOW_INITIAL_WIDTH = 800,
                    WINDOW_INITIAL_HEIGHT = 800,

                    DICE_BOX_WIDTH = 200,
                    DICE_BOX_HEIGHT = 100,
                    DICE_SIZE = 60,
                    DICE_GAP = 20,
                    DICE_PADDING_TOP = 15;

            public static final Map<String, String> IMAGE_FILES = new HashMap<String, String>() {{
                put("felt", "images/texture/felt.jpg");
                put("darkFelt", "images/texture/darkFelt.jpg");
                put("borderTL", "images/texture/TopLeftBorderCorner.png");
                put("borderTR", "images/texture/TopRightBorderCorner.png");
                put("borderBL", "images/texture/BottomLeftBorderCorner.png");
                put("borderBR", "images/texture/BottomRightBorderCorner.png");
                put("borderTop", "images/texture/TopBorderTile.png");
                put("borderBottom", "images/texture/BottomBorderTile.png");
                put("borderLeft", "images/texture/LeftBorderTile.png");
                put("borderRight", "images/texture/RightBorderTile.png");
                put("die1", "images/dice/die1.png");
                put("die2", "images/dice/die2.png");
                put("die3", "images/dice/die3.png");
                put("die4", "images/dice/die4.png");
                put("die5", "images/dice/die5.png");
                put("die6", "images/dice/die6.png");
    }};
    private Map<String, Image> images = new HashMap<>();

    private CluedoGame game = null;

    private JLabel boardBox, messageBox, infoBox;
    private ImagePanel cardBox, diceBox, die1, die2, playerBox;

    public GameWindow() {
        super(WINDOW_TITLE);
        setSize(WINDOW_INITIAL_WIDTH, WINDOW_INITIAL_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loadImages();
        buildWindow();

        // Finalize and display the window with the game
        pack();
        setVisible(true);
        //newGame();
    }

    /**
     * Loads images from files into image map
     */
    private void loadImages() {
        for(Map.Entry<String, String> image : IMAGE_FILES.entrySet()) {
            try {
                images.put(image.getKey(), ImageIO.read(new File(image.getValue())));
            } catch (IOException ex) {
                System.out.println("ERROR LOADING IMAGE: " + ex + " " + image.getValue());
            }
        }
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
        playerBox = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"));
        playerBox.setPreferredSize(new Dimension(200, 100));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 0;
        c.weighty = 1;

        container.add(playerBox, c);
    }

    private void buildDiceBox(JPanel container) {
        diceBox = new ImagePanel(images.get("felt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"));
        diceBox.setPreferredSize(new Dimension(DICE_BOX_WIDTH, DICE_BOX_HEIGHT));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(DICE_GAP);
        diceBox.setLayout(layout);
        diceBox.setBorder(new EmptyBorder(DICE_PADDING_TOP, 0, 0, 0));

        die1 = new ImagePanel(images.get("die1"), false);
        die1.setPreferredSize(new Dimension(DICE_SIZE, DICE_SIZE));
        die2 = new ImagePanel(images.get("die1"), false);
        die2.setPreferredSize(new Dimension(DICE_SIZE, DICE_SIZE));

        diceBox.add(die1);
        diceBox.add(die2);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 1;

        container.add(diceBox, c);
    }

    private void buildCardBox(JPanel container) {
        cardBox = new ImagePanel(images.get("felt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"));
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
        messageBox.setMinimumSize(new Dimension(0, 20));
        messageBox.setOpaque(true);
        messageBox.setBackground(Color.cyan);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        boardContainer.add(messageBox, c);

        boardBox = new JLabel();
        boardBox.setOpaque(true);
        setMinimumSize(new Dimension(600, 600));
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

    private void updateDice(DiceUpdate update) {
        die1.setBacktroundImage(images.get("die" + update.FirstDie));
        die1.repaint();
        die2.setBacktroundImage(images.get("die" + update.SecondDie));
        die2.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg != null) {
            if (arg instanceof PlayerRequest)
                request((PlayerRequest)arg);
            else if(arg instanceof DiceUpdate)
                updateDice((DiceUpdate)arg);
        }
    }
}

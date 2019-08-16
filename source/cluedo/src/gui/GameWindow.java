package gui;

import game.CluedoGame;
import game.cards.Card;
import gui.Update.DiceUpdate;
import gui.Update.HandUpdate;
import gui.request.PlayerCountRequest;
import gui.request.PlayerRequest;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GameWindow extends JFrame implements Observer, ActionListener {
            public static final String WINDOW_TITLE = "Cluedo";
            public static final int
                    WINDOW_MIN_WIDTH = 800,
                    WINDOW_MIN_HEIGHT = 800,

                    DICE_BOX_HEIGHT = 100,
                    DICE_SIZE = 60,
                    DICE_GAP = 20,
                    DICE_PADDING_TOP = 15,

                    SIDEBAR_WIDTH = 200,
                    BOTTOMBAR_HEIGHT = 200,
                    BORDER_WIDTH = 7,

                    INFO_BOX_WIDTH = 150,

                    CARD_WIDTH = 100,
                    CARD_HEIGHT = 170,
                    CARD_GAP = 10,
                    CARD_PADDING_TOP = 10;


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

                put("Miss Scarlett", "images/cards/Miss_scarlet.png");
                put("Mr Green", "images/cards/Mr_green.png");
                put("Colonel Mustard", "images/cards/Colonel_mustard.png");
                put("Professor Plum", "images/cards/Prof_plum.png");
                put("Mrs. Peacock", "images/cards/Mrs_peacock.png");
                put("Mrs. White", "images/cards/Mrs_White.png");

                put("Kitchen", "images/cards/Kitchen.png");
                put("Ball Room", "images/cards/Ballroom.png");
                put("Conservatory", "images/cards/Conservatory.png");
                put("Billiard Room", "images/cards/Billard_room.png");
                put("Dining Room", "images/cards/Dining_room.png");
                put("Library", "images/cards/Library.png");
                put("Hall", "images/cards/Hall.png");
                put("Lounge", "images/cards/Lounge.png");
                put("Study", "images/cards/Study.png");

                put("Candlestick", "images/cards/Candlestick.png");
                put("Knife", "images/cards/Knife.png");
                put("Lead Pipe", "images/cards/Pipe.png");
                put("Revolver", "images/cards/Revolver.png");
                put("Rope", "images/cards/Rope.png");
                put("Wrench", "images/cards/Wrench.png");
    }};
    private Map<String, Image> images = new HashMap<>();

    private CluedoGame game = null;

    private JLabel messageBox;
    private ImagePanel cardBox, diceBox, die1, die2, playerBox, boardBox, infoBox;
    private JScrollPane cardScollBox;

    public GameWindow() {
        super(WINDOW_TITLE);
        setMinimumSize(new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT));
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
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString().replace('\\','/');
                System.out.println("ERROR LOADING IMAGE: " + ex + " " + s + "/" + image.getValue());
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
        infoBox = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);;
        infoBox.setPreferredSize(new Dimension(INFO_BOX_WIDTH, BOTTOMBAR_HEIGHT));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 3;

        container.add(infoBox, c);
    }

    private void buildPlayerBox(JPanel container) {
        playerBox = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);
        playerBox.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 0;
        c.weighty = 1;

        container.add(playerBox, c);
    }

    private void buildDiceBox(JPanel container) {
        diceBox = new ImagePanel(images.get("felt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);
        diceBox.setPreferredSize(new Dimension(SIDEBAR_WIDTH, DICE_BOX_HEIGHT));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(DICE_GAP);
        diceBox.setLayout(layout);
        diceBox.setBorder(new EmptyBorder(DICE_PADDING_TOP, 0, 0, 0));

        die1 = new ImagePanel(images.get("die1"), false, BORDER_WIDTH);
        die1.setPreferredSize(new Dimension(DICE_SIZE, DICE_SIZE));
        die2 = new ImagePanel(images.get("die1"), false, BORDER_WIDTH);
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
        cardBox = new ImagePanel(images.get("felt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);


        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setHgap(CARD_GAP);
        cardBox.setBorder(new EmptyBorder(CARD_PADDING_TOP, CARD_GAP, 0, 0));
        cardBox.setLayout(layout);

        cardScollBox = new JScrollPane(cardBox, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardScollBox.setPreferredSize(new Dimension(0, BOTTOMBAR_HEIGHT));
        cardScollBox.setBorder(new EmptyBorder(0, 0, 0, 0));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;

        c.anchor = GridBagConstraints.WEST;

        container.add(cardScollBox, c);

    }

    private void buildBoardBox(JPanel container) {
        ImagePanel boardContainer = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);
        boardContainer.setLayout(new GridBagLayout());
        boardContainer.setBorder(new EmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));

        GridBagConstraints c = new GridBagConstraints();
        messageBox = new JLabel("Hello, here is a message!", SwingConstants.CENTER);
        messageBox.setMinimumSize(new Dimension(0, 20));
        messageBox.setOpaque(false);
        messageBox.setForeground(Color.white);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        boardContainer.add(messageBox, c);

        boardBox = new ImagePanel(images.get("darkFelt"), true, BORDER_WIDTH);

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

    /**
     * Changes the dice to reflect the dice rolled in the game
     * @param update Holds the dice values from the roll
     */
    private void updateDice(DiceUpdate update) {
        // "rolls" the dice for a bit
        final Random rng = new Random();
        final Timer timer = new Timer(100, e -> {
            diceBox.repaint();
            die1.setBacktroundImage(images.get("die" + (rng.nextInt(6) + 1)));
            die1.repaint();
            die2.setBacktroundImage(images.get("die" + (rng.nextInt(6) + 1)));
            die2.repaint();
        });
        timer.setRepeats(true);
        timer.start();

        // Stops the dice rolling and sets the value
        Timer stopTimer = new Timer(1500, e -> {
            timer.stop();
            diceBox.repaint();
            die1.setBacktroundImage(images.get("die" + update.FirstDie));
            die1.repaint();
            die2.setBacktroundImage(images.get("die" + update.SecondDie));
            die2.repaint();
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
    }

    private void updateHand(HandUpdate update) {
        cardBox.removeAll();
        cardBox.repaint();

        int delay = 100;
        for(Card c : update.hand) {
            ImagePanel cardPanel = new ImagePanel(images.get(c.getName()), false, BORDER_WIDTH);
            cardPanel.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));

            Timer t = new Timer(delay, e -> {
                cardBox.add(cardPanel);
                cardBox.revalidate();
            });
            t.setRepeats(false);
            t.start();
            delay += 100;
        }
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
            else if(arg instanceof HandUpdate)
                updateHand((HandUpdate)arg);
        }
    }
}

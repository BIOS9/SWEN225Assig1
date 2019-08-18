package gui;

import game.CluedoGame;
import game.Player;
import game.cards.Card;
import gui.Update.BoardUpdate;
import gui.Update.DiceUpdate;
import gui.Update.HandUpdate;
import gui.Update.MessageUpdate;
import gui.request.PlayerBeginTurnRequest;
import gui.request.PlayerCountRequest;
import gui.request.PlayerSetupRequest;
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
import java.util.List;

/**
 * 
 * Creates the game window using Jpanels, setting backgrounds and allocating the different spaces on the board.
 * Game panel is the observer, contains the buttons that allow the player to interact with the game elements
 * 
 * @author abbey
 *
 */
public class GameWindow extends JFrame implements Observer, ActionListener {
	
			//GameWindow attributes
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

            // Map of image names to file locations to make the drawing of the board easier.
            public static final Map<String, String> IMAGE_FILES = new HashMap<String, String>() {{
            	
            	//backgrounds
                put("felt", "images/texture/felt.jpg");
                put("darkFelt", "images/texture/darkFelt.jpg");
                
                //boarders
                put("borderTL", "images/texture/TopLeftBorderCorner.png");
                put("borderTR", "images/texture/TopRightBorderCorner.png");
                put("borderBL", "images/texture/BottomLeftBorderCorner.png");
                put("borderBR", "images/texture/BottomRightBorderCorner.png");
                put("borderTop", "images/texture/TopBorderTile.png");
                put("borderBottom", "images/texture/BottomBorderTile.png");
                put("borderLeft", "images/texture/LeftBorderTile.png");
                put("borderRight", "images/texture/RightBorderTile.png");
                
                //dice
                put("die1", "images/dice/die1.png");
                put("die2", "images/dice/die2.png");
                put("die3", "images/dice/die3.png");
                put("die4", "images/dice/die4.png");
                put("die5", "images/dice/die5.png");
                put("die6", "images/dice/die6.png");
                
                //player cards
                put("Miss Scarlett", "images/cards/Miss_scarlet.png");
                put("Mr Green", "images/cards/Mr_green.png");
                put("Colonel Mustard", "images/cards/Colonel_mustard.png");
                put("Professor Plum", "images/cards/Prof_plum.png");
                put("Mrs. Peacock", "images/cards/Mrs_peacock.png");
                put("Mrs. White", "images/cards/Mrs_White.png");
                
                //room cards
                put("Kitchen", "images/cards/Kitchen.png");
                put("Ball Room", "images/cards/Ballroom.png");
                put("Conservatory", "images/cards/Conservatory.png");
                put("Billiard Room", "images/cards/Billard_room.png");
                put("Dining Room", "images/cards/Dining_room.png");
                put("Library", "images/cards/Library.png");
                put("Hall", "images/cards/Hall.png");
                put("Lounge", "images/cards/Lounge.png");
                put("Study", "images/cards/Study.png");
                
                //weapon cards
                put("Candlestick", "images/cards/Candlestick.png");
                put("Knife", "images/cards/Knife.png");
                put("Lead Pipe", "images/cards/Pipe.png");
                put("Revolver", "images/cards/Revolver.png");
                put("Rope", "images/cards/Rope.png");
                put("Wrench", "images/cards/Wrench.png");
    }};
    private Map<String, Image> images = new HashMap<>();

    private JLabel messageBox;
    private ImagePanel cardBox, diceBox, die1, die2, playerBox, boardBox, infoBox;
    private JScrollPane cardScollBox;
    
    //Game window associations
    private CluedoGame game = null;
    
    /**
     * Constructs a new game window.
     */
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
        
        // The main container that holds all the elements
        JPanel container = new JPanel(new GridBagLayout()); 
        
        // Individual elements of the board.
        buildDiceBox(container);
        buildCardBox(container);
        buildBoardBox(container);
        buildPlayerBox(container);
        buildInfoBox(container);

        add(container);
    }

    /**
     * Constructs the menu bar with the menu items (new game, exit).
     */
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
    
    /**
     * Constructs the info box, have forgotten what it does .... sorra chewed my plan.
     * @param container
     */
    private void buildInfoBox(JPanel container) {
        infoBox = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);;
        infoBox.setPreferredSize(new Dimension(INFO_BOX_WIDTH, BOTTOMBAR_HEIGHT));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 3;

        container.add(infoBox, c);
    }
    
    /**
     * Constructs the player box, used to display the players who are in the game and who's turn it is currently.
     * @param container
     */
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

    /**
     * Constructs the dice box used for displaying the dice and total number rolled by a player during thier turn.
     * @param container
     */
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

    /**
     * Constructs the card box used for displaying the players hand.
     * @param container
     */
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

    /**
     * Constructs the board box used for displaying the game board.
     * @param container
     */
    private void buildBoardBox(JPanel container) {
        ImagePanel boardContainer = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);
        boardContainer.setLayout(new GridBagLayout());
        boardContainer.setBorder(new EmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));

        GridBagConstraints c = new GridBagConstraints();
        messageBox = new JLabel("Welcome to Cluedo!", SwingConstants.CENTER);
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

    /**
     * Constructs a new game, removing the observer links of any old games to ensure there is not multiple game
     * objects sending updates.
     */
    private void newGame() {
        if(game != null)
            game.deleteObserver(this);
        game = new CluedoGame();
        game.addObserver(this);
        game.startGame();
    }

    /**
     * Creates a new request from a player request checking if it is a count request or a 
     * begin turn request processing the request accordingly.
     * @param request
     */
    private void request(PlayerRequest request) {
        if(request instanceof PlayerSetupRequest) {
            PlayerSetupRequest playerSetupRequest = (PlayerSetupRequest)request;
            Player result = askPlayerInfo(playerSetupRequest);
            playerSetupRequest.setResponse(result); // Return the result to the game

        } else if (request instanceof PlayerBeginTurnRequest) {
            PlayerBeginTurnRequest playerBeginTurnRequest = (PlayerBeginTurnRequest)request;
            askPlayerBeginTurn(playerBeginTurnRequest);

        } else if(request instanceof PlayerCountRequest) {
            PlayerCountRequest playerCountRequest = (PlayerCountRequest)request;
            int count = askPlayerCount();
            playerCountRequest.setResponse(count);
        }
    }

    private Player askPlayerInfo(PlayerSetupRequest request) {
        PlayerSetupWindow window = new PlayerSetupWindow(request.characters, request.chosenCharacters, this);
        Player player = window.player;
        window.setVisible(false);
        window.dispose();
        return player;
    }

    private int askPlayerCount() {
        while (true) {
            try {
                int response = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players are playing? (3-6)", "Cluedo", JOptionPane.INFORMATION_MESSAGE));

                if(response > 6)
                    JOptionPane.showMessageDialog(this, "The maximum number of players is 6", "Cluedo", JOptionPane.WARNING_MESSAGE);
                else if (response < 3)
                    JOptionPane.showMessageDialog(this, "The minimum number of players is 3", "Cluedo", JOptionPane.WARNING_MESSAGE);
                else
                    return response;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number (3-6)!", "Cluedo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    /**
     * Updates the board to let a player know their turn is starting requiring them to acknowledge this.
     * @param request 
     */
    private void askPlayerBeginTurn(PlayerBeginTurnRequest request) {
        JOptionPane.showMessageDialog(this, request.player.getPlayerName() + " you're up!\nPress ok when you're ready to start.", "Cluedo", JOptionPane.INFORMATION_MESSAGE);
        request.setResponse(null);
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
    
    /**
     * Redisplays the cardBox.
     * @param update
     */
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
    
    private void updateMessage(MessageUpdate update) {
        messageBox.setText(update.message);
    }
    
    private void updateBoard(BoardUpdate board) {
    	
    	
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
            else if(arg instanceof MessageUpdate)
                updateMessage((MessageUpdate)arg);
            else if(arg instanceof BoardUpdate)
            	updateBoard((BoardUpdate)arg);
        }
    }
}

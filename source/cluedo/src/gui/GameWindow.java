package gui;

import game.CluedoGame;
import game.Player;
import game.Suggestion;
import game.board.Board;
import game.board.Cell;
import game.board.Position;
import game.cards.Card;
import game.cards.Room;
import gui.Update.*;
import gui.request.*;
import javafx.util.Pair;
import jdk.nashorn.internal.scripts.JO;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.*;

/**
 * Creates the game window using Jpanels, setting backgrounds and allocating the different spaces on the board.
 * Game panel is the observer, contains the buttons that allow the player to interact with the game elements
 *
 * @author abbey
 */
public class GameWindow extends JFrame implements Observer, ActionListener, MouseMotionListener, MouseListener {

    //region FIELDS

    //GameWindow attributes
    public static final String WINDOW_TITLE = "Cluedo";
    public static final int
            WINDOW_MIN_WIDTH = 800,
            WINDOW_MIN_HEIGHT = 800,
            DEFAULT_FONT_SIZE = 12,
            ROOM_LABEL_FONT_SIZE = 15,

            DICE_BOX_HEIGHT = 100,
            DICE_SIZE = 60,
            DICE_GAP = 20,
            DICE_PADDING_TOP = 15,

            PLAYER_HEIGHT = 20,
            PLAYER_COLOR_SIZE = 10,
            PLAYER_PADDING = 5,

            SIDEBAR_WIDTH = 200,
            BOTTOMBAR_HEIGHT = 200,
            BORDER_WIDTH = 7,

            INFO_BOX_WIDTH = 150,

            CARD_WIDTH = 100,
            CARD_HEIGHT = 170,
            CARD_GAP = 10,
            CARD_PADDING_TOP = 10;
    public static final float
            CELL_BORDER_OPACITY = 0.10f;

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

    private Map<String, Color> roomColors = new HashMap<String, Color>() {{
        put("Study", Color.decode("#F5B0A0"));
        put("Hall", Color.decode("#F7E7AB"));
        put("Lounge", Color.decode("#A3F5EF"));
        put("Library", Color.decode("#A5CAF7"));
        put("Dining Room", Color.decode("#C4A3F4"));
        put("Billiard Room", Color.decode("#F8A4E0"));
        put("Conservatory", Color.decode("#D0A39E"));
        put("Ball Room", Color.decode("#e3554b"));
        put("Kitchen", Color.decode("#807B77"));
        put("Hallway", Color.decode("#66BA5C"));
    }};

    private Map<Point2D, String> roomLabels = new HashMap<Point2D, String>() {{
        put(new Point2D.Float(3f, 4f), "Kitchen");
        put(new Point2D.Float(12f, 5f), "Ball Room");
        put(new Point2D.Float(21f, 3.5f), "Conservatory");
        put(new Point2D.Float(4f, 12.5f), "Dining Room");
        put(new Point2D.Float(21f, 10.5f), "Billiard Room");
        put(new Point2D.Float(3.5f, 22f), "Lounge");
        put(new Point2D.Float(12f, 21.5f), "Hall");
        put(new Point2D.Float(20.5f, 23f), "Study");
        put(new Point2D.Float(20.5f, 16.5f), "Library");
    }};

    private JLabel messageBox, roundNumberLabel, turnNumberLabel, movesLeftLabel, gameTimerLabel, attemptedMoveLabel;
    private ImagePanel cardBox, diceBox, die1, die2, playerBox, infoBox, turnActionBox;
    private JPanel boardBox;
    private String toolTipText = null;
    private JButton finishTurnButton, suggestButton, accuseButton;

    //Game window associations
    private CluedoGame game = null;
    private Board board;
    private Player currentPlayer;
    private int playerCount;
    private int roundNumber;
    private int attemptedMoveCount;
    private int movesLeft;
    private Instant gameStart;
    private Cell selectedCell;
    private boolean isSelectedVisited = false;
    private boolean isPlayerMoving = false;
    private Set<Cell> validMoveCells = new HashSet<>(); // Cells in the move that are valid
    private Set<Cell> visitedCells = new HashSet<>();
    private Set<Room> visitedRooms = new HashSet<>();
    private List<Cell.Direction> validMoveDirections = new ArrayList<>(); // Path of valid moves
    int cursorX, cursorY;

    //endregion

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

        newGame();
    }

    //region GUI SETUP

    /**
     * Loads images from files into image map
     */
    private void loadImages() {
        for (Map.Entry<String, String> image : IMAGE_FILES.entrySet()) {
            try {
                images.put(image.getKey(), ImageIO.read(new File(image.getValue())));
            } catch (IOException ex) {
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString().replace('\\', '/');
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
        buildTurnActionBox(container);
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
     *
     * @param container
     */
    private void buildInfoBox(JPanel container) {
        infoBox = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);
        infoBox.setPreferredSize(new Dimension(INFO_BOX_WIDTH, BOTTOMBAR_HEIGHT));
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setBorder(new EmptyBorder(BORDER_WIDTH * 2, BORDER_WIDTH * 2, BORDER_WIDTH * 2, BORDER_WIDTH * 2));

        roundNumberLabel = new JLabel();
        roundNumberLabel.setForeground(Color.white);

        turnNumberLabel = new JLabel();
        turnNumberLabel.setForeground(Color.white);

        movesLeftLabel = new JLabel();
        movesLeftLabel.setForeground(Color.white);

        attemptedMoveLabel = new JLabel();
        attemptedMoveLabel.setForeground(Color.white);

        gameTimerLabel = new JLabel();
        gameTimerLabel.setForeground(Color.white);

        infoBox.add(roundNumberLabel);
        infoBox.add(turnNumberLabel);
        infoBox.add(movesLeftLabel);
        infoBox.add(attemptedMoveLabel);
        infoBox.add(gameTimerLabel);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 3;

        container.add(infoBox, c);
        updateGameInfoBox();

        // Update timer every second
        Timer timer = new Timer(1000, e -> updateGameInfoBox());
        timer.setRepeats(true);
    }

    /**
     * Constructs the player box, used to display the players who are in the game and who's turn it is currently.
     *
     * @param container
     */
    private void buildPlayerBox(JPanel container) {
        playerBox = new ImagePanel(images.get("darkFelt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);
        playerBox.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));

        playerBox.setLayout(new FlowLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 0;
        c.weighty = 1;

        container.add(playerBox, c);
    }

    /**
     * Constructs the turn action box used to display the turn action buttons (suggest, skip and accuse) to the player
     *
     * @param container
     */
    private void buildTurnActionBox(JPanel container) {
        turnActionBox = new ImagePanel(images.get("felt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);
        turnActionBox.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 100));

        turnActionBox.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(BORDER_WIDTH * 2, BORDER_WIDTH  * 2, 0, BORDER_WIDTH * 2);

        finishTurnButton = new JButton("Finish Turn");
        finishTurnButton.setEnabled(false);
        finishTurnButton.addActionListener(e -> {
            if (game == null) return;
            game.nextTurn();
        });
        constraints.gridy = 0;
        turnActionBox.add(finishTurnButton, constraints);

        constraints.insets = new Insets(0, BORDER_WIDTH  * 2, 0, BORDER_WIDTH * 2);
        suggestButton = new JButton("Suggest");
        suggestButton.setEnabled(false);
        suggestButton.addActionListener(e -> {
            if (game == null) return;
            game.makeSuggestion();
        });
        constraints.gridy = 1;
        turnActionBox.add(suggestButton, constraints);

        constraints.insets = new Insets(0, BORDER_WIDTH  * 2, BORDER_WIDTH  * 2, BORDER_WIDTH * 2);
        accuseButton = new JButton("Accuse");
        accuseButton.setEnabled(false);
        accuseButton.addActionListener(e -> {
            if (game == null) return;
            int result = JOptionPane.showConfirmDialog(this, "A wrong accusation will cause you to be disqualified from the game!\nAre you sure you want to make an accusation?", "Cluedo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                game.makeAccusation();
            }
        });
        constraints.gridy = 2;
        turnActionBox.add(accuseButton, constraints);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 1;

        container.add(turnActionBox, c);
    }

    /**
     * Constructs the dice box used for displaying the dice and total number rolled by a player during thier turn.
     *
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
        c.gridy = 2;

        container.add(diceBox, c);
    }

    /**
     * Constructs the card box used for displaying the players hand.
     *
     * @param container
     */
    private void buildCardBox(JPanel container) {
        cardBox = new ImagePanel(images.get("felt"), images.get("borderTL"), images.get("borderTR"), images.get("borderBL"), images.get("borderBR"), images.get("borderTop"), images.get("borderBottom"), images.get("borderLeft"), images.get("borderRight"), BORDER_WIDTH);

        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setHgap(CARD_GAP);
        cardBox.setBorder(new EmptyBorder(CARD_PADDING_TOP, CARD_GAP, 0, 0));
        cardBox.setLayout(layout);

        JScrollPane cardScollBox = new JScrollPane(cardBox, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
     *
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

        boardBox = new JPanel() {
            @Override
            public void paint(Graphics g) {
                boardPaint(g);
            }
        };
        boardBox.setDoubleBuffered(true);
        boardBox.addMouseMotionListener(this);
        boardBox.addMouseListener(this);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridy = 0;
        boardContainer.add(boardBox, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 3;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        container.add(boardContainer, c);
    }

    //endregion

    //region UTILITY METHODS

    /**
     * Constructs a new game, removing the observer links of any old games to ensure there is not multiple game
     * objects sending updates.
     */
    private void newGame() {
        gameStart = Instant.now();
        if (game != null)
            game.deleteObserver(this);
        game = new CluedoGame();
        game.addObserver(this);
        game.startGame();
    }

    /**
     * Gets a cell at the specified graphical position on the boardBox
     */
    private Cell getCellAtPos(int x, int y) {
        if (board == null) return null;

        int width = boardBox.getWidth();
        int height = boardBox.getHeight();

        int cellSize;

        // base the size on the smallest dimension
        if (width < height) { // if width is smaller than height, we must base the size on the width
            cellSize = width / board.BOARD_WIDTH;
        } else { // base the size on the height
            cellSize = height / board.BOARD_HEIGHT;
        }

        int leftOffset = (width / 2) - (cellSize * board.BOARD_WIDTH / 2); // Center horizontally
        int topOffset = (height / 2) - (cellSize * board.BOARD_HEIGHT / 2); // Center vertically

        x -= leftOffset;
        y -= topOffset;

        Position pos = new Position(x / cellSize, y / cellSize);

        return board.getCell(pos);
    }

    /**
     * Renders the board, players, and tooltips in the board box on screen
     *
     * @param g Graphics object passed from the paint event of the board
     */
    private void boardPaint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font(g2D.getFont().toString(), 0, DEFAULT_FONT_SIZE));
        // Set rendering settings
        RenderingHints hints = new RenderingHints(new HashMap<RenderingHints.Key, Object>() {{
            put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }});
        g2D.setRenderingHints(hints);

        int width = boardBox.getWidth();
        int height = boardBox.getHeight();

        Image backgroundImage = images.get("darkFelt");

        // Draw the background image tiled
        for (int x = 0; x < width; x += backgroundImage.getWidth(null)) {
            for (int y = 0; y < height; y += backgroundImage.getHeight(null)) {
                g.drawImage(backgroundImage, x, y, null, null);
            }
        }

        if (board == null) return; // Dont draw the board if its null

        int cellSize;

        // base the size on the smallest dimension
        if (width < height) { // if width is smaller than height, we must base the size on the width
            cellSize = width / board.BOARD_WIDTH;
        } else { // base the size on the height
            cellSize = height / board.BOARD_HEIGHT;
        }

        int leftOffset = (width / 2) - (cellSize * board.BOARD_WIDTH / 2); // Center horizontally
        int topOffset = (height / 2) - (cellSize * board.BOARD_HEIGHT / 2); // Center vertically

        Set<Cell> occupiedCells = new HashSet<>();

        for (int r = 0; r < board.BOARD_HEIGHT; r++) {
            for (int c = 0; c < board.BOARD_WIDTH; c++) {

                Position pos = new Position(c, r);
                Cell cell = board.getCell(pos);

                if (cell == null) continue; // Skip empty cells

                int cellX = leftOffset + pos.x * cellSize;
                int cellY = topOffset + pos.y * cellSize;

                g2D.setStroke(new BasicStroke(1));

                // Draw cell
                g2D.setColor(roomColors.get(cell.getRoom().getName()));
                g2D.fillRect(cellX, cellY, cellSize, cellSize);

                // Draw border
                g2D.setComposite(AlphaComposite.SrcOver.derive(CELL_BORDER_OPACITY));
                g2D.setColor(Color.black);
                g2D.drawRect(cellX, cellY, cellSize, cellSize);
                g2D.setComposite(AlphaComposite.SrcOver);

                if (cell.isOccupied()) {
                    occupiedCells.add(cell);
                }

                // Draw the borders between walls
                g2D.setColor(Color.black);
                g2D.setStroke(new BasicStroke(2));
                Cell.Direction.getStream().forEach(d -> {
                    Cell neighbour = cell.getNeighbour(d);
                    if (neighbour == null) {
                        switch (d) {
                            case EAST:
                                g2D.drawLine(cellX + cellSize, cellY, cellX + cellSize, cellY + cellSize);
                                break;
                            case WEST:
                                g2D.drawLine(cellX, cellY, cellX, cellY + cellSize);
                                break;
                            case NORTH:
                                g2D.drawLine(cellX, cellY, cellX + cellSize, cellY);
                                break;
                            case SOUTH:
                                g2D.drawLine(cellX, cellY + cellSize, cellX + cellSize, cellY + cellSize);
                                break;
                        }
                    }
                });
            }
        }

        // Draw the valid move cells
        if (!validMoveCells.isEmpty()) {
            List<Cell> validCells = new ArrayList<>(validMoveCells);

            // Add current player pos to cells to draw
            if (currentPlayer != null) {
                Cell c = currentPlayer.getCharacter().getLocation();
                validCells.add(c);
            }

            // Draw cells that are valid places to move as green
            for (Cell c : validCells) {
                g2D.setColor(roomColors.get(c.getRoom().getName()).brighter());
                g2D.fillRect(leftOffset + c.position.x * cellSize, topOffset + c.position.y * cellSize, cellSize, cellSize);

                g2D.setStroke(new BasicStroke(2));
                g2D.setColor(Color.decode("#49de23"));
                g2D.drawRect(leftOffset + c.position.x * cellSize, topOffset + c.position.y * cellSize, cellSize, cellSize);
            }
        } else if (isSelectedVisited && selectedCell != null) {
            // Draw current cell as visited
            g2D.setColor(roomColors.get(selectedCell.getRoom().getName()).darker());
            g2D.fillRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);

            g2D.setStroke(new BasicStroke(2));
            g2D.setColor(Color.decode("#fca40a"));
            g2D.drawRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);
        } else if (selectedCell != null) {
            // Draw current cell as invalid
            g2D.setColor(roomColors.get(selectedCell.getRoom().getName()).darker());
            g2D.fillRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);

            g2D.setStroke(new BasicStroke(2));
            g2D.setColor(Color.decode("#de3923"));
            g2D.drawRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);
        }

        g2D.setFont(new Font(g2D.getFont().toString(), 0, ROOM_LABEL_FONT_SIZE));
        g2D.setColor(Color.black);
        for (Map.Entry<Point2D, String> roomLabel : roomLabels.entrySet()) {
            int textWidth = g2D.getFontMetrics().stringWidth(roomLabel.getValue());
            int textHeight = g2D.getFontMetrics().getHeight();

            double cellX = leftOffset + roomLabel.getKey().getX() * cellSize;
            double cellY = topOffset + roomLabel.getKey().getY() * cellSize;

            double textPosX = cellX - textWidth / 2.0;
            double textPosY = cellY + textHeight / 3.0;

            g2D.drawString(roomLabel.getValue(), (int) textPosX, (int) textPosY);
        }
        g2D.setFont(new Font(g2D.getFont().toString(), 0, DEFAULT_FONT_SIZE));

        // Draw players last to ensure player tokens are always on top (Except for tooltips)
        for (Cell c : occupiedCells) {
            int characterSize = (int) (cellSize * 0.8);
            int cellX = leftOffset + c.position.x * cellSize;
            int cellY = topOffset + c.position.y * cellSize;
            int circleX = cellX + (cellSize - characterSize) / 2;
            int circleY = cellY + (cellSize - characterSize) / 2;

            Color characterColor = c.getOccupant().getColor();
            // Draw character circle
            g2D.setColor(characterColor);
            g2D.fillOval(circleX, circleY, characterSize, characterSize);
            // Draw black border
            g2D.setStroke(new BasicStroke(2));
            g2D.setColor(Color.black);
            g2D.drawOval(circleX, circleY, characterSize, characterSize);
        }

        // Draw tooltips
        if (toolTipText != null) {
            g2D.setComposite(AlphaComposite.SrcOver.derive(0.7f));
            g2D.setColor(Color.black);

            int stringWidth = g2D.getFontMetrics().stringWidth(toolTipText); // Measure the string
            int boxWidth = stringWidth + 20;
            int boxHeight = g2D.getFontMetrics().getHeight() + 5;

            int xPos = cursorX - boxWidth / 2;
            int yPos = cursorY - boxHeight - 10;

            if (xPos < 0) // Prevents tooltip going off left edge of screen
                xPos -= xPos;
            if (xPos > width - boxWidth) // Prevents tooltip going off right edge of screen
                xPos = width - boxWidth;
            if (yPos < 0) // Prevents tooltip going off top of screen
                yPos = cursorY + 20;

            // Draw the box
            g2D.fillRect(xPos, yPos, boxWidth, boxHeight);

            // Draw the text
            g2D.setColor(Color.white);
            g2D.drawString(toolTipText, xPos + 10, yPos + 15);

            g2D.setComposite(AlphaComposite.SrcOver);
        }
    }

    /**
     * Uses path finding algorithm to find a route from the player to the specified cell
     */
    private void findPlayerRoute() {
        if (isPlayerMoving) return;

        isSelectedVisited = false;
        validMoveCells.clear();
        validMoveDirections.clear();

        if (selectedCell == null || currentPlayer == null || movesLeft == 0) return;

        List<Pair<Cell, Cell.Direction>> bestRoute = null;
        PriorityQueue<List<Pair<Cell, Cell.Direction>>> routes = new PriorityQueue<>(Comparator.comparingInt(List::size));

        Cell start = currentPlayer.getCharacter().getLocation();
        Cell end = selectedCell;

        if (start == end || end.isOccupied())
            return;

        //cant revisit cells or re enter rooms in one turn.
        if (visitedCells.contains(selectedCell) || (visitedRooms.contains(selectedCell.getRoom()) && !selectedCell.getRoom().equals(start.getRoom()))) {
            isSelectedVisited = true;
            return;
        }

        //add the initial route (starting cell)
        List<Pair<Cell, Cell.Direction>> initialRoute = new ArrayList<Pair<Cell, Cell.Direction>>() {{
            add(new Pair<>(start, null));
        }};
        routes.offer(initialRoute);

        while (!routes.isEmpty()) {
            List<Pair<Cell, Cell.Direction>> route = routes.poll(); // Get lowest cost route
            Cell tailCell = route.get(route.size() - 1).getKey(); // Get the cell at the end of the route

            for (Map.Entry<Cell.Direction, Cell> neighbour : tailCell.getNeighbours().entrySet()) {
                Cell.Direction direction = neighbour.getKey();
                Cell nextCell = neighbour.getValue();

                if (nextCell.equals(end) && (bestRoute == null || bestRoute.size() > route.size())) { // is a better route
                    route.add(new Pair<>(nextCell, direction));
                    bestRoute = route;
                } else if (!nextCell.isOccupied() && route.stream().noneMatch(x -> x.getKey().equals(nextCell)) && !visitedCells.contains(nextCell)) {
                    List<Pair<Cell, Cell.Direction>> newRoute = new ArrayList<>(route);
                    newRoute.add(new Pair<>(nextCell, direction));

                    if (newRoute.size() <= movesLeft)
                        routes.offer(newRoute);
                }
            }
        }
        if (bestRoute != null) {
            bestRoute.remove(0); // Remove start position from the route
            for (Pair<Cell, Cell.Direction> move : bestRoute) {
                validMoveCells.add(move.getKey());
                validMoveDirections.add(move.getValue());
            }
        }
    }

    /**
     * Uses the list of valid moves to move the player on the board
     */
    private void executeMoves() {
        if (isSelectedVisited) { //cant revist cells
            updateMessage(new MessageUpdate("You have already been there!"));
        }

        if (isPlayerMoving || validMoveCells.isEmpty() || currentPlayer == null || board == null) return;
        isPlayerMoving = true;

        // Timer to animate movement
        final Timer t = new Timer(100, e -> {
            // Get where to move
            Cell.Direction d = validMoveDirections.get(0);
            validMoveDirections.remove(0);

            // Move player
            game.moveCurrentPlayer(d);
            Cell playerCell = currentPlayer.getCharacter().getLocation();
            visitedCells.add(playerCell);
            if (!playerCell.getRoom().getName().equals("Hallway"))
                visitedRooms.add(playerCell.getRoom());

            validMoveCells.remove(playerCell); // Remove the cells as they are moved over

            if (validMoveDirections.isEmpty()) {
                ((Timer) e.getSource()).stop();
                isPlayerMoving = false;
                // Find new route from landing position
                findPlayerRoute();
            }

            boardBox.repaint();
        });
        t.setRepeats(true);
        t.start();
    }

    //endregion

    //region REQUESTS

    /**
     * Creates a new request from a player request checking if it is a count request or a
     * begin turn request processing the request accordingly.
     *
     * @param request
     */
    private void request(PlayerRequest request) {
        if (request instanceof PlayerSetupRequest) {
            PlayerSetupRequest playerSetupRequest = (PlayerSetupRequest) request;
            Player result = askPlayerInfo(playerSetupRequest);
            playerSetupRequest.setResponse(result); // Return the result to the game

        } else if (request instanceof PlayerBeginTurnRequest) {
            PlayerBeginTurnRequest playerBeginTurnRequest = (PlayerBeginTurnRequest) request;
            askPlayerBeginTurn(playerBeginTurnRequest);

        } else if (request instanceof PlayerCountRequest) {
            PlayerCountRequest playerCountRequest = (PlayerCountRequest) request;
            int count = askPlayerCount();
            playerCountRequest.setResponse(count);

        } else if (request instanceof PlayerAccusationRequest) {
            PlayerAccusationRequest playerAccusationRequest = (PlayerAccusationRequest) request;
            Suggestion accusation = askPlayerAccusation(playerAccusationRequest);
            playerAccusationRequest.setResponse(accusation);

        } else if (request instanceof PlayerSuggestionRequest) {
            PlayerSuggestionRequest playerSuggestionRequest = (PlayerSuggestionRequest) request;
            Suggestion suggestion = askPlayerSuggestion(playerSuggestionRequest);
            playerSuggestionRequest.setResponse(suggestion);
        } else if (request instanceof PlayerRefutationRequest) {
            PlayerRefutationRequest playerRefutationRequest = (PlayerRefutationRequest) request;
            Card refutation = askPlayerRefutations(playerRefutationRequest);
            playerRefutationRequest.setResponse(refutation);
        }
    }

    /**
     * Uses a PlayerSetupWindow to ask the player for their name and what character they would like
     */
    private Player askPlayerInfo(PlayerSetupRequest request) {
        PlayerSetupWindow window = new PlayerSetupWindow(request.characters, request.chosenCharacters, request.chosenNames, this);
        Player player = window.player;
        window.setVisible(false);
        window.dispose();
        return player;
    }

    /**
     * Uses JOptionPane to ask the user how many players are playing
     */
    private int askPlayerCount() {
        while (true) {
            try {
                int response = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players are playing? (3-6)", "Cluedo", JOptionPane.INFORMATION_MESSAGE));

                if (response > 6)
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
     *
     * @param request
     */
    private void askPlayerBeginTurn(PlayerBeginTurnRequest request) {
        JOptionPane.showMessageDialog(this, request.player.getPlayerName() + " you're up!\nPress ok when you're ready to start.", "Cluedo", JOptionPane.INFORMATION_MESSAGE);
        request.setResponse(null);
    }

    /**
     * Asks the player for accusation cards and generates a suggestion object
     */
    private Suggestion askPlayerAccusation(PlayerAccusationRequest request) {
        SuggestionWindow window = new SuggestionWindow(request.characters, request.rooms, request.weapons, request.player, this);
        Suggestion accusation = window.getSuggestion();
        window.setVisible(false);
        window.dispose();
        return accusation;
    }

    /**
     * Asks the player for suggestion cards and generates a suggestion object
     */
    private Suggestion askPlayerSuggestion(PlayerSuggestionRequest request) {
        SuggestionWindow window = new SuggestionWindow(request.characters, request.rooms, request.weapons, request.player, request.suggestedRoom, this);
        Suggestion suggestion = window.getSuggestion();
        window.setVisible(false);
        window.dispose();
        return suggestion;
    }

    /**
     * Asks players for a refutation card, stops when any player presents a valid card
     *
     * @return Refutation card or null if no players have a valid card
     */
    private Card askPlayerRefutations(PlayerRefutationRequest request) {
        Card card = null;
        Player refuter = null;

        List<Player> orderedPlayers = new ArrayList<>(); // List of players starting one position from the current player
        int playerIndex = request.playerList.indexOf(request.player);

        // Orders the players starting from and excluding current player
        for(int i = 0; i < request.playerList.size() - 1; ++i) {
            orderedPlayers.add(request.playerList.get((playerIndex + i + 1) % request.playerList.size()));
        }

        JOptionPane.showMessageDialog(this, request.player.getPlayerName() + " has made a suggestion, refutations will now begin starting with " + orderedPlayers.get(0).getPlayerName(), "Cluedo", JOptionPane.INFORMATION_MESSAGE);

        for(Player p : orderedPlayers) {
            JOptionPane.showMessageDialog(this, p.getPlayerName() + " continue when you are ready to make your refutation.", "Cluedo", JOptionPane.INFORMATION_MESSAGE);

            updateHand(new HandUpdate(p.getHand())); // Show the player's hand

            RefutationWindow window = new RefutationWindow(p, request.suggestion, this);
            Card refutation = window.refutation;
            window.setVisible(false);
            window.dispose();

            updateHand(new HandUpdate(new ArrayList<>())); // Hide the player's hand

            // Finish refutations
            if(refutation != null) {
                card = refutation;
                refuter = p;
                break;
            }
        }

        JOptionPane.showMessageDialog(this, "Refutations are finished.\n" + request.player.getPlayerName() + " continue when you are ready to see the result of the refutations.", "Cluedo", JOptionPane.INFORMATION_MESSAGE);
        if(card == null)
            JOptionPane.showMessageDialog(this, "Your suggestion was not refuted.\nYou may now make an accusation or finish your turn.", "Cluedo", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, "Your suggestion was refuted by " + refuter.getPlayerName() + " using the " + card.getName() + " card.\nYou may now make an accusation or finish your turn.", "Cluedo", JOptionPane.INFORMATION_MESSAGE);

        return card;
    }

    //endregion

    //region UPDATES

    /**
     * Changes the dice to reflect the dice rolled in the game
     *
     * @param update Holds the dice values from the roll
     */
    private void updateDice(DiceUpdate update) {
        // "rolls" the dice for a bit
        final Random rng = new Random();
        final Timer timer = new Timer(100, e -> {
            diceBox.repaint();
            die1.setBackgroundImage(images.get("die" + (rng.nextInt(6) + 1)));
            die1.repaint();
            die2.setBackgroundImage(images.get("die" + (rng.nextInt(6) + 1)));
            die2.repaint();
        });
        timer.setRepeats(true);
        timer.start();

        // Stops the dice rolling and sets the value
        Timer stopTimer = new Timer(1000, e -> {
            timer.stop();
            diceBox.repaint();
            die1.setBackgroundImage(images.get("die" + update.FirstDie));
            die1.repaint();
            die2.setBackgroundImage(images.get("die" + update.SecondDie));
            die2.repaint();
            updateMessage(new MessageUpdate(currentPlayer.getPlayerName() + " you rolled a " + movesLeft + "!"));
        });
        stopTimer.setRepeats(false);
        stopTimer.start();

        //updateMessage(new MessageUpdate(currentPlayer.getPlayerName() +" you have "+ movesLeft +" moves left!"));
    }

    /**
     * Redisplays the cardBox.
     *
     * @param update
     */
    private void updateHand(HandUpdate update) {
        cardBox.removeAll();
        cardBox.repaint();

        int delay = 100;
        for (Card c : update.hand) {
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

    /**
     * Updates the list of players in the sidebar
     *
     * @param update
     */
    private void updatePlayers(PlayersUpdate update) {
        playerBox.removeAll();
        playerBox.repaint();

        playerCount = update.players.size();
        for (Player p : update.players) {
            JPanel playerPanel = new JPanel();
            playerPanel.setOpaque(false);
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
            playerPanel.setPreferredSize(new Dimension(SIDEBAR_WIDTH - (BORDER_WIDTH * 2), PLAYER_HEIGHT));
            playerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            playerPanel.add(Box.createRigidArea(new Dimension(PLAYER_PADDING, PLAYER_HEIGHT))); // Add some padding

            // The colored square
            JLabel colorLabel = new JLabel();
            colorLabel.setOpaque(true);
            colorLabel.setBackground(p.getCharacter().getColor());
            colorLabel.setPreferredSize(new Dimension(PLAYER_COLOR_SIZE, PLAYER_COLOR_SIZE));
            colorLabel.setMinimumSize(new Dimension(PLAYER_COLOR_SIZE, PLAYER_COLOR_SIZE));
            colorLabel.setMaximumSize(new Dimension(PLAYER_COLOR_SIZE, PLAYER_COLOR_SIZE));
            playerPanel.add(colorLabel);

            playerPanel.add(Box.createRigidArea(new Dimension(PLAYER_PADDING, PLAYER_HEIGHT))); // Add some padding

            // Add player's name
            JLabel playerLabel = new JLabel(p.getPlayerName());
            if (p.getHasAcused())
                playerLabel.setForeground(Color.gray);
            else
                playerLabel.setForeground(Color.white);
            playerLabel.setOpaque(false);
            playerPanel.add(playerLabel);

            playerBox.add(playerPanel);
        }
        playerBox.revalidate();
    }

    /**
     * Updates the small message displayed to the user under the board box
     */
    private void updateMessage(MessageUpdate update) {
        messageBox.setText(update.message);
    }

    /**
     * Updates the board object in the GUI, repainting the gui to display the new board.
     *
     * @param update object containing the new board.
     */
    private void updateBoard(BoardUpdate update) {
        board = update.board;
        validMoveCells.clear();
        validMoveDirections.clear();
        selectedCell = null;
        boardBox.repaint();
    }

    /**
     * Updates the player turn, updating the current player and incrementing the round number
     * Clears all routes and visited areas ands updates current player pos into visited sets
     * and updates and repaints the relevant panels.
     *
     * @param update
     */
    private void updatePlayerTurn(PlayerTurnUpdate update) {
        currentPlayer = update.player;
        roundNumber = update.round;

        // Clear routes and visited areas
        visitedCells.clear();
        visitedRooms.clear();
        validMoveCells.clear();
        validMoveDirections.clear();

        // Add current player pos to visited sets
        if (currentPlayer != null) {
            visitedCells.add(currentPlayer.getCharacter().getLocation());

            if (!currentPlayer.getCharacter().getLocation().getRoom().getName().equals("Hallway")) {
                visitedRooms.add(currentPlayer.getCharacter().getLocation().getRoom());
            }
        }
        // Render updates
        updateGameInfoBox();
        boardBox.repaint();
    }

    /**
     * Updates the moves left field and the game info box.
     *
     * @param update
     */
    private void updateMovesLeft(MovesLeftUpdate update) {
        movesLeft = update.movesLeft;
        updateGameInfoBox();
        updateMessage(new MessageUpdate(currentPlayer.getPlayerName() + " you have " + movesLeft + " moves left!"));
    }

    /**
     * Updates the enabled state of the action buttons depending on the actions that are allowed
     *
     * @param update
     */
    private void updateAllowedActions(AllowedActionsUpdate update) {
        finishTurnButton.setEnabled(update.allowFinishTurn);
        suggestButton.setEnabled(update.allowSuggestion);
        accuseButton.setEnabled(update.allowAccusation);

    }

    /**
     * Shows a message telling the user that their accusation was wrong and shows them the solution
     *
     * @param update
     */
    private void showWrongAccusationMessage(WrongAccusationUpdate update) {
        JOptionPane.showMessageDialog(this, "Your accusation was incorrect! You are now disqualified from the game!\nThe correct solution cards were:\n\nCharacter: " + update.solution.getCharacter().getName() + "\nRoom: " + update.solution.getRoom().getName() + "\nWeapon: " + update.solution.getWeapon().getName(), "Cluedo", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show game won message and ask if the players would like to play a new game
     */
    private void showGameWonMessage(GameWonUpdate update) {
        if (!update.winByDefault)
            JOptionPane.showMessageDialog(this, "Your accusation was correct! " + update.winner.getPlayerName() + " you are the winner!\nThe correct solution cards were:\n\nCharacter: " + update.solution.getCharacter().getName() + "\nRoom: " + update.solution.getRoom().getName() + "\nWeapon: " + update.solution.getWeapon().getName(), "Cluedo", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, update.winner.getPlayerName() + " you are the winner by default!\nThe correct solution cards were:\n\nCharacter: " + update.solution.getCharacter().getName() + "\nRoom: " + update.solution.getRoom().getName() + "\nWeapon: " + update.solution.getWeapon().getName(), "Cluedo", JOptionPane.INFORMATION_MESSAGE);

        int result = JOptionPane.showConfirmDialog(this, "Would you like to start a new game?", "Cluedo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            newGame();
        } else {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    /**
     * Updates the game info box; round number, moves left and next moves
     */
    private void updateGameInfoBox() {
        if(playerCount == 0)
            roundNumberLabel.setText("Round: 1");
        else
            roundNumberLabel.setText("Round: " + (roundNumber / playerCount + 1));

        turnNumberLabel.setText("Turn: " + (roundNumber + 1));
        movesLeftLabel.setText("Moves Left: " + movesLeft);
        attemptedMoveLabel.setText("Next Moves: " + attemptedMoveCount);

//        String gameTimeString = "";
//        if(gameStart != null) {
//            Duration d = Duration.between(Instant.now(), gameStart);
//
//            gameTimeString = d.toHours() + ":" + d.toMinutes() + ":" + d.toMillis() / 1000;
//        }
//        gameTimerLabel.setText("Game Time: " + gameTimeString);
    }

    /**
     * Updates the tool tip text that is displayed when a cell or player is hovered by the mouse.
     */
    private void updateToolTip() {
        if (board == null || selectedCell == null) {
            toolTipText = null;
            return;
        }

        if (selectedCell.isOccupied()) {
            toolTipText = "Character: " + selectedCell.getOccupant().getName();
        } else if (isSelectedVisited) {
            toolTipText = "Already visited!";
        } else {
            toolTipText = null;
        }
    }

    //endregion

    //region IMPLEMENT METHODS

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Updates the ovserver based on the object arguments, calls relevant update method for
     * the arguments.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (arg instanceof PlayerRequest)
                request((PlayerRequest) arg);
            else if (arg instanceof DiceUpdate)
                updateDice((DiceUpdate) arg);
            else if (arg instanceof HandUpdate)
                updateHand((HandUpdate) arg);
            else if (arg instanceof MessageUpdate)
                updateMessage((MessageUpdate) arg);
            else if (arg instanceof BoardUpdate)
                updateBoard((BoardUpdate) arg);
            else if (arg instanceof PlayersUpdate)
                updatePlayers((PlayersUpdate) arg);
            else if (arg instanceof PlayerTurnUpdate)
                updatePlayerTurn((PlayerTurnUpdate) arg);
            else if (arg instanceof MovesLeftUpdate)
                updateMovesLeft((MovesLeftUpdate) arg);
            else if (arg instanceof AllowedActionsUpdate)
                updateAllowedActions((AllowedActionsUpdate) arg);
            else if (arg instanceof WrongAccusationUpdate)
                showWrongAccusationMessage((WrongAccusationUpdate) arg);
            else if (arg instanceof GameWonUpdate)
                showGameWonMessage((GameWonUpdate) arg);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Handles highlighting path and its avalibility based on where the mouse is.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        cursorX = x;
        cursorY = y;

        if (board == null) return;

        updateToolTip();

        Cell cell = getCellAtPos(x, y);

        if (cell != null && !cell.equals(selectedCell)) {
            selectedCell = cell;
            findPlayerRoute();
            attemptedMoveCount = validMoveCells.size();
            updateGameInfoBox();

        } else if (cell == null) {
            selectedCell = null;
        }

        boardBox.repaint(); // Repaint to draw tool tips and board changes
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentPlayer == null || board == null) return;

        selectedCell = getCellAtPos(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentPlayer == null || board == null) return;

        // Execute moves if the mouse is released in the same cell it was pressed in
        Cell currentCell = getCellAtPos(e.getX(), e.getY());
        if (currentCell != null && currentCell.equals(selectedCell))
            executeMoves();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //endregion

}

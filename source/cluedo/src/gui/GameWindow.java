package gui;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.org.apache.xpath.internal.operations.Bool;
import game.CluedoGame;
import game.Player;
import game.board.Board;
import game.board.Cell;
import game.board.Position;
import game.cards.Card;
import game.cards.Room;
import gui.Update.*;
import gui.request.PlayerBeginTurnRequest;
import gui.request.PlayerCountRequest;
import gui.request.PlayerSetupRequest;
import gui.request.PlayerRequest;
import javafx.util.Pair;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
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
public class GameWindow extends JFrame implements Observer, ActionListener, MouseMotionListener, MouseListener {
	
			//GameWindow attributes
            public static final String WINDOW_TITLE = "Cluedo";
            public static final int
                    WINDOW_MIN_WIDTH = 800,
                    WINDOW_MIN_HEIGHT = 800,

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

    private JLabel messageBox, roundNumberLabel, movesLeftLabel, gameTimerLabel, attemptedMoveLabel;
    private ImagePanel cardBox, diceBox, die1, die2, playerBox, infoBox;
    private JPanel boardBox;
    private JScrollPane cardScollBox;
    private String toolTipText = null;
    
    //Game window associations
    private CluedoGame game = null;
    private Board board;
    private Player currentPlayer;
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
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setBorder(new EmptyBorder(BORDER_WIDTH * 2, BORDER_WIDTH * 2, BORDER_WIDTH * 2, BORDER_WIDTH * 2));

        roundNumberLabel = new JLabel();
        roundNumberLabel.setForeground(Color.white);

        movesLeftLabel = new JLabel();
        movesLeftLabel.setForeground(Color.white);

        attemptedMoveLabel = new JLabel();
        attemptedMoveLabel.setForeground(Color.white);

        gameTimerLabel = new JLabel();
        gameTimerLabel.setForeground(Color.white);

        infoBox.add(roundNumberLabel);
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
        Timer timer = new Timer(1000, e -> {
            updateGameInfoBox();
        });
        timer.setRepeats(true);
    }
    
    /**
     * Constructs the player box, used to display the players who are in the game and who's turn it is currently.
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
        gameStart = Instant.now();
        if(game != null)
            game.deleteObserver(this);
        game = new CluedoGame();
        game.addObserver(this);
        game.startGame();
    }

    /**
     * Gets a cell at the specified graphical position on the boardBox
     */
    private Cell getCellAtPos(int x, int y) {
        if(board == null) return null;

        int width = boardBox.getWidth();
        int height = boardBox.getHeight();

        int cellSize;

        // base the size on the smallest dimension
        if(width < height) { // if width is smaller than height, we must base the size on the width
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

    private void boardPaint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
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

        if(board == null) return; // Dont draw the board if its null

        int cellSize;

        // base the size on the smallest dimension
        if(width < height) { // if width is smaller than height, we must base the size on the width
            cellSize = width / board.BOARD_WIDTH;
        } else { // base the size on the height
            cellSize = height / board.BOARD_HEIGHT;
        }

        int leftOffset = (width / 2) - (cellSize * board.BOARD_WIDTH / 2); // Center horizontally
        int topOffset = (height / 2) - (cellSize * board.BOARD_HEIGHT / 2); // Center vertically

        Set<Cell> occupiedCells = new HashSet<>();

        for(int r=0; r<board.BOARD_HEIGHT; r++) {
            for(int c=0; c<board.BOARD_WIDTH; c++) {

                Position pos = new Position(c, r);
                Cell cell = board.getCell(pos);

                if(cell == null) continue; // Skip empty cells

                int cellX = leftOffset + pos.x*cellSize;
                int cellY = topOffset + pos.y*cellSize;

                g2D.setStroke(new BasicStroke(1));

                // Draw cell
                g2D.setColor(roomColors.get(cell.getRoom().getName()));
                g2D.fillRect(cellX, cellY, cellSize, cellSize);

                // Draw border
                g2D.setColor(Color.black);
                g2D.drawRect(cellX, cellY, cellSize, cellSize);

                if(cell.isOccupied()) {
                    occupiedCells.add(cell);
                }
            }
        }

        // Draw the valid move cells
        if(!validMoveCells.isEmpty()) {
            List<Cell> validCells = new ArrayList<>(validMoveCells);

            // Add current player pos to cells to draw
            if(currentPlayer != null) {
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
        } else if(isSelectedVisited && selectedCell != null) {
            // Draw current cell as visited
            g2D.setColor(roomColors.get(selectedCell.getRoom().getName()).darker());
            g2D.fillRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);

            g2D.setStroke(new BasicStroke(2));
            g2D.setColor(Color.decode("#fca40a"));
            g2D.drawRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);
        } else if(selectedCell != null) {
            // Draw current cell as invalid
            g2D.setColor(roomColors.get(selectedCell.getRoom().getName()).darker());
            g2D.fillRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);

            g2D.setStroke(new BasicStroke(2));
            g2D.setColor(Color.decode("#de3923"));
            g2D.drawRect(leftOffset + selectedCell.position.x * cellSize, topOffset + selectedCell.position.y * cellSize, cellSize, cellSize);
        }

        // Draw players last to ensure player tokens are always on top (Except for tooltips)
        for(Cell c : occupiedCells) {
            int characterSize = (int)(cellSize * 0.8);
            int cellX = leftOffset + c.position.x*cellSize;
            int cellY = topOffset + c.position.y*cellSize;
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

        if(toolTipText != null) {
            g2D.setComposite(AlphaComposite.SrcOver.derive(0.8f));
            g2D.setColor(Color.black);

            int stringWidth = g2D.getFontMetrics().stringWidth(toolTipText); // Measure the string
            int boxWidth = stringWidth + 20;
            int boxHeight = g2D.getFontMetrics().getHeight() + 5;

            int xPos = cursorX - boxWidth / 2;
            int yPos = cursorY - boxHeight - 10;

            if(xPos < 0)
                xPos -= xPos;
            if(yPos < 0)
                yPos = cursorY + 20;

            g2D.fillRect(xPos, yPos, boxWidth, boxHeight);

            g2D.setColor(Color.white);
            g2D.drawString(toolTipText, xPos + 10, yPos + 15);

            g2D.setComposite(AlphaComposite.SrcOver);
        }
    }

    /**
     * Uses path finding algorithm to find a route from the player to the specified cell
     */
    private void findPlayerRoute() {
        if(isPlayerMoving) return;

        isSelectedVisited = false;
        validMoveCells.clear();
        validMoveDirections.clear();

        if(selectedCell == null || currentPlayer == null || movesLeft == 0) return;

        List<Pair<Cell, Cell.Direction>> bestRoute = null;
        PriorityQueue<List<Pair<Cell, Cell.Direction>>> routes = new PriorityQueue<>(Comparator.comparingInt(List::size));

        Cell start = currentPlayer.getCharacter().getLocation();
        Cell end = selectedCell;

        if(start == end || end.isOccupied())
            return;

        if(visitedCells.contains(selectedCell) || visitedRooms.contains(selectedCell.getRoom())) {
            isSelectedVisited = true;
            return;
        }

        List<Pair<Cell, Cell.Direction>> initialRoute = new ArrayList<Pair<Cell, Cell.Direction>>() {{ add(new Pair<>(start, null)); }};
        routes.offer(initialRoute);

        while (!routes.isEmpty()) {
            List<Pair<Cell, Cell.Direction>> route = routes.poll(); // Get lowest cost route
            Cell tailCell = route.get(route.size() - 1).getKey(); // Get the cell at the end of the route

            for(Map.Entry<Cell.Direction, Cell> neighbour : tailCell.getNeighbours().entrySet()) {
                Cell.Direction direction = neighbour.getKey();
                Cell nextCell = neighbour.getValue();

                if(nextCell.equals(end) && (bestRoute == null || bestRoute.size() > route.size())) {
                    route.add(new Pair<>(nextCell, direction));
                    bestRoute = route;
                } else if(!nextCell.isOccupied() && route.stream().noneMatch(x -> x.getKey().equals(nextCell))){
                    List<Pair<Cell, Cell.Direction>> newRoute = new ArrayList<>(route);
                    newRoute.add(new Pair<>(nextCell, direction));

                    if(newRoute.size() <= movesLeft)
                        routes.offer(newRoute);
                }
            }
        }

        if(bestRoute != null) {
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
        if(isSelectedVisited) {
            updateMessage(new MessageUpdate("You have already been there!"));
        }

        if(isPlayerMoving || validMoveCells.isEmpty() || currentPlayer == null || board == null) return;
        isPlayerMoving = true;

        // Timer to animate movement
        final Timer t = new Timer(100, e -> {
            if(validMoveDirections.isEmpty()) {
                ((Timer)e.getSource()).stop();
                isPlayerMoving = false;
                return;
            }

            // Get where to move
            Cell.Direction d = validMoveDirections.get(0);
            validMoveDirections.remove(0);

            game.moveCurrentPlayer(d);
            Cell playerCell = currentPlayer.getCharacter().getLocation();
            visitedCells.add(playerCell);
            if(!playerCell.getRoom().getName().equals("Hallway"))
                visitedRooms.add(playerCell.getRoom());

            validMoveCells.remove(playerCell); // Remove the cells as they are moved over
            boardBox.repaint();
        });
        t.setRepeats(true);
        t.start();
    }

    private void updateGameInfoBox() {
        roundNumberLabel.setText("Round: " + (roundNumber + 1));
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

    private void updateToolTip() {
        if(board == null || selectedCell == null) {
            toolTipText = null;
            return;
        }

        if(selectedCell.isOccupied()) {
            toolTipText = "Character: " + selectedCell.getOccupant().getName();
        } else if(isSelectedVisited) {
            toolTipText = "Already visited!";
        } else {
            toolTipText = null;
        }
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
        PlayerSetupWindow window = new PlayerSetupWindow(request.characters, request.chosenCharacters, request.chosenNames,this);
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
            die1.setBackgroundImage(images.get("die" + (rng.nextInt(6) + 1)));
            die1.repaint();
            die2.setBackgroundImage(images.get("die" + (rng.nextInt(6) + 1)));
            die2.repaint();
        });
        timer.setRepeats(true);
        timer.start();

        // Stops the dice rolling and sets the value
        Timer stopTimer = new Timer(1500, e -> {
            timer.stop();
            diceBox.repaint();
            die1.setBackgroundImage(images.get("die" + update.FirstDie));
            die1.repaint();
            die2.setBackgroundImage(images.get("die" + update.SecondDie));
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

    /**
     * Updates the list of players in the sidebar
     * @param update
     */
    private void updatePlayers(PlayersUpdate update) {
        playerBox.removeAll();
        playerBox.repaint();

        for(Player p : update.players) {
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
            playerLabel.setForeground(Color.white);
            playerLabel.setOpaque(false);
            playerPanel.add(playerLabel);

            playerBox.add(playerPanel);
        }

        playerBox.revalidate();
    }

    private void updateMessage(MessageUpdate update) {
        messageBox.setText(update.message);
    }
    
    private void updateBoard(BoardUpdate update) {
    	board = update.board;
    	validMoveCells.clear();
    	validMoveDirections.clear();
    	selectedCell = null;
    	boardBox.repaint();
    }

    private void updatePlayerTurn(PlayerTurnUpdate update) {
        currentPlayer = update.player;
        roundNumber = update.round;

        // Clear routes and visited areas
        visitedCells.clear();
        visitedRooms.clear();
        validMoveCells.clear();
        validMoveDirections.clear();

        // Add current player pos to visited sets
        if(currentPlayer != null) {
            visitedCells.add(currentPlayer.getCharacter().getLocation());

            if(!currentPlayer.getCharacter().getLocation().getRoom().getName().equals("Hallway")) {
                visitedRooms.add(currentPlayer.getCharacter().getLocation().getRoom());
            }
        }
        // Render updates
        updateGameInfoBox();
        boardBox.repaint();
    }

    private void updateMovesLeft(MovesLeftUpdate update) {
        movesLeft = update.movesLeft;
        updateGameInfoBox();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg != null) {
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
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        cursorX = x;
        cursorY = y;

        if(board == null) return;

        updateToolTip();

        Cell cell = getCellAtPos(x, y);
        if(cell != null && !cell.equals(selectedCell)) {
            selectedCell = cell;

            findPlayerRoute();
            attemptedMoveCount = validMoveCells.size();
            updateGameInfoBox();
        } else if(cell == null) {
            selectedCell = null;
        }

        boardBox.repaint(); // Repaint to draw tool tips and board changes
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(currentPlayer == null || board == null) return;

        selectedCell = getCellAtPos(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(currentPlayer == null || board == null) return;

        // Execute moves if the mouse is released in the same cell it was pressed in
        Cell currentCell = getCellAtPos(e.getX(), e.getY());
        if(currentCell != null && currentCell.equals(selectedCell))
            executeMoves();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

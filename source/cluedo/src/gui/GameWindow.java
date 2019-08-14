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

    private CluedoGame game = null;

    public GameWindow() {
        super(WINDOW_TITLE);
        setSize(WINDOW_INITIAL_WIDTH, WINDOW_INITIAL_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildWindow();

        // Finalize and display the window with the game
        pack();
        setVisible(true);
        newGame();
    }

    /**
     * Defines layout of the window and adds all the elements
     */
    private void buildWindow() {
        buildMenuBar();

        JPanel container = new JPanel(new GridBagLayout()); // The main container that holds all the elements


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

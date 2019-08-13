package gui;

import game.CluedoGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class GameWindow implements Observer, ActionListener, CluedoController {
    public static final String WINDOW_TITLE = "Cluedo";
    public static final int WINDOW_INITIAL_WIDTH = 800;
    public static final int WINDOW_INITIAL_HEIGHT = 800;

    private JFrame frame;
    private CluedoGame game;

    public GameWindow() {
        frame = new JFrame(WINDOW_TITLE);
        frame.setSize(WINDOW_INITIAL_WIDTH, WINDOW_INITIAL_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildWindow();

        // Finalize and display the window with the game
        frame.pack();
        newGame();
        frame.setVisible(true);
    }

    /**
     * Defines layout of the window and adds all the elements
     */
    private void buildWindow() {
        buildMenuBar();

        JPanel container = new JPanel(new GridBagLayout()); // The main container that holds all the elements


        frame.add(container);
    }

    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener((e) -> newGame());
        menu.add(newGameItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((e) -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        menu.add(exitItem);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    private void newGame() {
        game = new CluedoGame(this);
        game.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

package org.cis1200.minesweeper;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class instantiates a Minesweeper object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Minesweeper ms; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ms = new Minesweeper(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                ms.selectSquare(p.x / 50, p.y / 50);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ms.reset();
        status.setText("Flags remaining: 40");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        status.setText("" + ms.getFlagsRemaining());

        if (ms.isGameOver()) {
            if (ms.checkWin()) {
                status.setText("You won!");
            } else {
                status.setText("You lost!");
            }
        }
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        int unitWidth = BOARD_WIDTH / 10;
        int unitHeight = BOARD_HEIGHT / 8;

        g.drawLine(unitWidth, 0, unitWidth, BOARD_HEIGHT);
        g.drawLine(unitWidth * 2, 0, unitWidth * 2, BOARD_HEIGHT);
        g.drawLine(0, unitHeight, BOARD_WIDTH, unitHeight);
        g.drawLine(0, unitHeight * 2, BOARD_WIDTH, unitHeight * 2);

        //draws squares
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            for (int c = 0; c < BOARD_WIDTH; c++) {
                Square curr = ms.getSquare(r, c);
                NumAdjBombs numAdjBombs = curr.getNumAdjBombs();

                //draw covered squares
                if (curr.isCovered()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(100 * c, 100 * r, 50, 50);
                }

                //draw flags
                if (curr.isFlagged()) {
                    g.setColor(Color.RED);
                    g.fillOval(100 * c + 25, 100 * r + 25, 40, 40);
                }

                //draw uncovered squares
                if (!curr.isCovered()) {
                    g.setColor(Color.WHITE);
                    g.fillRect(100 * c, 100 * r, 50, 50);

                    //draw number of adjacent bombs
                    if (numAdjBombs == NumAdjBombs.ONE) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("1", 100 * c, 100 * r);
                    }

                    if (numAdjBombs == NumAdjBombs.TWO) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("2", 100 * c, 100 * r);
                    }

                    if (numAdjBombs == NumAdjBombs.THREE) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("3", 100 * c, 100 * r);
                    }

                    if (numAdjBombs == NumAdjBombs.FOUR) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("4", 100 * c, 100 * r);
                    }

                    if (numAdjBombs == NumAdjBombs.FIVE) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("5", 100 * c, 100 * r);
                    }

                    if (numAdjBombs == NumAdjBombs.SIX) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("6", 100 * c, 100 * r);
                    }

                    if (numAdjBombs == NumAdjBombs.SEVEN) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("7", 100 * c, 100 * r);
                    }

                    if (numAdjBombs == NumAdjBombs.EIGHT) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        g.drawString("8", 100 * c, 100 * r);
                    }
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}

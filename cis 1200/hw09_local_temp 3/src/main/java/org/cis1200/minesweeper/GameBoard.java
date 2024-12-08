package org.cis1200.minesweeper;

/*
 * CIS 120 HW09 - MineSweeper
 * (c) University of Pennsylvania
 * Created by Jasmin Wu in Fall 2024.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * This class instantiates a Minesweeper object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * <p>
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * <p>
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
                System.out.println("covered before before");

                // updates the model given the coordinates of the mouseclick
                if (e.isShiftDown()) {
                    // click with shift button held down detected
                    ms.flag(p.y / 50,p.x / 50);
                    System.out.println("flagged");
                } else {
                    //click detected
                    ms.selectSquare(p.y / 50, p.x / 50);
                    System.out.println("clicked");
                }

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
        status.setText("Flags remaining: 10");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Saves the current state of the game to a text file minesweeper_save.txt
     * If there is an error saving the file, a pop-up error message appears.
     */
    public void save(String fileName) {
        try {
            Square[][] displayedBoard = ms.getDisplayedBoard();
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            //saves overall game state
            bw.write("" + ms.getFlagsRemaining());
            bw.newLine();

            bw.write("" + ms.getNumBombs());
            bw.newLine();

            bw.write("" + ms.isGameOver());
            bw.newLine();

            //saves data of all the squares
            for (int r = 0; r < displayedBoard.length; r++) {
                for (int c = 0; c < displayedBoard[r].length; c++) {
                    Square curr = displayedBoard[r][c];
                    bw.write(curr.isCovered() + "," + curr.getNumAdjBombs() + ","
                            + curr.isFlagged() + "," + curr.isMine() + ","
                            + curr.isChecked());
                    bw.newLine();
                }
            }
            status.setText("Game progress saved!");
            bw.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error message: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Loads the most recent save of the game from the text file minesweeper_save.txt
     * If there is an error loading the file, displays a pop-up error message.
     */
    public void load(String fileName) {
        try {
            Square[][] displayedBoard = new Square[8][10];

            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            //loads overall game state
            int flagsRemaining = Integer.parseInt(br.readLine());
            ms.setFlagsRemaining(flagsRemaining);

            int numBombs = Integer.parseInt(br.readLine());
            ms.setNumBombs(numBombs);

            boolean gameOver = Boolean.parseBoolean(br.readLine());
            ms.setGameOver(gameOver);

            //loads data of all squares
            for (int r = 0; r < displayedBoard.length; r++) {
                for (int c = 0; c < displayedBoard[r].length; c++) {
                    String squareLine = br.readLine();
                    String[] squareProperties = squareLine.split(",");
                    boolean isCovered = Boolean.parseBoolean(squareProperties[0]);
                    NumAdjBombs numAdjBombs = NumAdjBombs.valueOf(squareProperties[1]);
                    boolean isFlagged = Boolean.parseBoolean(squareProperties[2]);
                    boolean isMine = Boolean.parseBoolean(squareProperties[3]);

                    boolean isChecked = Boolean.parseBoolean(squareProperties[4]);
                    Square curr = new Square(isCovered, numAdjBombs, isFlagged, isMine, isChecked);

                    displayedBoard[r][c] = curr;
                }
            }
            br.close();

            ms.setDisplayedBoard(displayedBoard);

            status.setText("Most recent game progress loaded!");
            updateUI();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error message: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        status.setText("Flags remaining: " + ms.getFlagsRemaining());

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
     * <p>
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

        //draws squares
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 10; c++) {
                Square curr = ms.getSquare(r, c);
                NumAdjBombs numAdjBombs = curr.getNumAdjBombs();

                //vertical grid lines
                g.setColor(Color.BLACK);
                g.drawLine(unitWidth, 0, unitWidth, BOARD_HEIGHT);
                g.drawLine(unitWidth * 2, 0, unitWidth * 2, BOARD_HEIGHT);
                g.drawLine(unitWidth * 3, 0, unitWidth * 3, BOARD_HEIGHT);
                g.drawLine(unitWidth * 4, 0, unitWidth * 4, BOARD_HEIGHT);
                g.drawLine(unitWidth * 5, 0, unitWidth * 5, BOARD_HEIGHT);
                g.drawLine(unitWidth * 6, 0, unitWidth * 6, BOARD_HEIGHT);
                g.drawLine(unitWidth * 7, 0, unitWidth * 7, BOARD_HEIGHT);
                g.drawLine(unitWidth * 8, 0, unitWidth * 8, BOARD_HEIGHT);
                g.drawLine(unitWidth * 9, 0, unitWidth * 9, BOARD_HEIGHT);
                g.drawLine(unitWidth * 10, 0, unitWidth * 10, BOARD_HEIGHT);


                //horizontal grid lines
                g.drawLine(0, unitHeight, BOARD_WIDTH, unitHeight);
                g.drawLine(0, unitHeight * 2, BOARD_WIDTH, unitHeight * 2);
                g.drawLine(0, unitHeight * 3, BOARD_WIDTH, unitHeight * 3);
                g.drawLine(0, unitHeight * 4, BOARD_WIDTH, unitHeight * 4);
                g.drawLine(0, unitHeight * 5, BOARD_WIDTH, unitHeight * 5);
                g.drawLine(0, unitHeight * 6, BOARD_WIDTH, unitHeight * 6);
                g.drawLine(0, unitHeight * 7, BOARD_WIDTH, unitHeight * 7);
                g.drawLine(0, unitHeight * 8, BOARD_WIDTH, unitHeight * 8);

                //draw covered squares
                if (curr.isCovered()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(50 * c, 50 * r, unitWidth, unitHeight);

                    //draw flags
                    if (curr.isFlagged()) {
                        g.setColor(Color.PINK);
                        g.fillOval(50 * c + 1, 50 * r + 1, 48, 48);
                    }
                }

                //draw uncovered squares
                //draws mine if mine is uncovered
                if (!curr.isCovered() && curr.isMine()) {
                    g.setColor(Color.RED);
                    g.fillOval(50 * c + 1, 50 * r + 1, 48, 48);

                    //draws uncovered squares if they do not have mines
                } else if (!curr.isCovered()) {
                    g.setColor(Color.WHITE);
                    g.fillRect(50 * c, 50 * r, unitWidth, unitHeight);

                    //draw number of adjacent bombs
                    if (numAdjBombs == NumAdjBombs.ONE) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("1", 50 * c + 15, 50 * r + 37);
                    }

                    if (numAdjBombs == NumAdjBombs.TWO) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("2", 50 * c + 15, 50 * r + 37);
                    }

                    if (numAdjBombs == NumAdjBombs.THREE) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("3", 50 * c + 15, 50 * r + 37);
                    }

                    if (numAdjBombs == NumAdjBombs.FOUR) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("4", 50 * c + 15, 50 * r + 37);
                    }

                    if (numAdjBombs == NumAdjBombs.FIVE) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("5", 50 * c + 15, 50 * r + 37);
                    }

                    if (numAdjBombs == NumAdjBombs.SIX) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("6", 50 * c + 15, 50 * r + 37);
                    }

                    if (numAdjBombs == NumAdjBombs.SEVEN) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("7", 50 * c + 15, 50 * r + 37);
                    }

                    if (numAdjBombs == NumAdjBombs.EIGHT) {
                        g.setColor(Color.BLUE);
                        g.setFont(new Font("Arial", Font.BOLD, 35));
                        g.drawString("8", 50 * c + 15, 50 * r + 37);
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
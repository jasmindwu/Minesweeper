package org.cis1200.minesweeper;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunMinesweeper implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(500, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save("src/main/java/org/cis1200/files/minesweeper_save.txt"));
        control_panel.add(save);

        final JButton load = new JButton("Load");
        load.addActionListener(e -> board.load("src/main/java/org/cis1200/files/minesweeper_save.txt"));
        control_panel.add(load);

        String instructionsMessage =

                "You're now playing Minesweeper!\n\n" +
                        "Your objective is to uncover all the squares\n" +
                        "without mines hidden underneath.\n" +
                        "Be careful not to detonate any of the 10 mines\n" +
                        "in the process, or the game will end.\n" +
                        "You can place flags where you think there are mines to keep track.\n" +
                        "Win by avoiding all mines and uncovering all non-mines!\n\n\n" +
                        "How to play:\n\n" +
                        "-Click on any square to uncover it.\n" +
                        "-Place flags by holding shift then clicking.\n" +
                        "-Hold shift and click on any flags to remove them.\n" +
                        "-To save your game progress, press the save button.\n" +
                        "-Restore your most recent save anytime\n" +
                        "by pressing the load button.\n\n" +
                        "If you ever want to see these instructions again,\n" +
                        "just press the instructions button!";

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> JOptionPane.showMessageDialog(null, instructionsMessage, "Instructions", JOptionPane.INFORMATION_MESSAGE));
        control_panel.add(instructions);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
        JOptionPane.showMessageDialog(null, instructionsMessage, "Instructions", JOptionPane.INFORMATION_MESSAGE) ;
    }
}
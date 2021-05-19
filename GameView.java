import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 */

public class GameView extends JFrame {

    private DotButton[][] board;

    private GameController gameController;

    private GameModel gameModel;

    private javax.swing.JLabel nbreOfStepsLabel;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {

        this.gameModel = gameModel;
        this.gameController = gameController;
        int width, heigth;
        width = gameModel.getWidth();
        heigth = gameModel.getHeigth();
        board = new DotButton[width][heigth];


        JFrame myFrame = new JFrame();
        myFrame.setTitle("Minesweeper");
        myFrame.setSize(600, 420);
        setBackground(Color.white);
        myFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // PANEL FOR GAME

        JPanel grid = new JPanel(new GridLayout(width, heigth));
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                board[i][j] = new DotButton(i, j, 11);
                board[i][j].setBorder(null);
                board[i][j].addActionListener(gameController);
                grid.add(board[i][j]);
            }
        }
        grid.setBorder(BorderFactory.createLineBorder(Color.black));
        myFrame.add(grid, BorderLayout.CENTER);

        // BOTTOM PANEL (quit, reset, #turns)
        JPanel myPanel = new JPanel();
        myPanel.setBackground(Color.white);
        myFrame.add(myPanel, BorderLayout.SOUTH);

        nbreOfStepsLabel = new JLabel("Number of Steps: " + gameModel.getNumberOfSteps());
        myPanel.add(nbreOfStepsLabel);

        JButton quit, reset;

        reset = new JButton("Reset");
        reset.addActionListener(gameController);
        myPanel.add(reset);

        quit = new JButton("Quit");
        quit.addActionListener(gameController);
        myPanel.add(quit);

        System.out.println(gameModel.toString());


        pack();
        myFrame.setVisible(true);
       

    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){

        DotButton b;
        
        JPanel grid = new JPanel(new GridLayout(gameModel.getWidth(), gameModel.getHeigth()));


        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
            	if(gameModel.hasBeenClicked(i,j)){
                	board[i][j].setIconNumber(getIcon(i,j));
                	board[i][j].removeActionListener(gameController);
            	}
            	else{
                	board[i][j].setIconNumber(getIcon(i,j));
            	}
            }
        }
        nbreOfStepsLabel.setText("Number of Steps: " + gameModel.getNumberOfSteps());

        repaint();

    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        
        if(gameModel.hasBeenClicked(i,j) && gameModel.isMined(i, j)){
            return 10; // clicked mine
        }
        else if(!gameModel.isCovered(i, j) && gameModel.isMined(i,j)){
            return 9; // mine
        }

        else if(gameModel.hasBeenClicked(i,j) && !gameModel.isMined(i, j)){
            return gameModel.getNeighbooringMines(i, j);
        }

        else if(gameModel.isFinished() && gameModel.isMined(i, j)){
            return 12; //flagged mine
        }

        else{
            return 11; //blank
        }


    }


}

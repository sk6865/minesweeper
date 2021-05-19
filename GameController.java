import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 */


public class GameController implements ActionListener {

    private GameModel gameModel;

    private GameView gameView;

    private int width, heigth, numberOfMines;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {

        this.width = width;
        this.heigth = height;
        this.numberOfMines = numberOfMines;

        gameModel = new GameModel(width, heigth, numberOfMines);
        gameView = new GameView(gameModel, this);
        gameView.update();

    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {

        DotButton b;

        if (e.getActionCommand().equals("Reset")){
            reset();
        }
        if (e.getActionCommand().equals("Quit")){
            System.exit(0);
        }

        if (e.getSource() instanceof DotButton){
            b = (DotButton) e.getSource();
            play(b.getColumn(), b.getRow());
        }
    }

    /**
     * resets the game
     */
    private void reset(){

        gameModel.reset();
        gameView.update();

    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

        gameModel.step();
        gameModel.click(width, heigth);
        gameModel.uncover(width, heigth);


            if(gameModel.isMined(width,heigth)){
                gameModel.uncoverAll();
                gameView.update();

                JFrame frame = new JFrame();
                Object[] choices = {"Quit", "Play Again"};
                int n =  JOptionPane.showOptionDialog(frame,
                "Aouch, you lost in " + gameModel.getNumberOfSteps() + " moves! Would you like to play again?",
                "Boom!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, choices, choices[1]);

                frame.setVisible(true);

                if (n == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
                else if(n == JOptionPane.NO_OPTION){
                    reset();
                }


            }
            else if(gameModel.isFinished()){
                gameModel.uncoverAll();
                gameView.update();

                JFrame frame = new JFrame();
                Object[] choices = {"Quit", "Play Again"};
                int n =  JOptionPane.showOptionDialog(frame,
                "Congratulations! You won the game in " + gameModel.getNumberOfSteps() + " moves. Would you like to play again?",
                "You won!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, choices, choices[1]);

                frame.setVisible(true);

                if (n == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
                else if(n == JOptionPane.NO_OPTION){
                    reset();
                }

            }
            else{ // if point is not a mine
                //clearZone(gameModel.get(width, heigth));
                gameView.update();
            }

        }



   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {
        
        Stack<DotInfo> stack = new GenericArrayStack<DotInfo>(width*heigth);
        DotInfo d;

        stack.push(initialDot);

        while (!stack.isEmpty()){
            d = stack.pop();

            for(int i =  Math.max(d.getX()-1, 0); i < Math.min(d.getX()+2, width); i++){
                for(int j = Math.max(d.getY()-1, 0); j < Math.min(d.getY()+2, heigth); j++){
                    if(gameModel.isCovered(i,j)){
                        gameModel.uncover(i,j);
                        if(gameModel.getNeighbooringMines(i,j)==0){
                            stack.push(gameModel.get(i,j));
                        }
                    }
                }
            }
        }
    }


}

import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 */
public class GameModel {

    private java.util.Random generator = new java.util.Random();

    private int heigthOfGame;

    private DotInfo[][] model;

    private int numberOfMines;

    private int numberOfSteps;

    private int numberUncovered;

    private int widthOfGame;

    private int minesAdded;



    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {

        widthOfGame = width;
        heigthOfGame = heigth;
        this.numberOfMines = numberOfMines;
        model = new DotInfo[widthOfGame][heigthOfGame];
        minesAdded = 0;

        reset();

    }


    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){
        
        model = new DotInfo[widthOfGame][heigthOfGame];

        for(int i=0; i<model.length; i++){
            for(int j=0; j<model[i].length; j++){
                model[i][j] = new DotInfo(i, j);
            }
        }
        minesAdded = 0;

        this.addMines();
        this.addNumbers();
        
        numberOfSteps = 0;

    }


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
        return heigthOfGame;

    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
        return widthOfGame;

    }


    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){

        return model[i][j].isMined();

    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){

        return model[i][j].hasBeenClicked();

    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
        if (model[i][j].getNeighbooringMines()==0){
            return true;
        }
        else{
            return false;
        }

    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
        return model[i][j].isCovered();

    }

    /**
    * Randomly places mines throughout the board
    */
    private void addMines(){

        int i, j;
        
        while(minesAdded < numberOfMines){
            i = generator.nextInt(widthOfGame);
            j = generator.nextInt(heigthOfGame);
            if(!model[i][j].isMined()){
                model[i][j].setMined();
                minesAdded++;
            }
        }
    }

    /**
    * Assigns number of neighboring mines to each dot on the board
    */
    private void addNumbers(){

        int mines;

        for(int i=0; i<widthOfGame; i++){
            for(int j=0; j<heigthOfGame; j++){

                mines = 0;

                if(i==0 && j==0){
                    if (model[i+1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i][j+1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j+1].isMined()){
                        mines += 1;
                    }
                }

                else if(i==0 && j==heigthOfGame-1){
                    if (model[i][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j-1].isMined()){
                        mines += 1;
                    }
                }

                else if(i==widthOfGame-1 && j==0){
                    if (model[i-1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j+1].isMined()){
                        mines += 1;
                    }
                    if (model[i][j+1].isMined()){
                        mines += 1;
                    }
                }

                else if(i==widthOfGame-1 && j==heigthOfGame-1){
                    if (model[i][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j].isMined()){
                        mines += 1;
                    }
                }

                else if(0<i && i<widthOfGame-1 && j==0){

                    if (model[i-1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j+1].isMined()){
                        mines += 1;
                    }
                    if (model[i][j+1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j+1].isMined()){
                        mines += 1;
                    }
                }

                else if(0<i && i<widthOfGame-1 && j==heigthOfGame-1){
                    if (model[i-1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j].isMined()){
                        mines += 1;
                    }
                }

                else if(i==0 && j>0 && j<heigthOfGame-1){
                    if (model[i][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j+1].isMined()){
                        mines += 1;
                    }
                    if (model[i][j+1].isMined()){
                        mines += 1;
                    }
                }

                else if(i==widthOfGame-1 && j>0 && j<heigthOfGame-1){
                    if (model[i][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i-1][j+1].isMined()){
                        mines += 1;
                    }
                    if (model[i][j+1].isMined()){
                        mines += 1;
                    }
                }

                else{
                    if (model[i][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i][j+1].isMined()){
                        mines += 1;
                    }

                    if (model[i-1][j].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j].isMined()){
                        mines += 1;
                    }

                    if (model[i-1][j+1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j-1].isMined()){
                        mines += 1;
                    }

                    if (model[i-1][j-1].isMined()){
                        mines += 1;
                    }
                    if (model[i+1][j+1].isMined()){
                        mines += 1;
                    }
                }

                model[i][j].setNeighbooringMines(mines);
            }
        }
    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        
        return model[i][j].getNeighbooringMines();

    }



    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
        model[i][j].uncover();

    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
        model[i][j].click();

    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        
        for(int i=0; i<widthOfGame; i++){
            for(int j=0; j<heigthOfGame; j++){
                if (model[i][j].isCovered()){
                    model[i][j].uncover();
                }
            }
        }
    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
        return numberOfSteps;

    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
        return model[i][j];

    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
        numberOfSteps++;

        

    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        

        for(int i=0; i<widthOfGame; i++){
            for(int j=0; j<heigthOfGame; j++){
                if (!isMined(i, j) && model[i][j].isCovered()){
                    return false;
                }
            }
        }

        return true;
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        
        String stateOfModel = "";

        for (int i = 0; i < widthOfGame; i++){
            for (int j = 0; j < heigthOfGame; j++){
                stateOfModel = stateOfModel + "(" + i + ", " + j + "): Mined("+this.isMined(i,j)+"), Number of Neighboring Mines("+this.getNeighbooringMines(i,j)+")\n";
            }
        }

        return stateOfModel;

    }
}
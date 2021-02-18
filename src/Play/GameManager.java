package Play;

import GameObjects.GameObject;
import GameObjects.Ground;
import GameObjects.Tank;
import Inputs.TankInputAI;
import Inputs.TankInputAIBuilder;
import Inputs.TankInputPlayerBuilder;
import UIPcg.GameUI;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends PApplet{
    private static final float MOVES_BEFORE_WIND_CHANGE = 3;
    private static final float WIND_CHANGE_X = 0.1f;
    private static final float WIND_CHANGE_Y = 0.02f;
    private static final float BOTTOM_HEIGHT = 50;
    private static final int SPACE_FOR_TANK = 5;
    private static final int NUM_OF_WINS = 3;
    private static final int PAUSE = 100;
    private static final int TANK1_X = 20;
    private static final int TANK2_X = -100;
    private static final int TANK_Y = 800;
    private static GameManager instance = null;

    private Tank tank1;
    private Tank tank2;
    private GameUI gameUI;
    private PVector windVector;
    private float moveCounter = 0;
    private boolean started = false;
    private int pauseCounter = 0;
    private boolean gameWon = false;

    private boolean firstTurn = true;

    public PVector highestBlockPos;

    public void setup(){
        this.tank1 = new Tank(this, TANK1_X, TANK_Y,false);
        this.tank2 = new Tank(this,width + TANK2_X,TANK_Y,true);
        this.gameUI = new GameUI(this);
        this.highestBlockPos = new PVector(0, height);
        this.started = false;
        gameUI.setPlayerStarted(false);
        gameUI.setAiStarted(false);
        generateBottomGround();
        startTurns();
        changeWindVector();
    }

    public void settings(){
        size(1500, 1000);
    }

    public void draw(){
        background(51, 156, 255);
        resolveWin();
        if (!started) {
            if (gameUI.isPlayerStarted() || gameUI.isAiStarted()){
                if (gameUI.isAiStarted()){
                    tank2.setTankInput(new TankInputAIBuilder());
                    ((TankInputAI)(this.tank2.input)).setTargetPos(tank1.position);
                }
                started = true;
                generateGroundBetween();
                tank1.position.y = 100;
                tank2.position.y = 100;
                changeTurns();
            }
        }
        gameUI.update();
        GameObject.updateAll();
    }

    public static void main(String[] args){
        String[] processingArgs = {"Tanks"};
        GameManager gameController = GameManager.getInstance();
        PApplet.runSketch(processingArgs, gameController);
    }

    /**
     * change whose turn is it by enabling and disabling input components
     * also set the wind vector
     */
    public void changeTurns(){
        changeWindVector();
        if (firstTurn) {
            moveCounter++;
            tank1.input.setEnabled(true);
            tank2.input.setEnabled(false);
            firstTurn = false;
        }
        else {
            tank2.input.setEnabled(true);
            tank1.input.setEnabled(false);
            firstTurn = true;
        }
    }

    /**
     * set starting turns
     */
    private void startTurns(){
        tank1.input.setEnabled(false);
        tank2.input.setEnabled(false);
        firstTurn = true;
    }

    /**
     * Generates bottom blocks of the map
     * @return array of bottom ground blocks
     */
    private List<GameObject> generateBottomGround(){
        float blockWidth = Ground.WIDTH;
        int blocksInScreen = (int) (width / blockWidth);
        float toAdd = (width - blocksInScreen*blockWidth)/2f;
        List<GameObject> lowBlocks = new ArrayList<>(blocksInScreen);
        for (int i = 0; i < blocksInScreen; i++) {
            lowBlocks.add(new Ground(this, i*blockWidth + toAdd, height - BOTTOM_HEIGHT));
        }

        return lowBlocks;
    }

    /**
     * Generates bottom blocks of the map
     * @return array of bottom ground blocks
     */
    private List<GameObject> generateGroundBetween(){
        List<GameObject> grounds = new ArrayList<>();
        float blockHeight = Ground.HEIGHT;
        int blocksInScreen = (int) (width / Ground.WIDTH);
        int[] heights = generateHeights(blocksInScreen - SPACE_FOR_TANK*2);
        float toAdd = (width - blocksInScreen*blockHeight)/2f;
        float startFrom = SPACE_FOR_TANK * Ground.WIDTH + toAdd;
        highestBlockPos = new PVector(0, height);

        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i]; j++) {
                Ground newGround = new Ground(this, startFrom + i * Ground.WIDTH,
                                            height - ((BOTTOM_HEIGHT + Ground.WIDTH) + j * Ground.WIDTH));
                newGround.gravity = true;
                grounds.add(newGround);
                if (newGround.position.y < highestBlockPos.y)
                    highestBlockPos = newGround.position;
            }
        }

        return grounds;
    }

    /**
     * Generates array of numbers which represent number of blocks in each column
     * @param numOfHeights
     * @return
     */
    private int[] generateHeights(int numOfHeights){
        int[] heights = new int[numOfHeights];
        for (int i = 0; i < numOfHeights/2; i++) {
            if (i == 0) {
                heights[i] = 2;
            }
            else{
                heights[i] = heights[i - 1] + (int)((Math.random() * 3)) - 1;
            }
        }
        for (int i = numOfHeights - 1; i >= numOfHeights/2; i--) {
            {
                if (i == numOfHeights - 1) {
                    heights[i] = 2;
                }
                else{
                    heights[i] = heights[i + 1] + (int)((Math.random() * 3)) - 1;
                }
            }
        }

        return heights;
    }

    /**
     * changes wind vector if certain conditions met
     */
    private void changeWindVector(){
        if (firstTurn && moveCounter % MOVES_BEFORE_WIND_CHANGE == 0) {
            windVector = new PVector((float)(WIND_CHANGE_X * Math.random()) - WIND_CHANGE_X/2,
                    (float)(WIND_CHANGE_Y * Math.random() - WIND_CHANGE_Y/2));
        }
    }

    /**
     * resolve situation if one of the tanks is destroyed
     */
    private void resolveWin(){
        if (pauseCounter > 1){
            pauseCounter--;
        }
        else if (pauseCounter == 1){
            if (gameWon) {
                gameWon = false;
                setup();
            }
            else {
                resetRound();
                gameUI.newRound();
                changeTurns();

            }
            pauseCounter--;
        }
        else {
            if (isWonRound()){
                int tankNum = (tank1.isDestroyed()) ? 2 : 1;
                if(isWonGame()){
                    gameUI.onGameWon(tankNum, tank1.isDestroyed() ? tank2.getWins() : tank1.getWins());
                    GameObject.destroyAll();
                    gameWon = true;
                }
                else {
                    gameUI.onRoundWon(tankNum, tank1.isDestroyed() ? tank2.getWins() : tank1.getWins());
                }
                pauseCounter = PAUSE;
            }
        }

    }

    /**
     * @return true if round is won by one of the tanks
     */
    private boolean isWonRound(){
        if (tank1.isDestroyed()) {
            tank2.incrementWins();
            return true;
        }
        else if (tank2.isDestroyed()){
            tank1.incrementWins();
            return true;
        }
        return false;
    }

    /**
     * @return true if game is won by one of the tanks
     */
    private boolean isWonGame(){
        return (tank1.getWins() == NUM_OF_WINS) || (tank2.getWins() == NUM_OF_WINS);
    }

    /**
     * resets destroyed tank
     */
    private void resetRound(){
        if (tank1.isDestroyed() || tank2.isDestroyed()) {
            if (tank1.isDestroyed()) {
                this.tank1 = new Tank(this, TANK1_X, TANK_Y,false, new TankInputPlayerBuilder());
            }
            if (tank2.isDestroyed()){
                this.tank2 = new Tank(this,width + TANK2_X,TANK_Y,true, new TankInputAIBuilder());
            }
        }
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    public PVector getWindVector(){
        return windVector;
    }
}
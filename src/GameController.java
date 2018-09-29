import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class GameController extends PApplet{
    private static final float BOTTOM_HEIGHT = 50;
    private static final int SPACE_FOR_TANK = 5;
    public static GameController instance = null;

    private Tank tank1;
    private Tank tank2;
    private List<GameObject> grounds;
    private List<GameObject> bottom;

    boolean firstTurn = false;

    public void setup(){
        tank1 = new Tank(this,0,900,false);
        tank2 = new Tank(this,width - 100,900,true);
        bottom = generateBottomGround();
        grounds = generateGroundBetween();

        //set whose turn it is
        changeTurns();
    }

    public void settings(){
        size(1500, 1000);
    }

    public void draw(){
        background(100);
//        GameObject.updateAll(bottom);
//        GameObject.updateAll(grounds);
//        drawCols(Collider.colliders1);
//        tank1.update();
//        tank2.update();
        GameObject.updateAll();
    }

    public static void main(String[] args){
        String[] processingArgs = {"Tanks"};
        GameController gameController = GameController.getInstance();
        PApplet.runSketch(processingArgs, gameController);
    }

    public void changeTurns(){
        if (firstTurn) {
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
        int blocksInScreen = bottom.size();
        int[] heights = generateHeights(blocksInScreen - SPACE_FOR_TANK*2);
        float toAdd = (width - blocksInScreen*blockHeight)/2f;
        float startFrom = SPACE_FOR_TANK * Ground.WIDTH + toAdd;

        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i]; j++) {
                Ground newGround = new Ground(this, startFrom + i * Ground.WIDTH,
                                            height - ((BOTTOM_HEIGHT + Ground.WIDTH) + j * Ground.WIDTH));
                newGround.gravity = true;
                grounds.add(newGround);
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

    private void drawCols(List<Collider> colliders1){
        for (Collider c: colliders1) {
            if (c instanceof BoxCollider) {
                noFill();
                stroke(204, 102, 0);
                rect(c.colX, c.colY, ((BoxCollider) c).width, ((BoxCollider) c).height);
            }
        }
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }
}
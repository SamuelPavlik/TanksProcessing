package GameObjects;

import Colliders.BoxCollider;
import Inputs.TankInputBuilder;
import Inputs.TankInputPlayerBuilder;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Tank extends GameObject{
    public static final float SIZE_MULTIPLIER = 0.3f;
    public static final float TANK_ROT_X = 38 * SIZE_MULTIPLIER;
    public static final float TANK_ROT_Y = 37 * SIZE_MULTIPLIER;
    public static final float HEAD_WIDTH = 38.5f * SIZE_MULTIPLIER;
    public static final float HEAD_HEIGHT = 37 * SIZE_MULTIPLIER;
    public static final float INVERSE_DIFF = 55;
    private static final float COL_DIFF = 14;

    private PImage tankBottom;
    private PImage tankHead;
    private int inverseVar;
    private PVector lastShellPosition;
    private int wins = 0;

    public Shell shell;

    public Tank(PApplet pApplet, float x, float y, boolean inverse) {
        super(pApplet, x, y);
        if (inverse)
            this.inverseVar = -1;
        else
            this.inverseVar = 1;

        this.tankBottom = pApplet.loadImage("TankBottom.png");
        this.tankHead = pApplet.loadImage("TankHead.png");
        this.shell = null;
        this.collider = new BoxCollider(this, x, y - COL_DIFF, tankBottom.width*SIZE_MULTIPLIER, tankBottom.height*SIZE_MULTIPLIER - COL_DIFF);
        this.input = new TankInputPlayerBuilder().getTankInput(pApplet, this);
        this.gravity = true;
        this.lastShellPosition = new PVector(0, pApplet.height);
    }


    public Tank(PApplet pApplet, float x, float y, boolean inverse, TankInputBuilder inputBuilder) {
        super(pApplet, x, y);
        if (inverse)
            this.inverseVar = -1;
        else
            this.inverseVar = 1;

        this.tankBottom = pApplet.loadImage("TankBottom.png");
        this.tankHead = pApplet.loadImage("TankHead.png");
        this.shell = null;
        this.collider = new BoxCollider(this, x, y - COL_DIFF, tankBottom.width*SIZE_MULTIPLIER, tankBottom.height*SIZE_MULTIPLIER - COL_DIFF);
        this.input = inputBuilder.getTankInput(pApplet, this);
        this.gravity = true;
        this.lastShellPosition = new PVector(0, pApplet.height);
    }

    /**
     * draws the upper and lower part of the object
     */
    public void updateRaw(){
        pApplet.pushMatrix();
        //change scale if the tank is facing left
        pApplet.scale(inverseVar, 1);
        //draw the upper part which rotates
        drawHead();
        //draw the bottom part
        pApplet.image(tankBottom, inverseVar*position.x, position.y, inverseVar*tankHead.width * SIZE_MULTIPLIER, tankHead.height * SIZE_MULTIPLIER);
        //check if shell was fired
        if (shell != null){
            //if fired, is it destroyed
            if (shell.isDestroyed()) {
                //if destroyed, save the last position for ai learning purposes
                lastShellPosition = shell.position.copy();
                shell = null;
            }
        }
        pApplet.popMatrix();
    }

    /**
     * draw the upper part if the input component is disabled
     */
    private void drawHead(){
        pApplet.pushMatrix();
        if (!input.isEnabled()) {
            pApplet.image(tankHead,
                        inverseVar*position.x, position.y,
                        inverseVar*(tankHead.width * SIZE_MULTIPLIER),
                        tankHead.height * SIZE_MULTIPLIER);
        }
        pApplet.popMatrix();
    }

    public PImage getTankHead() {
        return tankHead;
    }

    public int getInverseVar() {
        return inverseVar;
    }

    public PVector getLastShellPosition() {
        return lastShellPosition;
    }

    public int getWins() {
        return wins;
    }

    public void incrementWins(){
        wins++;
    }

    /**
     * set tank input to either player or ai input
     * @param builder player or ai input builder
     */
    public void setTankInput(TankInputBuilder builder){
        this.input = builder.getTankInput(pApplet, this);
    }

}

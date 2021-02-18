package Inputs;

import GameObjects.Tank;
import Play.GameManager;
import processing.core.PApplet;
import processing.core.PVector;

public class TankInputAI extends TankInput {
    private static final float FORCE_FACTOR = 0.01f;
    private static final float OVER_BLOCK = 500;

    private PVector targetPos;
    private float targetForce = 0;
    private boolean forceSet = false;
    private boolean firstTime = true;

    public TankInputAI(PApplet pApplet, Tank gameObject) {
        super(pApplet, gameObject);
        this.targetPos = new PVector(0, pApplet.height);
    }

    /**
     * updates ai input
     * @param moveSpeed
     */
    @Override
    public void update(float moveSpeed) {
        super.update(moveSpeed);
        if (!isEnabled())
            return;

        //sets force based on where did the shell hit last time
        setForce();
        //draws head always if input is enabled
        super.drawHead(true);
        //loads based on current force and target force
        super.shoot(getLoading());
    }

    /**
     * set position of the target to shoot at
     * @param targetPos position of the target to shoot at
     */
    public void setTargetPos(PVector targetPos) {
        this.targetPos = targetPos;
    }

    @Override
    void move(float moveSpeed) {

    }

    /**
     * gets the highest block in the map and sets the angle as to overshoot it
     * @return angle at which to shoot at
     */
    @Override
    float getAngle() {
        PVector highestBlockPos = GameManager.getInstance().highestBlockPos;
        if (gameObject.getLastShellPosition() != null){
            if (gameObject.getLastShellPosition().y < 0){
                highestBlockPos.y -= 100;
                GameManager.getInstance().highestBlockPos.y -= 100;
            }
        }
        PVector fromTankOverBlock = new PVector((highestBlockPos.x - gameObject.position.x + Tank.TANK_ROT_X)*inverseVar,
                                            (highestBlockPos.y - (gameObject.position.y + Tank.TANK_ROT_Y)) - OVER_BLOCK);
        currAngle = PVector.angleBetween(new PVector(1, 0), fromTankOverBlock);
        if (fromTankOverBlock.y < 0)
            currAngle = -currAngle;

        return currAngle;
    }

    /**
     * @return true if loading, that is if the current force is lower than the target force to shoot at
     */
    private boolean getLoading(){
        if (targetForce > force)
            return true;
        forceSet = false;
        return false;
    }

    /**
     * sets target force based on last position where the shell hit
     */
    private void setForce(){
        if (forceSet)
            return;
        if (firstTime) {
            targetForce = 15;
            forceSet = true;
            firstTime = false;
            return;
        }
        targetForce += FORCE_FACTOR * (gameObject.getLastShellPosition().x - targetPos.x);
        if (targetForce > MAX_FORCE)
            targetForce = MAX_FORCE;

        forceSet = true;
    }
}

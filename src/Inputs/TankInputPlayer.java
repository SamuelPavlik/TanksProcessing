package Inputs;

import GameObjects.Tank;
import processing.core.PApplet;
import processing.core.PVector;

public class TankInputPlayer extends TankInput {
    public TankInputPlayer(PApplet pApplet, Tank gameObject) {
        super(pApplet, gameObject);
    }

    /**
     * update player input
     * @param moveSpeed
     */
    public void update(float moveSpeed){
        super.update(moveSpeed);
        if (!isEnabled())
            return;
        //move tank
        move(moveSpeed);
        //rotate head if above certain level with mouse
        drawHead(pApplet.mouseY - (gameObject.position.y + Tank.TANK_ROT_Y) < 0);
        //shoot if space pressed
        shoot(pApplet.keyPressed && pApplet.key == ' ');
    }

    /**
     * move tank if arrow keys pressed
     * @param moveSpeed
     */
    void move(float moveSpeed){
        moveVector = zeroVector;
        if(pApplet.keyPressed){
            if(pApplet.keyCode == pApplet.LEFT){
                moveVector = new PVector(-moveSpeed, 0);
            }
            else if(pApplet.keyCode == pApplet.RIGHT){
                moveVector = new PVector(moveSpeed, 0);
            }
            gameObject.velocity.add(moveVector);
        }
    }

    /**
     * @return angle at which to shoot at based on position of mouse
     */
    @Override
    float getAngle(){
        PVector fromHeadToMouse = new PVector(inverseVar*(pApplet.mouseX - (gameObject.position.x + Tank.TANK_ROT_X)),
                pApplet.mouseY - (gameObject.position.y + Tank.TANK_ROT_Y) + 15);
        currAngle = PVector.angleBetween(new PVector(1, 0), fromHeadToMouse);
        if (pApplet.mouseY - (gameObject.position.y + Tank.TANK_ROT_Y) + 15 < 0)
            currAngle = -currAngle;

        return currAngle;
    }
}

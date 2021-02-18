package Inputs;

import GameObjects.Shell;
import GameObjects.Tank;
import Other.Shapes;
import Play.GameManager;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public abstract class TankInput implements IInput {
    static final int MAX_FORCE = 20;
    static PVector zeroVector = new PVector(0, 0);

    PApplet pApplet;
    Tank gameObject;
    float currAngle;
    int inverseVar;
    float force = 0;

    private PImage tankHead;
    PVector moveVector;
    private PVector endOfGun;
    private PVector forceVector;
    private boolean enabled = false;
    private boolean spacePressed = false;

    public TankInput(PApplet pApplet, Tank gameObject) {
        this.pApplet = pApplet;
        this.gameObject = gameObject;
        this.moveVector = new PVector(0, 0);
        this.currAngle = 0;
        this.inverseVar = gameObject.getInverseVar();
        this.tankHead = gameObject.getTankHead();
        this.forceVector = new PVector(0, 0);
    }

    public void update(float moveSpeed){
        drawForce();
    }

    /**
     * changes velocity based on move speed and input
     * @param moveSpeed
     */
    abstract void move(float moveSpeed);

    /**
     * @return the angle at which the upper part of tank should be set
     */
    abstract float getAngle();

    /**
     * sets the rotation of the upper part of tank
     * @param isAiming condition under which will the upper part rotate
     */
    void drawHead(boolean isAiming){
        pApplet.pushMatrix();
        if (inverseVar == -1)
            pApplet.scale(-1, 1);
        if (isAiming) {
            if (inverseVar == -1) {
                pApplet.translate(inverseVar*(gameObject.position.x + Tank.TANK_ROT_X) - Tank.INVERSE_DIFF,
                                    gameObject.position.y + Tank.TANK_ROT_Y);
            }
            else{
                pApplet.translate(inverseVar*(gameObject.position.x + Tank.TANK_ROT_X),
                                    gameObject.position.y + Tank.TANK_ROT_Y);
            }

            pApplet.rotate(getAngle());
            if (inverseVar == -1) {
                pApplet.image(tankHead, inverseVar*(-Tank.HEAD_WIDTH) + Tank.INVERSE_DIFF, -Tank.HEAD_HEIGHT,
                        inverseVar*(tankHead.width * Tank.SIZE_MULTIPLIER),
                        tankHead.height * Tank.SIZE_MULTIPLIER);
            }
            else{
                pApplet.image(tankHead, inverseVar*(-Tank.HEAD_WIDTH), -Tank.HEAD_HEIGHT,
                        inverseVar*(tankHead.width * Tank.SIZE_MULTIPLIER),
                        tankHead.height * Tank.SIZE_MULTIPLIER);
            }
        }
        else {
            currAngle = 0;
            pApplet.image(tankHead,
                        inverseVar* (gameObject.position.x), gameObject.position.y,
                        inverseVar*(tankHead.width * Tank.SIZE_MULTIPLIER),
                        tankHead.height * Tank.SIZE_MULTIPLIER);
        }
        pApplet.popMatrix();
    }

    /**
     * loads and shoots from the gun if isLoading condition met
     * @param isLoading loads the gun, if this condition is met, else fires
     */
    void shoot(boolean isLoading){
        if (isLoading){
            //adds force to fire
            spacePressed = true;
            if (force < MAX_FORCE)
                force += 0.2f;

            PVector rotPoint = new PVector(gameObject.position.x + inverseVar*Tank.TANK_ROT_X, gameObject.position.y);
            if (inverseVar == -1){
                rotPoint.x += 75;
            }
            pApplet.fill(0);
            pApplet.ellipse(rotPoint.x, rotPoint.y, 5, 5);

            PVector toEndOfGun = new PVector(inverseVar*(Tank.HEAD_WIDTH + 40), 0);
            toEndOfGun.rotate(inverseVar*currAngle);
            endOfGun = rotPoint.copy().add(toEndOfGun);

            forceVector.x = inverseVar*force;
            forceVector.y = 0;
            forceVector.rotate(inverseVar*currAngle);

            //draws aiming arrow that will lenghten with higher force
            drawAimArrow();
        }
        else{
            //shoots, the last frame was loading but this isn't
            if (spacePressed){
                //creates shell and fires it with the force vector applied
                gameObject.shell = new Shell(pApplet, endOfGun.x, endOfGun.y, GameManager.getInstance().getWindVector());
                gameObject.shell.addForce(forceVector);
                //resets fields
                spacePressed = false;
                force = 0;
                currAngle = 0;
                //disable input after shot
                this.enabled = false;
            }
        }
    }

    /**
     * draws aiming arrow that will lenghten with higher force
     */
    private void drawAimArrow(){
        pApplet.stroke(0);
        PVector lineForceVector = forceVector.copy().mult(10);
        pApplet.line(endOfGun.x, endOfGun.y, endOfGun.x + lineForceVector.x, endOfGun.y + lineForceVector.y);
        Shapes.drawArrow(pApplet, endOfGun.x, endOfGun.y, endOfGun.x + lineForceVector.x,
                        endOfGun.y + lineForceVector.y, 0, 4, true);
    }

    /**
     * draw force and angle details above the tank
     */
    private void drawForce(){
        pApplet.textAlign(PConstants.CENTER);
        pApplet.fill(255);
        pApplet.text("Force: " + Math.round(force * 100)/100f,
                    gameObject.position.x + ((tankHead.width* Tank.SIZE_MULTIPLIER)/2f),
                    gameObject.position.y - 60);
        pApplet.text("Angle: " + Math.round(PApplet.degrees(-currAngle)),
                    gameObject.position.x + ((tankHead.width*Tank.SIZE_MULTIPLIER)/2f),
                    gameObject.position.y - 30);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

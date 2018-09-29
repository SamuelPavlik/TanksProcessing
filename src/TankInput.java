import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class TankInput implements IInput {
    private static final int MAX_FORCE = 20;
    private static PVector zeroVector = new PVector(0, 0);

    private PApplet pApplet;
    private Tank gameObject;
    private PVector moveVector;
    private float currAngle;
    private int inverseVar;
    private PImage tankHead;

    private PVector endOfGun;
    private PVector forceVector;

    private boolean enabled = true;
    private boolean spacePressed = false;
    private float force = 0;

    public TankInput(PApplet pApplet, Tank gameObject) {
        this.pApplet = pApplet;
        this.gameObject = gameObject;
        this.moveVector = new PVector(0, 0);
        this.currAngle = 0;
        this.inverseVar = gameObject.getInverseVar();
        this.tankHead = gameObject.getTankHead();
    }

    public void update(float moveSpeed){
        if (!enabled)
            return;

        move(moveSpeed);
        drawHead();
        shoot();
    }

    private void move(float moveSpeed){
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

    private float getAngleToMouse(){
        PVector fromHeadToMouse = new PVector(inverseVar*(pApplet.mouseX - (gameObject.position.x + Tank.TANK_ROT_X)),
                pApplet.mouseY - (gameObject.position.y + Tank.TANK_ROT_Y) + 15);
        currAngle = PVector.angleBetween(new PVector(1, 0), fromHeadToMouse);
        if (pApplet.mouseY - (gameObject.position.y + Tank.TANK_ROT_Y) + 15 < 0)
            currAngle = -currAngle;

        return currAngle;
    }

    private void drawHead(){
        pApplet.pushMatrix();
        if (inverseVar == -1)
            pApplet.scale(-1, 1);
        if (pApplet.mousePressed && pApplet.mouseY - (gameObject.position.y + Tank.TANK_ROT_Y) < 0) {
            if (inverseVar == -1) {
                pApplet.translate(inverseVar*(gameObject.position.x + Tank.TANK_ROT_X) - Tank.INVERSE_DIFF, gameObject.position.y + Tank.TANK_ROT_Y);
            }
            else{
                pApplet.translate(inverseVar*(gameObject.position.x + Tank.TANK_ROT_X), gameObject.position.y + Tank.TANK_ROT_Y);
            }

            pApplet.rotate(getAngleToMouse());
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

    private void shoot(){
        if (pApplet.keyPressed && pApplet.key == ' '){
            spacePressed = true;
            if (force < MAX_FORCE)
                force += 0.2f;

            PVector rotPoint = new PVector(gameObject.position.x + Tank.TANK_ROT_X, gameObject.position.y);
            PVector toEndOfGun = new PVector(Tank.HEAD_WIDTH + 40, 0);
            toEndOfGun.rotate(currAngle);
            endOfGun = rotPoint.add(toEndOfGun);
            forceVector = new PVector(force, 0).rotate(currAngle);

            pApplet.stroke(0);
            PVector lineForceVector = forceVector.copy().mult(10);
            pApplet.line(rotPoint.x, rotPoint.y, rotPoint.x + lineForceVector.x, rotPoint.y + lineForceVector.y);
        }
        else{
            if (spacePressed){
                gameObject.shell = new Shell(pApplet, endOfGun.x, endOfGun.y);
                gameObject.shell.addForce(forceVector);
                spacePressed = false;
                force = 0;
                GameController.getInstance().changeTurns();
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

import processing.core.PApplet;
import processing.core.PImage;

public class Tank extends GameObject{
    public static final float SIZE_MULTIPLIER = 0.3f;
    public static final float TANK_ROT_X = 38 * SIZE_MULTIPLIER;
    public static final float TANK_ROT_Y = 37 * SIZE_MULTIPLIER;
    public static final float HEAD_WIDTH = 38.5f * SIZE_MULTIPLIER;
    public static final float HEAD_HEIGHT = 37 * SIZE_MULTIPLIER;
    public static final float INVERSE_DIFF = 55;

    private PImage tankBottom;
    private PImage tankHead;
    private int inverseVar;

    Shell shell;

    public Tank(PApplet pApplet, float x, float y, boolean inverse) {
        super(pApplet, x, y);
        if (inverse)
            inverseVar = -1;
        else
            inverseVar = 1;

        tankBottom = pApplet.loadImage("TankBottom.png");
        tankHead = pApplet.loadImage("TankHead.png");
        shell = null;
        collider = new BoxCollider(this, x, y, tankBottom.width*SIZE_MULTIPLIER, tankBottom.height*SIZE_MULTIPLIER);
        input = new TankInput(pApplet, this);
        gravity = true;
    }

    public void drawRaw(){
        pApplet.pushMatrix();
        pApplet.scale(inverseVar, 1);
        drawHead();
        pApplet.image(tankBottom, inverseVar*position.x, position.y, inverseVar*tankHead.width * SIZE_MULTIPLIER, tankHead.height * SIZE_MULTIPLIER);
        if (shell != null){
            if (shell.collider.collided) {
                destroyTouched(shell);
                destroy(shell);
                shell = null;
            }
        }
        pApplet.popMatrix();
    }

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

//    private void drawHead(){
//        pApplet.pushMatrix();
//        if (pApplet.mousePressed && pApplet.mouseY - (position.y + Tank.TANK_ROT_Y) < 0) {
//            if (inverseVar == -1) {
//                pApplet.translate(inverseVar*(position.x + Tank.TANK_ROT_X) - Tank.INVERSE_DIFF, position.y + Tank.TANK_ROT_Y);
//            }
//            else{
//                pApplet.translate(inverseVar*(position.x + Tank.TANK_ROT_X), position.y + Tank.TANK_ROT_Y);
//            }
//
//            pApplet.rotate(getAngleToMouse());
//            if (inverseVar == -1) {
//                pApplet.image(tankHead, inverseVar*(-Tank.HEAD_WIDTH) + Tank.INVERSE_DIFF, -Tank.HEAD_HEIGHT,
//                        inverseVar*(tankHead.width * Tank.SIZE_MULTIPLIER),
//                        tankHead.height * Tank.SIZE_MULTIPLIER);
//            }
//            else{
//                pApplet.image(tankHead, inverseVar*(-Tank.HEAD_WIDTH), -Tank.HEAD_HEIGHT,
//                        inverseVar*(tankHead.width * Tank.SIZE_MULTIPLIER),
//                        tankHead.height * Tank.SIZE_MULTIPLIER);
//            }
//        }
//        else {
//            pApplet.image(tankHead,
//                    inverseVar*position.x, position.y,
//                    inverseVar*(tankHead.width * Tank.SIZE_MULTIPLIER),
//                    tankHead.height * Tank.SIZE_MULTIPLIER);
//        }
//        pApplet.popMatrix();
//    }

    public PImage getTankHead() {
        return tankHead;
    }

    public int getInverseVar() {
        return inverseVar;
    }
}

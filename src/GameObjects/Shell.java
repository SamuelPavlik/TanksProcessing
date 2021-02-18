package GameObjects;

import Colliders.CircleCollider;
import Play.GameManager;
import processing.core.PApplet;
import processing.core.PVector;

public class Shell extends GameObject {
    private static final float RADIUS = 5;

    private PVector windVector;

    public Shell(PApplet pApplet, float x, float y, PVector windVector) {
        super(pApplet, x, y);
        this.gravity = true;
        this.collider = new CircleCollider(this, position.x, position.y, RADIUS);
        this.windVector = windVector;
    }

    @Override
    public void updateRaw() {
        //apply wind factor
        addForce(windVector);
        //resolve collision if there is one
        onCollision();
        //draw the shell
        pApplet.fill(0);
        pApplet.ellipse(position.x, position.y, 2*RADIUS, 2*RADIUS);
    }

    /**
     * adds vector to the current velocity vector
     * @param force vector to add
     */
    public void addForce(PVector force){
        velocity.add(force);
    }

    /**
     * destroys shell and changes whose turn it is
     */
    @Override
    public void destroy() {
        super.destroy();
        if (pApplet instanceof GameManager)
            ((GameManager)pApplet).changeTurns();
    }

    /**
     * destroys shell and everything it touches if in collision
     */
    private void onCollision(){
        if (collider.isCollided()){
            destroyTouched();
            destroy();
        }
    }
}

import processing.core.PApplet;
import processing.core.PVector;

public class Shell extends GameObject {
    public static final float RADIUS = 5;

    public Shell(PApplet pApplet, float x, float y) {
        super(pApplet, x, y);
        gravity = true;
        collider = new CircleCollider(this, position.x, position.y, RADIUS);
    }

    @Override
    public void drawRaw() {
        pApplet.fill(0);
        pApplet.ellipse(position.x, position.y, 2*RADIUS, 2*RADIUS);
    }

    public void addForce(PVector force){
        velocity.add(force);
    }

    private void onCollision(){
        if (collider.collided){
//            for (:
//                 ) {
//
//            }
        }
    }
}

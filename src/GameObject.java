import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    public static final float GRAVITY = 0.1f;
    public static List<GameObject> gameObjects = new ArrayList<>();

    PApplet pApplet;
    PVector position;
    PVector velocity;
    Collider collider;
    IInput input;
    boolean gravity;

    public GameObject(PApplet pApplet, float x, float y) {
        this.pApplet = pApplet;
        this.position = new PVector(x, y);
        this.velocity = new PVector(0, 0);

        gameObjects.add(this);
    }

    public void update(){
        if (gravity)
            velocity.add(new PVector(0, GRAVITY));
        if (input != null)
            input.update(0.2f);
        //must be check after input and gravity
        if (collider != null)
            collider.update();
        applyDrag();
        drawRaw();
        position.add(velocity);
    }

    public abstract void drawRaw();

    private void applyDrag(){
        if (collider != null){
            if (collider.collided){
                velocity.mult(0.7f);
            }
            else{
                velocity.mult(0.995f);
            }
        }
        else{
            velocity.mult(0.995f);
        }
    }

    public static void updateAll(){
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update();
        }
    }

    public static void destroyTouched(GameObject gameObject){
        for (GameObject obj: ((CircleCollider)gameObject.collider).touched) {
            Collider.colliders1.remove(obj.collider);
            gameObjects.remove(obj);
        }
    }

    public static void destroy(GameObject gameObject){
        if (gameObject.collider != null){
            Collider.colliders1.remove(gameObject.collider);
        }
        gameObjects.remove(gameObject);
    }

    //    public void move() {
    //        PVector moveVector = moveRaw();
    //        PVector gravityVector = getGravityVector();
    //        PVector collisionVector = getCollisionVector(moveVector, gravityVector);
    //        PVector resultVector = moveVector.add(gravityVector).add(collisionVector);
    //        position.add(resultVector);
    //    }

    /*private PVector moveByInput(float moveSpeed){
        float x = 0;
        if (input != null) {
            input.update(1);
        }

        return new PVector(x, 0);
    }

    private PVector getCollisionVector(PVector moveVector, PVector gravityVector){
        PVector resultVector = new PVector(0, 0);
        if (collider != null){
            if (collider.checkCollision()) {
                List<PVector> touchVectors = collider.getTouchVectors();
                if (moveVector.mag() != 0){
                    for (PVector tv: touchVectors) {
                        if (tv.add(moveVector).equals(new PVector(0, 0))){
                            resultVector.add(tv);
                        }
                    }
                }
                if (gravityVector.mag() != 0){
                    for (PVector tv: touchVectors) {
                        if (tv.add(gravityVector).equals(new PVector(0, 0))){
                            resultVector.add(tv);
                        }
                    }
                }
            }
        }

        return resultVector;
    }

    private PVector getGravityVector() {
        if (gravity)
            return new PVector(0, GRAVITY);
        else
            return new PVector(0, 0);
    }

    private PVector getWindVector(){
        return null;
    }*/
}

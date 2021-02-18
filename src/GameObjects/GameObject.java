package GameObjects;

import Colliders.CircleCollider;
import Colliders.Collider;
import Inputs.IInput;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    private static final float SCREEN_DELTA = 50;
    private static List<GameObject> gameObjects = new ArrayList<>();
    public static final float GRAVITY = 0.1f;

    PApplet pApplet;
    Collider collider;

    public PVector position;
    public PVector velocity;
    public IInput input;
    public boolean gravity;

    private boolean destroyed;

    public GameObject() {
        this.pApplet = null;
        this.position = new PVector();
        this.velocity = new PVector();
        this.destroyed = false;
    }

    public GameObject(PApplet pApplet, float x, float y) {
        this.pApplet = pApplet;
        this.position = new PVector(x, y);
        this.velocity = new PVector(0, 0);
        this.destroyed = false;

        gameObjects.add(this);
    }

    /**
     * updates game object based on its components and position
     */
    public void update(){
        //if out of bounds
        onOutOfBounds();
        //if gravity applies
        if (gravity)
            velocity.add(new PVector(0, GRAVITY));
        //if player or ai input is taken into account
        if (input != null)
            input.update(0.2f);
        //must be check after input and gravity to work
        //if collides with other objects
        if (collider != null)
            collider.update();
        //apply air drag
        applyDrag();
        //draws just object
        updateRaw();
        //adds accumulated velocity vector to the position vector
        position.add(velocity);
    }

    /**
     * removes object from the array list and and its collider too, if it has one
     */
    public void destroy(){
        if (this.collider != null){
            Collider.colliders1.remove(this.collider);
        }
        gameObjects.remove(this);
        this.destroyed = true;
    }

    /**
     * updates all objects in the array list
     */
    public static void updateAll(){
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update();
        }
    }

    /**
     * sets all objects in the array list to destroyed and resets it
     */
    public static void destroyAll(){
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).destroyed = true;
        }
        gameObjects = new ArrayList<>();
        Collider.colliders1 = new ArrayList<>();
    }

    /**
     * update object without components
     */
    public abstract void updateRaw();

    /**
     * destroys all objects touched by this object currently
     * works only for circle colliders
     */
    void destroyTouched(){
        for (GameObject obj: ((CircleCollider)this.collider).getTouched()) {
            obj.destroy();
        }
    }

    /**
     * applies movement resistance based on whether the object is touching anything or not
     */
    private void applyDrag(){
        if (collider != null){
            if (collider.isCollided()){
                velocity.mult(0.7f);
            }
            else{
                if (this instanceof Tank) {
                    velocity.mult(0.960f);
                } else {
                    velocity.mult(0.995f);
                }
            }
        }
        else{
            velocity.mult(0.995f);
        }
    }

    /**
     * destroys object if outside of the screen
     */
    private void onOutOfBounds(){
        if ((position.x + SCREEN_DELTA < 0 || position.x - SCREEN_DELTA > pApplet.width) || position.y > pApplet.height){
            this.destroy();
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}

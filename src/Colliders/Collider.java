package Colliders;

import GameObjects.GameObject;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public abstract class Collider {
    public static List<Collider> colliders1 = new ArrayList<>();

    GameObject gameObject;
    private PVector positionDiff;
    boolean collided;
    boolean lowerEdge = false;

    float colX;
    float colY;
    List<PVector> touchVectors;

    Collider(GameObject gameObject, float colX, float colY) {
        this.gameObject = gameObject;
        this.colX = colX;
        this.colY = colY;
        this.positionDiff = new PVector(gameObject.position.x - colX, gameObject.position.y - colY);
        this.collided = false;
        colliders1.add(0, this);
    }

    /**
     * checks if collider collides with any other colliders
     * @return true if in collision with another object
     */
    public abstract boolean checkCollision();

    /**
     * updates collider based on object's velocity and collision with other objects
     */
    public void update(){
        updatePosition();
        PVector resultVector = new PVector(0, 0);
        if (checkCollision()) {
            for (PVector tv : touchVectors) {
                //in case of touching from lower edge
                if (tv.y < 0) {
                    gameObject.velocity.y = 0;
                }
                //in case of touching from sides
                else {
                    if ((gameObject.velocity.x > 0 && tv.x < 0) || (gameObject.velocity.x < 0 && tv.x > 0)){
                        gameObject.velocity.x = 0;
                    }
                }
            }
        }
        gameObject.velocity.add(resultVector);
    }

    /**
     * update position of the collider so that it's in the same distance as when
     * initialized
     */
    private void updatePosition(){
        this.colX = gameObject.position.x + positionDiff.x;
        this.colY = gameObject.position.y + positionDiff.y;
    }

    public boolean isCollided() {
        return collided;
    }

    public float getColX() {
        return colX;
    }

    public float getColY() {
        return colY;
    }

    public static void resetColliders(){
        colliders1 = new ArrayList<>();
    }
}

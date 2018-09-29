import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public abstract class Collider {
    static List<Collider> colliders1 = new ArrayList<>();
    static int counter = 0;

    GameObject gameObject;
    private PVector positionDiff;
    private int id;
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
        this.id = counter;
        colliders1.add(this.id, this);
    }

    public abstract boolean checkCollision();

    public List<PVector> getTouchVectors() {
        return touchVectors;
    }

    public void update(){
        updatePosition();
        PVector resultVector = new PVector(0, 0);
        if (checkCollision()) {
            List<PVector> touchVectors = getTouchVectors();
            for (PVector tv : touchVectors) {
                //in case of touching from lower edge
                if (tv.y < 0) {
//                    resultVector.add(tv.mult(gameObject.velocity.y));
                    gameObject.velocity.y = 0;
                }
                //in case of touching from sides
                else {
//                    resultVector.add(tv.mult(gameObject.velocity.x));
                    if ((gameObject.velocity.x > 0 && tv.x < 0) || (gameObject.velocity.x < 0 && tv.x > 0)){
//                        resultVector.add(tv.mult(gameObject.velocity.x));
                        gameObject.velocity.x = 0;
                    }
                }
            }
        }
        gameObject.velocity.add(resultVector);
    }

    public void updatePosition(){
        this.colX = gameObject.position.x + positionDiff.x;
        this.colY = gameObject.position.y + positionDiff.y;
    }

    public boolean isCollided() {
        return collided;
    }

    public int getId() {
        return id;
    }
}

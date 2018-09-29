import processing.core.PVector;
import java.util.ArrayList;

public class BoxCollider extends Collider {
    private static final float DELTA = 5;

    float width;
    float height;

    public BoxCollider(GameObject gameObject, float x, float y, float width, float height) {
        super(gameObject, x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean checkCollision() {
        touchVectors = new ArrayList<>();
        collided = false;
        lowerEdge = false;
        for (Collider m: colliders1) {
            if (m.equals(this))
                continue;
            //in case of the other object being circle
            if (m instanceof CircleCollider){
                CircleCollider circle = (CircleCollider) m;
                if ((circle.colY + circle.radius > this.colY)
                        && (circle.colY - circle.radius < this.colY + this.height)){
                    if ((circle.colX + circle.radius > this.colX)
                            && (circle.colX - circle.radius < this.colX + this.width)){
                        PVector touchVector = null;
                        //if touching from lower edge
                        if ((circle.colY > this.colY + this.height) && (circle.colX > this.colX) && (circle.colX + width < this.colX)){
                            lowerEdge = true;
                            touchVector = new PVector(0, -1);
                        }
                        //if touching from upper edge
                        else if ((circle.colY < this.colY) && (circle.colX > this.colX) && (circle.colX + width < this.colX)){
                            touchVector = new PVector(0, 1);
                        }
                        //if touching from sides
                        else if ((circle.colY > this.colY) && (circle.colY < this.colY + this.height)){
                            //from left
                            if (circle.colX < this.colX) {
                                touchVector = new PVector(1, 0);
                            }
                            //from right
                            else{
                                touchVector = new PVector(-1, 0);
                            }
                        }

                        if (touchVector != null) {
                            collided = true;
                            touchVectors.add(touchVector);
                        }
                    }
                }
            }
            //in case of the other object being rectangle
            else if (m instanceof BoxCollider) {
                BoxCollider box = (BoxCollider) m;
                //collision from upper edge
                if ((box.colY + box.height > this.colY)
                        && (box.colY < this.colY + this.height)){
                    if ((box.colX + box.width > this.colX)
                            && (box.colX < this.colX + this.width)){
                        PVector touchVector = null;
                        //touching from upper edge
                        if (Math.abs(this.colY - (box.colY + box.height)) < DELTA){
                            touchVector = (new PVector(0, 1));
                        }
                        //touching from lower edge
                        else if (Math.abs(this.colY + this.height - box.colY) < DELTA){
                            touchVector = new PVector(0, -1);
                            lowerEdge = true;
                        }
                        //touching from left
                        else if (Math.abs(this.colX - (box.colX + box.width)) < DELTA){
                            touchVector = (new PVector(1, 0));
                        }
                        //touching from right
                        else if (Math.abs(this.colX + this.width - box.colX) < DELTA){
                            touchVector = (new PVector(-1, 0));
                        }
                        if (touchVector != null) {
                            collided = true;
                            touchVectors.add(touchVector);
                        }
                    }
                }
            }
        }

        return collided;
    }
}
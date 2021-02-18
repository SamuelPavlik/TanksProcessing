package Colliders;

import GameObjects.GameObject;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class CircleCollider extends Collider {
    float radius;

    private List<GameObject> touched;

    public CircleCollider(GameObject gameObject, float x, float y, float radius) {
        super(gameObject, x, y);
        this.radius = radius;
        this.touched = new ArrayList<>();
    }

    /**
     * checks if collider collides with any other colliders and initializes the fields touchVectors and touched
     * @return true if in collision with another object
     */
    @Override
    public boolean checkCollision() {
        touched = new ArrayList<>();
        touchVectors = new ArrayList<>();
        PVector touchVector = null;

        for (Collider m : colliders1) {
            if (m.equals(this))
                continue;
            if (m instanceof CircleCollider){
                CircleCollider circle = (CircleCollider) m;
                PVector dist = new PVector(this.colX - circle.colX, this.colY - circle.colY);
                if (dist.mag() < this.radius + circle.radius){
                    collided = true;
                    touchVector = new PVector(this.colX - circle.colX, this.colY - circle.colY).normalize();
                    touchVectors.add(touchVector);
                    touched.add(m.gameObject);
                }
            }
            else if (m instanceof BoxCollider) {
                BoxCollider box = (BoxCollider) m;
                if ((this.colY + this.radius > box.colY)
                        && (this.colY - this.radius < box.colY + box.height)){
                    if ((this.colX + this.radius > box.colX)
                            && (this.colX - this.radius < box.colX + box.width)){
                        collided = true;
                        if ((this.colX + this.radius > box.colX)
                                && (this.colX - this.radius < box.colX + box.width)) {
                            //if touching from upper edge
                            if ((this.colY - this.radius < box.colY + box.height) && (this.colY > box.colY)){
                                touchVector = new PVector(0, 1);
                            }
                            //if touching from lower edge
                            else if ((this.colY + this.radius > box.colY) && (this.colY < box.colY)){
                                lowerEdge = true;
                                touchVector = new PVector(0, -1);
                            }
                        }

                        //if touching from sides
                        else if ((this.colY + this.radius > box.colY) && (this.colY - this.radius < box.colY + box.height)){
                            //from right
                            if (this.colX + this.radius > box.colX) {
                                touchVector = new PVector(-1, 0);
                            }
                            //from left
                            else if (this.colX - this.radius < box.colX + box.width){
                                touchVector = new PVector(1, 0);
                            }
                        }

                        if (touchVector != null) {
                            touchVectors.add(touchVector);
                            touched.add(m.gameObject);
                        }
                    }
                }
            }
        }

        return collided;
    }

    public List<GameObject> getTouched() {
        return touched;
    }
}

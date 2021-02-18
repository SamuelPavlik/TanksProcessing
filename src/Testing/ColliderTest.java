package Testing;

import Colliders.BoxCollider;
import Colliders.CircleCollider;
import GameObjects.GameObject;
import GameObjects.Ground;
import org.junit.Test;
import processing.core.PVector;

import static org.junit.Assert.*;

public class ColliderTest {

    @Test
    public void update() {
        GameObject obj = new Ground();
        obj.position = new PVector(100, 100);
        GameObject obj1 = new Ground();
        obj1.position = new PVector(100, 105);
        GameObject obj2 = new Ground();
        obj1.position = new PVector(95, 100);

        CircleCollider circle = new CircleCollider(obj, 100, 100, 10);
        circle.update();
        assertEquals(circle.getTouched().size(), 0);

        BoxCollider box = new BoxCollider(obj1, 100, 105, 10, 10);
        circle.update();
        assertEquals(circle.getTouched().size(), 1);
        assertTrue(circle.getTouched().contains(obj1));

        CircleCollider otherCircle = new CircleCollider(obj2, 95, 100, 10);
        circle.update();
        assertEquals(circle.getTouched().size(), 2);
        assertTrue(circle.getTouched().contains(obj1));
        assertTrue(circle.getTouched().contains(obj2));

    }
}
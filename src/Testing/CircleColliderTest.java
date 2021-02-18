package Testing;

import Colliders.BoxCollider;
import Colliders.CircleCollider;
import Colliders.Collider;
import GameObjects.GameObject;
import GameObjects.Ground;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import processing.core.PVector;

import static org.junit.Assert.*;

public class CircleColliderTest {
    CircleCollider circle;
    BoxCollider box;
    CircleCollider otherCircle;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        Collider.resetColliders();
    }

    @Test
    public void checkCollisionCircleNone() {
        circle = new CircleCollider(new Ground(), 0, 0, 10);
        otherCircle = new CircleCollider(new Ground(), 100, 100, 10);
        assertFalse(circle.checkCollision());
    }

    @Test
    public void checkCollisionBoxNone() {
        circle = new CircleCollider(new Ground(), 0, 0, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertFalse(circle.checkCollision());
    }

    @Test
    public void checkCollisionCircle() {
        circle = new CircleCollider(new Ground(), 95, 100, 10);
        otherCircle = new CircleCollider(new Ground(), 100, 100, 10);
        assertTrue(circle.checkCollision());
    }

    @Test
    public void checkCollisionBoxLeft() {
        circle = new CircleCollider(new Ground(), 100, 100, 10);
        box = new BoxCollider(new Ground(), 95, 100, 10, 10);
        assertTrue(circle.checkCollision());
    }

    @Test
    public void checkCollisionBoxRight() {
        circle = new CircleCollider(new Ground(), 100, 100, 10);
        box = new BoxCollider(new Ground(), 105, 100, 10, 10);
        assertTrue(circle.checkCollision());
    }

    @Test
    public void checkCollisionBoxUp() {
        circle = new CircleCollider(new Ground(), 100, 100, 10);
        box = new BoxCollider(new Ground(), 100, 95, 10, 10);
        assertTrue(circle.checkCollision());
    }

    @Test
    public void checkCollisionBoxDown() {
        circle = new CircleCollider(new Ground(), 100, 100, 10);
        box = new BoxCollider(new Ground(), 100, 105, 10, 10);
        assertTrue(circle.checkCollision());
    }

    @Test
    public void getTouched() {
        GameObject obj1 = new Ground();
        GameObject obj2 = new Ground();

        circle = new CircleCollider(new Ground(), 100, 100, 10);
        assertEquals(circle.getTouched().size(), 0);

        box = new BoxCollider(obj1, 100, 105, 10, 10);
        circle.checkCollision();
        assertEquals(circle.getTouched().size(), 1);
        assertTrue(circle.getTouched().contains(obj1));

        otherCircle = new CircleCollider(obj2, 95, 100, 10);
        circle.checkCollision();
        assertEquals(circle.getTouched().size(), 2);
        assertTrue(circle.getTouched().contains(obj1));
        assertTrue(circle.getTouched().contains(obj2));
    }
}
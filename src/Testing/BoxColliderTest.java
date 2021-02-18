package Testing;

import Colliders.BoxCollider;
import Colliders.CircleCollider;
import Colliders.Collider;
import GameObjects.GameObject;
import GameObjects.Ground;
import GameObjects.Tank;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoxColliderTest {
    CircleCollider circle;
    BoxCollider box;
    BoxCollider otherBox;

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
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertFalse(box.checkCollision());
    }

    @Test
    public void checkCollisionBoxNone() {
        otherBox = new BoxCollider(new Ground(), 0, 0, 10, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertFalse(box.checkCollision());
    }

    @Test
    public void checkCollisionCircleLeft() {
        circle = new CircleCollider(new Ground(), 95, 100, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }

    @Test
    public void checkCollisionCircleRight() {
        circle = new CircleCollider(new Ground(), 105, 100, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }

    @Test
    public void checkCollisionCircleUp() {
        circle = new CircleCollider(new Ground(), 100, 95, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }

    @Test
    public void checkCollisionCircleDown() {
        circle = new CircleCollider(new Ground(), 100, 105, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }

    @Test
    public void checkCollisionBoxLeft() {
        otherBox = new BoxCollider(new Ground(), 95, 100, 10, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }

    @Test
    public void checkCollisionBoxRight() {
        otherBox = new BoxCollider(new Ground(), 105, 100, 10, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }

    @Test
    public void checkCollisionBoxUp() {
        otherBox = new BoxCollider(new Ground(), 100, 95, 10, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }

    @Test
    public void checkCollisionBoxDown() {
        otherBox = new BoxCollider(new Ground(), 100, 105, 10, 10);
        box = new BoxCollider(new Ground(), 100, 100, 10, 10);
        assertTrue(box.checkCollision());
    }
}
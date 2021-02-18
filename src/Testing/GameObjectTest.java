package Testing;

import Colliders.Collider;
import GameObjects.GameObject;
import GameObjects.Ground;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import processing.core.PVector;

import static org.junit.Assert.*;

public class GameObjectTest {
    final static float drag = 0.995f;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void update() {

        Ground ground = new Ground();
        ground.position = new PVector(-100, -100);
        ground.update();
        assertTrue(ground.isDestroyed());

        ground = new Ground();
        ground.gravity = true;
        ground.update();
        assertEquals(GameObject.GRAVITY*drag, ground.velocity.y, 0);
        GameObject.destroyAll();
    }

    @Test
    public void destroy() {
        Ground ground = new Ground();
        assertFalse(ground.isDestroyed());
        ground.destroy();
        assertTrue(ground.isDestroyed());
    }

    @Test
    public void updateAll() {
        Ground ground1 = new Ground(100, 100);
        Ground ground2 = new Ground(0, 100);
        ground1.gravity = true;
        ground2.gravity = true;
        assertEquals(0, ground1.velocity.y, 0);
        assertEquals(0, ground2.velocity.y, 0);

        GameObject.updateAll();
        assertEquals(GameObject.GRAVITY*drag, ground1.velocity.y, 0);
        assertEquals(GameObject.GRAVITY*drag, ground2.velocity.y, 0);
    }

    @Test
    public void destroyAll() {
        Ground ground1 = new Ground(100, 100);
        Ground ground2 = new Ground(0, 100);
        assertFalse(ground1.isDestroyed());
        assertFalse(ground2.isDestroyed());
        GameObject.destroyAll();

        assertTrue(ground1.isDestroyed());
        assertTrue(ground2.isDestroyed());
    }
}
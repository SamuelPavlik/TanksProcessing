package Testing;

import GameObjects.GameObject;
import GameObjects.Ground;
import GameObjects.Shell;
import Play.GameManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import processing.core.PVector;

import static org.junit.Assert.*;

public class ShellTest {
    Shell shell;

    @Before
    public void setUp() throws Exception {
        shell = new Shell(GameManager.getInstance(), 0, 0, new PVector());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void updateRaw() {
        PVector windVector = new PVector(1, 1);
        shell = new Shell(GameManager.getInstance(), 0, 0, windVector);
        GameObject obj = new Ground();
        try {
            shell.updateRaw();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        assertEquals(shell.velocity, windVector);
    }

    @Test
    public void addForce() {
        PVector vector = new PVector(1, 1);
        shell.addForce(vector);
        assertEquals(shell.velocity, vector);
        shell.addForce(vector);
        assertEquals(shell.velocity, vector.add(vector));
    }

    @Test
    public void destroy() {
        try {
            shell.destroy();
        } catch (NullPointerException e) {
        }
        assertTrue(shell.isDestroyed());
    }
}
package GameObjects;

import Colliders.BoxCollider;
import Play.GameManager;
import processing.core.PApplet;
import processing.core.PImage;

public class Ground extends GameObject {
    public static final float WIDTH = 32f;
    public static final float HEIGHT = 32f;
    public static final float SIZE_MULTIPLIER = 0.1f;

    private PImage grassImg;

    public Ground() {
        super(GameManager.getInstance(), 0, 0);
        this.grassImg = null;
        this.collider = new BoxCollider(this, 0, 0, WIDTH, HEIGHT);
    }

    public Ground(float x, float y) {
        super(GameManager.getInstance(), 0, 0);
        this.grassImg = null;
        this.collider = new BoxCollider(this, x, y, WIDTH, HEIGHT);
    }

    public Ground(PApplet pApplet, float x, float y) {
        super(pApplet, x, y);
        this.grassImg = pApplet.loadImage("grass.jpg");
        this.collider = new BoxCollider(this, x, y, WIDTH, HEIGHT);
    }

    @Override
    public void updateRaw() {
        if (grassImg != null) {
            pApplet.image(grassImg, position.x, position.y, grassImg.width * SIZE_MULTIPLIER,
                        grassImg.height * SIZE_MULTIPLIER);
        }
    }
}

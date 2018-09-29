import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;

public class Ground extends GameObject {
    public static final float WIDTH = 32f;
    public static final float HEIGHT = 32f;

    private PImage grassImg;
//    private float width;
//    private float height;

    public Ground(PApplet pApplet, float x, float y) {
        super(pApplet, x, y);
        this.grassImg = pApplet.loadImage("grass.jpg");
//        this.width = grassImg.width * 0.1f;
//        this.height = grassImg.height * 0.1f;
        this.collider = new BoxCollider(this, x, y, WIDTH, HEIGHT);
    }

    @Override
    public void drawRaw() {
        pApplet.image(grassImg, position.x, position.y, grassImg.width * 0.1f, grassImg.height * 0.1f);
    }
}

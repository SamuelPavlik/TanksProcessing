package UIPcg;

import processing.core.PApplet;

/**
 * source: https://blog.startingelectronics.com/a-simple-button-for-processing-language-code/
 */
public class Label {
    private PApplet pApplet;
    private String label;
    private float x;    // top left corner x position
    private float y;    // top left corner y position
    private float width;    // width of button
    private float height;    // height of button
    private boolean isButton;
    private boolean enabled = true;

    public Label(PApplet pApplet, String labelB, float xpos, float ypos, float widthB, float heightB, boolean isButton) {
        this.pApplet = pApplet;
        this.label = labelB;
        this.x = xpos;
        this.y = ypos;
        this.width = widthB;
        this.height = heightB;
        this.isButton = isButton;
    }

    public void update() {
        if (!enabled)
            return;

        if (isButton && mouseIsOver()) {
            pApplet.fill(218);
        } else {
            pApplet.fill(250);
        }
        pApplet.stroke(141);
        pApplet.rect(x, y, width, height, 10);
        pApplet.textAlign(PApplet.CENTER, PApplet.CENTER);
        pApplet.fill(0);
        pApplet.text(label, x + (width / 2), y + (height / 2));
    }

    public boolean mouseIsOver() {
        if (pApplet.mouseX > x && pApplet.mouseX < (x + width) && pApplet.mouseY > y && pApplet.mouseY < (y + height)) {
            return true;
        }
        return false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

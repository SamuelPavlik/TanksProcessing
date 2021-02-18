package UIPcg;

import processing.core.PApplet;

public abstract class UI {

    PApplet pApplet;
    boolean enabled = true;

    public UI(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public abstract void update();

    public boolean isEnabled() {
        return enabled;
    }
}

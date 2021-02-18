package Inputs;

import GameObjects.Tank;
import processing.core.PApplet;

public interface TankInputBuilder {
    TankInput getTankInput(PApplet pApplet, Tank gameObject);
}

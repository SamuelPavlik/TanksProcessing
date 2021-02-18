package Inputs;

import GameObjects.Tank;
import processing.core.PApplet;

public class TankInputPlayerBuilder implements TankInputBuilder {

    @Override
    public TankInput getTankInput(PApplet pApplet, Tank gameObject) {
        return new TankInputPlayer(pApplet, gameObject);
    }
}

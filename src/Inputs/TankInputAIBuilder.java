package Inputs;

import GameObjects.Tank;
import processing.core.PApplet;

public class TankInputAIBuilder implements TankInputBuilder {

    @Override
    public TankInput getTankInput(PApplet pApplet, Tank gameObject) {
        return new TankInputAI(pApplet, gameObject);
    }
}

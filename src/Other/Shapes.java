package Other;

import processing.core.PApplet;
import processing.core.PVector;

public class Shapes {
    /**
     * draws arrow with specified parameters
     * source: http://gaelbn.com/processing/2014/01/10/a_simple_way_to_draw_arrows_in_Processing.html
     */
    public static void drawArrow(PApplet pApplet, float x0, float y0, float x1, float y1, float beginHeadSize, float endHeadSize, boolean filled) {
        PVector d = new PVector(x1 - x0, y1 - y0);
        d.normalize();

        float coeff = 1.5f;

        pApplet.strokeCap(PApplet.SQUARE);

        pApplet.line(x0+d.x*beginHeadSize*coeff/(filled?1.0f:1.75f),
                y0+d.y*beginHeadSize*coeff/(filled?1.0f:1.75f),
                x1-d.x*endHeadSize*coeff/(filled?1.0f:1.75f),
                y1-d.y*endHeadSize*coeff/(filled?1.0f:1.75f));

        float angle = PApplet.atan2(d.y, d.x);

        if (filled) {
            // begin head
            pApplet.pushMatrix();
            pApplet.translate(x0, y0);
            pApplet.rotate(angle+PApplet.PI);
            pApplet.triangle(-beginHeadSize*coeff, -beginHeadSize,
                    -beginHeadSize*coeff, beginHeadSize,
                    0, 0);
            pApplet.popMatrix();
            // end head
            pApplet.pushMatrix();
            pApplet.translate(x1, y1);
            pApplet.rotate(angle);
            pApplet.triangle(-endHeadSize*coeff, -endHeadSize,
                    -endHeadSize*coeff, endHeadSize,
                    0, 0);
            pApplet.popMatrix();
        }
        else {
            // begin head
            pApplet.pushMatrix();
            pApplet.translate(x0, y0);
            pApplet.rotate(angle+PApplet.PI);
            pApplet.strokeCap(PApplet.ROUND);
            pApplet.line(-beginHeadSize*coeff, -beginHeadSize, 0, 0);
            pApplet.line(-beginHeadSize*coeff, beginHeadSize, 0, 0);
            pApplet.popMatrix();
            // end head
            pApplet.pushMatrix();
            pApplet.translate(x1, y1);
            pApplet.rotate(angle);
            pApplet.strokeCap(PApplet.ROUND);
            pApplet.line(-endHeadSize*coeff, -endHeadSize, 0, 0);
            pApplet.line(-endHeadSize*coeff, endHeadSize, 0, 0);
            pApplet.popMatrix();
        }
    }

}

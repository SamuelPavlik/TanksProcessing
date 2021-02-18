package UIPcg;

import Other.Shapes;
import Play.GameManager;
import processing.core.PApplet;
import processing.core.PVector;

public class GameUI extends UI{
    private static final String START_GAME1 = "PLAYER VS PLAYER";
    private static final String START_GAME2 = "PLAYER VS AI";
    private static final String EXIT = "EXIT";
    private static final float MENU_TEXT_SIZE = 20;
    private static final String SCORE_TEXT = "SCORE: ";
    private static final float SCORE_TEXT_SIZE = 20;
    private static final float SCORE_POS_DIFF = 50;
    private static final String ROUND_TEXT = "ROUND WON BY TANK ";
    private static final float ROUND_TEXT_SIZE = 30;
    private static final String GAME_TEXT = "GAME WON BY TANK ";

    private static final float ARROW_X = 130;
    private static final float ARROW_Y = 150;

    private Label startButton;
    private Label aiButton;
    private Label exitButton;
    private Label scoreLabel1;
    private Label scoreLabel2;
    private Label roundLabel;

    private boolean playerStarted = false;
    private boolean aiStarted = false;

    public GameUI(PApplet pApplet) {
        super(pApplet);
        float xCenter = pApplet.width / 2 - (START_GAME1.length() * MENU_TEXT_SIZE) / 2;
        float yCenter = pApplet.height / 2 - (MENU_TEXT_SIZE / 2);
        this.startButton = new Label(pApplet, START_GAME1, xCenter, yCenter,
                                START_GAME1.length()* MENU_TEXT_SIZE, MENU_TEXT_SIZE,
                                true);
        this.aiButton = new Label(pApplet, START_GAME2, xCenter, yCenter + 30,
                START_GAME1.length()* MENU_TEXT_SIZE, MENU_TEXT_SIZE,
                true);
        this.exitButton = new Label(pApplet, EXIT, xCenter, yCenter + 60,
                                START_GAME1.length()* MENU_TEXT_SIZE, MENU_TEXT_SIZE,
                                true);
        this.scoreLabel1 = new Label(pApplet, SCORE_TEXT + 0, SCORE_POS_DIFF, SCORE_POS_DIFF,
                                    (SCORE_TEXT.length() + 1)*SCORE_TEXT_SIZE,
                                            SCORE_TEXT_SIZE, false);
        this.scoreLabel2 = new Label(pApplet, SCORE_TEXT + 0,
                                pApplet.width - (SCORE_POS_DIFF + (SCORE_TEXT.length() + 1)*SCORE_TEXT_SIZE), SCORE_POS_DIFF,
                                (SCORE_TEXT.length() + 1)*SCORE_TEXT_SIZE,
                                        SCORE_TEXT_SIZE, false);

        float labelWidth = (ROUND_TEXT.length() + 1) * ROUND_TEXT_SIZE;
        xCenter = pApplet.width/2 - labelWidth/2;
        yCenter = pApplet.height/2 - ROUND_TEXT_SIZE;
        this.roundLabel = new Label(pApplet, "", xCenter, yCenter, labelWidth, ROUND_TEXT_SIZE, false);
        this.roundLabel.setEnabled(false);
    }

    /**
     * update game menu and ui
     */
    public void update(){
        if (!enabled)
            return;

        if (!playerStarted && !aiStarted) {
            startButton.update();
            aiButton.update();
            exitButton.update();
            if (pApplet.mousePressed) {
                if (startButton.mouseIsOver())
                    playerStarted = true;
                else if (aiButton.mouseIsOver())
                    aiStarted = true;
                else if (exitButton.mouseIsOver())
                    System.exit(0);
            }
        }
        else {
            scoreLabel1.update();
            scoreLabel2.update();
            roundLabel.update();
            drawWindArrow(GameManager.getInstance().getWindVector());
        }
    }

    /**
     * draw wind arrow
     * @param windVector
     */
    private void drawWindArrow(PVector windVector){
        PVector wv = windVector.copy().mult(500);
        pApplet.fill(0);
        pApplet.stroke(0);
        Shapes.drawArrow(pApplet, ARROW_X - wv.x, ARROW_Y - wv.y, ARROW_X + wv.x, ARROW_Y + wv.y, 0, 4, true);
        pApplet.fill(255);
        pApplet.ellipse(ARROW_X, ARROW_Y, 5, 5);
    }

    /**
     * if round won, reset scores and display 'round won' label
     * @param tank which tank won
     * @param score tank's score
     */
    public void onRoundWon(int tank, int score){
        this.roundLabel.setLabel(ROUND_TEXT + tank);
        roundLabel.setEnabled(true);
        if (tank == 1)
            this.scoreLabel1.setLabel(SCORE_TEXT + score);
        else
            this.scoreLabel2.setLabel(SCORE_TEXT + score);
    }

    /**
     * if game won, reset scores and display 'game won' label
     * @param tank which tank won
     * @param score tank's score
     */
    public void onGameWon(int tank, int score){
        this.roundLabel.setLabel(GAME_TEXT + tank);
        if (tank == 1)
            this.scoreLabel1.setLabel(SCORE_TEXT + score);
        else
            this.scoreLabel2.setLabel(SCORE_TEXT + score);
        roundLabel.setEnabled(true);
    }

    /**
     * disable round label
     */
    public void newRound(){
        roundLabel.setEnabled(false);
    }

    public boolean isPlayerStarted() {
        return playerStarted;
    }

    public void setPlayerStarted(boolean playerStarted) {
        this.playerStarted = playerStarted;
    }

    public boolean isAiStarted() {
        return aiStarted;
    }

    public void setAiStarted(boolean aiStarted) {
        this.aiStarted = aiStarted;
    }
}

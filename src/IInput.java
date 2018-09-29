import processing.core.PApplet;

public interface IInput {
    void update(float moveSpeed);
    boolean isEnabled();
    void setEnabled(boolean enabled);
}

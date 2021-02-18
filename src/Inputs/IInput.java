package Inputs;

public interface IInput {
    void update(float moveSpeed);
    boolean isEnabled();
    void setEnabled(boolean enabled);
}

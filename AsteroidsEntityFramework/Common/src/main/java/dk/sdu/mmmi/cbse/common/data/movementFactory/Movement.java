package dk.sdu.mmmi.cbse.common.data.movementFactory;

public class Movement {
    public boolean up = false;
    public boolean right = false;
    public boolean left = false;

    public Movement() {
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}

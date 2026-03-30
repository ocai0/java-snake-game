import java.awt.Graphics;

public abstract class Boost {
    protected int x;
    protected int y;

    public Boost(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void draw(Graphics g);

    public abstract void applyEffect(GamePanel game);
}
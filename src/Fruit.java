import java.awt.Graphics;

public abstract class Fruit {
    protected int x;
    protected int y;
    protected int points;

    public Fruit(int x, int y, int points) {
        this.x = x;
        this.y = y;
        this.points = points;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getPoints() { return points; }

    public abstract void applyEffect(GamePanel game);
}
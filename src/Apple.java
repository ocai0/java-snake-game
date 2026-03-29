import java.awt.*;

public class Apple extends Fruit {

    public Apple(int x, int y) {
        super(x, y, 10);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x * GamePanel.UNIT_SIZE, y * GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
    }

    @Override
    public void applyEffect(GamePanel game) {
        game.player.bodyParts++;
    }
}
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Pear extends Fruit {
    protected BufferedImage sprite;

    public Pear(int x, int y) {
        super(x, y, -10);
        try {
            this.sprite = ImageIO.read(new File("./sprites/pera.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void draw(Graphics g) {
        int uX = x * GamePanel.UNIT_SIZE;
        int uY = y * GamePanel.UNIT_SIZE;
        if(this.sprite == null) {
            g.setColor(Color.GREEN);
            g.fillOval(uX, uY, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
        }
        else {
            g.drawImage(this.sprite, uX, uY, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
        }
    }

    @Override
    public void applyEffect(GamePanel game) {
        game.addToScore(points);
        game.player.shrink();
    }

    @Override
    public String toString(){
        return "This is an Pear class that gives " + points + " points";
    }
}
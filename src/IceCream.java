import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class IceCream extends Boost {
    protected BufferedImage sprite;

    public IceCream(int x, int y) {
        super(x, y);
        try {
            this.sprite = ImageIO.read(new File("./sprites/sorvete.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        int uX = x * GamePanel.UNIT_SIZE;
        int uY = y * GamePanel.UNIT_SIZE;

        if (this.sprite == null) {
            g.setColor(Color.PINK);
            g.fillOval(uX, uY, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
        } else {
            g.drawImage(this.sprite, uX, uY, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
        }
    }

    @Override
    public void applyEffect(GamePanel game) {
        game.activateIceCreamPower();
    }

    @Override
    public String toString(){
        return "This is an IceCream-Boost class";
    }
}
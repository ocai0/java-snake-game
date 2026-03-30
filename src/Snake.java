import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Snake {
    char direction = 'R';
    int bodyParts = 6;
    boolean alive = true;
    final int x[] = new int[GamePanel.GAME_UNITS];
    final int y[] = new int[GamePanel.GAME_UNITS];
    boolean wantsToReset = false;

    boolean flowerActive = false;
    Color flowerColor = Color.MAGENTA;

    Random random = new Random();

    public Snake() {
        x[0] = 0;
        y[0] = 0;
    }

    public boolean wantsToResetGame() {
        if (wantsToReset) {
            wantsToReset = false;
            return true;
        }
        return false;
    }

    public void grow() {
        bodyParts++;
        x[bodyParts - 1] = x[bodyParts - 2];
        y[bodyParts - 1] = y[bodyParts - 2];
    }

    public void shrink() {
        if (bodyParts <= 2)
            return;
        bodyParts--;
    }

    public void doubleSize() {
        int oldBodyParts = bodyParts;
        bodyParts = bodyParts * 2;

        for (int i = oldBodyParts; i < bodyParts; i++) {
            x[i] = x[oldBodyParts - 1];
            y[i] = y[oldBodyParts - 1];
        }
    }

    public void halfSize() {
        bodyParts = Math.max(2, bodyParts / 2);
    }

    public void activateFlowerColor() {
        flowerActive = true;

        int r = random.nextInt(3);

        if (r == 0) flowerColor = Color.BLUE;
        if (r == 1) flowerColor = Color.MAGENTA;
        if (r == 2) flowerColor = Color.YELLOW;
    }

    private void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getHeadXPos() {
        return x[0];
    }

    public int getHeadYPos() {
        return y[0];
    }

    public boolean isDead() {
        return !alive;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < bodyParts; i++) {

            if (flowerActive) {
                g.setColor(flowerColor);
            } else if (i == 0) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(new Color(45, 180, 0));
            }

            g.fillRect(x[i], y[i], GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
        }
    }

    public void update() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - GamePanel.UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + GamePanel.UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - GamePanel.UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + GamePanel.UNIT_SIZE;
                break;
        }

        checkCollisions();
    }

    public void checkCollisions() {
        checkBodyCollision();
        checkScreenCollisions();
    }

    public void checkBodyCollision() {
        for (int i = bodyParts; i > 0; i--) {
            if ((getHeadXPos() == x[i]) && (getHeadYPos() == y[i])) {
                kill();
            }
        }
    }

    public void checkScreenCollisions() {
        if (getHeadXPos() < 0) kill();
        if (getHeadXPos() >= GamePanel.SCREEN_WIDTH) kill();
        if (getHeadYPos() < 0) kill();
        if (getHeadYPos() >= GamePanel.SCREEN_HEIGHT) kill();
    }

    public KeyAdapter bindControls() {
        return new PlayerControls(this);
    }
}

class PlayerControls extends KeyAdapter {
    Snake player;

    PlayerControls(Snake player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (player.direction != 'L' && player.direction != 'R')
                    player.direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (player.direction != 'R' && player.direction != 'L')
                    player.direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (player.direction != 'U' && player.direction != 'D')
                    player.direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (player.direction != 'D' && player.direction != 'U')
                    player.direction = 'D';
                break;
            case KeyEvent.VK_ENTER:
                player.wantsToReset = true;
                break;
        }
    }
}
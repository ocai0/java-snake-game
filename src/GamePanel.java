import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    int score;
    ArrayList<Apple> fruits = new ArrayList<Apple>();
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    Snake player;
    KeyAdapter activeControls;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        startGame();
    }


    public void startGame() {
        player = new Snake();
        activeControls = player.bindControls();
        this.addKeyListener(activeControls);
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        generateFruit();
    }

    private void generateFruit() {
        fruits.add(new Apple(random.nextInt(SCREEN_WIDTH / UNIT_SIZE), random.nextInt(SCREEN_HEIGHT / UNIT_SIZE)));
    }
    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running) {
            drawScore(g);
            player.draw(g);
            for(Fruit fruit : fruits) fruit.draw(g);
        }
        else {
            gameOver(g);
        }
        
    }

    public void checkPlayerAndFruitCollisions() {
        for(Fruit fruit : fruits) {
            if((player.getHeadXPos() == fruit.x * UNIT_SIZE) && (player.getHeadYPos() == fruit.y * UNIT_SIZE)) {
                player.grow();
                score += fruit.getPoints();
                fruits.remove(fruit);
                generateFruit();
            }
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2 - 40);
        
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        metrics = getFontMetrics(g.getFont());
        g.drawString(
            "Score: " + score,
            (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,
            g.getFont().getSize() + 280
        );

        g.setFont(new Font("Ink Free", Font.BOLD, 32));
        g.setColor(Color.WHITE);
        metrics = getFontMetrics(g.getFont());
        String text = "Pressione ENTER para jogar";
        g.drawString(
            text,
            (SCREEN_WIDTH - metrics.stringWidth(text)) / 2,
            g.getFont().getSize() + 340
        );
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,
                g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            player.update();
            checkPlayerAndFruitCollisions();
            player.checkCollisions();
            if(player.isDead()) running = false;
        }
        else {
             if(player.isDead() && player.wantsToResetGame()) {
                timer.stop();
                fruits.clear();
                this.removeKeyListener(activeControls);
                score = 0;
                startGame();
             }
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'L' && direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'R' && direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'U' && direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'D' && direction != 'U') direction = 'D';
                    break;
            }
        }
    }

}
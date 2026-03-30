import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    int score;

    ArrayList<Fruit> fruits = new ArrayList<>();
    ArrayList<Boost> boosts = new ArrayList<>();

    private final List<BiFunction<Integer, Integer, Fruit>> FRUITS_TYPES = List.of(
            Apple::new,
            Pear::new,
            Banana::new);

    boolean running = false;
    Timer timer;
    Timer iceCreamTimer;
    Timer flowerTimer;
    Random random;
    Snake player;
    KeyAdapter activeControls;

    int comboFruitCount = 0;
    int pearCount = 0;
    int totalFruitCount = 0;

    boolean iceCreamActive = false;
    boolean iceCreamOnMap = false;
    boolean starOnMap = false;
    boolean flowerOnMap = false;
    boolean flowerSpawnedInCombo = false;

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
        score = 0;

        comboFruitCount = 0;
        pearCount = 0;
        iceCreamActive = false;
        iceCreamOnMap = false;
        starOnMap = false;
        flowerOnMap = false;
        flowerSpawnedInCombo = false;
        totalFruitCount = 0;

        fruits.clear();
        boosts.clear();
        generateFruit();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void generateFruit() {
        int x = random.nextInt(SCREEN_WIDTH / UNIT_SIZE);
        int y = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE);
        int i = random.nextInt(FRUITS_TYPES.size());

        Fruit newFruit = FRUITS_TYPES.get(i).apply(x, y);
        fruits.add(newFruit);
    }

    private void spawnIceCream() {
        if (iceCreamOnMap)
            return;

        int x = random.nextInt(SCREEN_WIDTH / UNIT_SIZE);
        int y = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE);

        boosts.add(new IceCream(x, y));
        iceCreamOnMap = true;
    }

    private void spawnStar() {
        if (starOnMap)
            return;

        int x = random.nextInt(SCREEN_WIDTH / UNIT_SIZE);
        int y = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE);

        boosts.add(new Star(x, y));
        starOnMap = true;
    }

    private void spawnFlower() {
        if (flowerOnMap)
            return;

        int x = random.nextInt(SCREEN_WIDTH / UNIT_SIZE);
        int y = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE);

        boosts.add(new Flower(x, y));
        flowerOnMap = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            drawScore(g);

            if (iceCreamActive) {
                drawIceCreamMessage(g);
            }

            player.draw(g);

            for (Fruit fruit : fruits) {
                fruit.draw(g);
            }

            for (Boost boost : boosts) {
                boost.draw(g);
            }
        } else {
            gameOver(g);
        }
    }

    public void drawIceCreamMessage(Graphics g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("SUPER CRESCIMENTO!", 130, 300);
    }

    public void checkPlayerAndFruitCollisions() {
        for (int i = 0; i < fruits.size(); i++) {
            Fruit fruit = fruits.get(i);

            if ((player.getHeadXPos() == fruit.getX() * UNIT_SIZE) &&
                    (player.getHeadYPos() == fruit.getY() * UNIT_SIZE)) {

                if (fruit instanceof Banana || fruit instanceof Apple) {
                    comboFruitCount++;
                }

                totalFruitCount++;

                fruit.applyEffect(this);
                fruits.remove(i);
                generateFruit();
                checkCombo();
                break;
            }
        }
    }

    public void checkPlayerAndBoostCollisions() {
        for (int i = 0; i < boosts.size(); i++) {
            Boost boost = boosts.get(i);

            if ((player.getHeadXPos() == boost.getX() * UNIT_SIZE) &&
                    (player.getHeadYPos() == boost.getY() * UNIT_SIZE)) {

                if (boost instanceof IceCream) {
                    iceCreamOnMap = false;
                }

                if (boost instanceof Star) {
                    starOnMap = false;
                }

                if (boost instanceof Flower) {
                    flowerOnMap = false;
                }

                boost.applyEffect(this);
                boosts.remove(i);
                break;
            }
        }
    }

    public void checkCombo() {
        if (comboFruitCount >= 5) {
            spawnIceCream();
            comboFruitCount = 0;
        }

        if (totalFruitCount >= 5) {
            spawnFlower();
            totalFruitCount = 0;
        }

        if (pearCount >= 3) {
            spawnStar();
            pearCount = 0;
        }
    }

    public void activateIceCreamPower() {
        iceCreamActive = true;

        player.doubleSize();

        if (score > 0) {
            score = score * 2;
        }

        if (iceCreamTimer != null && iceCreamTimer.isRunning()) {
            iceCreamTimer.stop();
        }

        iceCreamTimer = new Timer(5000, e -> {
            iceCreamActive = false;
            ((Timer) e.getSource()).stop();
        });

        iceCreamTimer.setRepeats(false);
        iceCreamTimer.start();
    }

    public void activateFlowerPower() {
        player.activateFlowerColor();

        if (flowerTimer != null && flowerTimer.isRunning()) {
            flowerTimer.stop();
        }

        flowerTimer = new Timer(3000, e -> {
            player.flowerActive = false;
            ((Timer) e.getSource()).stop();
        });

        flowerTimer.setRepeats(false);
        flowerTimer.start();
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
                g.getFont().getSize() + 280);

        g.setFont(new Font("Ink Free", Font.BOLD, 32));
        g.setColor(Color.WHITE);
        metrics = getFontMetrics(g.getFont());
        String text = "Pressione ENTER para jogar";
        g.drawString(
                text,
                (SCREEN_WIDTH - metrics.stringWidth(text)) / 2,
                g.getFont().getSize() + 340);
    }

    public void addToScore(int points) {
        score += points;
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(
                "Score: " + score,
                (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,
                g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            player.update();
            checkPlayerAndFruitCollisions();
            checkPlayerAndBoostCollisions();
            player.checkCollisions();

            if (player.isDead()) {
                running = false;
            }
        } else {
            if (player.isDead() && player.wantsToResetGame()) {
                timer.stop();

                if (iceCreamTimer != null) {
                    iceCreamTimer.stop();
                }

                if (flowerTimer != null) {
                    flowerTimer.stop();
                }

                fruits.clear();
                boosts.clear();
                this.removeKeyListener(activeControls);
                startGame();
            }
        }

        repaint();
    }
}
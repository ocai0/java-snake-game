public class Apple extends Fruit {

    public Apple(int x, int y) {
        super(x, y, 10);
    }

    @Override
    public void applyEffect(GamePanel game) {
        game.player.bodyParts++;
    }
}
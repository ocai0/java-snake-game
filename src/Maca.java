public class Maca extends Fruta {

    public Maca(int x, int y) {
        super(x, y, 10);
    }

    @Override
    public void efeito(GamePanel game) {
        game.bodyParts++;
        game.applesEaten++;
    }
}
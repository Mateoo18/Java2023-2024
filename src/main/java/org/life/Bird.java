package org.life;
import java.util.Random;
public class Bird extends Organism {

    public Bird(int i, int i1) {
        super(i, i1);
    }

    private Random random = new Random();

    public Position skocz_random() {
        Position nowa_dla_ruchu_pozycja = new Position(position.getX(), position.getY());
        boolean moveVertically = random.nextBoolean();

        if (moveVertically) {
            // Move up or down by 1
            int newY = 0;
            newY += random.nextBoolean() ? 3 : -3;
            if (nowa_dla_ruchu_pozycja.getY()+newY >= 0 && newY+ nowa_dla_ruchu_pozycja.getY() < 10) {
                nowa_dla_ruchu_pozycja.y += newY;
            }
        } else {
            // Move left or right by 1
            int newX = 0;
            newX += random.nextBoolean() ? 3 : -3;
            if (nowa_dla_ruchu_pozycja.getX()+newX >= 0 && newX+ nowa_dla_ruchu_pozycja.getX() < 10) {
                nowa_dla_ruchu_pozycja.x += newX;
            }
        }
        return nowa_dla_ruchu_pozycja;
    }
}
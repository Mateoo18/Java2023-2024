package org.life;

import java.util.Random;

public class Organism  {

  public int energy;
  public int bite;
  public Position position;
  private Random random = new Random();

  public Organism(int energy,int bite) {
    this.energy = energy;
    this.bite=bite;
  }

  public Position move() {
    Position nowa_dla_ruchu_pozycja=new Position(0,0);
    int newX = position.getX();
    int newY = position.getY();

    // Decide whether to move vertically or horizontally
    boolean moveVertically = random.nextBoolean();

    if (moveVertically) {
      // Move up or down by 1
      newY += random.nextBoolean() ? 1 : -1;
    } else {
      // Move left or right by 1
      newX += random.nextBoolean() ? 1 : -1;
    }
    nowa_dla_ruchu_pozycja.x=newX;
    nowa_dla_ruchu_pozycja.y=newY;
    return nowa_dla_ruchu_pozycja;
    // TODO: Use the board's moveOrganism method to move the organism
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return position;
  }
}


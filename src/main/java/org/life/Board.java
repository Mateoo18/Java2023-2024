package org.life;

public class Board {

  private int width;
  private int height;
  private Organism[][] organisms;

  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    this.organisms = new Organism[width][height];
  }
  public Position getDimensions(){
    Position dim=new Position(width,height);
    return dim;
  }

  public void addOrganism(Organism organism, int x, int y) {
    if (organisms[x][y] == null) {
      organisms[x][y] = organism;
      organism.setPosition(new Position(x, y));
    } else {
      System.out.println("Position already occupied!");
    }
  }
  public void eat(Organism organism,int newX,int newY,int gdzie_sie_ruszamy){
    if(newY+1<height && organisms[newX][newY+1]!=null)
    {
      System.out.println("atak z gory");
      organisms[newX][newY+1].energy-=organism.bite;
    }
    else if(newY-1>=0 && organisms[newX][newY-1]!=null)
    {
      System.out.println("atak z dolu");
      organisms[newX][newY-1].energy-=organism.bite;
    }
    else if(newX+1<width && organisms[newX+1][newY]!=null )
    {
      System.out.println("atak z prawej");
      organisms[newX+1][newY].energy-=organism.bite;
    }
    else if(newX-1>=0 && organisms[newX-1][newY]!=null)
    {
      System.out.println("atak z lewej");
      organisms[newX-1][newY].energy-=organism.bite;
    }
    else{
      System.out.println("Nie atakuje nikogo");
    }

  }

  public void moveOrganism(Organism organism, int newX, int newY,int gdzie_sie_ruszamy) {
    // TODO implement that one organism eats the other
    if (newX >= 0 && newX < width && newY >= 0 && newY < height && organisms[newX][newY] == null) {//usuwam jeden warunek organisms[newX][newY] == null
      System.out.println("idziesz na pole: "+newX+" "+newY);
      organisms[organism.getPosition().getX()][organism.getPosition().getY()] = null;
      organisms[newX][newY] = organism;
      organism.setPosition(new Position(newX, newY));
      eat(organism,newX,newY,gdzie_sie_ruszamy);

    }else if(newX >= 0 && newX < width && newY >= 0 && newY < height && organisms[newX][newY] != null)//nie porusza się dany obiekt
    {
      if(organisms[newX][newY].bite==organism.bite){//sprawdzamy czy to to samo zwierze
      System.out.println("Nie poruszam się");
        eat(organism,newX,newY,gdzie_sie_ruszamy);
      }
    }
    else {
      System.out.println("Chciales wejsc na: "+ newX+" "+newY);
      System.out.println("Invalid move!");
    }
  }
  public void kill_animal(Organism organism,Position pos)
  {
    organisms[pos.x][pos.y]=null;
  }
}

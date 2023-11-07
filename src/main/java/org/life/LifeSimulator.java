package org.life;

import java.net.StandardSocketOptions;

public class LifeSimulator {

  public static void main(String[] args) {

    Board board = new Board(10, 10);
    /* TODO:
     - Add at least 2 classes that implement the Organism interface.
     - The new classes should possess unique abilities, such as:
        -- Jumping (moving more than 1 step at a time).
        -- Sight (detecting other organisms within a certain radius).
        -- Avoiding illegal moves.
     - Ensure that an Organism doesn't move if it attempts an illegal move.
     - Implement a mechanism where an Organism consumes another (taking all its energy) when it occupies the same space.
     - Run simulation for some time, eg. when there is only one Organism left
     */
    /*
    moje TODO:

    --stworzenie 3 klas -drapieznika -ptaka -zwierze roslinozerne ( maja dziedziczyc po klasie Organizm)
    -- kazdy organizm nie moze wychodzic poza tablice (za kazdym razem sprawdzać)
         -drapeiznik
            *moze skakac co 2 pola tzn moze pojsc 2 dlugosc(jezeli sa one w odleglosci 3) (np prawo->dol)+
            *widzi zwierzete na 2 pola wokol siebie
            *zabiera innym obiektom przy ataku 20 energii

        -ptak
            *moze skakac co 3 pola
            *zabiera innym obiektom przy ataku 20 energii
        -roslinozerca
            *ma funkcje heal'owania sie (leczenia) o 5 jednostek

    */
    Hunter tygrys=new Hunter(70,15);
    Bird ptak=new Bird(50,5);
    Vegan vegan=new Vegan(30,2);
   board.addOrganism(tygrys,1,1);
    board.addOrganism(ptak,6,7);
    board.addOrganism(vegan,5,5);
    vegan.Setenergy(30);
    int flagatygrysa=0,flagaptaka=0,flagavegana=0;
    int counter=3;
    do{
        int tygrys_energy=tygrys.energy;
        if(tygrys_energy>0)
        {
            System.out.println("       TYGRYS:");
          Position pos_tygrys=tygrys.getPosition();

        Position nowa_pozycja_tiger= tygrys.radius_tiger(ptak.getPosition(),vegan.getPosition(),tygrys.getPosition(),board.getDimensions());
        int liczba=tygrys.gdzie_sie_ruszamy;
         if(nowa_pozycja_tiger==tygrys.getPosition()){
           System.out.println("Za daleko od nich!");
           pos_tygrys= tygrys.move();
           board.moveOrganism(tygrys,pos_tygrys.x,pos_tygrys.y,liczba);
         }
         else {
           System.out.println("Mozemy skoczyc");
           board.moveOrganism(tygrys, nowa_pozycja_tiger.getX(), nowa_pozycja_tiger.getY(),liczba);
         }
        tygrys.gdzie_sie_ruszamy=0;
            System.out.println("Energia tygrysa: "+tygrys.energy);
        }
        else {//tygrys nie zyje
            if(flagatygrysa==0) {
                board.kill_animal(tygrys, tygrys.getPosition());
                tygrys.setPosition(new Position(-10000, -100000));
            flagatygrysa=1;
            counter--;
            }

        }

        if(ptak.energy>0)
        {
            System.out.println("        PTAK:");
            Position wczesniejsza_pos_ptaka=ptak.getPosition();
            Position new_pos_ptak=ptak.skocz_random();
            if(new_pos_ptak==wczesniejsza_pos_ptaka)//oznacza ze chcial uciec za plansze
            {
                System.out.println("Invalid move skok za plansze!");
                wczesniejsza_pos_ptaka=ptak.move();
                board.moveOrganism(ptak, wczesniejsza_pos_ptaka.getX(), wczesniejsza_pos_ptaka.getY(), 0);
            }
            else {

                board.moveOrganism(ptak, new_pos_ptak.getX(), new_pos_ptak.getY(), 0);
            }
            System.out.println("Energia ptaka: "+ptak.energy);
        }
        else
        {
            if(flagaptaka==0) {
                board.kill_animal(ptak, ptak.getPosition());
                ptak.setPosition(new Position(-10000, -10000));
                flagaptaka=1;
                counter--;
            }
        }

        if(vegan.energy>0)
        {
            System.out.println("        VEGAN");
            int obecna_energia_vegana=vegan.heal(vegan.energy);
            vegan.energy=obecna_energia_vegana;
          Position pozwegana= vegan.move();
            board.moveOrganism(vegan,pozwegana.getX(), pozwegana.getY(), 0);
            System.out.println("energia vegana: "+vegan.energy);

        }
        else{
            if(flagavegana==0) {
                board.kill_animal(vegan, vegan.getPosition());
                vegan.setPosition(new Position(-10000, -10000));
            flagavegana=1;
            counter--;
            }

        }
        System.out.println("ILOSC ZWIERZAT: "+ counter);
    }while(counter>1);

  }
}
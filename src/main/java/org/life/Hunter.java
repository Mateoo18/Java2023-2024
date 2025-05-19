package org.life;

import static java.lang.Math.abs;

public class Hunter extends Organism {
    public int gdzie_sie_ruszamy=0;
    public Position ptak_pos;
    public Position dim;
    public Position vegan_pos;

    public Hunter(int i, int i1) {
        super(i,i1);
    }

public Position search_closest_free(Position atakowany,Position sprawdzany){
        Position nowepostigera=new Position(0,0);
        nowepostigera.x=atakowany.x;
        nowepostigera.y=atakowany.y-1;//jedno do gory

    if ( (nowepostigera.y>= 0 && nowepostigera.y < dim.y) && nowepostigera!=sprawdzany )//sprawdzamy czy nie wychodzimy za plansze i czy nie wchodzimy na pole veganina
    {
        gdzie_sie_ruszamy=1;
     return nowepostigera;
    }
    nowepostigera.x=atakowany.x;
    nowepostigera.y=atakowany.y+1;//jedno w dol
    if((nowepostigera.y>= 0 && nowepostigera.y < dim.y) && nowepostigera!=sprawdzany){
        gdzie_sie_ruszamy=2;
        return nowepostigera;
    }
    nowepostigera.x=atakowany.x+1;//jedno w prawo
    nowepostigera.y=atakowany.y;
    if((nowepostigera.x>= 0 && nowepostigera.x < dim.x) && nowepostigera!=sprawdzany){
        gdzie_sie_ruszamy=3;
        return nowepostigera;
    }
    nowepostigera.x=atakowany.x-1;//jedno w prawo
    nowepostigera.y=atakowany.y;
    if((nowepostigera.x>= 0 && nowepostigera.x < dim.x) && nowepostigera!=sprawdzany){
        gdzie_sie_ruszamy=4;
        return nowepostigera;
    }
    return nowepostigera;//do tego nigdy nie dojdzie
}
    public Position radius_tiger(Position posptak,Position posvegan,Position postiger,Position dims){
        int posptakX=posptak.getX();
        int posptakY=posptak.getY();
        int posveganX=posvegan.getX();
        int posveganY=posvegan.getY();
        int postigerX=postiger.getX();
        int postigerY=postiger.getY();
        ptak_pos=posptak;
        vegan_pos=posvegan;
        dim=dims;
        int wynik_dla_tiger_ptak=abs(postigerX-posptakX)+abs(postigerY-posptakY);//wzor manhattan
        int wynik_dla_tiger_vegan=abs(postigerX-posveganX)+abs(postigerY-posveganY);
if(wynik_dla_tiger_ptak<wynik_dla_tiger_vegan && wynik_dla_tiger_ptak<=2)
{
   Position dokladny_position=search_closest_free(posptak,posvegan);
    return dokladny_position;
}
else if(wynik_dla_tiger_ptak>wynik_dla_tiger_vegan && wynik_dla_tiger_vegan<=2){
    Position dokladny_position=search_closest_free(posvegan,posptak);
    return dokladny_position;

}
else {
    return postiger;
}
    }

}

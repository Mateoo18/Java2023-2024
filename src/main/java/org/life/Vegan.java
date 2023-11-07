package org.life;

public class Vegan extends Organism{
    public int energiaa;
    public Vegan(int i, int i1) {
        super(i,i1);
    }
    public int heal(int obecna_energia)
    {
        if(energiaa!=obecna_energia)
        {
            obecna_energia+=6;
        }
        return obecna_energia;
    }
    public void Setenergy(int energy)
    {
        this.energiaa=energy;
    }
}

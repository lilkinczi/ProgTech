package AutoFactory.SUV.Skoda;

import AutoFactory.SUV.SUVFactory;
import AutoFactory.Szin;
import AutoFactory.Tipus;

public class Kodiak extends SUVFactory {
    private final int KodiakAr;

    public int getKodiakAr() {
        return KodiakAr;
    }
    private Szin szin;


    public Kodiak(Tipus tipus)  {
        super(tipus);
        KodiakAr = 15854480;
    }
}

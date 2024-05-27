package AutoFactory.SUV.Skoda;

import AutoFactory.SUV.SUVFactory;
import AutoFactory.Tipus;

public abstract class SkodaFactory extends SUVFactory {
    public SkodaFactory(Tipus tipus)  {
        super(tipus);
    }
    public abstract Kodiak CreateKodiak();
    public abstract Karoq CreateKaroq();
    public abstract Kamiq CreateKamiq();
}

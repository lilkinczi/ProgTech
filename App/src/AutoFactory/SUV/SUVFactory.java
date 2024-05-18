package AutoFactory.SUV;

import AutoFactory.AbstractAutoFacrory;
import AutoFactory.*;

public abstract class SUVFactory extends AbstractAutoFacrory {
    public SUVFactory(Tipus tipus) {
        super(Tipus.Suv);
    }

}

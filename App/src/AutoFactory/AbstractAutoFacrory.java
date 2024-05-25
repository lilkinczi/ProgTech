package AutoFactory;

public abstract class AbstractAutoFacrory {
    private Tipus tipus;


    public AbstractAutoFacrory(Tipus tipus) {
        this.tipus = tipus;
    }

    public Tipus getTipus() {return tipus;}

    public void setTipus() {this.tipus = tipus;}
}

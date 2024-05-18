package AutoFactory;

public abstract class AbstractAutoFacrory {
    private Tipus tipus;

    public AbstractAutoFacrory(Tipus tipus) {this.tipus = tipus;}

    public Tipus getMarka() {return tipus;}

    public void setMarka() {this.tipus = tipus;}
}

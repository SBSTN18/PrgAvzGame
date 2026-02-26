public class Attemp {

    private int points;
    private Embocada embocada;

    public Attemp(Embocada embocada) {
        this.embocada = embocada;
        this.points = 0;
        
    }

    public int determinatePoints(){
        if (embocada.isSuccessfull()) {
            return embocada.getPoints();
        }
        return 0;
    } 

}

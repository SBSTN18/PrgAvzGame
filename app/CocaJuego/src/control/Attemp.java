package control;
import java.util.Random;

import modelo.Embocada;

public class Attemp {

    private Embocada embocada;
    private Random random;
    private int points = 0;

    public Attemp() {
    }

    public int execute(Embocada embocada) {
        
        if (embocada == Embocada.SIMPLE) {
            this.embocada = Embocada.SIMPLE;
            
        }else if (embocada == Embocada.DOBLE) {
            this.embocada = Embocada.DOBLE;
           
        }else if (embocada == Embocada.VERTICAL) {
            this.embocada = Embocada.VERTICAL;

        } else if (embocada == Embocada.MARIQUITA) {
            this.embocada = Embocada.MARIQUITA;
            
        } else if (embocada == Embocada.PUÑALADA) {
            this.embocada = Embocada.PUÑALADA;
            
        } else if (embocada == Embocada.PURTIÑA) {
            this.embocada = Embocada.PURTIÑA;

        } else if (embocada == Embocada.DOMINIO) {
            this.embocada = Embocada.DOMINIO;
            
        }
        random = new Random();
        int chance = random.nextInt(100) + 1; // Genera un número
        if (chance <= embocada.getProbability()) {
            points = embocada.getPoints();
        } else {
            points = 0;
        }
        return points;
    } 

}

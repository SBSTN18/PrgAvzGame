package control;
import java.util.Random;

import modelo.Embocada;

public class Attemp {

    private Embocada embocada;
    private Random random;
    private int points = 0;

    public Attemp() {
    }

    public int determinatePoints(String type) {
        
        if (type.equalsIgnoreCase("simple")) {
            embocada = Embocada.SIMPLE;
            
        }else if (type.equalsIgnoreCase("doble")) {
            embocada = Embocada.DOBLE;
           
        }else if (type.equalsIgnoreCase("vertical")) {
            embocada = Embocada.VERTICAL;

        } else if (type.equalsIgnoreCase("mariquita")) {
            embocada = Embocada.MARIQUITA;
            
        } else if (type.equalsIgnoreCase("puñalada")) {
            embocada = Embocada.PUÑALADA;
            
        } else if (type.equalsIgnoreCase("purtiña")) {
            embocada = Embocada.PURTIÑA;

        } else if (type.equalsIgnoreCase("dominio")) {
            embocada = Embocada.DOMINIO;
            
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

package control;
import modelo.Embocada;

public class Attemp {

    private Embocada embocada;

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
        return embocada.getPoints();
    
    } 

}

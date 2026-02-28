package modelo;


public enum Embocada {
    SIMPLE(2, 60),
    DOBLE(10, 5),
    VERTICAL(3, 50),
    MARIQUITA(4, 40),
    PUÑALADA(5, 30),
    PURTIÑA(6, 20),
    DOMINIO(7, 10);

    private int points;
    private int probability;

    Embocada(int points, int probability) {
        this.points = points;
        this.probability = probability;
    }

    public int getPoints() {
        return points;
    }

    public int getProbability() {
        return probability;
    }
}    

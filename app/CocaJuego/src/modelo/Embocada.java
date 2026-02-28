package modelo;

import java.util.Random;

public enum Embocada {
    SIMPLE(2, 60),
    DOBLE(10, 5),
    VERTICAL(3, 50),
    MARIQUITA(4, 40),
    PUÑALADA(5, 30),
    PURTIÑA(6, 20),
    DOMINIO(7, 10),
    FALLO(0, 0);

    private int points;
    private int probability;
    private Random random;

    Embocada(int points, int probability) {
        this.points = points;
        this.probability = probability;
    }

    public int getPoints() {
        random = new Random();
        int randomNumber = random.nextInt(100) + 1; // Genera un número entre 1 y 100
        if(randomNumber <= probability){
            return points;
        }else{
            return 0;
        }
    }
}    

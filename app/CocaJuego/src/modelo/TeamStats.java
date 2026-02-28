package modelo;

public class TeamStats {

    private int points;
    private int attempts;
    private int embocadas;

    public void addPoints(int p) {
        this.points += p;
    }

    public void addAttempt() {
        this.attempts++;
    }

    public void addEmbocada() {
        this.embocadas++;
    }

    public int getPoints() { return points; }
    public int getAttempts() { return attempts; }
    public int getEmbocadas() { return embocadas; }
}
import java.util.List;


public class Team {

    private String proyect;
    private String name;
    private List<Player> players;
    private int pin;
    private int score;
    private int totalAttemps;
    private int totalEmbo;
    private boolean ready;

    public Team(String proyect, String name, List<Player> players, int pin) {
        this.proyect = proyect;
        this.name = name;
        this.players = players;
        this.pin = pin;
        this.score = 0;
        this.totalAttemps = 0;
        this.totalEmbo = 0;
        this.ready = false;
    }

    public String getProyect() {
        return proyect;
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPin() {
        return pin;
    }

    public int getScore() {
        return score;
    }   

    public int getTotalAttemps() {
        return totalAttemps;
    }           

    public int getTotalEmbo() {
        return totalEmbo;
    }

    public boolean getReady() {
        return ready;
    }

    public int getCantPlayers() {
        return players.size();
    }

    public void calculateScore(int points) {
        this.score += points;
    }

    public void calculateTotalAttemps(int attemps) {
        this.totalAttemps += attemps;
    }

    public void calculateTotalEmbo(int embo) {
        this.totalEmbo += embo;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

}

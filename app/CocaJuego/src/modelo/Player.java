public class Player {

    private String name;
    private int code;
    private int points;
    private int attemps;
    private int cantEmbo;
    private boolean status;

    public Player(String name, int code) {
        this.name = name;
        this.code = code;
        this.points = 0;
        this.attemps = 0;
        this.cantEmbo = 0;
        this.status = false;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public int getPoints() {
        return points;
    }

    public int getAttemps() {
        return attemps;
    }

    public int getCantEmbo() {
        return cantEmbo;
    }

    public boolean getStatus() {
        return status;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setAttemps(int attemps) {
        this.attemps = attemps;
    }

    public void setCantEmbo(int cantEmbo) {
        this.cantEmbo = cantEmbo;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }   


}

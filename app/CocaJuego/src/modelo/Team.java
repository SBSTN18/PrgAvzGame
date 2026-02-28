package modelo;

import java.io.Serializable;
import java.util.List;


public class Team implements Serializable{
    private static final long serialVersionUID = 1L;

    private String proyect;
    private String name;
    private List<Player> players;
    private int pin;
    

    public Team(String proyect, String name, List<Player> players, int pin) {
        this.proyect = proyect;
        this.name = name;
        this.players = players;
        this.pin = pin;
        
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

   public void clearPlayers() {
        players.clear();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public int getCantPlayers() {
        return players.size();
    }

}

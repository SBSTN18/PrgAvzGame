package control;

import modelo.Player;
import modelo.Team;

import java.util.List;

public class TeamControl {

    private Team team;

    public TeamControl() {    
    }

    public Team createTeam(String proyect, String name, List<Player> players, int pin) {
        return new Team(proyect, name, players, pin);
    }


    



}

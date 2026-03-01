package control;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import modelo.Player;
import modelo.Team;

public class TeamsGeneralControl {


    private PlayerControl pc;
    private TeamControl tc;
    private List<Player> players;

    public TeamsGeneralControl() {
        pc = new PlayerControl();
        tc = new TeamControl();
    }

    public Team createTeam(String proyect, String name, List<String> names, List<Integer> codes, int pin) {
        players = createPlayers(names, codes);
        return tc.createTeam(proyect, name, players, pin);
    }

    public List<Player> createPlayers(List<String> names, List<Integer> codes) {
        players = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            players.add(pc.createPlayer(names.get(i), codes.get(i)));
        }
        return players;
    }

    public Team validateTeam(Team team, List<Integer> codes, int pin) {
    if (team.getPin() != pin) return null;

    // Guardamos los jugadores originales ANTES de limpiar
    List<Player> original = new ArrayList<>(team.getPlayers());
    team.clearPlayers();

    for (Integer code : codes) {
        for (Player player : original) {  // ← iteramos la copia, no la lista vacía
            if (player.getCode() == code) {
                team.addPlayer(player);
            }
        }
    }

    return team;
}

    

}

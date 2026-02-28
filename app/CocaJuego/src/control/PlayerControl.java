package control;
import modelo.Player;

public class PlayerControl {

    

    public PlayerControl() {
    }

    public Player createPlayer(String name, int code) {
        return new Player(name, code);
    }

}

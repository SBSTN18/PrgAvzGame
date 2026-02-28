package control;
import modelo.Player;

public class PlayerControl {

    private PrincipalControl Cp;

    public PlayerControl(PrincipalControl cp) {
        this.Cp = cp;
    }

    public Player createPlayer(String name, int code) {
        return new Player(name, code);
    }

}

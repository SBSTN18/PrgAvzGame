package control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import modelo.Embocada;
import modelo.Player;
import modelo.Team;
import modelo.TeamStats;

/**
 * Controlador principal de la aplicación.
 * Centraliza toda la lógica de negocio: gestión de equipos,
 * sesión de juego, intentos y persistencia.
 */
public class PrincipalControl {

    private VistaControl vistaControl;
    private PersistenceControl persistenceControl;
    private TeamsGeneralControl teamsControl;
    private GameSession session;
    private Attemp attemp;

    private List<Team> teams;
    private Team lastAddedTeam;
    private String lastResult;

    public PrincipalControl() {
        this.persistenceControl = new PersistenceControl();
        this.teamsControl       = new TeamsGeneralControl();
        this.attemp             = new Attemp();
        this.teams              = new ArrayList<>();
        this.vistaControl        = new VistaControl(this);
    }

    // ─── GESTIÓN DE EQUIPOS ──────────────────────────────────────────────────

    /**
     * Carga un equipo desde un archivo .properties.
     *
     * @param file archivo .properties del equipo
     * @return equipo cargado, null si hubo error
     */
    public Team loadTeamFromFile(File file) {
        return persistenceControl.loadTeamProperties(file);
    }

    /**
     * Valida el PIN y códigos de un equipo y lo agrega si son correctos.
     * Retorna null si todo está bien, o un mensaje de error si hay problema.
     *
     * @param team equipo a validar
     * @param pin PIN ingresado
     * @param c1 código jugador 1
     * @param c2 código jugador 2
     * @param c3 código jugador 3 (puede estar vacío)
     * @return mensaje de error o null si es válido
     */
    public String validateAndAddTeam(Team team, String pin, String c1, String c2, String c3) {
        if (pin.isEmpty() || c1.isEmpty() || c2.isEmpty()) {
            return "PIN y al menos 2 códigos son obligatorios.";
        }

        int pinInt;
        List<Integer> codigos = new ArrayList<>();

        try {
            pinInt = Integer.parseInt(pin);
            codigos.add(Integer.parseInt(c1));
            codigos.add(Integer.parseInt(c2));
            if (!c3.isEmpty()) codigos.add(Integer.parseInt(c3));
        } catch (NumberFormatException e) {
            return "PIN y códigos deben ser numéricos.";
        }

        Team validated = teamsControl.validateTeam(team, codigos, pinInt);
        if (validated == null) {
            return "PIN o códigos incorrectos.";
        }

        addTeam(validated);
        return null;
    }

    /**
     * Crea un equipo nuevo desde el formulario, guarda el .properties
     * y lo agrega a la lista del torneo.
     * Retorna null si todo está bien, o un mensaje de error si hay problema.
     *
     * @return mensaje de error o null si es válido
     */
    public String createAndSaveTeam(
            String nombre, String proyecto,
            String pin,    String pinConfirm,
            String nombreJ1, String codigoJ1,
            String nombreJ2, String codigoJ2,
            String nombreJ3, String codigoJ3) {

        if (nombre.isEmpty() || proyecto.isEmpty() || pin.isEmpty()) {
            return "Nombre, proyecto y PIN son obligatorios.";
        }
        if (!pin.equals(pinConfirm)) {
            return "Los PIN no coinciden.";
        }
        if (nombreJ1.isEmpty() || codigoJ1.isEmpty() ||
            nombreJ2.isEmpty() || codigoJ2.isEmpty() ||
            nombreJ3.isEmpty() || codigoJ3.isEmpty()) {
            return "Todos los jugadores son obligatorios.";
        }

        try {
            int pinInt = Integer.parseInt(pin);
            List<String> nombres = List.of(nombreJ1, nombreJ2, nombreJ3);
            List<Integer> codigos = List.of(
                Integer.parseInt(codigoJ1),
                Integer.parseInt(codigoJ2),
                Integer.parseInt(codigoJ3)
            );

            Team team = teamsControl.createTeam(nombre, proyecto, nombres, codigos, pinInt);
            persistenceControl.saveTeamProperties(team);
            addTeam(team);
            return null;

        } catch (NumberFormatException e) {
            return "PIN y códigos deben ser numéricos.";
        }
    }

    /**
     * Agrega un equipo a la lista del torneo.
     *
     * @param team equipo a agregar
     */
    public void addTeam(Team team) {
        teams.add(team);
        lastAddedTeam = team;
    }

    /**
     * Elimina un equipo de la lista por índice.
     *
     * @param index índice del equipo a eliminar
     */
    public void removeTeam(int index) {
        if (index >= 0 && index < teams.size()) {
            teams.remove(index);
        }
    }

    /**
     * Carga los equipos serializados de la última sesión.
     * Retorna null si cargó correctamente, o mensaje de error si no hay datos.
     *
     * @return mensaje de error o null si cargó bien
     */
    public String loadLastSession() {
        List<Team> loaded = persistenceControl.loadMatchSer();
        if (loaded.isEmpty()) {
            return "No se encontró ninguna partida guardada.";
        }
        teams = loaded;
        initSession();
        return null;
    }

    /**
     * Resetea el estado del torneo.
     */
    public void reset() {
        teams.clear();
        session = null;
        lastAddedTeam = null;
        lastResult = null;
    }

    // ─── SESIÓN DE JUEGO ─────────────────────────────────────────────────────

    /**
     * Inicializa la sesión de juego con los equipos actuales.
     */
    public void initSession() {
        session = new GameSession();
        session.createTable(teams);
    }

    /**
     * Ejecuta un intento de embocada del jugador actual.
     *
     * @param embocada embocada seleccionada
     */
    public void executeAttemp(Embocada embocada) {
        int points = attemp.execute(embocada);
        session.addPoints(points);

        lastResult = points > 0
            ? embocada.name() + " (+" + points + "pts)"
            : "Fallo";
    }

    /**
     * Avanza al siguiente turno.
     */
    public void nextTurn() {
        session.next();
    }

    /**
     * Verifica si el juego terminó.
     *
     * @return true si todos los equipos ya jugaron
     */
    public boolean isGameFinished() {
        return session == null || session.isFinished();
    }

    /**
     * Finaliza el juego: serializa equipos, guarda ganador en RAF
     * y retorna el número de victorias del ganador.
     *
     * @return número de victorias del equipo ganador
     */
    public int finishGame() {
        // Serializar equipos
        persistenceControl.saveMatchSer(teams);

        // Guardar ganador en RandomAccessFile
        Team winner = session.getWinner();
        int points  = session.getStats(winner).getPoints();

        try {
            persistenceControl.saveWinner(winner, points);
            return persistenceControl.countWins(winner.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    // ─── GETTERS PARA VISTACONTROL ───────────────────────────────────────────

    public List<Team> getTeams()              { return teams;                          }
    public Team getLastAddedTeam()            { return lastAddedTeam;                  }
    public String getLastResult()             { return lastResult != null ? lastResult : "-"; }
    public GameSession getSession()           { return session;                        }
    public Team getWinner()                   { return session.getWinner();            }
    public TeamStats getWinnerStats()         { return session.getStats(getWinner());  }
    public Map<Team, TeamStats> getStandings(){ return session.getTable();             }

    public Team getCurrentTeam() {
        return session.getCurrentTeam();
    }

    public int getCurrentPlayerIndex() {
        return session.getCurrentPlayerIndex();
    }

    public String getCurrentTeamName() {
        return session.getCurrentTeam().getName();
    }

    public String getCurrentPlayerName() {
        Player p = getCurrentPlayer();
        return p != null ? p.getName() : "-";
    }

    public int getCurrentPlayerCode() {
        Player p = getCurrentPlayer();
        return p != null ? p.getCode() : 0;
    }

    public int getCurrentTeamPoints() {
        return session.getStats(session.getCurrentTeam()).getPoints();
    }

    private Player getCurrentPlayer() {
        Team team = session.getCurrentTeam();
        int idx   = session.getCurrentPlayerIndex();
        if (team == null || idx >= team.getPlayers().size()) return null;
        return team.getPlayers().get(idx);
    }
}
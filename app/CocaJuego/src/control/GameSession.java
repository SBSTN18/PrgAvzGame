package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import modelo.Team;
import modelo.TeamStats;

/**
 * Gestiona la sesión de juego del balero.
 * Mantiene el orden de los equipos, sus estadísticas
 * y el avance de turnos durante la partida.
 */
public class GameSession {

    private LinkedHashMap<Team, TeamStats> table;
    private List<Team> order;
    private int currentTeamIndex;
    private int currentPlayerIndex;

    public GameSession() {
        this.table = new LinkedHashMap<>();
        this.currentTeamIndex = 0;
        this.currentPlayerIndex = 0;
    }

    /**
     * Carga los equipos en la tabla de juego,
     * inicializando las estadísticas de cada uno en cero.
     *
     * @param teams lista de equipos a cargar
     */
    public void createTable(List<Team> teams) {
        table.clear();
        for (Team team : teams) {
            table.put(team, new TeamStats());
        }
        this.order = teams;
        this.currentTeamIndex = 0;
        this.currentPlayerIndex = 0;
    }

    /**
     * Retorna el orden de los equipos en la partida.
     *
     * @return lista ordenada de equipos
     */
    public List<Team> getOrder() {
        return order;
    }

    /**
     * Avanza al siguiente jugador. Si ya jugaron los 3 del equipo,
     * avanza al siguiente equipo.
     *
     * @return true si aún hay equipos por jugar, false si la partida terminó
     */
    public boolean next() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= getCurrentTeam().getPlayers().size()) {
            currentPlayerIndex = 0;
            currentTeamIndex++;
        }
        return currentTeamIndex < order.size();
    }

    /**
     * Registra los puntos obtenidos por el equipo actual.
     *
     * @param points puntos a agregar
     */
    public void addPoints(int points) {
        TeamStats stats = table.get(getCurrentTeam());
        if (points > 0) {
            stats.addEmbocada();
            stats.addPoints(points);
        }
        stats.addAttempt();
    }

    /**
     * Retorna el equipo que está jugando actualmente.
     *
     * @return equipo actual
     */
    public Team getCurrentTeam() {
        return order.get(currentTeamIndex);
    }

    /**
     * Retorna el índice del jugador actual dentro del equipo.
     *
     * @return índice del jugador actual
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Retorna las estadísticas de un equipo específico.
     *
     * @param team equipo a consultar
     * @return estadísticas del equipo
     */
    public TeamStats getStats(Team team) {
        return table.get(team);
    }

    /**
     * Retorna la tabla completa de equipos con sus estadísticas.
     *
     * @return mapa de equipos y estadísticas
     */
    public Map<Team, TeamStats> getTable() {
        return table;
    }

    /**
     * Determina el equipo ganador al final de la partida.
     * En caso de empate en puntos, gana quien tuvo más intentos embocados.
     *
     * @return equipo ganador
     */
    public Team getWinner() {
        Team winner = null;
        int maxPoints = -1;
        int maxEmbocadas = -1;

        for (Map.Entry<Team, TeamStats> entry : table.entrySet()) {
            TeamStats stats = entry.getValue();
            if (stats.getPoints() > maxPoints ||
               (stats.getPoints() == maxPoints && stats.getEmbocadas() > maxEmbocadas)) {
                maxPoints = stats.getPoints();
                maxEmbocadas = stats.getEmbocadas();
                winner = entry.getKey();
            }
        }
        return winner;
    }

    /**
     * Verifica si la partida ha terminado.
     *
     * @return true si todos los equipos ya jugaron
     */
    public boolean isFinished() {
        return currentTeamIndex >= order.size();
    }
}
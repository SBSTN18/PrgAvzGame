package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un equipo participante en el juego del Balero.
 *
 * <p>Cada equipo pertenece a un proyecto curricular de la Universidad Distrital
 * y está compuesto por exactamente {@value #JUGADORES_POR_EQUIPO} jugadores.
 * El puntaje total del equipo es la suma de los puntajes individuales de sus jugadores.</p>
 *
 * <p>Implementa {@link Serializable} para permitir la persistencia del estado
 * del equipo al finalizar cada partida.</p>
 *
 * <p><b>Principio SRP:</b> esta clase solo es responsable de agrupar jugadores
 * y calcular estadísticas del equipo.</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 * @see Jugador
 * @see Partida
 */
public class Equipo implements Serializable {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Número exacto de jugadores que debe tener cada equipo.
     * Se usa en {@link #esValido()} y en la lógica de tiempo del controlador.
     */
    public static final int JUGADORES_POR_EQUIPO = 3;

    // ─────────────────────────────────────────────
    //  Atributos
    // ─────────────────────────────────────────────

    /** Nombre identificador del equipo. */
    private String nombreEquipo;

    /** Nombre del proyecto curricular al que pertenece el equipo. */
    private String proyectoCurricular;

    /**
     * Lista de jugadores del equipo.
     * Máximo {@link #JUGADORES_POR_EQUIPO} jugadores permitidos.
     */
    private List<Jugador> jugadores;

    // ─────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────

    /**
     * Construye un nuevo equipo con su nombre y proyecto curricular.
     * La lista de jugadores comienza vacía.
     *
     * @param nombreEquipo       nombre del equipo
     * @param proyectoCurricular nombre del proyecto curricular
     */
    public Equipo(String nombreEquipo, String proyectoCurricular) {
        this.nombreEquipo       = nombreEquipo;
        this.proyectoCurricular = proyectoCurricular;
        this.jugadores          = new ArrayList<>();
    }

    // ─────────────────────────────────────────────
    //  Métodos de negocio
    // ─────────────────────────────────────────────

    /**
     * Agrega un jugador al equipo.
     * Solo acepta hasta {@link #JUGADORES_POR_EQUIPO} jugadores.
     *
     * @param jugador jugador a agregar
     * @throws IllegalStateException si el equipo ya tiene 3 jugadores
     */
    public void agregarJugador(Jugador jugador) {
        if (jugadores.size() >= JUGADORES_POR_EQUIPO) {
            throw new IllegalStateException(
                "El equipo ya tiene el maximo de " + JUGADORES_POR_EQUIPO + " jugadores.");
        }
        jugadores.add(jugador);
    }

    /**
     * Calcula el puntaje total del equipo sumando los puntajes individuales
     * de todos sus jugadores.
     *
     * @return puntaje total acumulado del equipo
     */
    public int getPuntajeTotal() {
        int total = 0;
        for (Jugador j : jugadores) {
            total += j.getPuntaje();
        }
        return total;
    }

    /**
     * Calcula el total de intentos realizados por todos los jugadores del equipo.
     * Se usa para resolver empates en la puntuación.
     *
     * @return total de intentos del equipo
     */
    public int getIntentosTotal() {
        int total = 0;
        for (Jugador j : jugadores) {
            total += j.getIntentos();
        }
        return total;
    }

    /**
     * Verifica si el equipo es válido para participar en la partida.
     * Un equipo es válido cuando tiene exactamente {@link #JUGADORES_POR_EQUIPO} jugadores.
     *
     * @return {@code true} si el equipo tiene exactamente 3 jugadores
     */
    public boolean esValido() {
        return jugadores.size() == JUGADORES_POR_EQUIPO;
    }

    // ─────────────────────────────────────────────
    //  Getters y Setters
    // ─────────────────────────────────────────────

    /**
     * Retorna el nombre del equipo.
     *
     * @return nombre del equipo
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param nombreEquipo nuevo nombre del equipo
     */
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    /**
     * Retorna el nombre del proyecto curricular del equipo.
     *
     * @return proyecto curricular
     */
    public String getProyectoCurricular() {
        return proyectoCurricular;
    }

    /**
     * Establece el proyecto curricular del equipo.
     *
     * @param proyectoCurricular nuevo proyecto curricular
     */
    public void setProyectoCurricular(String proyectoCurricular) {
        this.proyectoCurricular = proyectoCurricular;
    }

    /**
     * Retorna una copia de la lista de jugadores del equipo.
     *
     * @return lista de {@link Jugador} del equipo
     */
    public List<Jugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    /**
     * Retorna una representación en texto del equipo con sus estadísticas.
     *
     * @return cadena con nombre, proyecto y puntaje total
     */
    @Override
    public String toString() {
        return "Equipo{nombre='" + nombreEquipo
                + "', proyecto='" + proyectoCurricular
                + "', puntajeTotal=" + getPuntajeTotal() + "}";
    }
}
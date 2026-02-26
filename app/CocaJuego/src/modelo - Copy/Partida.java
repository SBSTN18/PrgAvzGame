package modelo;

import java.util.List;

/**
 * Representa una partida del juego del Balero.
 *
 * <p>Gestiona la lista de equipos participantes, el tiempo asignado por equipo,
 * el estado de la partida (en curso / finalizada) y la determinación del ganador.</p>
 *
 * <p>Reglas para determinar el ganador:</p>
 * <ol>
 *   <li>Gana el equipo con mayor puntaje total.</li>
 *   <li>En caso de empate en puntaje, gana el equipo con mayor número de intentos.</li>
 * </ol>
 *
 * <p><b>Principio SRP:</b> esta clase solo gestiona el estado y las reglas
 * de una partida, sin lógica de interfaz ni de persistencia.</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 * @see Equipo
 */
public class Partida {

    // ─────────────────────────────────────────────
    //  Atributos
    // ─────────────────────────────────────────────

    /** Lista de equipos que participan en esta partida. */
    private final List<Equipo> equipos;

    /** Tiempo total en segundos asignado a cada equipo. */
    private final int tiempoPorEquipoSegundos;

    /** Equipo declarado ganador al finalizar la partida. */
    private Equipo equipoGanador;

    /** Indica si la partida está actualmente en curso. */
    private boolean enCurso;

    // ─────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────

    /**
     * Construye una nueva partida con los equipos y el tiempo por equipo indicados.
     *
     * @param equipos                 lista de equipos que participarán
     * @param tiempoPorEquipoSegundos tiempo en segundos para cada equipo
     * @throws IllegalArgumentException si la lista de equipos es nula o vacía
     */
    public Partida(List<Equipo> equipos, int tiempoPorEquipoSegundos) {
        if (equipos == null || equipos.isEmpty()) {
            throw new IllegalArgumentException(
                "Debe haber al menos un equipo para iniciar la partida.");
        }
        this.equipos                  = equipos;
        this.tiempoPorEquipoSegundos  = tiempoPorEquipoSegundos;
        this.enCurso                  = false;
        this.equipoGanador            = null;
    }

    // ─────────────────────────────────────────────
    //  Métodos de control de partida
    // ─────────────────────────────────────────────

    /**
     * Marca la partida como iniciada (en curso).
     */
    public void iniciar() {
        enCurso = true;
    }

    /**
     * Finaliza la partida: calcula el equipo ganador y marca el estado como terminado.
     */
    public void finalizar() {
        enCurso       = false;
        equipoGanador = determinarGanador();
    }

    /**
     * Determina el equipo ganador según las reglas del juego:
     * <ol>
     *   <li>Mayor puntaje total gana.</li>
     *   <li>Empate en puntaje: mayor número total de intentos gana.</li>
     * </ol>
     *
     * @return equipo ganador de la partida
     */
    public Equipo determinarGanador() {
        Equipo ganador = null;

        for (Equipo equipo : equipos) {
            if (ganador == null) {
                ganador = equipo;
            } else {
                int ptsGanador = ganador.getPuntajeTotal();
                int ptsActual  = equipo.getPuntajeTotal();

                if (ptsActual > ptsGanador) {
                    // Este equipo tiene más puntos: nuevo ganador
                    ganador = equipo;
                } else if (ptsActual == ptsGanador) {
                    // Empate en puntos: desempate por intentos
                    if (equipo.getIntentosTotal() > ganador.getIntentosTotal()) {
                        ganador = equipo;
                    }
                }
            }
        }
        return ganador;
    }

    /**
     * Calcula el tiempo en segundos que le corresponde a cada jugador dentro del equipo.
     * Es exactamente un tercio del tiempo total del equipo.
     *
     * @return tiempo por jugador en segundos
     */
    public int getTiempoPorJugador() {
        return tiempoPorEquipoSegundos / Equipo.JUGADORES_POR_EQUIPO;
    }

    // ─────────────────────────────────────────────
    //  Getters
    // ─────────────────────────────────────────────

    /**
     * Retorna la lista de equipos participantes en la partida.
     *
     * @return lista de {@link Equipo}
     */
    public List<Equipo> getEquipos() {
        return equipos;
    }

    /**
     * Retorna el tiempo total en segundos asignado por equipo.
     *
     * @return tiempo por equipo en segundos
     */
    public int getTiempoPorEquipoSegundos() {
        return tiempoPorEquipoSegundos;
    }

    /**
     * Retorna el equipo ganador de la partida.
     * Solo tiene valor después de llamar a {@link #finalizar()}.
     *
     * @return equipo ganador, o {@code null} si la partida no ha finalizado
     */
    public Equipo getEquipoGanador() {
        return equipoGanador;
    }

    /**
     * Indica si la partida está actualmente en curso.
     *
     * @return {@code true} si está en curso, {@code false} si no ha iniciado o ya terminó
     */
    public boolean isEnCurso() {
        return enCurso;
    }
}
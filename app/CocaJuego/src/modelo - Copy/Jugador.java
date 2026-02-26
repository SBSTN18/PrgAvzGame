package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un jugador participante en el juego del Balero.
 *
 * <p>Almacena la información personal del estudiante (código y nombre),
 * su puntaje acumulado durante la partida, el número total de intentos
 * realizados y el historial de embocadas logradas.</p>
 *
 * <p>Implementa {@link Serializable} para permitir guardar y restaurar
 * el estado de los jugadores entre ejecuciones del programa.</p>
 *
 * <p><b>Principio SRP:</b> esta clase solo es responsable de almacenar
 * y gestionar los datos de un único jugador.</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 * @see TipoEmbocada
 * @see Equipo
 */
public class Jugador implements Serializable {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    // ─────────────────────────────────────────────
    //  Atributos
    // ─────────────────────────────────────────────

    /** Código único del estudiante en la universidad. */
    private String codigoEstudiante;

    /** Nombre completo del jugador. */
    private String nombre;

    /** Puntaje acumulado durante la partida actual. */
    private int puntaje;

    /** Total de intentos realizados (embocados y fallidos). */
    private int intentos;

    /** Lista de tipos de embocada logrados por el jugador. */
    private List<TipoEmbocada> embocadasLogradas;

    // ─────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────

    /**
     * Construye un nuevo jugador con su código de estudiante y nombre.
     * Inicializa el puntaje e intentos en cero y la lista de embocadas vacía.
     *
     * @param codigoEstudiante código único del estudiante
     * @param nombre           nombre completo del jugador
     */
    public Jugador(String codigoEstudiante, String nombre) {
        this.codigoEstudiante  = codigoEstudiante;
        this.nombre            = nombre;
        this.puntaje           = 0;
        this.intentos          = 0;
        this.embocadasLogradas = new ArrayList<>();
    }

    // ─────────────────────────────────────────────
    //  Métodos de negocio
    // ─────────────────────────────────────────────

    /**
     * Registra un intento del jugador.
     *
     * <p>Siempre incrementa el contador de intentos. Si el tipo de embocada
     * es diferente de {@link TipoEmbocada#SIN_EMBOCADA}, también suma los
     * puntos correspondientes y agrega la embocada al historial.</p>
     *
     * @param tipo tipo de embocada realizada (puede ser SIN_EMBOCADA)
     */
    public void registrarIntento(TipoEmbocada tipo) {
        intentos++;
        if (tipo != TipoEmbocada.SIN_EMBOCADA) {
            puntaje += tipo.getPuntos();
            embocadasLogradas.add(tipo);
        }
    }

    /**
     * Reinicia las estadísticas del jugador para comenzar una nueva partida.
     * Pone el puntaje e intentos en cero y vacía el historial de embocadas.
     */
    public void reiniciar() {
        puntaje  = 0;
        intentos = 0;
        embocadasLogradas.clear();
    }

    // ─────────────────────────────────────────────
    //  Getters y Setters
    // ─────────────────────────────────────────────

    /**
     * Retorna el código del estudiante.
     *
     * @return código del estudiante
     */
    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    /**
     * Establece el código del estudiante.
     *
     * @param codigoEstudiante nuevo código del estudiante
     */
    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    /**
     * Retorna el nombre completo del jugador.
     *
     * @return nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del jugador.
     *
     * @param nombre nuevo nombre del jugador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el puntaje acumulado del jugador en la partida actual.
     *
     * @return puntaje total del jugador
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Retorna el total de intentos realizados por el jugador.
     *
     * @return número de intentos (incluye fallidos y exitosos)
     */
    public int getIntentos() {
        return intentos;
    }

    /**
     * Retorna una copia de la lista de embocadas logradas por el jugador.
     *
     * @return lista de {@link TipoEmbocada} exitosas
     */
    public List<TipoEmbocada> getEmbocadasLogradas() {
        return new ArrayList<>(embocadasLogradas);
    }

    /**
     * Retorna una representación en texto del jugador con sus estadísticas.
     *
     * @return cadena con código, nombre, puntaje e intentos
     */
    @Override
    public String toString() {
        return "Jugador{codigo='" + codigoEstudiante
                + "', nombre='" + nombre
                + "', puntaje=" + puntaje
                + ", intentos=" + intentos + "}";
    }
}
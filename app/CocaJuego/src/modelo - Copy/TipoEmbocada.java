package modelo;

/**
 * Enumeración que define los tipos de embocada posibles en el juego del Balero.
 *
 * <p>Cada constante del ENUM representa una técnica de juego con su respectivo
 * puntaje y descripción. Los valores son usados por el controlador para generar
 * embocadas aleatorias y por el modelo para acumular puntos.</p>
 *
 * <p>Incluye {@code SIN_EMBOCADA} para representar intentos fallidos (0 puntos).</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 */
public enum TipoEmbocada {

    /**
     * Emboque básico y directo en el primer intento.
     * Puntaje: 2 puntos.
     */
    SIMPLE(2, "Emboque basico y directo"),

    /**
     * Dos giros o movimientos técnicos antes de encajar la pieza.
     * Puntaje: 10 puntos.
     */
    DOBLE(10, "Dos giros antes de encajar la pieza"),

    /**
     * Técnica estándar donde la pieza sube recta y se encaja.
     * Puntaje: 3 puntos.
     */
    VERTICAL(3, "La pieza sube recta y se encaja"),

    /**
     * Técnica con giro o balanceo particular.
     * Puntaje: 4 puntos.
     */
    MARIQUITA(4, "Tecnica con giro o balanceo particular"),

    /**
     * Emboque rápido, seco y contundente.
     * Puntaje: 5 puntos.
     */
    PUÑALADA(5, "Emboque rapido, seco y contundente"),

    /**
     * Variante técnica tradicional para atrapar la bola.
     * Puntaje: 6 puntos.
     */
    PURTIÑA(6, "Variante tecnica tradicional"),

    /**
     * Emboque realizado con la mano en posición invertida.
     * Puntaje: 8 puntos.
     */
    DOMINIO_REVES(8, "Emboque con la mano invertida"),

    /**
     * Intento fallido: no se logró embocar.
     * Puntaje: 0 puntos.
     */
    SIN_EMBOCADA(0, "Intento fallido, no se emboeo");

    // ─────────────────────────────────────────────
    //  Atributos del enum
    // ─────────────────────────────────────────────

    /** Puntos que otorga este tipo de embocada al jugador. */
    private final int puntos;

    /** Descripción textual del movimiento. */
    private final String descripcion;

    // ─────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────

    /**
     * Constructor del enum TipoEmbocada.
     *
     * @param puntos      puntaje que otorga la embocada
     * @param descripcion descripción del movimiento
     */
    TipoEmbocada(int puntos, String descripcion) {
        this.puntos      = puntos;
        this.descripcion = descripcion;
    }

    // ─────────────────────────────────────────────
    //  Getters
    // ─────────────────────────────────────────────

    /**
     * Retorna los puntos asociados a este tipo de embocada.
     *
     * @return puntos del tipo de embocada
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Retorna la descripción textual del movimiento.
     *
     * @return descripción de la embocada
     */
    public String getDescripcion() {
        return descripcion;
    }
}
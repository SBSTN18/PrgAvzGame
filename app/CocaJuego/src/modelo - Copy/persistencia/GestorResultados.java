package modelo.persistencia;

import modelo.Equipo;
import modelo.Jugador;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la persistencia de los resultados del juego usando {@link RandomAccessFile}.
 *
 * <p><b>IMPORTANTE:</b> Este componente usa EXCLUSIVAMENTE {@link RandomAccessFile}
 * (acceso aleatorio/directo). El uso de cualquier otro tipo de archivo para este
 * requerimiento implica calificación de 0.0 según las reglas del taller.</p>
 *
 * <p>Cada registro tiene un <b>tamaño fijo</b> de {@value #BYTES_POR_REGISTRO} bytes,
 * lo que permite acceso directo por índice sin recorrer el archivo secuencialmente.</p>
 *
 * <p>Estructura de cada registro:</p>
 * <pre>
 *  Campo              Tipo / Tamaño        Bytes
 *  ─────────────────  ───────────────────  ──────
 *  clave              long (8 bytes)            8
 *  nombreEquipo       50 chars × 2 bytes      100
 *  nombreJugador1     40 chars × 2 bytes       80
 *  nombreJugador2     40 chars × 2 bytes       80
 *  nombreJugador3     40 chars × 2 bytes       80
 *  puntaje            int (4 bytes)             4
 *  ─────────────────────────────────────────────
 *  TOTAL                                      352 bytes
 * </pre>
 *
 * <p><b>Principio SRP:</b> esta clase solo gestiona la lectura y escritura
 * del archivo de resultados.</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 * @see Equipo
 */
public class GestorResultados implements IGestorArchivo {

    // ─────────────────────────────────────────────
    //  Constantes de tamaño de campo
    // ─────────────────────────────────────────────

    /** Máximo de caracteres para el nombre del equipo en el registro. */
    private static final int TAM_EQUIPO = 50;

    /** Máximo de caracteres para el nombre de un jugador en el registro. */
    private static final int TAM_JUGADOR = 40;

    /**
     * Tamaño total en bytes de cada registro del archivo:
     * 8 (long) + 100 (equipo) + 80 + 80 + 80 (jugadores) + 4 (int) = 352 bytes.
     */
    private static final int BYTES_POR_REGISTRO =
            Long.BYTES                         // clave
            + (TAM_EQUIPO  * Character.BYTES)  // nombreEquipo
            + (TAM_JUGADOR * Character.BYTES)  // nombreJugador1
            + (TAM_JUGADOR * Character.BYTES)  // nombreJugador2
            + (TAM_JUGADOR * Character.BYTES)  // nombreJugador3
            + Integer.BYTES;                   // puntaje

    // ─────────────────────────────────────────────
    //  Atributos
    // ─────────────────────────────────────────────

    /** Ruta al archivo de resultados {@code resultados.dat}. */
    private final String rutaArchivo;

    // ─────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────

    /**
     * Construye el gestor apuntando a la ruta de archivo indicada.
     * Crea los directorios necesarios si no existen.
     *
     * @param rutaArchivo ruta completa del archivo {@code resultados.dat}
     */
    public GestorResultados(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File dir = new File(rutaArchivo).getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
    }

    // ─────────────────────────────────────────────
    //  Métodos públicos
    // ─────────────────────────────────────────────

    /**
     * Guarda el resultado del equipo ganador al final del archivo de acceso aleatorio.
     *
     * <p>La clave del registro se genera automáticamente como el número total
     * de registros existentes más uno.</p>
     *
     * @param equipo equipo cuyos datos se van a persistir en el archivo
     * @throws IOException si ocurre un error durante la escritura
     */
    public void guardarResultado(Equipo equipo) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long clave = calcularSiguienteClave(raf);
            raf.seek(raf.length()); // posicionarse al final del archivo

            raf.writeLong(clave);
            escribirString(raf, equipo.getNombreEquipo(), TAM_EQUIPO);

            List<Jugador> jugadores = equipo.getJugadores();
            for (int i = 0; i < Equipo.JUGADORES_POR_EQUIPO; i++) {
                String nombreJug = (i < jugadores.size()) ? jugadores.get(i).getNombre() : "";
                escribirString(raf, nombreJug, TAM_JUGADOR);
            }

            raf.writeInt(equipo.getPuntajeTotal());
        }
    }

    /**
     * Lee el archivo completo de forma secuencial y cuenta cuántas veces
     * el equipo indicado aparece como ganador en registros anteriores.
     *
     * @param nombreEquipo nombre exacto del equipo a buscar
     * @return número de victorias registradas para ese equipo (incluye la actual)
     * @throws IOException si ocurre un error durante la lectura
     */
    public int contarVictorias(String nombreEquipo) throws IOException {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists() || archivo.length() == 0) {
            return 0;
        }

        int victorias = 0;
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long totalRegistros = raf.length() / BYTES_POR_REGISTRO;
            for (long i = 0; i < totalRegistros; i++) {
                raf.seek(i * BYTES_POR_REGISTRO);
                raf.readLong(); // saltar la clave (8 bytes)
                String nombreLeido = leerString(raf, TAM_EQUIPO).trim();
                if (nombreLeido.equalsIgnoreCase(nombreEquipo.trim())) {
                    victorias++;
                }
            }
        }
        return victorias;
    }

    /**
     * Lee todos los registros del archivo y los retorna como lista de arreglos de texto.
     *
     * <p>Cada arreglo tiene el formato:
     * {@code [clave, nombreEquipo, jugador1, jugador2, jugador3, puntaje]}.</p>
     *
     * @return lista de registros leídos del archivo; lista vacía si el archivo no existe
     * @throws IOException si ocurre un error durante la lectura
     */
    public List<String[]> leerTodos() throws IOException {
        List<String[]> registros = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists() || archivo.length() == 0) {
            return registros;
        }

        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long totalRegistros = raf.length() / BYTES_POR_REGISTRO;
            for (long i = 0; i < totalRegistros; i++) {
                raf.seek(i * BYTES_POR_REGISTRO);
                String clave   = String.valueOf(raf.readLong());
                String equipo  = leerString(raf, TAM_EQUIPO).trim();
                String jug1    = leerString(raf, TAM_JUGADOR).trim();
                String jug2    = leerString(raf, TAM_JUGADOR).trim();
                String jug3    = leerString(raf, TAM_JUGADOR).trim();
                String puntaje = String.valueOf(raf.readInt());
                registros.add(new String[]{clave, equipo, jug1, jug2, jug3, puntaje});
            }
        }
        return registros;
    }

    // ─────────────────────────────────────────────
    //  Métodos privados auxiliares
    // ─────────────────────────────────────────────

    /**
     * Calcula la siguiente clave disponible contando los registros existentes en el archivo.
     *
     * @param raf instancia abierta del {@link RandomAccessFile}
     * @return siguiente clave = (número de registros) + 1
     * @throws IOException si ocurre un error al leer la longitud del archivo
     */
    private long calcularSiguienteClave(RandomAccessFile raf) throws IOException {
        return (raf.length() / BYTES_POR_REGISTRO) + 1L;
    }

    /**
     * Escribe una cadena de longitud fija en el archivo usando {@code writeChar}.
     * Si la cadena es más corta que {@code tam}, se rellena con espacios.
     * Si es más larga, se trunca.
     *
     * @param raf   instancia de {@link RandomAccessFile} abierta en modo escritura
     * @param texto texto a escribir
     * @param tam   número de caracteres fijos a escribir
     * @throws IOException si ocurre un error de escritura
     */
    private void escribirString(RandomAccessFile raf, String texto, int tam) throws IOException {
        String fuente = (texto == null) ? "" : texto;
        if (fuente.length() > tam) {
            fuente = fuente.substring(0, tam);
        }
        for (int i = 0; i < tam; i++) {
            char c = (i < fuente.length()) ? fuente.charAt(i) : ' ';
            raf.writeChar(c);
        }
    }

    /**
     * Lee exactamente {@code tam} caracteres del {@link RandomAccessFile} usando
     * {@code readChar} y los retorna como cadena.
     *
     * @param raf instancia de {@link RandomAccessFile} abierta en modo lectura
     * @param tam número de caracteres a leer
     * @return cadena leída del archivo
     * @throws IOException si ocurre un error de lectura
     */
    private String leerString(RandomAccessFile raf, int tam) throws IOException {
        StringBuilder sb = new StringBuilder(tam);
        for (int i = 0; i < tam; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString();
    }

    // ─────────────────────────────────────────────
    //  Implementación de IGestorArchivo
    // ─────────────────────────────────────────────

    /**
     * Guarda el resultado del equipo recibido en el archivo de acceso aleatorio.
     *
     * @param objeto se espera un objeto {@link Equipo}
     * @throws Exception si el tipo no es {@link Equipo} o hay un error de escritura
     */
    @Override
    public void guardar(Object objeto) throws Exception {
        if (objeto instanceof Equipo) {
            guardarResultado((Equipo) objeto);
        }
    }

    /**
     * Lee todos los registros del archivo indicado.
     *
     * @param archivo ignorado; se usa la ruta configurada en el constructor
     * @return lista de arreglos {@code String[]} con los registros del archivo
     * @throws Exception si ocurre un error de lectura
     */
    @Override
    public Object cargar(File archivo) throws Exception {
        return leerTodos();
    }
}
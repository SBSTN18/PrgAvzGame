package modelo.persistencia;

import modelo.Equipo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la serialización y deserialización de la lista de equipos del juego.
 *
 * <p>Al finalizar cada partida, todos los equipos participantes se serializan
 * en un archivo {@code .ser}. En la siguiente ejecución del programa, si el
 * usuario presiona el botón de precarga, los equipos se deserializan y se
 * restauran al estado en que quedaron.</p>
 *
 * <p><b>Principio SRP:</b> esta clase solo se encarga de la serialización
 * y deserialización de objetos {@link Equipo}. No contiene lógica de negocio.</p>
 *
 * <p><b>Principio DIP:</b> implementa {@link IGestorArchivo} para que
 * el controlador dependa de la abstracción, no de esta clase concreta.</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 * @see Equipo
 * @see IGestorArchivo
 */
public class GestorSerializacion implements IGestorArchivo {

    // ─────────────────────────────────────────────
    //  Métodos públicos
    // ─────────────────────────────────────────────

    /**
     * Serializa la lista completa de equipos en el archivo especificado.
     *
     * <p>Crea el archivo y los directorios necesarios si no existen.
     * Sobreescribe cualquier archivo existente con el mismo nombre.</p>
     *
     * @param equipos lista de {@link Equipo} a serializar
     * @param archivo archivo de destino ({@code .ser})
     * @throws IOException si ocurre un error durante la escritura
     */
    public void serializarEquipos(List<Equipo> equipos, File archivo) throws IOException {
        if (archivo.getParentFile() != null) {
            archivo.getParentFile().mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(equipos);
        }
    }

    /**
     * Deserializa y retorna la lista de equipos desde el archivo especificado.
     *
     * <p>Si el archivo no existe, retorna una lista vacía sin lanzar excepción.</p>
     *
     * @param archivo archivo {@code .ser} desde donde se leerán los equipos
     * @return lista de {@link Equipo} deserializados, o lista vacía si el archivo no existe
     * @throws IOException            si ocurre un error durante la lectura
     * @throws ClassNotFoundException si la clase {@link Equipo} no se encuentra al deserializar
     */
    @SuppressWarnings("unchecked")
    public List<Equipo> deserializarEquipos(File archivo) throws IOException, ClassNotFoundException {
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Equipo>) ois.readObject();
        }
    }

    // ─────────────────────────────────────────────
    //  Implementación de IGestorArchivo
    // ─────────────────────────────────────────────

    /**
     * Serializa el objeto recibido si es una {@code List} de equipos.
     * Guarda en la ruta por defecto {@code Specs/data/equipos.ser}.
     *
     * @param objeto se espera una {@code List<Equipo>}
     * @throws Exception si el tipo no es compatible o hay error de escritura
     */
    @Override
    @SuppressWarnings("unchecked")
    public void guardar(Object objeto) throws Exception {
        if (objeto instanceof List) {
            File archivo = new File("Specs/data/equipos.ser");
            serializarEquipos((List<Equipo>) objeto, archivo);
        }
    }

    /**
     * Deserializa los equipos desde el archivo recibido.
     *
     * @param archivo archivo {@code .ser} de origen
     * @return lista de equipos como {@link Object}
     * @throws Exception si hay error de lectura o de clase
     */
    @Override
    public Object cargar(File archivo) throws Exception {
        return deserializarEquipos(archivo);
    }
}
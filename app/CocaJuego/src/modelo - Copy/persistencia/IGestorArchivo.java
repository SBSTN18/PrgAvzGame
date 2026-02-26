package modelo.persistencia;

import java.io.File;

/**
 * Interfaz que define el contrato para todos los gestores de archivos del proyecto.
 *
 * <p>Permite aplicar el <b>Principio de Inversión de Dependencias (DIP)</b>:
 * el controlador depende de esta abstracción y no de las implementaciones concretas
 * ({@code GestorResultados}, {@code GestorSerializacion}).</p>
 *
 * <p>También cumple el <b>Principio Abierto/Cerrado (OCP)</b>: se pueden agregar
 * nuevas formas de persistencia (XML, base de datos, etc.) simplemente creando
 * nuevas clases que implementen esta interfaz, sin modificar el controlador.</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 * @see modelo.persistencia.GestorResultados
 * @see modelo.persistencia.GestorSerializacion
 */
public interface IGestorArchivo {

    /**
     * Guarda el objeto recibido en el medio de persistencia correspondiente.
     *
     * @param objeto objeto a persistir
     * @throws Exception si ocurre algún error durante la operación de escritura
     */
    void guardar(Object objeto) throws Exception;

    /**
     * Carga datos desde el archivo indicado.
     *
     * @param archivo archivo de origen desde donde se leerán los datos
     * @return objeto cargado desde el archivo de persistencia
     * @throws Exception si ocurre algún error durante la operación de lectura
     */
    Object cargar(File archivo) throws Exception;
}
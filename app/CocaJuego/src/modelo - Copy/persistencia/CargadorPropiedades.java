package modelo.persistencia;

import modelo.Equipo;
import modelo.Jugador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Lee y parsea el archivo {@code .properties} para construir los equipos y jugadores
 * que participarán en la partida.
 *
 * <p>El formato esperado del archivo de propiedades es:</p>
 * <pre>
 * equipos.cantidad=2
 *
 * equipo1.proyecto=Ingenieria de Sistemas
 * equipo1.nombre=Los Compiladores
 * equipo1.jugador1.codigo=20231001
 * equipo1.jugador1.nombre=Juan Perez
 * equipo1.jugador2.codigo=20231002
 * equipo1.jugador2.nombre=Maria Garcia
 * equipo1.jugador3.codigo=20231003
 * equipo1.jugador3.nombre=Carlos Lopez
 * </pre>
 *
 * <p><b>Principio SRP:</b> esta clase solo se encarga de leer y parsear
 * el archivo de propiedades. La apertura del archivo con {@code JFileChooser}
 * ocurre en la capa de controlador, no aquí.</p>
 *
 * <p><b>Principio OCP:</b> si cambia el formato del archivo, solo se modifica
 * esta clase, sin afectar el resto del sistema.</p>
 *
 * @author Programacion Avanzada - UD
 * @version 1.0
 * @see Equipo
 * @see Jugador
 */
public class CargadorPropiedades implements IGestorArchivo {

    // ─────────────────────────────────────────────
    //  Métodos públicos
    // ─────────────────────────────────────────────

    /**
     * Lee el archivo {@code .properties} y construye la lista de equipos con sus jugadores.
     *
     * <p>Solo incluye en la lista los equipos que tienen exactamente 3 jugadores válidos.
     * Los equipos incompletos son ignorados silenciosamente.</p>
     *
     * @param archivo archivo {@code .properties} a leer
     * @return lista de {@link Equipo} cargados correctamente
     * @throws IOException si ocurre un error al abrir o leer el archivo
     */
    public List<Equipo> cargarEquipos(File archivo) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(archivo)) {
            props.load(fis);
        }

        int cantidad      = Integer.parseInt(props.getProperty("equipos.cantidad", "0").trim());
        List<Equipo> lista = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            Equipo equipo = crearEquipo(props, i);
            if (equipo != null && equipo.esValido()) {
                lista.add(equipo);
            }
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    //  Métodos privados de construcción
    // ─────────────────────────────────────────────

    /**
     * Construye un objeto {@link Equipo} a partir de las propiedades del archivo.
     *
     * @param props objeto {@link Properties} con todas las claves del archivo
     * @param index índice del equipo (1-based) para construir el prefijo de búsqueda
     * @return equipo construido, o {@code null} si faltan datos obligatorios
     */
    private Equipo crearEquipo(Properties props, int index) {
        String prefijo  = "equipo" + index + ".";
        String proyecto = props.getProperty(prefijo + "proyecto");
        String nombre   = props.getProperty(prefijo + "nombre");

        if (proyecto == null || nombre == null) {
            return null;
        }

        Equipo equipo = new Equipo(nombre.trim(), proyecto.trim());

        for (int j = 1; j <= Equipo.JUGADORES_POR_EQUIPO; j++) {
            Jugador jugador = crearJugador(props, prefijo + "jugador" + j + ".");
            if (jugador != null) {
                equipo.agregarJugador(jugador);
            }
        }
        return equipo;
    }

    /**
     * Construye un objeto {@link Jugador} a partir de las propiedades del archivo.
     *
     * @param props   objeto {@link Properties} con todas las claves
     * @param prefijo prefijo de la clave del jugador (ej: {@code "equipo1.jugador1."})
     * @return jugador construido, o {@code null} si faltan datos obligatorios
     */
    private Jugador crearJugador(Properties props, String prefijo) {
        String codigo = props.getProperty(prefijo + "codigo");
        String nombre = props.getProperty(prefijo + "nombre");

        if (codigo == null || nombre == null) {
            return null;
        }
        return new Jugador(codigo.trim(), nombre.trim());
    }

    // ─────────────────────────────────────────────
    //  Implementación de IGestorArchivo
    // ─────────────────────────────────────────────

    /**
     * No aplica para esta clase.
     * La carga se hace mediante {@link #cargarEquipos(File)}.
     * Método presente únicamente para cumplir el contrato de {@link IGestorArchivo}.
     *
     * @param objeto ignorado
     */
    @Override
    public void guardar(Object objeto) {
        // No aplica para CargadorPropiedades (solo lectura)
    }

    /**
     * Delega en {@link #cargarEquipos(File)} para cumplir el contrato de la interfaz.
     *
     * @param archivo archivo {@code .properties} a leer
     * @return lista de equipos como {@link Object}
     * @throws Exception si ocurre un error durante la lectura
     */
    @Override
    public Object cargar(File archivo) throws Exception {
        return cargarEquipos(archivo);
    }
}
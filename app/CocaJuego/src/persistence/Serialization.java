package persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import modelo.Team;

public class Serialization {

    private static final String PATH = "Specs/data/equipos.ser";

    public Serialization(){
    }


    /**
     * Serializa la lista de equipos, guardandola en un archivo .ser
     * devuelve true si se pudo serializar correctamente, false en caso contrario
     * @param teams
     * @return
     */
    public boolean save(List<Team> teams) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(PATH))) {

            out.writeObject(teams);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deserializa la lista de equipos, leyendo el archivo .ser
     * devuelve la lista de equipos deserializada, o una lista vacia si no se pudo deserializar
     * @return
     */
    public List<Team> load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(PATH))) {

            return (List<Team>) in.readObject();

        }  catch (IOException e) {
        return new ArrayList<>();

        } catch (ClassNotFoundException e) {
         return new ArrayList<>();
        }
    }
}

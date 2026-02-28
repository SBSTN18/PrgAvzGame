package persistence;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import modelo.Player;
import modelo.Team;

public class Winners {

    private static final int teamSize = 50;
    private static final int playerSize = 40;
    private final String rutaArchivo;

    private static final int bytesForRegister =
            Long.BYTES                         // clave
            + (teamSize  * Character.BYTES)  // nombreEquipo
            + (playerSize * Character.BYTES)  // nombreJugador1
            + (playerSize * Character.BYTES)  // nombreJugador2
            + (playerSize * Character.BYTES)  // nombreJugador3
            + Integer.BYTES;                 // puntaje

    public Winners(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File dir = new File(rutaArchivo).getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
    }


    public void saveWinner(Team team, int score) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long clave = calculateNext(raf);
            raf.seek(raf.length()); // posicionarse al final del archivo

            raf.writeLong(clave);
            writeString(raf, team.getName(), teamSize);

            List<Player> players = team.getPlayers();
            for (int i = 0; i < 3; i++) {
                String playerName = (i < players.size()) ? players.get(i).getName() : "";
                writeString(raf, playerName, playerSize);
            }

            raf.writeInt(score);
        }
    }

    private void writeString(RandomAccessFile raf, String texto, int tamaño) throws IOException {
        StringBuilder sb = new StringBuilder(texto);

        if (sb.length() > tamaño) {
            sb.setLength(tamaño);
        } else {
            while (sb.length() < tamaño) {
                sb.append(" ");
            }
        }

        raf.writeChars(sb.toString());
    }


    private long calculateNext(RandomAccessFile raf) throws IOException {
        return (raf.length() / bytesForRegister) + 1L;
    }
}

package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import modelo.Player;
import modelo.Team;

public class SavedTeams {

    

    public SavedTeams() {
    }

    public void saveTeam(Team team) {
        Properties properties = new Properties();
        OutputStream out = null;
        try {
            out = new FileOutputStream("Specs/data/teams/" + team.getName() + ".properties");
            properties.setProperty("proyect", team.getProyect());
            properties.setProperty("name", team.getName());
            properties.setProperty("pin", Integer.toString(team.getPin()));
            properties.setProperty("players.count", Integer.toString(team.getCantPlayers()));
           for (int i = 0; i < team.getCantPlayers(); i++) {
                properties.setProperty("player" + (i + 1) + ".name", team.getPlayers().get(i).getName());
                properties.setProperty("player" + (i + 1) + ".code", Integer.toString(team.getPlayers().get(i).getCode()));
            }
            properties.store(out, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Team loadTeam(File file) {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            properties.load(in);
            String proyect = properties.getProperty("proyect");
            String name = properties.getProperty("name");
            int pin = Integer.parseInt(properties.getProperty("pin"));
            int cantPlayers = Integer.parseInt(properties.getProperty("players.count"));
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < cantPlayers; i++) {
                String playerName = properties.getProperty("player" + (i + 1) + ".name");
                int playerCode = Integer.parseInt(properties.getProperty("player" + (i + 1) + ".code"));
                players.add(new Player(playerName, playerCode));
            }
            Team team = new Team(proyect, name, players, pin);
            return team;
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



}

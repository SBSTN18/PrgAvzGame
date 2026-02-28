package control;

import java.io.File;
import java.io.IOException;
import java.util.List;
import modelo.Team;
import persistence.SavedTeams;
import persistence.Serialization;
import persistence.Winners;

public class PersistenceControl {

    private Winners winners;
    private Serialization serialization;
    private SavedTeams savedTeams;

    public PersistenceControl() {
        this.winners = new Winners();
        this.serialization = new Serialization();
        this.savedTeams = new SavedTeams();
    }

    public boolean saveMatchSer(List<Team> teams) {
        return serialization.save(teams);
    }

    public List<Team> loadMatchSer() {
        return serialization.load();
    }

    public void saveTeamProperties(Team team) {
        savedTeams.saveTeam(team);
    }

    public Team loadTeamProperties(File file) {
        return savedTeams.loadTeam(file);
    }

    public void saveWinner(Team team, int score) throws IOException {
        winners.saveWinner(team, score);
    }

    public int countWins(String teamName) throws IOException {
        return winners.timesWinned(teamName);
    }

}

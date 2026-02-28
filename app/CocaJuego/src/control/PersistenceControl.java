package control;

import java.util.List;
import modelo.Team;
import persistence.SavedTeams;
import persistence.Serialization;

public class PersistenceControl {


    public PersistenceControl() {
    }

    public boolean saveMatchSer(List<Team> teams) {
        Serialization serialization = new Serialization();
        return serialization.save(teams);
    }

    public List<Team> loadMatchSer() {
        Serialization serialization = new Serialization();
        return serialization.load();
    }

    public void saveTeamProperties(Team team) {
        SavedTeams saveTeam = new SavedTeams();
        saveTeam.saveTeam(team);
    }

    
        

}

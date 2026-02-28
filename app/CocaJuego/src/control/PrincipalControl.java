package control;

public class PrincipalControl {

    private PersistenceControl persistenceControl;
    private TeamsGeneralControl teamControl;
    private Attemp attemp;

    public PrincipalControl() {
        this.persistenceControl = new PersistenceControl();
        this.teamControl = new TeamsGeneralControl();
        this.attemp = new Attemp();
    }

    
}

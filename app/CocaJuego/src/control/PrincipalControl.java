package control;

public class PrincipalControl {

    private PersistenceControl persistenceControl;
    private TeamsGeneralControl teamControl;
    private Attemp attemp;
    private VistaControl vistaControl;

    public PrincipalControl() {
        this.persistenceControl = new PersistenceControl();
        this.teamControl = new TeamsGeneralControl();
        this.attemp = new Attemp();
        this.vistaControl = new VistaControl(this);
    }

    
}

package Vista;

import java.awt.*;
import javax.swing.*;
import control.PrincipalControl;
import control.VistaControl;
import Vista.panels.HomePanel;
//import Vista.panels.LoadPanel;
import Vista.panels.GamePanel;
//import Vista.panels.ResultsPanel;

/**
 * Ventana principal de la aplicación.
 * Solo gestiona la estructura visual y el CardLayout.
 * Sin lógica ni listeners.
 */
public class MainFrame extends JFrame {

    public static final String HOME_PANEL    = "HOME";
    public static final String LOAD_PANEL    = "LOAD";
    public static final String GAME_PANEL    = "GAME";
    public static final String RESULTS_PANEL = "RESULTS";

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private HomePanel homePanel;
    private LoadPanel loadPanel;
    private GamePanel gamePanel;
    private ResultsPanel resultsPanel;

    public MainFrame() {
        initFrame();
        initPanels();
    }

    private void initFrame() {
        setTitle("El Juego del Balero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(new Color(20, 20, 20));
    }

    private void initPanels() {
        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(20, 20, 20));

        homePanel    = new HomePanel();
        loadPanel    = new LoadPanel();
        gamePanel    = new GamePanel();
        resultsPanel = new ResultsPanel();

        mainPanel.add(homePanel,    HOME_PANEL);
        mainPanel.add(loadPanel,    LOAD_PANEL);
        mainPanel.add(gamePanel,    GAME_PANEL);
        mainPanel.add(resultsPanel, RESULTS_PANEL);

        add(mainPanel);
        showPanel(HOME_PANEL);
    }

    /**
     * Muestra el panel indicado por su nombre.
     *
     * @param panel constante del panel a mostrar
     */
    public void showPanel(String panel) {
        cardLayout.show(mainPanel, panel);
    }

    // ─── GETTERS para VistaControl ───────────────────────────────────────────

    public HomePanel    getHomePanel()    { return homePanel;    }
    public LoadPanel    getLoadPanel()    { return loadPanel;    }
    public GamePanel    getGamePanel()    { return gamePanel;    }
    public ResultsPanel getResultsPanel() { return resultsPanel; }
}
package control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import modelo.Embocada;
import modelo.Team;
import Vista.MainFrame;
import Vista.dialogs.TeamValidationDialog;
import Vista.panels.*;

/**
 * Controlador de la vista.
 * Gestiona navegación y eventos de botones.
 * Delega toda la lógica a PrincipalControl.
 */
public class VistaControl {

    private MainFrame mainFrame;
    private PrincipalControl principalControl;
    private Timer gameTimer;
    private Timer delayTimer;
    private int tiempoPorJugador;

    public VistaControl(PrincipalControl principalControl) {
        this.mainFrame = new MainFrame();
        mainFrame.showPanel("HOME");
        init();
        mainFrame.setVisible(true);
        this.principalControl = principalControl;
    }

    /**
     * Inicializa todos los listeners de la aplicación.
     */
    public void init() {
        initHomeListeners();
        initMenuTorneoListeners();
        initRegisterListeners();
        initGameListeners();
        initResultsListeners();
    }

    // ─── HOME PANEL ──────────────────────────────────────────────────────────

    private void initHomeListeners() {
        HomePanel home = mainFrame.getHomePanel();

        home.getBtnNuevoTorneo().addActionListener(e ->
            mainFrame.showPanel(MainFrame.MENU_TORNEO_PANEL)
        );

        home.getBtnCargarUltimo().addActionListener(e -> {
            String error = principalControl.loadLastSession();
            if (error != null) {
                JOptionPane.showMessageDialog(mainFrame,
                    error, "Sin datos", JOptionPane.WARNING_MESSAGE);
            } else {
                tiempoPorJugador = 90;
                startGame();
            }
        });
    }

    // ─── MENU TORNEO PANEL ───────────────────────────────────────────────────

    private void initMenuTorneoListeners() {
        MenuTPanel menu = mainFrame.getMenuTPanel();

        menu.getBtnVolver().addActionListener(e ->
            mainFrame.showPanel(MainFrame.HOME_PANEL)
        );

        menu.getBtnRegistrarEquipo().addActionListener(e -> {
            mainFrame.getRegisterPanel().clear();
            mainFrame.showPanel(MainFrame.REGISTER_PANEL);
        });

        menu.getBtnCargarEquipo().addActionListener(e ->
            loadTeamFromFile()
        );

        menu.getBtnEliminarEquipo().addActionListener(e -> {
            int idx = menu.removeSelectedTeam();
            if (idx != -1) principalControl.removeTeam(idx);
        });

        menu.getBtnIniciarJuego().addActionListener(e -> {
            tiempoPorJugador = menu.getTiempoSegundos() / 3;
            principalControl.initSession();
            startGame();
        });
    }

    // ─── REGISTER PANEL ──────────────────────────────────────────────────────

    private void initRegisterListeners() {
        RegisterPanel register = mainFrame.getRegisterPanel();

        register.getBtnVolver().addActionListener(e ->
            mainFrame.showPanel(MainFrame.MENU_TORNEO_PANEL)
        );

        register.getBtnLimpiar().addActionListener(e ->
            register.clear()
        );

        register.getBtnGuardar().addActionListener(e ->
            saveNewTeam()
        );
    }

    // ─── GAME PANEL ──────────────────────────────────────────────────────────

    private void initGameListeners() {
    GamePanel game = mainFrame.getGamePanel();

    // Radio buttons — solo habilitan el botón intentar
    game.getEmbocadaButtons().forEach(btn ->
        btn.addActionListener(e ->
            game.setBtnIntentarEnabled(true)
        )
    );

    // Botón intentar — ejecuta el intento con delay
    game.getBtnIntentar().addActionListener(e -> {
        Embocada seleccionada = game.getSelectedEmbocada();
        if (seleccionada == null) return;
        game.setEmbocadasEnabled(false);
        game.setBtnIntentarEnabled(false);
        delayTimer = new Timer(1000, ev -> onIntentar(seleccionada));
        delayTimer.setRepeats(false);
        delayTimer.start();
        });

    game.getBtnIniciarRonda().addActionListener(e -> {
        game.setBtnIniciarRondaVisible(false);
        game.setEmbocadasEnabled(true);
        startTimer();
        });
    }

    private void startTimer() {
    GamePanel game = mainFrame.getGamePanel();
    int[] tiempo = {tiempoPorJugador};
    game.updateTimer(tiempo[0]);

    gameTimer = new Timer(1000, e -> {
        tiempo[0]--;
        game.updateTimer(tiempo[0]);

        if (tiempo[0] <= 0) {
                gameTimer.stop();
                game.setEmbocadasEnabled(false);
                game.setBtnIntentarEnabled(false);
                principalControl.nextTurn();
                startNextTurn();
            }
        });
        gameTimer.start();
    }

    // ─── RESULTS PANEL ───────────────────────────────────────────────────────

    private void initResultsListeners() {
        ResultsPanel results = mainFrame.getResultsPanel();

        results.getBtnNuevoTorneo().addActionListener(e -> {
            principalControl.reset();
            mainFrame.getMenuTPanel().loadTeams(new ArrayList<>());
            mainFrame.showPanel(MainFrame.HOME_PANEL);
        });

        results.getBtnSalir().addActionListener(e ->
            System.exit(0)
        );
    }

    // ─── FLUJO DE CARGA ──────────────────────────────────────────────────────

    private void loadTeamFromFile() {
        File carpeta = new File("Specs/data/teams");
        if (!carpeta.exists()) carpeta.mkdirs();
        JFileChooser fileChooser = new JFileChooser(carpeta.getAbsoluteFile());
        fileChooser.setDialogTitle("Seleccionar archivo de equipo");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos de propiedades (*.properties)", "properties"
        ));

        if (fileChooser.showOpenDialog(mainFrame) != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        Team team = principalControl.loadTeamFromFile(file);

        if (team == null) {
            JOptionPane.showMessageDialog(mainFrame,
                "Error al leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        showValidationDialog(team);
    }

    private void showValidationDialog(Team team) {
        TeamValidationDialog dialog = new TeamValidationDialog(mainFrame);

        dialog.getBtnCancelar().addActionListener(e ->
            dialog.dispose()
        );

        dialog.getBtnConfirmar().addActionListener(e -> {
            String error = principalControl.validateAndAddTeam(
                team,
                dialog.getPin(),
                dialog.getCodigo1(),
                dialog.getCodigo2(),
                dialog.getCodigo3()
            );

            if (error != null) {
                dialog.showError(error);
            } else {
                mainFrame.getMenuTPanel().addTeamToList(
                    principalControl.getLastAddedTeam()
                );
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void saveNewTeam() {
        RegisterPanel register = mainFrame.getRegisterPanel();

        String error = principalControl.createAndSaveTeam(
            register.getNombreEquipo(),
            register.getProyecto(),
            register.getPin(),
            register.getConfirmarPin(),
            register.getNombreJ1(), register.getCodigoJ1(),
            register.getNombreJ2(), register.getCodigoJ2(),
            register.getNombreJ3(), register.getCodigoJ3()
        );

        if (error != null) {
            register.showError(error);
        } else {
            mainFrame.getMenuTPanel().addTeamToList(
                principalControl.getLastAddedTeam()
            );
            register.clear();
            mainFrame.showPanel(MainFrame.MENU_TORNEO_PANEL);
        }
    }

    // ─── FLUJO DEL JUEGO ─────────────────────────────────────────────────────

    private void startGame() {
        GamePanel game = mainFrame.getGamePanel();
        game.init(principalControl.getTeams());
        mainFrame.showPanel(MainFrame.GAME_PANEL);
        startNextTurn();
    }

    private void startNextTurn() {
        if (principalControl.isGameFinished()) {
            finishGame();
            return;
        }

        GamePanel game = mainFrame.getGamePanel();

        game.updateTurn(
            principalControl.getCurrentTeam(),
            principalControl.getCurrentPlayerIndex()
        );
        game.updatePlayerInfo(
            principalControl.getCurrentTeamName(),
            principalControl.getCurrentPlayerName(),
            principalControl.getCurrentPlayerCode(),
            principalControl.getCurrentTeamPoints(),
            "-"
        );
        game.updateStandings(principalControl.getStandings());
        game.setEmbocadasEnabled(true);
        game.setBtnIntentarEnabled(false);
        game.setBtnIniciarRondaVisible(true);

    }

    private void onIntentar(Embocada embocada) {
        GamePanel game = mainFrame.getGamePanel();

        principalControl.executeAttemp(embocada);

        game.updatePlayerInfo(
            principalControl.getCurrentTeamName(),
            principalControl.getCurrentPlayerName(),
            principalControl.getCurrentPlayerCode(),
            principalControl.getCurrentTeamPoints(),
            principalControl.getLastResult()
        );
        game.updateStandings(principalControl.getStandings());
        game.setEmbocadasEnabled(true);
    }

    private void finishGame() {
        if (gameTimer != null) gameTimer.stop();

        int victorias = principalControl.finishGame();

        ResultsPanel results = mainFrame.getResultsPanel();
        results.showWinner(
            principalControl.getWinner(),
            principalControl.getWinnerStats(),
            victorias
        );
        results.showFinalTable(principalControl.getStandings());
        mainFrame.showPanel(MainFrame.RESULTS_PANEL);
    }
}
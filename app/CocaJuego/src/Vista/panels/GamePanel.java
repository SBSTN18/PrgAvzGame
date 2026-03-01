package Vista.panels;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import control.PrincipalControl;
import control.VistaControl;
import modelo.Embocada;
import modelo.Team;
import modelo.TeamStats;
import Vista.components.StandingsTable;
import Vista.components.TeamCard;

/**
 * Panel principal del juego.
 * Solo contiene componentes visuales, sin lógica ni listeners.
 */
public class GamePanel extends JPanel {

    // Izquierda — opciones de embocada
    private JPanel leftPanel;
    private ButtonGroup embocadaGroup;
    private List<JRadioButton> embocadaButtons;

    // Centro — grilla de equipos
    private JScrollPane centerScroll;
    private JPanel centerGrid;
    private List<TeamCard> teamCards;

    // Derecha — tabla + info jugador
    private JPanel rightPanel;
    private StandingsTable standingsTable;
    private JLabel lblEquipoActual;
    private JLabel lblJugadorActual;
    private JLabel lblCodigoActual;
    private JLabel lblPuntosActual;
    private JLabel lblTiempo;
    private JLabel lblUltimaEmbocada;

    public GamePanel() {
        this.teamCards = new ArrayList<>();
        this.embocadaButtons = new ArrayList<>();
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(20, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buildLeft();
        buildCenter();
        buildRight();
    }

    // ─── PANEL IZQUIERDO ────────────────────────────────────────────────────

    private void buildLeft() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(35, 35, 35));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        leftPanel.setPreferredSize(new Dimension(180, 0));

        JLabel lblTitulo = new JLabel("Tipo de Embocada");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(lblTitulo);
        leftPanel.add(Box.createVerticalStrut(15));

        embocadaGroup = new ButtonGroup();

        for (Embocada e : Embocada.values()) {
            JRadioButton btn = new JRadioButton(e.name() + " (" + e.getPoints() + "pts)");
            btn.setForeground(Color.LIGHT_GRAY);
            btn.setBackground(new Color(35, 35, 35));
            btn.setFont(new Font("Arial", Font.PLAIN, 12));
            btn.setActionCommand(e.name());
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            embocadaGroup.add(btn);
            embocadaButtons.add(btn);
            leftPanel.add(btn);
            leftPanel.add(Box.createVerticalStrut(8));
        }

        add(leftPanel, BorderLayout.WEST);
    }

    // ─── PANEL CENTRAL ──────────────────────────────────────────────────────

    private void buildCenter() {
        centerGrid = new JPanel();
        centerGrid.setBackground(new Color(20, 20, 20));

        centerScroll = new JScrollPane(centerGrid);
        centerScroll.setOpaque(false);
        centerScroll.getViewport().setOpaque(false);
        centerScroll.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        centerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(centerScroll, BorderLayout.CENTER);
    }

    // ─── PANEL DERECHO ──────────────────────────────────────────────────────

    private void buildRight() {
        rightPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        rightPanel.setBackground(new Color(20, 20, 20));
        rightPanel.setPreferredSize(new Dimension(250, 0));

        standingsTable = new StandingsTable();
        rightPanel.add(standingsTable);
        rightPanel.add(buildPlayerInfoPanel());

        add(rightPanel, BorderLayout.EAST);
    }

    private JPanel buildPlayerInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titulo = new JLabel("Jugador en Turno");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTiempo         = createInfoLabel("⏱ 00:00");
        lblTiempo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTiempo.setForeground(new Color(100, 200, 100));
        lblEquipoActual   = createInfoLabel("Equipo: -");
        lblJugadorActual  = createInfoLabel("Jugador: -");
        lblCodigoActual   = createInfoLabel("Código: -");
        lblPuntosActual   = createInfoLabel("Puntos: 0");
        lblUltimaEmbocada = createInfoLabel("Última: -");

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblTiempo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblEquipoActual);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblJugadorActual);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblCodigoActual);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblPuntosActual);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblUltimaEmbocada);

        return panel;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // ─── GETTERS para VistaControl ───────────────────────────────────────────

    /**
     * Retorna los radio buttons de embocada para que
     * VistaControl les agregue los listeners.
     */
    public List<JRadioButton> getEmbocadaButtons() {
        return embocadaButtons;
    }

    public ButtonGroup getEmbocadaGroup() {
        return embocadaGroup;
    }

    // ─── MÉTODOS DE ACTUALIZACIÓN (llamados por VistaControl) ───────────────

    /**
     * Inicializa la grilla con los equipos cargados.
     */
    public void init(List<Team> teams) {
        buildTeamGrid(teams);
        standingsTable.update(Map.of());
        setEmbocadasEnabled(false);
    }

    private void buildTeamGrid(List<Team> teams) {
        teamCards.clear();
        centerGrid.removeAll();

        int cols = Math.min(teams.size(), 3);
        int rows = (int) Math.ceil(teams.size() / (double) cols);
        centerGrid.setLayout(new GridLayout(rows, cols, 15, 15));

        for (Team team : teams) {
            TeamCard card = new TeamCard(team);
            teamCards.add(card);
            centerGrid.add(card);
        }

        centerGrid.revalidate();
        centerGrid.repaint();
    }

    public void updateTurn(Team activeTeam, int playerIndex) {
        for (TeamCard card : teamCards) {
            if (card.getTeam().equals(activeTeam)) {
                card.setNormal();
                card.setActivePlayer(playerIndex);
                standingsTable.highlightTeam(activeTeam.getName());
            } else {
                card.setWatermark();
            }
        }
    }

    public void updateTimer(int segundos) {
        int min = segundos / 60;
        int sec = segundos % 60;
        lblTiempo.setText(String.format("⏱ %02d:%02d", min, sec));
        lblTiempo.setForeground(segundos <= 10 ? Color.RED : new Color(100, 200, 100));
    }

    public void updatePlayerInfo(String equipo, String jugador, int codigo, int puntos, String ultimaEmbocada) {
        lblEquipoActual.setText("Equipo: " + equipo);
        lblJugadorActual.setText("Jugador: " + jugador);
        lblCodigoActual.setText("Código: " + codigo);
        lblPuntosActual.setText("Puntos: " + puntos);
        lblUltimaEmbocada.setText("Última: " + ultimaEmbocada);
    }

    public void updateStandings(Map<Team, TeamStats> stats) {
        standingsTable.update(stats);
    }

    public void setEmbocadasEnabled(boolean enabled) {
        embocadaButtons.forEach(btn -> btn.setEnabled(enabled));
        if (enabled) embocadaGroup.clearSelection();
    }
}
package Vista.panels;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import modelo.Team;
import modelo.TeamStats;

/**
 * Panel de resultados finales del torneo.
 * Muestra el equipo ganador, sus jugadores y la tabla final de posiciones.
 * Sin lógica ni listeners — solo componentes visuales.
 */
public class ResultsPanel extends JPanel {

    private JLabel lblGanador;
    private JLabel lblProyecto;
    private JLabel lblPuntaje;
    private JLabel lblJugador1;
    private JLabel lblJugador2;
    private JLabel lblJugador3;
    private JLabel lblHistorial;
    private JButton btnNuevoTorneo;
    private JButton btnSalir;

    // Tabla final
    private javax.swing.table.DefaultTableModel tableModel;
    private JTable tabla;

    private static final String[] COLUMNS = {"Pos", "Equipo", "Proyecto", "Puntos", "Intentos", "Embocadas"};

    public ResultsPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(20, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buildTop();
        buildCenter();
        buildBottom();
    }

    // ─── PARTE SUPERIOR — título ──────────────────────────────────────────────

    private void buildTop() {
        JLabel titulo = new JLabel("Resultados del Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        titulo.setForeground(new Color(210, 160, 60));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);
    }

    // ─── PARTE CENTRAL — ganador + tabla ─────────────────────────────────────

    private void buildCenter() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(new Color(20, 20, 20));

        centerPanel.add(buildWinnerPanel());
        centerPanel.add(buildFinalTablePanel());

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel buildWinnerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(createTitledBorder("Equipo Ganador"));

        // Trofeo
        JLabel lblTrofeo = new JLabel("🏆", SwingConstants.CENTER);
        lblTrofeo.setFont(new Font("Arial", Font.PLAIN, 60));
        lblTrofeo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblGanador = createResultLabel("-", new Font("Serif", Font.BOLD, 26), new Color(210, 160, 60));
        lblProyecto = createResultLabel("-", new Font("Arial", Font.ITALIC, 14), Color.LIGHT_GRAY);
        lblPuntaje = createResultLabel("Puntos: 0", new Font("Arial", Font.BOLD, 18), new Color(100, 200, 100));

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(Color.DARK_GRAY);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Jugadores ganadores
        JLabel lblTituloJugadores = createResultLabel("Jugadores", new Font("Arial", Font.BOLD, 13), Color.WHITE);
        lblJugador1 = createResultLabel("-", new Font("Arial", Font.PLAIN, 13), Color.LIGHT_GRAY);
        lblJugador2 = createResultLabel("-", new Font("Arial", Font.PLAIN, 13), Color.LIGHT_GRAY);
        lblJugador3 = createResultLabel("-", new Font("Arial", Font.PLAIN, 13), Color.LIGHT_GRAY);

        // Separador
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(Color.DARK_GRAY);
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Historial de victorias
        lblHistorial = createResultLabel(" ", new Font("Arial", Font.ITALIC, 12), new Color(150, 150, 255));

        panel.add(Box.createVerticalStrut(15));
        panel.add(lblTrofeo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblGanador);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblProyecto);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblPuntaje);
        panel.add(Box.createVerticalStrut(15));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblTituloJugadores);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblJugador1);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblJugador2);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblJugador3);
        panel.add(Box.createVerticalStrut(15));
        panel.add(sep2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblHistorial);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel buildFinalTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(createTitledBorder("Tabla Final de Posiciones"));

        tableModel = new javax.swing.table.DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(tableModel);
        tabla.setBackground(new Color(30, 30, 30));
        tabla.setForeground(Color.WHITE);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setRowHeight(32);
        tabla.getTableHeader().setBackground(new Color(60, 60, 60));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.setSelectionBackground(new Color(70, 130, 180));

        // Centrar columnas numéricas
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < COLUMNS.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ancho columna posición
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ─── PARTE INFERIOR — botones ─────────────────────────────────────────────

    private void buildBottom() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(20, 20, 20));

        btnNuevoTorneo = createButton("Nuevo Torneo", new Color(70, 130, 180));
        btnSalir       = createButton("Salir", new Color(150, 50, 50));

        bottomPanel.add(btnNuevoTorneo);
        bottomPanel.add(btnSalir);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private JLabel createResultLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private TitledBorder createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            Color.LIGHT_GRAY
        );
    }

    // ─── MÉTODOS DE ACTUALIZACIÓN (llamados por VistaControl) ────────────────

    /**
     * Muestra el equipo ganador y sus datos.
     *
     * @param team equipo ganador
     * @param stats estadísticas del ganador
     * @param victorias cantidad de veces que ha ganado en el historial
     */
    public void showWinner(Team team, TeamStats stats, int victorias) {
        lblGanador.setText(team.getName());
        lblProyecto.setText(team.getProyect());
        lblPuntaje.setText("Puntos: " + stats.getPoints());

        java.util.List<modelo.Player> players = team.getPlayers();
        lblJugador1.setText(players.size() > 0 ? "👤 " + players.get(0).getName() : "-");
        lblJugador2.setText(players.size() > 1 ? "👤 " + players.get(1).getName() : "-");
        lblJugador3.setText(players.size() > 2 ? "👤 " + players.get(2).getName() : "-");

        if (victorias > 1) {
            lblHistorial.setText("¡Este equipo ha ganado " + victorias + " veces!");
        } else {
            lblHistorial.setText("¡Primera victoria de este equipo!");
        }
    }

    /**
     * Carga la tabla final de posiciones ordenada.
     *
     * @param stats mapa de equipos con sus estadísticas
     */
    public void showFinalTable(Map<Team, TeamStats> stats) {
        tableModel.setRowCount(0);

        stats.entrySet().stream()
            .sorted((a, b) -> {
                int cmp = Integer.compare(b.getValue().getPoints(), a.getValue().getPoints());
                if (cmp != 0) return cmp;
                return Integer.compare(b.getValue().getEmbocadas(), a.getValue().getEmbocadas());
            })
            .forEach(new java.util.function.Consumer<Map.Entry<Team, TeamStats>>() {
                int pos = 1;
                @Override
                public void accept(Map.Entry<Team, TeamStats> entry) {
                    TeamStats s = entry.getValue();
                    tableModel.addRow(new Object[]{
                        pos++,
                        entry.getKey().getName(),
                        entry.getKey().getProyect(),
                        s.getPoints(),
                        s.getAttempts(),
                        s.getEmbocadas()
                    });
                }
            });

        // Resaltar primera fila (ganador)
        if (tableModel.getRowCount() > 0) {
            tabla.setRowSelectionInterval(0, 0);
        }
    }

    // ─── GETTERS para VistaControl ────────────────────────────────────────────

    public JButton getBtnNuevoTorneo() { return btnNuevoTorneo; }
    public JButton getBtnSalir()       { return btnSalir;       }
}
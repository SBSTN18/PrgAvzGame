package Vista.panels;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import modelo.Team;

/**
 * Panel del menú del torneo.
 * Permite registrar equipos nuevos, cargar equipos desde archivo
 * y configurar el tiempo antes de iniciar el juego.
 * Sin lógica ni listeners — solo componentes visuales.
 */
public class MenuTPanel extends JPanel {

    private JButton btnRegistrarEquipo;
    private JButton btnCargarEquipo;
    private JButton btnIniciarJuego;
    private JButton btnEliminarEquipo;
    private JButton btnVolver;
    private JSpinner spinnerTiempo;
    private JList<String> listaEquipos;
    private DefaultListModel<String> listModel;

    public MenuTPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(20, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buildTop();
        buildCenter();
        buildBottom();
    }

    // ─── PARTE SUPERIOR — título + volver ────────────────────────────────────

    private void buildTop() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(20, 20, 20));

        btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 12));
        btnVolver.setForeground(Color.LIGHT_GRAY);
        btnVolver.setBackground(new Color(40, 40, 40));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel titulo = new JLabel("Nuevo Torneo", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(new Color(210, 160, 60));

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
    }

    // ─── PARTE CENTRAL — opciones + lista ────────────────────────────────────

    private void buildCenter() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(new Color(20, 20, 20));

        centerPanel.add(buildOptionsPanel());
        centerPanel.add(buildTeamListPanel());

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel buildOptionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(createTitledBorder("Agregar Equipos"));

        // Registrar equipo nuevo
        JLabel lblRegistrar = new JLabel("Crear equipo nuevo");
        lblRegistrar.setForeground(Color.LIGHT_GRAY);
        lblRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        lblRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnRegistrarEquipo = createButton("Registrar Equipo", new Color(70, 130, 180));

        JLabel lblDescRegistrar = new JLabel("<html><center>Completa el formulario para<br>crear un equipo nuevo</center></html>");
        lblDescRegistrar.setForeground(Color.GRAY);
        lblDescRegistrar.setFont(new Font("Arial", Font.ITALIC, 11));
        lblDescRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(Color.DARK_GRAY);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Cargar equipo desde archivo
        JLabel lblCargar = new JLabel("Cargar desde archivo");
        lblCargar.setForeground(Color.LIGHT_GRAY);
        lblCargar.setFont(new Font("Arial", Font.BOLD, 13));
        lblCargar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCargarEquipo = createButton("Seleccionar .properties", new Color(80, 80, 80));

        JLabel lblDescCargar = new JLabel("<html><center>Selecciona un archivo .properties<br>y valida el equipo con PIN y códigos</center></html>");
        lblDescCargar.setForeground(Color.GRAY);
        lblDescCargar.setFont(new Font("Arial", Font.ITALIC, 11));
        lblDescCargar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(lblRegistrar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnRegistrarEquipo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblDescRegistrar);
        panel.add(Box.createVerticalStrut(20));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblCargar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCargarEquipo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblDescCargar);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel buildTeamListPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(createTitledBorder("Equipos del Torneo"));

        // Lista de equipos
        listModel = new DefaultListModel<>();
        listaEquipos = new JList<>(listModel);
        listaEquipos.setBackground(new Color(30, 30, 30));
        listaEquipos.setForeground(Color.WHITE);
        listaEquipos.setFont(new Font("Arial", Font.PLAIN, 13));
        listaEquipos.setSelectionBackground(new Color(70, 130, 180));
        listaEquipos.setFixedCellHeight(35);

        JScrollPane scroll = new JScrollPane(listaEquipos);
        scroll.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        scroll.setOpaque(false);

        btnEliminarEquipo = createButton("Eliminar Seleccionado", new Color(150, 50, 50));
        btnEliminarEquipo.setEnabled(false);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnEliminarEquipo, BorderLayout.SOUTH);

        return panel;
    }

    // ─── PARTE INFERIOR — tiempo + iniciar ───────────────────────────────────

    private void buildBottom() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        bottomPanel.setBackground(new Color(20, 20, 20));

        JLabel lblTiempo = new JLabel("Tiempo por equipo (segundos):");
        lblTiempo.setForeground(Color.LIGHT_GRAY);
        lblTiempo.setFont(new Font("Arial", Font.PLAIN, 13));

        spinnerTiempo = new JSpinner(new SpinnerNumberModel(90, 30, 300, 10));
        spinnerTiempo.setPreferredSize(new Dimension(80, 35));
        spinnerTiempo.setFont(new Font("Arial", Font.PLAIN, 13));

        btnIniciarJuego = createButton("Iniciar Juego", new Color(50, 150, 80));
        btnIniciarJuego.setEnabled(false);
        btnIniciarJuego.setPreferredSize(new Dimension(200, 45));

        bottomPanel.add(lblTiempo);
        bottomPanel.add(spinnerTiempo);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(btnIniciarJuego);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(280, 45));
        btn.setPreferredSize(new Dimension(280, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
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
     * Agrega un equipo a la lista visual.
     *
     * @param team equipo a mostrar
     */
    public void addTeamToList(Team team) {
        listModel.addElement("⚑ " + team.getName() + " — " + team.getProyect());
        btnIniciarJuego.setEnabled(listModel.size() >= 2);
        btnEliminarEquipo.setEnabled(true);
    }

    /**
     * Carga múltiples equipos a la vez.
     *
     * @param teams lista de equipos
     */
    public void loadTeams(List<Team> teams) {
        listModel.clear();
        teams.forEach(this::addTeamToList);
    }

    /**
     * Elimina el equipo seleccionado de la lista visual.
     *
     * @return índice eliminado, -1 si no había selección
     */
    public int removeSelectedTeam() {
        int idx = listaEquipos.getSelectedIndex();
        if (idx != -1) {
            listModel.remove(idx);
            btnIniciarJuego.setEnabled(listModel.size() >= 2);
            btnEliminarEquipo.setEnabled(listModel.size() > 0);
        }
        return idx;
    }

    /**
     * Retorna el tiempo configurado en segundos.
     */
    public int getTiempoSegundos() {
        return (int) spinnerTiempo.getValue();
    }

    // ─── GETTERS para VistaControl ────────────────────────────────────────────

    public JButton getBtnRegistrarEquipo() { return btnRegistrarEquipo; }
    public JButton getBtnCargarEquipo()    { return btnCargarEquipo;    }
    public JButton getBtnIniciarJuego()    { return btnIniciarJuego;    }
    public JButton getBtnEliminarEquipo()  { return btnEliminarEquipo;  }
    public JButton getBtnVolver()          { return btnVolver;          }
    public JList<String> getListaEquipos() { return listaEquipos;       }
}
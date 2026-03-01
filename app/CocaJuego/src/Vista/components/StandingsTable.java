package Vista.components;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Team;
import modelo.TeamStats;

/**
 * Componente visual que muestra la tabla de posiciones en tiempo real.
 * Se actualiza automáticamente conforme los equipos van jugando.
 */
public class StandingsTable extends JPanel {

    private static final String[] COLUMNS = {"Equipo", "Proyecto", "Puntos", "Intentos", "Embocadas"};

    private DefaultTableModel tableModel;
    private JTable table;

    public StandingsTable() {
        setLayout(new BorderLayout());
        setOpaque(false);
        build();
    }

    private void build() {
        // Título
        JLabel title = new JLabel("Tabla de Posiciones", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Modelo de tabla no editable
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setOpaque(false);
        table.setBackground(new Color(30, 30, 30, 180));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.getTableHeader().setBackground(new Color(60, 60, 60));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setSelectionBackground(new Color(100, 100, 200, 150));

        // Centrar contenido de todas las columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < COLUMNS.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        add(title, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Actualiza la tabla de posiciones con los datos actuales de la sesión.
     * Ordena los equipos de mayor a menor puntaje.
     *
     * @param stats mapa de equipos con sus estadísticas
     */
    public void update(Map<Team, TeamStats> stats) {
        tableModel.setRowCount(0); // limpiar filas

        // Ordenar por puntos descendente, desempate por embocadas
        stats.entrySet().stream()
            .sorted((a, b) -> {
                int cmp = Integer.compare(b.getValue().getPoints(), a.getValue().getPoints());
                if (cmp != 0) return cmp;
                return Integer.compare(b.getValue().getEmbocadas(), a.getValue().getEmbocadas());
            })
            .forEach(entry -> {
                Team team = entry.getKey();
                TeamStats s = entry.getValue();
                tableModel.addRow(new Object[]{
                    team.getName(),
                    team.getProyect(),
                    s.getPoints(),
                    s.getAttempts(),
                    s.getEmbocadas()
                });
            });
    }

    /**
     * Resalta la fila del equipo que está jugando actualmente.
     *
     * @param teamName nombre del equipo activo
     */
    public void highlightTeam(String teamName) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(teamName)) {
                table.setRowSelectionInterval(i, i);
                return;
            }
        }
    }
}
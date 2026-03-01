package Vista.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Panel de registro de nuevo equipo.
 * Formulario para ingresar datos del equipo y sus jugadores.
 * Sin lógica ni listeners — solo componentes visuales.
 */
public class RegisterPanel extends JPanel {

    // Equipo
    private JTextField fieldNombreEquipo;
    private JTextField fieldProyecto;
    private JPasswordField fieldPin;
    private JPasswordField fieldConfirmarPin;

    // Jugadores
    private JTextField fieldNombreJ1;
    private JTextField fieldCodigoJ1;
    private JTextField fieldNombreJ2;
    private JTextField fieldCodigoJ2;
    private JTextField fieldNombreJ3;
    private JTextField fieldCodigoJ3;

    // Botones
    private JButton btnGuardar;
    private JButton btnVolver;
    private JButton btnLimpiar;

    // Error
    private JLabel lblError;

    public RegisterPanel() {
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

        JLabel titulo = new JLabel("Registrar Equipo", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(new Color(210, 160, 60));

        topPanel.add(btnVolver, BorderLayout.WEST);
        topPanel.add(titulo, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
    }

    // ─── PARTE CENTRAL — formulario ──────────────────────────────────────────

    private void buildCenter() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(new Color(20, 20, 20));

        centerPanel.add(buildTeamDataPanel());
        centerPanel.add(buildPlayersPanel());

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel buildTeamDataPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(createTitledBorder("Datos del Equipo"));

        fieldNombreEquipo = createTextField();
        fieldProyecto     = createTextField();
        fieldPin          = new JPasswordField();
        fieldConfirmarPin = new JPasswordField();

        stylePasswordField(fieldPin);
        stylePasswordField(fieldConfirmarPin);

        panel.add(Box.createVerticalStrut(15));
        panel.add(createFieldRow("Nombre del equipo:", fieldNombreEquipo));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createFieldRow("Proyecto curricular:", fieldProyecto));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createFieldRow("PIN del equipo:", fieldPin));
        panel.add(Box.createVerticalStrut(12));
        panel.add(createFieldRow("Confirmar PIN:", fieldConfirmarPin));
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel buildPlayersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(createTitledBorder("Jugadores"));

        fieldNombreJ1 = createTextField();
        fieldCodigoJ1 = createTextField();
        fieldNombreJ2 = createTextField();
        fieldCodigoJ2 = createTextField();
        fieldNombreJ3 = createTextField();
        fieldCodigoJ3 = createTextField();

        panel.add(Box.createVerticalStrut(10));
        panel.add(createPlayerSection("Jugador 1", fieldNombreJ1, fieldCodigoJ1));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createSeparator());
        panel.add(Box.createVerticalStrut(10));
        panel.add(createPlayerSection("Jugador 2", fieldNombreJ2, fieldCodigoJ2));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createSeparator());
        panel.add(Box.createVerticalStrut(10));
        panel.add(createPlayerSection("Jugador 3", fieldNombreJ3, fieldCodigoJ3));
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createPlayerSection(String titulo, JTextField fieldNombre, JTextField fieldCodigo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(new Color(210, 160, 60));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(6));
        panel.add(createFieldRow("Nombre:", fieldNombre));
        panel.add(Box.createVerticalStrut(6));
        panel.add(createFieldRow("Código:", fieldCodigo));

        return panel;
    }

    // ─── PARTE INFERIOR — error + botones ────────────────────────────────────

    private void buildBottom() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(20, 20, 20));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        lblError = new JLabel(" ");
        lblError.setForeground(new Color(220, 80, 80));
        lblError.setFont(new Font("Arial", Font.ITALIC, 12));
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        btnPanel.setBackground(new Color(20, 20, 20));

        btnLimpiar = createButton("Limpiar", new Color(80, 80, 80));
        btnGuardar = createButton("Guardar Equipo", new Color(50, 150, 80));

        btnPanel.add(btnLimpiar);
        btnPanel.add(btnGuardar);

        bottomPanel.add(lblError);
        bottomPanel.add(btnPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        return field;
    }

    private void stylePasswordField(JPasswordField field) {
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
    }

    private JPanel createFieldRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(new Color(35, 35, 35));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setPreferredSize(new Dimension(140, 32));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(Color.DARK_GRAY);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(180, 42));
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
     * Limpia todos los campos del formulario.
     */
    public void clear() {
        fieldNombreEquipo.setText("");
        fieldProyecto.setText("");
        fieldPin.setText("");
        fieldConfirmarPin.setText("");
        fieldNombreJ1.setText(""); fieldCodigoJ1.setText("");
        fieldNombreJ2.setText(""); fieldCodigoJ2.setText("");
        fieldNombreJ3.setText(""); fieldCodigoJ3.setText("");
        lblError.setText(" ");
    }

    /**
     * Muestra un mensaje de error en el formulario.
     *
     * @param mensaje mensaje a mostrar
     */
    public void showError(String mensaje) {
        lblError.setText(mensaje);
    }

    // ─── GETTERS para VistaControl ────────────────────────────────────────────

    public String getNombreEquipo()   { return fieldNombreEquipo.getText().trim();   }
    public String getProyecto()       { return fieldProyecto.getText().trim();       }
    public String getPin()            { return new String(fieldPin.getPassword());   }
    public String getConfirmarPin()   { return new String(fieldConfirmarPin.getPassword()); }

    public String getNombreJ1()       { return fieldNombreJ1.getText().trim(); }
    public String getCodigoJ1()       { return fieldCodigoJ1.getText().trim(); }
    public String getNombreJ2()       { return fieldNombreJ2.getText().trim(); }
    public String getCodigoJ2()       { return fieldCodigoJ2.getText().trim(); }
    public String getNombreJ3()       { return fieldNombreJ3.getText().trim(); }
    public String getCodigoJ3()       { return fieldCodigoJ3.getText().trim(); }

    public JButton getBtnGuardar()    { return btnGuardar; }
    public JButton getBtnVolver()     { return btnVolver;  }
    public JButton getBtnLimpiar()    { return btnLimpiar; }
}
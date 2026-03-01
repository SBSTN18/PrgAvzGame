package Vista.dialogs;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * Diálogo de validación de equipo.
 * Solicita el PIN del equipo y los códigos de los jugadores presentes.
 * Sin lógica ni listeners — solo componentes visuales.
 */
public class TeamValidationDialog extends JDialog {

    private JPasswordField fieldPin;
    private JTextField fieldCodigo1;
    private JTextField fieldCodigo2;
    private JTextField fieldCodigo3;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblError;

    public TeamValidationDialog(JFrame parent) {
        super(parent, "Validar Equipo", true);
        setSize(350, 380);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(35, 35, 35));
        setLayout(new BorderLayout(10, 10));
        buildContent();
        buildButtons();
    }

    private void buildContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        // Título
        JLabel titulo = new JLabel("Validación de Equipo");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(210, 160, 60));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Descripción
        JLabel desc = new JLabel("<html><center>Ingresa el PIN del equipo y los códigos<br>de los jugadores presentes</center></html>");
        desc.setFont(new Font("Arial", Font.ITALIC, 11));
        desc.setForeground(Color.GRAY);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(Color.DARK_GRAY);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // PIN
        fieldPin = new JPasswordField();
        fieldPin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // Códigos
        fieldCodigo1 = createTextField("Código jugador 1");
        fieldCodigo2 = createTextField("Código jugador 2");
        fieldCodigo3 = createTextField("Código jugador 3 (opcional)");

        // Label error
        lblError = new JLabel(" ");
        lblError.setForeground(new Color(220, 80, 80));
        lblError.setFont(new Font("Arial", Font.ITALIC, 11));
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(desc);
        panel.add(Box.createVerticalStrut(15));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(15));
        panel.add(createFieldRow("PIN del equipo:", fieldPin));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFieldRow("Código jugador 1:", fieldCodigo1));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createFieldRow("Código jugador 2:", fieldCodigo2));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createFieldRow("Código jugador 3:", fieldCodigo3));
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblError);

        add(panel, BorderLayout.CENTER);
    }

    private void buildButtons() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(new Color(35, 35, 35));

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(new Color(70, 130, 180));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 13));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setPreferredSize(new Dimension(120, 38));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(80, 80, 80));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setPreferredSize(new Dimension(120, 38));

        btnPanel.add(btnConfirmar);
        btnPanel.add(btnCancelar);

        add(btnPanel, BorderLayout.SOUTH);
    }

    private JTextField createTextField(String tooltip) {
        JTextField field = new JTextField();
        field.setToolTipText(tooltip);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }

    private JPanel createFieldRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(new Color(35, 35, 35));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setPreferredSize(new Dimension(130, 35));

        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        ((JTextComponent) field).setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    // ─── MÉTODOS DE ACTUALIZACIÓN (llamados por VistaControl) ────────────────

    /**
     * Muestra un mensaje de error en el diálogo.
     *
     * @param mensaje mensaje a mostrar
     */
    public void showError(String mensaje) {
        lblError.setText(mensaje);
    }

    /**
     * Limpia todos los campos del diálogo.
     */
    public void clear() {
        fieldPin.setText("");
        fieldCodigo1.setText("");
        fieldCodigo2.setText("");
        fieldCodigo3.setText("");
        lblError.setText(" ");
    }

    // ─── GETTERS para VistaControl ────────────────────────────────────────────

    public String getPin() {
        return new String(fieldPin.getPassword());
    }

    public String getCodigo1() { return fieldCodigo1.getText().trim(); }
    public String getCodigo2() { return fieldCodigo2.getText().trim(); }
    public String getCodigo3() { return fieldCodigo3.getText().trim(); }

    public JButton getBtnConfirmar() { return btnConfirmar; }
    public JButton getBtnCancelar()  { return btnCancelar;  }
}
package Vista.panels;

import java.awt.*;
import javax.swing.*;

/**
 * Panel de inicio de la aplicación.
 * Muestra el título del juego y las opciones de nuevo juego o cargar partida.
 * Sin lógica ni listeners — solo componentes visuales.
 */
public class HomePanel extends JPanel {

    private JButton btnNuevoJuego;
    private JButton btnCargar;

    public HomePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20));
        buildTitle();
        buildButtons();
    }

    private void buildTitle() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(20, 20, 20));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        // Título principal
        JLabel lblTitulo = new JLabel("El Juego del Balero");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 58));
        lblTitulo.setForeground(new Color(210, 160, 60));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel lblSub = new JLabel("Juegos Tradicionales Boyacenses");
        lblSub.setFont(new Font("Serif", Font.ITALIC, 22));
        lblSub.setForeground(new Color(160, 120, 40));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Decoración
        JLabel lblDeco = new JLabel("~ ✦ ~");
        lblDeco.setFont(new Font("Serif", Font.PLAIN, 28));
        lblDeco.setForeground(new Color(210, 160, 60));
        lblDeco.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(lblTitulo);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(lblSub);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(lblDeco);

        add(titlePanel, BorderLayout.CENTER);
    }

    private void buildButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(20, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));

        btnNuevoJuego = createButton("Nuevo Juego", new Color(70, 130, 180));
        btnCargar     = createButton("Cargar Partida", new Color(60, 60, 60));

        buttonPanel.add(btnNuevoJuego);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(btnCargar);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(280, 50));
        btn.setPreferredSize(new Dimension(280, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ─── GETTERS para VistaControl ───────────────────────────────────────────

    /**
     * Retorna el botón de nuevo juego para que VistaControl le agregue el listener.
     */
    public JButton getBtnNuevoTorneo() {
        return btnNuevoJuego;
    }

    /**
     * Retorna el botón de cargar partida para que VistaControl le agregue el listener.
     */
    public JButton getBtnCargarUltimo() {
        return btnCargar;
    }
}
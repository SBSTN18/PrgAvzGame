package Vista.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import modelo.Player;

/**
 * Componente visual que representa a un jugador.
 * Muestra su imagen genérica y su nombre.
 * Soporta tres estados: normal, oscuro (turno activo) y traslúcido (marca de agua).
 */
public class PlayerCard extends JPanel {

    private static final float ALPHA_NORMAL = 1.0f;
    private static final float ALPHA_DARK = 0.5f;
    private static final float ALPHA_WATERMARK = 0.2f;

    private static final ImageIcon PLAYER_IMAGE = loadImage();

    private Player player;
    private float alpha;

    public PlayerCard(Player player) {
        this.player = player;
        this.alpha = ALPHA_NORMAL;
        setLayout(new BorderLayout(5, 5));
        setOpaque(false);
        build();
    }

    private void build() {
        // Imagen del jugador
        JLabel imgLabel = new JLabel(PLAYER_IMAGE, SwingConstants.CENTER);

        // Nombre del jugador
        JLabel nameLabel = new JLabel(player.getName(), SwingConstants.CENTER); //errpr graso
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setForeground(Color.WHITE);

        add(imgLabel, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.SOUTH);
    }

    /**
     * Carga la imagen genérica del jugador.
     * Reemplaza "player.png" por el nombre de tu imagen.
     */
    private static ImageIcon loadImage() {
    try {
        File imgFile = new File("Specs/resources/player.png").getAbsoluteFile();
        System.out.println("Buscando imagen en: " + imgFile.getAbsolutePath());
        System.out.println("Existe: " + imgFile.exists());
        if (!imgFile.exists()) {
            return createPlaceholder();
        }
        ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
        Image scaled = icon.getImage().getScaledInstance(110, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    } catch (Exception e) {
        return createPlaceholder();
    }
    }

    private static ImageIcon createPlaceholder() {
        BufferedImage placeholder = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = placeholder.createGraphics();
        g.setColor(Color.GRAY);
        g.fillOval(0, 0, 60, 60);
        g.dispose();
        return new ImageIcon(placeholder);
    }

    /**
     * Pone el jugador en estado normal.
     */
    public void setNormal() {
        this.alpha = ALPHA_NORMAL;
        repaint();
    }

    /**
     * Oscurece el jugador para indicar que es su turno.
     */
    public void setActive() {
        this.alpha = ALPHA_DARK;
        repaint();
    }

    /**
     * Pone el jugador en marca de agua (equipo no activo).
     */
    public void setWatermark() {
        this.alpha = ALPHA_WATERMARK;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paint(g2d);
        g2d.dispose();
    }
}
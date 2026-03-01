package control;

import javax.swing.JFrame;

import Vista.panels.GamePanel;

public class VistaControl {

    private PrincipalControl principalControl;
    private GamePanel gamePanel;
     private JFrame frame;

    public VistaControl(PrincipalControl principalControl) {
        this.principalControl = principalControl;
        this.gamePanel = new GamePanel();
        this.frame = new JFrame("Mi Juego");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}

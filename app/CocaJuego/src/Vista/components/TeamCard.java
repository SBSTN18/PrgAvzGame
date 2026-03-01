package Vista.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import modelo.Player;
import modelo.Team;

import java.util.List;

/**
 * Componente visual que representa a un equipo.
 * Muestra el nombre del equipo y las tarjetas de sus jugadores.
 * Soporta efectos de marca de agua cuando otro equipo está jugando.
 */
public class TeamCard extends JPanel {

    private Team team;
    private List<PlayerCard> playerCards;

    public TeamCard(Team team) {
        this.team = team;
        setLayout(new BorderLayout(5, 5));
        setOpaque(false);
        build();
    }

    private void build() {
        // Título con nombre del equipo
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            team.getName(),
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13),
            Color.WHITE
        );
        setBorder(border);

        // Nombre del proyecto curricular
        JLabel proyectLabel = new JLabel(team.getProyect(), SwingConstants.CENTER);
        proyectLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        proyectLabel.setForeground(Color.LIGHT_GRAY);

        // Panel de jugadores
        JPanel playersPanel = new JPanel(new GridLayout(1, team.getCantPlayers(), 10, 0));
        playersPanel.setOpaque(false);

        playerCards = new java.util.ArrayList<>();
        for (Player player : team.getPlayers()) {
            PlayerCard card = new PlayerCard(player);
            playerCards.add(card);
            playersPanel.add(card);
        }

        add(proyectLabel, BorderLayout.NORTH);
        add(playersPanel, BorderLayout.CENTER);
    }

    /**
     * Pone todo el equipo en marca de agua.
     * Se usa cuando otro equipo está jugando.
     */
    public void setWatermark() {
        for (PlayerCard card : playerCards) {
            card.setWatermark();
        }
    }

    /**
     * Restaura el equipo a estado normal.
     */
    public void setNormal() {
        for (PlayerCard card : playerCards) {
            card.setNormal();
        }
    }

    /**
     * Activa visualmente al jugador en turno.
     * Los demás jugadores del equipo quedan en estado normal.
     *
     * @param playerIndex índice del jugador activo
     */
    public void setActivePlayer(int playerIndex) {
        for (int i = 0; i < playerCards.size(); i++) {
            if (i == playerIndex) {
                playerCards.get(i).setActive();
            } else {
                playerCards.get(i).setNormal();
            }
        }
    }

    /**
     * Retorna el equipo asociado a esta tarjeta.
     *
     * @return equipo
     */
    public Team getTeam() {
        return team;
    }
}
package Graphical_Interfaces;

import Domain.Player;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import utils.Colors;

public class GameInfoBar extends JPanel {

    private static final int BAR_HEIGHT = 60;
    private static final Color BAR_BACKGROUND = new Color(0, 0, 0, 180);
    private static final Font INFO_FONT = new Font("Serif", Font.BOLD, 16);
    private static final Color TEXT_COLOR = Color.WHITE;

    public GameInfoBar(Player player, Player enemy, ActionListener onPauseAction) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setBackground(BAR_BACKGROUND);
        setPreferredSize(new Dimension(800, BAR_HEIGHT)); 

        // --- Botão de Pause ---
        RoundedButton pauseButton = new RoundedButton("Pause", 15);
        pauseButton.setBackground(Colors.OPTION_BUTTON);
        pauseButton.setForeground(Colors.TEXT_OPTION_BUTTON);
        pauseButton.setPreferredSize(new Dimension(80, 30));
        pauseButton.addActionListener(onPauseAction);
        add(pauseButton);

        add(createSeparator());

        // --- Status do Jogo ---
        add(createLabel("Turno: 1"));
        add(createLabel("Tempo: 00:00"));

        add(createSeparator());

        // --- Status do Inimigo ---
        add(createLabel(enemy.getName() + ": " + enemy.getHealth() + " HP"));

        add(createSeparator());

        // --- Status do Jogador ---
        add(createLabel(player.getName() + ": " + player.getHealth() + " HP"));
        
        // Exibe os recursos do jogador
        JLabel moneyLabel = createLabel("$ " + player.getMoney());
        moneyLabel.setForeground(new Color(255, 215, 0)); // Dourado
        add(moneyLabel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(INFO_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JComponent createSeparator() {
        JLabel sep = new JLabel("|");
        sep.setFont(INFO_FONT);
        sep.setForeground(Color.GRAY);
        return sep;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
    }
}
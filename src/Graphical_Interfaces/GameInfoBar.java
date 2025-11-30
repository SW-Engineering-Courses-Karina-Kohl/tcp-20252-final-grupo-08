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

    // Labels que precisam ser atualizados transformados em campos da classe
    private JLabel turnLabel;
    private JLabel timeLabel;
    private JLabel enemyStatusLabel;
    private JLabel playerStatusLabel;
    private JLabel moneyLabel;

    private final Player player;
    private final Player enemy;

    public GameInfoBar(Player player, Player enemy, ActionListener onPauseAction) {
        this.player = player;
        this.enemy = enemy;

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
        turnLabel = createLabel("Turno: 1");
        add(turnLabel);
        
        timeLabel = createLabel("Tempo: 00:00");
        add(timeLabel);

        add(createSeparator());

        // --- Status do Inimigo ---
        enemyStatusLabel = createLabel(""); // Texto definido no updateValues
        add(enemyStatusLabel);

        add(createSeparator());

        // --- Status do Jogador ---
        playerStatusLabel = createLabel(""); // Texto definido no updateValues
        add(playerStatusLabel);
        
        // Exibe os recursos do jogador
        moneyLabel = createLabel("");
        moneyLabel.setForeground(new Color(255, 215, 0)); // Dourado
        add(moneyLabel);

        // Define os textos iniciais
        updateValues();
    }

    // Método chamado para atualizar os textos na tela
    public void updateValues() {
        enemyStatusLabel.setText(enemy.getName() + ": " + enemy.getHealth() + " HP");
        playerStatusLabel.setText(player.getName() + ": " + player.getHealth() + " HP");
        moneyLabel.setText("$ " + player.getMoney());
        
        // Se tiver lógica de turno/tempo no Player, atualize aqui também
        // turnLabel.setText("Turno: " + ...);
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
        // Garante que os valores estejam atualizados antes de desenhar
        updateValues();
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
    }
}
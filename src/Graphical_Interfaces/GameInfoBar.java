package Graphical_Interfaces;

import Domain.Player;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import utils.Colors;

public class GameInfoBar extends JPanel {

    private static final int BAR_HEIGHT = 110; 
    private static final Color BAR_BACKGROUND = new Color(0, 0, 0, 180);
    private static final Font INFO_FONT = new Font("SansSerif", Font.BOLD, 12);
    private static final Color TEXT_COLOR = Color.WHITE;

    private JLabel turnLabel;
    private JLabel enemyStatusLabel;
    private JLabel playerStatusLabel;
    private JLabel moneyLabel;

    private final Player player;
    private final Player enemy;

    public GameInfoBar(Player player, Player enemy, 
        ActionListener onPauseAction, 
        ActionListener onEndTurnAction, 
        ActionListener onDrawClick) {

        this.player = player;
        this.enemy = enemy;

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        setBackground(BAR_BACKGROUND);
        setPreferredSize(new Dimension(0, BAR_HEIGHT)); 

        // --- Botão para saque adicional ---
        RoundedButton drawButton = new RoundedButton("Draw", 10);
        drawButton.setFont(new Font("SansSerif", Font.BOLD, 11));
        drawButton.setBackground(Colors.OPTION_BUTTON);
        drawButton.setForeground(Colors.TEXT_OPTION_BUTTON);
        drawButton.setPreferredSize(new Dimension(70, 25));
        drawButton.addActionListener(onDrawClick);
        add(drawButton);


        // --- Botão de Pause ---
        RoundedButton pauseButton = new RoundedButton("Pause", 10);
        pauseButton.setFont(new Font("SansSerif", Font.BOLD, 11)); 
        pauseButton.setBackground(Colors.OPTION_BUTTON);
        pauseButton.setForeground(Colors.TEXT_OPTION_BUTTON);
        pauseButton.setPreferredSize(new Dimension(70, 25)); 
        pauseButton.addActionListener(onPauseAction);
        add(pauseButton);

        // --- Botão de Passar Turno ---
        RoundedButton endTurnButton = new RoundedButton("Passar", 10);
        endTurnButton.setFont(new Font("SansSerif", Font.BOLD, 11));
        endTurnButton.setBackground(new Color(200, 50, 50)); 
        endTurnButton.setForeground(Color.WHITE);
        endTurnButton.setPreferredSize(new Dimension(70, 25));
        endTurnButton.addActionListener(onEndTurnAction);
        add(endTurnButton);

        add(createSeparator());

        // --- Status do Jogo ---
        turnLabel = createLabel("Turno: 1");
        add(turnLabel);

        add(createSeparator());

        // --- Status do Inimigo ---
        enemyStatusLabel = createLabel(""); 
        add(enemyStatusLabel);

        add(createSeparator());

        // --- Status do Jogador ---
        playerStatusLabel = createLabel(""); 
        add(playerStatusLabel);
        
        add(createSeparator());

        // --- Dinheiro ---
        moneyLabel = createLabel("");
        moneyLabel.setForeground(new Color(255, 215, 0)); 
        add(moneyLabel);

        updateValues(1); 
    }

    public void updateValues(int currentTurn) {
        turnLabel.setText("Turno: " + currentTurn);
        enemyStatusLabel.setText(enemy.getName() + ": " + enemy.getHealth());
        playerStatusLabel.setText(player.getName() + ": " + player.getHealth());
        moneyLabel.setText("$" + player.getMoney());
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
        sep.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        return sep;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}
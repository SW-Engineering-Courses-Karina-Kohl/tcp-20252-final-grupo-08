package Graphical_Interfaces;

import javax.swing.*;
import java.awt.*;

import static Graphical_Interfaces.RoundedButton.setButtonSize;

public class HomeMenu_GraphicWindow extends JPanel {

    private JPanel container;
    private JLabel title;
    private RoundedButton startButton;
    private RoundedButton optionsButton;

    public HomeMenu_GraphicWindow() {

        setLayout(null); // vamos posicionar o container manualmente

        // ========== CONTAINER INTERNO ==========
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        // Título
        title = new JLabel("A Generic Card Game!");
        title.setForeground(Colors.TITLE_TEXT);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão A
        startButton = new RoundedButton("Start",20);
        startButton.setBackground(Colors.GENERAL_BUTTON);
        startButton.setForeground(Colors.TEXT_BUTTON);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonSize(startButton, 220, 70);


        // Botão B
        optionsButton = new RoundedButton("Options",20);
        optionsButton.setBackground(Colors.GENERAL_BUTTON);
        optionsButton.setForeground(Colors.TEXT_BUTTON);
        optionsButton.setFocusPainted(false);
        optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonSize(optionsButton, 220, 70);

        // Espaçamentos internos (igual HTML margin)
        container.add(Box.createVerticalStrut(20));
        container.add(title);
        container.add(Box.createVerticalStrut(30));
        container.add(startButton);
        container.add(Box.createVerticalStrut(20));
        container.add(optionsButton);

        add(container);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int menu_Width = getWidth();
        int menu_Height = getHeight();

        // SQUARE RESPONSIVO
        int squareWidth = Math.min(menu_Width, menu_Height) - 80;
        int x = (menu_Width - squareWidth) / 2;
        int y = (menu_Height - squareWidth) / 2;

        g2d.setColor(Colors.GENERAL_BACKGROUND);
        g2d.fillRoundRect(x, y, squareWidth, squareWidth, 25, 25);

        // ========== AJUSTA O CONTAINER DENTRO DO SQUARE ==========
        int containerWidth = (int)(squareWidth * 0.7);
        int containerHeight = (int)(squareWidth * 0.5);

        container.setBounds(
                x + (squareWidth - containerWidth) / 2,  // centro X
                y + (squareWidth - containerHeight) / 2,   // centro Y
                containerWidth,
                containerHeight
        );

        // Fonte proporcional ao tamanho
        int fontSizetitle = squareWidth / 14;
        int fontSizeBotao = squareWidth / 18;

        title.setFont(new Font("Serif", Font.BOLD, fontSizetitle));
        startButton.setFont(new Font("Serif", Font.BOLD, fontSizeBotao));
        optionsButton.setFont(new Font("Serif", Font.BOLD, fontSizeBotao));
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Square com Container Interno");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new HomeMenu_GraphicWindow());

            frame.setSize(800, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

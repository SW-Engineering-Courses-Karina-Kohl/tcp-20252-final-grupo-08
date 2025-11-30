package Graphical_Interfaces;

import static Graphical_Interfaces.RoundedButton.setButtonSize;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.*;
import utils.Colors;
import utils.GameStartListener;

public class HomeMenu_GraphicWindow extends JPanel {

    private final JLabel title;
    private final RoundedButton startButton;
    private final RoundedButton optionsButton;

    private final ContainerManager containerManager;
    private final GameStartListener gameStartListener;
    private final JPanel container; // Tornar atributo para revalidar

    public HomeMenu_GraphicWindow(ArrayList<Object[]> sizes, GameStartListener listener) {

        this.gameStartListener = listener;

        int windowsWidth = 640;
        int windowsHeight = 480;

        int buttonWidth = (int) (windowsWidth * 0.22);
        int buttonHeight = (int) (windowsHeight * 0.07);

        setLayout(null); 

        // ========== CONTAINER INTERNO ==========
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        // Criar o gerenciador do container
        containerManager = new ContainerManager(container);

        // ========== TÍTULO ==========
        title = new JLabel("A Generic Card Game!");
        title.setForeground(Colors.TITLE_TEXT);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão Start
        startButton = new RoundedButton("Start", 20);
        startButton.setBackground(Colors.GENERAL_BUTTON);
        startButton.setForeground(Colors.TEXT_BUTTON);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonSize(startButton, buttonWidth, buttonHeight);

        // Botão Options
        optionsButton = new RoundedButton("Options", 20);
        optionsButton.setBackground(Colors.GENERAL_BUTTON);
        optionsButton.setForeground(Colors.TEXT_BUTTON);
        optionsButton.setFocusPainted(false);
        optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonSize(optionsButton, buttonWidth, buttonHeight);

        // Espaçamentos
        container.add(Box.createVerticalStrut(20));
        container.add(title);
        container.add(Box.createVerticalStrut(30));
        container.add(startButton);
        container.add(Box.createVerticalStrut(20));
        container.add(optionsButton);

        add(container);

        startButton.addActionListener(e -> {
            Object[] first = sizes.getFirst();

            int width = (int) first[0];
            int height = (int) first[1];

            if (gameStartListener != null) {
                gameStartListener.onStartGame(width, height);
                
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (frame != null) {
                    frame.dispose();
                }
            }
        });

        // Reagir ao redimensionamento da janela
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();
                containerManager.ajust(w, h);
                updateButtonSizes(w, h);
                container.revalidate();
            }
        });

        optionsButton.addActionListener(e -> {
            OptionsMenu_GraphicWindow op = new OptionsMenu_GraphicWindow(sizes);

            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "A Generic Card Game - Opções", false);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(null);
            dialog.add(op);
            dialog.setVisible(true);
        });

    }
    
    private void updateButtonSizes(int w, int h) {
        int buttonWidth = (int) (w * 0.22);
        int buttonHeight = (int) (h * 0.07);
        setButtonSize(startButton, buttonWidth, buttonHeight);
        setButtonSize(optionsButton, buttonWidth, buttonHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fundo da janela
        g2d.setColor(Colors.GENERAL_BACKGROUND);
        g2d.fillRoundRect(0, 0, w, h, 25, 25);

        // Ajuste das fontes de forma responsiva
        int base = Math.min(w, h);

        int fontSizeTitle = base / 12;
        int fontSizeButton = base / 20;

        title.setFont(new Font("Serif", Font.BOLD, fontSizeTitle));
        startButton.setFont(new Font("Serif", Font.BOLD, fontSizeButton));
        optionsButton.setFont(new Font("Serif", Font.BOLD, fontSizeButton));
    }

    private void setAction(JButton button, Runnable action) {
        button.addActionListener(e -> action.run());
    }
}
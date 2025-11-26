package Graphical_Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import static Graphical_Interfaces.RoundedButton.setButtonSize;

public class HomeMenu_GraphicWindow extends JPanel {

    private JPanel container;
    private JLabel title;
    private RoundedButton startButton;
    private RoundedButton optionsButton;
    private int buttonWidth;
    private int buttonHeight;

    private ContainerManager containerManager; // <<< nova classe

    public HomeMenu_GraphicWindow(ArrayList<Object[]> sizes) {

        Object[] first = sizes.get(0);

        int windowsWidth = (int) first[0];
        int windowsHeight = (int) first[1];

        buttonWidth = (int)(windowsWidth * 0.22);
        buttonHeight = (int)(windowsHeight * 0.07);

        setLayout(null); // container será posicionado manualmente

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

        // Reagir ao redimensionamento da janela
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                containerManager.ajustar(getWidth(), getHeight());
            }
        });

        optionsButton.addActionListener(e -> {
            // Cria o painel da nova tela
            OptionsMenu_GraphicWindow op = new OptionsMenu_GraphicWindow(sizes);

            // Cria janela sobreposta
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Options", false);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(null);
            dialog.add(op);
            dialog.setVisible(true);
        });

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


    public static void main(String[] args) {
        int width = 640;
        int height = 480;
        String actualScale = new String("640p");
        String actualSound = new String("100%");

        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[] {width, height, actualScale, actualSound});

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Square com Container Interno");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new HomeMenu_GraphicWindow(sizes));

            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

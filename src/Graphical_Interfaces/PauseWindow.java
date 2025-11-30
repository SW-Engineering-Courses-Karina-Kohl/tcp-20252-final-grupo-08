package Graphical_Interfaces;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.*;
import utils.Colors;
import utils.GameStartListener;


public class PauseWindow extends JPanel {

    private final JLabel title;

    private final JButton resumeButton;
    private final JButton optionsButton; 
    private final JButton menuButton;    

    private static final String TXT_CONFIRM_TITLE = "Sair";
    private static final String TXT_CONFIRM_MSG = "Deseja voltar ao menu principal?";
    private static final String WINDOW_TITLE_MENU = "A Generic Card Game - Menu Principal";
    private static final String WINDOW_TITLE_MATCH = "A Generic Card Game - Partida";

    private final ContainerManager containerManager;

    public PauseWindow(ArrayList<Object[]> sizes) {
        
        int windowsWidth = 320;
        int windowsHeight = 240;

        int buttonWidth = (int) (windowsWidth * 0.42);
        int buttonHeight = (int) (windowsHeight * 0.1);
        

        setLayout(null);

        // ===================== CONTAINER =====================
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        containerManager = new ContainerManager(container);

        // ==================== ResumeButton ====================

        // ===================== TÍTULO =====================
        title = new JLabel("A Generic Card Game!");
        title.setForeground(Colors.TITLE_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===================== BOTÕES GRANDES =====================
        resumeButton = new RoundedButton("Resume", 20);
        resumeButton.setBackground(Colors.GENERAL_BUTTON);
        resumeButton.setForeground(Colors.TEXT_BUTTON);
        RoundedButton.setButtonSize(resumeButton, buttonWidth, buttonHeight);

        resumeButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

        // Botão Options
        optionsButton = new RoundedButton("Options", 20);
        optionsButton.setBackground(Colors.GENERAL_BUTTON);
        optionsButton.setForeground(Colors.TEXT_BUTTON);
        RoundedButton.setButtonSize(optionsButton, buttonWidth, buttonHeight);

        // Botão Main Menu
        menuButton = new RoundedButton("Main Menu", 20);
        menuButton.setBackground(Colors.GENERAL_BUTTON);
        menuButton.setForeground(Colors.TEXT_BUTTON);
        RoundedButton.setButtonSize(menuButton, buttonWidth, buttonHeight);


        // ===================== LINHA SCALE =====================
        JPanel linhaResume = new JPanel();
        linhaResume.setLayout(new BoxLayout(linhaResume, BoxLayout.X_AXIS));
        linhaResume.setOpaque(false);
        linhaResume.add(resumeButton);

        // ===================== LINHA Main Menu =====================
        JPanel linhaMenu = new JPanel();
        linhaMenu.setLayout(new BoxLayout(linhaMenu, BoxLayout.X_AXIS));
        linhaMenu.setOpaque(false);
        linhaMenu.add(menuButton);

        // ===================== LINHA OPTIONS =====================
        JPanel linhaOptions = new JPanel();
        linhaOptions.setLayout(new BoxLayout(linhaOptions, BoxLayout.X_AXIS));
        linhaOptions.setOpaque(false);
        linhaOptions.add(optionsButton);



        // ===================== ADIÇÃO AO CONTAINER =====================
        container.add(title);
        container.add(Box.createVerticalStrut(20));
        container.add(linhaResume);
        container.add(Box.createVerticalStrut(20));
        container.add(linhaMenu);
        container.add(Box.createVerticalStrut(20));
        container.add(linhaOptions);


        add(container);

        optionsButton.addActionListener(e -> {
            // Cria o painel da nova tela
            OptionsMenu_GraphicWindow op = new OptionsMenu_GraphicWindow(sizes);

            // Cria janela sobreposta
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "A Generic Card Game - Opções", false);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(640, 480);
            dialog.setLocationRelativeTo(null);
            dialog.add(op);
            dialog.setVisible(true);
        });

        menuButton.addActionListener(e -> {
            menuGenerator(sizes);
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();

                containerManager.ajust(w, h);
                updateButtonSizes(w, h); // Atualiza tamanho físico dos botões
                container.revalidate(); // Força o layout a reconhecer os novos tamanhos
            }
        });


    }

    private void updateButtonSizes(int w, int h) {
        int buttonWidth = (int) (w * 0.42);
        int buttonHeight = (int) (h * 0.1);
        
        RoundedButton.setButtonSize(resumeButton, buttonWidth, buttonHeight);
        RoundedButton.setButtonSize(optionsButton, buttonWidth, buttonHeight);
        RoundedButton.setButtonSize(menuButton, buttonWidth, buttonHeight);
    }

    private void menuGenerator(ArrayList<Object[]> sizes){

        int confirm = JOptionPane.showConfirmDialog(this,
                TXT_CONFIRM_MSG, TXT_CONFIRM_TITLE, JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            for (Window w : Window.getWindows()) {
                if (w instanceof JFrame && w.isVisible()) {
                    w.dispose();
                }
            }

            GameStartListener starter = (newW, newH) -> SwingUtilities.invokeLater(() -> {
                JFrame gameFrame = createGameFrame(newW, newH);
                gameFrame.setVisible(true);
                gameFrame.add(new MatchWindow(sizes));
            });

            SwingUtilities.invokeLater(() -> {
                JFrame menuFrame = new JFrame(WINDOW_TITLE_MENU);
                menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menuFrame.setResizable(false);
                menuFrame.add(new HomeMenu_GraphicWindow(sizes, starter));
                menuFrame.getContentPane().setPreferredSize(new Dimension(640, 480));
                menuFrame.pack();
                menuFrame.setLocationRelativeTo(null);
                menuFrame.setVisible(true);
            });
        }
    }

    private JFrame createGameFrame(int w, int h) {
        JFrame gameFrame = new JFrame(WINDOW_TITLE_MATCH);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);

        gameFrame.setPreferredSize(new Dimension(w, h));
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        return gameFrame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2d.setColor(Colors.GENERAL_BACKGROUND);
        g2d.fillRoundRect(0, 0, w, h, 25, 25);

        // Tamanhos responsivos das fontes
        int base = Math.min(w, h);

        int fontSizeTitle = base / 12;
        int fontSizeButton = base / 16;
        Font fontButton = new Font("Serif", Font.BOLD, fontSizeButton);

        title.setFont(new Font("Serif", Font.BOLD, fontSizeTitle));
        resumeButton.setFont(fontButton);
        optionsButton.setFont(fontButton);
        menuButton.setFont(fontButton);

    }

}
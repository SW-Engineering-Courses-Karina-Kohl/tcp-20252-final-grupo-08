package Graphical_Interfaces;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.*;

import Sounds.GlobalMusic;
import utils.Colors;
import utils.EnumerateScales;
import utils.EnumerateSounds;

public class OptionsMenu_GraphicWindow extends JPanel {

    private final JLabel title;

    private final RoundedButton scaleButton;
    private final SizedComboBox<String> scaleSelector;

    private final RoundedButton soundButton;
    private final SizedComboBox<String> soundSelector;

    private final JButton backButton;

    private String actualScale;
    private String actualSound;

    private final ContainerManager containerManager;  // <<< Gerenciador do container

    public OptionsMenu_GraphicWindow(ArrayList<Object[]> sizes) {

        Object[] first = sizes.getFirst();

        int windowsWidth = 640;
        int windowsHeight = 480;
        actualScale = (String) first[2];
        actualSound = (String) first[3];

        int boxWidth = (int) (windowsWidth * 0.22);
        int boxHeight = (int) (windowsHeight * 0.07);

        int spaceButtons = (int) (boxWidth * 0.8);
        int buttonWidth = boxWidth / 2;
        int buttonHeight = boxHeight / 2;

        setLayout(null);

        // ===================== CONTAINER =====================
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        containerManager = new ContainerManager(container);

        // ==================== BackButton ====================
        backButton = new JButton("←");
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Colors.TEXT_OPTION_BUTTON);
        backButton.setBounds(10, 10, boxWidth, boxHeight);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

        add(backButton);


        // ===================== TÍTULO =====================
        title = new JLabel("A Generic Card Game!");
        title.setForeground(Colors.TITLE_TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===================== BOTÕES GRANDES =====================
        scaleButton = new RoundedButton("Scale", 50);
        scaleButton.setBackground(Colors.GENERAL_BUTTON);
        scaleButton.setForeground(Colors.TEXT_BUTTON);
        RoundedButton.setButtonSize(scaleButton, boxWidth, boxHeight);

        soundButton = new RoundedButton("Sound", 50);
        soundButton.setBackground(Colors.GENERAL_BUTTON);
        soundButton.setForeground(Colors.TEXT_BUTTON);
        RoundedButton.setButtonSize(soundButton, boxWidth, boxHeight);

        // ===================== SELECTORS =====================
        scaleSelector = new SizedComboBox<>(
                new String[]{"480p", "720p", "1080p", "1440p"},
                buttonWidth, buttonHeight
        );
        scaleSelector.setSelectedItem(actualScale);
        scaleSelector.setBackground(Colors.OPTION_BUTTON);
        scaleSelector.setForeground(Colors.TEXT_OPTION_BUTTON);

        soundSelector = new SizedComboBox<>(
                new String[]{"0%", "25%", "50%", "75%", "100%"},
                buttonWidth, buttonHeight
        );
        soundSelector.setSelectedItem(actualSound);
        soundSelector.setBackground(Colors.OPTION_BUTTON);
        soundSelector.setForeground(Colors.TEXT_OPTION_BUTTON);

        scaleSelector.addActionListener(e -> {
            actualScale = (String) scaleSelector.getSelectedItem();
            updateScale(sizes, actualScale);
        });

        soundSelector.addActionListener(e -> {
            actualSound = (String) soundSelector.getSelectedItem();
            updateSound(sizes, actualSound);
        });

        // ===================== LINHA SCALE =====================
        JPanel linhaScale = new JPanel();
        linhaScale.setLayout(new BoxLayout(linhaScale, BoxLayout.X_AXIS));
        linhaScale.setOpaque(false);
        linhaScale.add(scaleButton);
        linhaScale.add(Box.createRigidArea(new Dimension(spaceButtons, 0)));
        linhaScale.add(scaleSelector);

        // ===================== LINHA SOUND =====================
        JPanel linhaSound = new JPanel();
        linhaSound.setLayout(new BoxLayout(linhaSound, BoxLayout.X_AXIS));
        linhaSound.setOpaque(false);
        linhaSound.add(soundButton);
        linhaSound.add(Box.createRigidArea(new Dimension(spaceButtons, 0)));
        linhaSound.add(soundSelector);

        // ===================== ADIÇÃO AO CONTAINER =====================
        container.add(Box.createVerticalStrut(25));
        container.add(title);
        container.add(Box.createVerticalStrut(40));
        container.add(linhaScale);
        container.add(Box.createVerticalStrut(30));
        container.add(linhaSound);

        add(container);

        // Centralizar ao redimensionar
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                containerManager.ajust(getWidth(), getHeight());
            }
        });

    }

    private void updateScale(ArrayList<Object[]> sizes, String actualScale){
        Object[] first = sizes.getFirst();

        EnumerateScales scale = EnumerateScales.fromLabel(actualScale);
        assert scale != null;
        int width = scale.getWidth();
        int height = scale.getHeight();

        sizes.set(0, new Object[] { width, height, actualScale, first[3] });
    }

    private void updateSound(ArrayList<Object[]> sizes, String actualSound) {
        Object[] first = sizes.getFirst();

        sizes.set(0, new Object[] { first[0], first[1], first[2], actualSound });

        EnumerateSounds sound = EnumerateSounds.fromLabel(actualSound);
        if (sound != null && GlobalMusic.themeMusic != null) {
            GlobalMusic.themeMusic.setVolume(sound.getValue());
        }
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

        title.setFont(new Font("Serif", Font.BOLD, fontSizeTitle));
        scaleButton.setFont(new Font("Serif", Font.BOLD, fontSizeButton));
        soundButton.setFont(new Font("Serif", Font.BOLD, fontSizeButton));
        backButton.setFont(new Font("Serif", Font.BOLD, fontSizeButton));

    }

    public static void main(String[] args) {
        int width = 640;
        int height = 480;
        String  scale = new  String("640p");
        String  sound = new  String("100%");
        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[]{width, height, scale, sound});

        OptionsMenu_GraphicWindow menuOptions = new OptionsMenu_GraphicWindow(sizes);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Janela com Container");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(menuOptions);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

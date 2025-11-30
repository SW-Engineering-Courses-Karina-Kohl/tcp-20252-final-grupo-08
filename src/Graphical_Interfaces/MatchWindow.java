package Graphical_Interfaces;

import Domain.Card;
import Domain.MonsterCard;
import Domain.Player;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.*;
import utils.Colors;

public class MatchWindow extends JPanel implements CardWidget.OnCardClickListener {

    private static final double INSPECTION_PANEL_WIDTH_RATIO = 0.5;
    private static final double INSPECTION_PANEL_HEIGHT_RATIO = 0.9;
    
    private static final double HAND_PANEL_HEIGHT_RATIO = 0.30;
    private static final double HAND_CARD_WIDTH_RATIO = 0.66;
    
    private static final double BOARD_PANEL_HEIGHT_RATIO = 0.50; 
    
    private static final int HAND_MARGIN = 25;
    private static final int HAND_GAP = 10;
    private static final int PAUSE_BUTTON_RADIUS = 15;
    private static final String TXT_PAUSE_BUTTON = "Pause";
    
    private final JLayeredPane layeredPane;
    private final JPanel gameContent;
    
    private JPanel handPanel;
    private JPanel boardPanel; 
    private CardInspectionPanel inspectionPanel;

    private final Player player;
    private final Player enemy;
    
    public MatchWindow(ArrayList<Object[]> sizes, Player player, Player enemy) {
        this.player = player;
        this.enemy = enemy;

        Object[] first = sizes.getFirst();
        int width = (int) first[0];
        int height = (int) first[1];

        setLayout(new BorderLayout());
        setSize(width, height);
        setBackground(Colors.GENERAL_BACKGROUND.darker());
        
        layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        gameContent = new JPanel(new BorderLayout());
        gameContent.setOpaque(false);
        layeredPane.add(gameContent, JLayeredPane.DEFAULT_LAYER);

        initializeTopBar(sizes);
        initializeBoardPanel();
        initializeHandPanel(width, height);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayoutBounds();
            }
        });
    }
    
    private void updateLayoutBounds() {
        int w = getWidth();
        int h = getHeight();
        
        gameContent.setBounds(0, 0, w, h);
        
        if (inspectionPanel != null && inspectionPanel.isVisible()) {
            int panelW = (int)(w * INSPECTION_PANEL_WIDTH_RATIO);
            int panelH = (int)(h * INSPECTION_PANEL_HEIGHT_RATIO); 
            inspectionPanel.setBounds((w - panelW)/2, (h - panelH)/2, panelW, panelH);
        }
        
        if (handPanel != null) {
            int handH = (int)(h * HAND_PANEL_HEIGHT_RATIO);
            handPanel.setPreferredSize(new Dimension(w, handH));

            int cardHeight = handH - HAND_MARGIN;
            int cardWidth = (int)(cardHeight * HAND_CARD_WIDTH_RATIO);
            Dimension cardSize = new Dimension(cardWidth, cardHeight);

            for (Component comp : handPanel.getComponents()) {
                if (comp instanceof CardWidget) {
                    comp.setPreferredSize(cardSize);
                    comp.revalidate();
                }
            }
            handPanel.revalidate();
        }

        if (boardPanel != null) {
            int boardH = (int)(h * BOARD_PANEL_HEIGHT_RATIO);
            boardPanel.setPreferredSize(new Dimension(w, boardH));
            boardPanel.revalidate();
        }
    }

    private void initializeTopBar(ArrayList<Object[]> sizes) {
        RoundedButton pauseButton = new RoundedButton(TXT_PAUSE_BUTTON, PAUSE_BUTTON_RADIUS);
        pauseButton.setBackground(Colors.OPTION_BUTTON);
        pauseButton.setForeground(Colors.TEXT_OPTION_BUTTON);

        pauseButton.addActionListener(e -> handleOpenPauseMenu(sizes));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false);
        topPanel.add(pauseButton);
        
        gameContent.add(topPanel, BorderLayout.NORTH);
    }

    private void initializeBoardPanel() {
        boardPanel = new JPanel(new GridLayout(2, 5, 10, 10)); 
        boardPanel.setOpaque(false);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); 
        
        gameContent.add(boardPanel, BorderLayout.CENTER);
        updateBoardVisuals();
    }
    
    private void initializeHandPanel(int w, int h) {
        handPanel = new JPanel();
        int handHeight = (int)(h * HAND_PANEL_HEIGHT_RATIO);
        
        handPanel.setPreferredSize(new Dimension(w, handHeight));
        handPanel.setBackground(new Color(0, 0, 0, 80)); 
        handPanel.setLayout(new FlowLayout(FlowLayout.CENTER, HAND_GAP, HAND_GAP));
        
        gameContent.add(handPanel, BorderLayout.SOUTH);
        updateHandVisuals();
    }

    private void handleOpenPauseMenu(ArrayList<Object[]> sizes) {
        PauseWindow menuPause = new PauseWindow(sizes);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Janela de Pause");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(menuPause);
            frame.setSize(425, 320);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public void updateHandVisuals() {
        handPanel.removeAll();

        int panelHeight = handPanel.getPreferredSize().height;
        if (panelHeight == 0) panelHeight = 150; 
        
        int cardHeight = panelHeight - HAND_MARGIN;
        int cardWidth = (int)(cardHeight * HAND_CARD_WIDTH_RATIO);
        
        for (Card card : player.getHand()) {
            CardWidget widget = new CardWidget(card, cardWidth, cardHeight, this); 
            handPanel.add(widget);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    public void updateBoardVisuals() {
        boardPanel.removeAll();

        for (int i = 0; i < 5; i++) {
            Optional<MonsterCard> monster = enemy.getBoard().getMonsterAt(i);
            if (monster.isPresent()) {
                boardPanel.add(new CardWidget(monster.get(), 0, 0, this)); 
            } else {
                boardPanel.add(createEmptySlot());
            }
        }

        for (int i = 0; i < 5; i++) {
            Optional<MonsterCard> monster = player.getBoard().getMonsterAt(i);
            if (monster.isPresent()) {
                boardPanel.add(new CardWidget(monster.get(), 0, 0, this));
            } else {
                boardPanel.add(createEmptySlot());
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private JPanel createEmptySlot() {
        JPanel slot = new JPanel();
        slot.setBackground(new Color(255, 255, 255, 30)); 
        slot.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2, 5, 2, true));
        return slot;
    }

    @Override
    public void onCardClicked(Card card) {
        showCardDetails(card);
    }

    private void showCardDetails(Card card) {
        if (inspectionPanel != null) {
            layeredPane.remove(inspectionPanel);
        }

        inspectionPanel = new CardInspectionPanel(card, this::closeInspection);
        
        layeredPane.add(inspectionPanel, JLayeredPane.PALETTE_LAYER);
        
        gameContent.setEnabled(false);
        handPanel.setVisible(false);
        boardPanel.setVisible(false);
        
        updateLayoutBounds();
        layeredPane.revalidate();
        layeredPane.repaint();
    }
    
    private void closeInspection() {
        if (inspectionPanel != null) {
            inspectionPanel.setVisible(false);
            layeredPane.remove(inspectionPanel);
            inspectionPanel = null;
        }
        gameContent.setEnabled(true);
        handPanel.setVisible(true);
        boardPanel.setVisible(true);
        
        updateHandVisuals();
        updateBoardVisuals();
        
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void startGameSetup() {
        if (player.getHand().isEmpty()) {
            for(int i=0; i<5; i++) {
                player.drawCard();
                enemy.drawCard();
            }
            updateHandVisuals();
        }
    }
}
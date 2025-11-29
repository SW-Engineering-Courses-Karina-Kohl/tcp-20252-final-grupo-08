package Graphical_Interfaces;

import Domain.Card;
import Domain.CardFactory;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MatchWindow extends JPanel implements CardWidget.OnCardClickListener {

    
    private static final double INSPECTION_PANEL_WIDTH_RATIO = 0.5;
    private static final double INSPECTION_PANEL_HEIGHT_RATIO = 0.9;
    private static final double HAND_PANEL_HEIGHT_RATIO = 0.35;
    private static final double CARD_WIDTH_RATIO = 0.66;
    private static final int HAND_MARGIN = 35;
    private static final int HAND_GAP = 15;
    private static final int EXIT_BUTTON_RADIUS = 15;
    
    
    private static final String TXT_EXIT_BUTTON = "Voltar ao Menu";
    private static final String TXT_CONFIRM_TITLE = "Sair";
    private static final String TXT_CONFIRM_MSG = "Deseja voltar ao menu principal?";
    private static final String WINDOW_TITLE_MENU = "A Generic Card Game - Menu Principal";
    private static final String WINDOW_TITLE_MATCH = "A Generic Card Game - Partida";

    private final JLayeredPane layeredPane;
    private final JPanel gameContent;
    private JPanel handPanel;
    private CardInspectionPanel inspectionPanel;
    
    public MatchWindow(int width, int height) {
        setLayout(new BorderLayout());
        setSize(width, height);
        setBackground(Colors.GENERAL_BACKGROUND.darker());
        
        layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        gameContent = new JPanel(new BorderLayout());
        gameContent.setOpaque(false);
        layeredPane.add(gameContent, JLayeredPane.DEFAULT_LAYER);

        initializeTopBar();
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
            handPanel.revalidate();
        }
    }

    private void initializeTopBar() {
        RoundedButton exitButton = new RoundedButton(TXT_EXIT_BUTTON, EXIT_BUTTON_RADIUS);
        exitButton.setBackground(Colors.END_GAME_BUTTON);
        exitButton.setForeground(Colors.TEXT_END_GAME_BUTTON);
        
        exitButton.addActionListener(e -> handleExitToMenu());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false);
        topPanel.add(exitButton);
        
        gameContent.add(topPanel, BorderLayout.NORTH);
    }
    
    private void handleExitToMenu() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            TXT_CONFIRM_MSG, TXT_CONFIRM_TITLE, JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (currentFrame != null) currentFrame.dispose();
            
            rebuildMainMenu(getWidth(), getHeight());
        }
    }

    private void rebuildMainMenu(int w, int h) {
        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[] {w, h, "640p", "100%"}); 

        GameStartListener starter = (newW, newH) -> SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = createGameFrame(newW, newH);
            gameFrame.setVisible(true);
            gameFrame.add(new MatchWindow(newW, newH));
        });

        SwingUtilities.invokeLater(() -> {
            JFrame menuFrame = new JFrame(WINDOW_TITLE_MENU);
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setResizable(false);
            menuFrame.add(new HomeMenu_GraphicWindow(sizes, starter));
            menuFrame.getContentPane().setPreferredSize(new Dimension(w, h));
            menuFrame.pack();
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);
        });
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
    
    private void initializeHandPanel(int w, int h) {
        handPanel = new JPanel();
        int handHeight = (int)(h * HAND_PANEL_HEIGHT_RATIO);
        
        handPanel.setPreferredSize(new Dimension(w, handHeight));
        handPanel.setBackground(new Color(0, 0, 0, 80)); 
        handPanel.setLayout(new FlowLayout(FlowLayout.CENTER, HAND_GAP, HAND_GAP));
        
        gameContent.add(handPanel, BorderLayout.SOUTH);
        loadMockHand();
    }

    private void loadMockHand() {
        List<Card> mockCards = new ArrayList<>();
        mockCards.add(CardFactory.createSwordsman());
        mockCards.add(CardFactory.createFireball());
        mockCards.add(CardFactory.createHealing());

        int panelHeight = handPanel.getPreferredSize().height;
        int cardHeight = panelHeight - HAND_MARGIN;
        int cardWidth = (int)(cardHeight * CARD_WIDTH_RATIO);
        
        for (Card card : mockCards) {
            CardWidget widget = new CardWidget(card, cardWidth, cardHeight, this); 
            handPanel.add(widget);
        }
        handPanel.revalidate();
        handPanel.repaint();
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
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void startGameSetup() {
        System.out.println("Partida iniciada. Lógica de turno aqui...");
    }
}
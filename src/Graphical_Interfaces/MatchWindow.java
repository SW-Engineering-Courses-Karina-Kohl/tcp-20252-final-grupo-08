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
            int panelW = (int)(w * 0.5);
            // AUMENTADO PARA 0.9 (90%) PARA EVITAR CORTAR O BOTÃO
            int panelH = (int)(h * 0.9); 
            inspectionPanel.setBounds((w - panelW)/2, (h - panelH)/2, panelW, panelH);
        }
        
        if (handPanel != null) {
            int handH = (int)(h * 0.35);
            handPanel.setPreferredSize(new Dimension(w, handH));
            handPanel.revalidate();
        }
    }

    private void initializeTopBar() {
        RoundedButton exitButton = new RoundedButton("Voltar ao Menu", 15);
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
            "Deseja voltar ao menu principal?", "Sair", JOptionPane.YES_NO_OPTION);
            
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
            JFrame menuFrame = new JFrame("A Generic Card Game - Menu Principal");
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
        JFrame gameFrame = new JFrame("A Generic Card Game - Partida");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false); 
        
        gameFrame.setPreferredSize(new Dimension(w, h));
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        return gameFrame;
    }
    
    private void initializeHandPanel(int w, int h) {
        handPanel = new JPanel();
        int handHeight = (int)(h * 0.35);
        
        handPanel.setPreferredSize(new Dimension(w, handHeight));
        handPanel.setBackground(new Color(0, 0, 0, 80)); 
        handPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        
        gameContent.add(handPanel, BorderLayout.SOUTH);
        loadMockHand();
    }

    private void loadMockHand() {
        List<Card> mockCards = new ArrayList<>();
        mockCards.add(CardFactory.createSwordsman());
        mockCards.add(CardFactory.createFireball());
        mockCards.add(CardFactory.createHealing());

        int panelHeight = handPanel.getPreferredSize().height;
        int margin = 35; 
        int cardHeight = panelHeight - margin;
        int cardWidth = (int)(cardHeight * 0.66);
        
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
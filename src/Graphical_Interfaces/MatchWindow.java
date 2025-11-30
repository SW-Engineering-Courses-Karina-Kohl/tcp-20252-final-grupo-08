package Graphical_Interfaces;

import Domain.Card;
import Domain.MonsterCard;
import Domain.Player;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.*;
import utils.Colors;

public class MatchWindow extends JPanel implements CardWidget.OnCardClickListener {

    private static final double HAND_RATIO_H = 0.25;
    private static final double BOARD_RATIO_H = 0.60;
    private static final double INSPECTION_RATIO_W = 0.5;
    private static final double INSPECTION_RATIO_H = 0.9;

    private final JLayeredPane layeredPane;
    private final JPanel gameContent;
    

    private final HandPanel handPanel;    
    private final BoardPanel boardPanel;  
    private final GameInfoBar topBar; 
    
    private CardInspectionPanel inspectionPanel;

    private final Player player;
    private final Player enemy;
    private Card pendingCard = null;

    public MatchWindow(ArrayList<Object[]> sizes, Player player, Player enemy) {
        this.player = player;
        this.enemy = enemy;

        Object[] first = sizes.getFirst();
        int width = (int) first[0];
        int height = (int) first[1];

        setLayout(new BorderLayout());
        setSize(width, height);
        
        setBackground(Colors.GENERAL_BACKGROUND.darker());
        setOpaque(true);

        layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        gameContent = new JPanel(new BorderLayout());
        gameContent.setOpaque(false);
        layeredPane.add(gameContent, JLayeredPane.DEFAULT_LAYER);

        //top bar da HUD
        this.topBar = new GameInfoBar(player, enemy, e -> openPauseMenu(sizes));
        gameContent.add(topBar, BorderLayout.NORTH);

        this.boardPanel = new BoardPanel();
        gameContent.add(boardPanel, BorderLayout.CENTER);

        this.handPanel = new HandPanel(width, (int)(height * HAND_RATIO_H));
        gameContent.add(handPanel, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayoutBounds();
            }
        });

        refreshUI();
    }

    private void prepareToPlaceCard(Card card) {
        if (card instanceof MonsterCard) {
            this.pendingCard = card;
            refreshUI(); 
        } else {
            JOptionPane.showMessageDialog(this, "Apenas tropas podem ir ao campo.");
        }
    }

    private void onBoardSlotClicked(int index) {
        if (pendingCard != null && pendingCard instanceof MonsterCard) {
            boolean success = player.getBoard().placeMonsterAt(index, (MonsterCard) pendingCard);
            
            if (success) {
                player.getHand().remove(pendingCard);
                pendingCard = null;
                refreshUI();
            } else {
                JOptionPane.showMessageDialog(this, "Espaço ocupado!");
            }
        }
    }

    private void refreshUI() {
        handPanel.updateHand(player.getHand(), this);
        
        boolean selectionMode = (pendingCard != null);
        boardPanel.updateBoard(
            enemy.getBoard().getMonsters(),
            player.getBoard().getMonsters(),
            this,                 
            this::onBoardSlotClicked, 
            selectionMode
        );
    }

    @Override
    public void onCardClicked(Card card) {
        if (pendingCard != null) {
            pendingCard = null; 
            refreshUI();
        }
        openInspection(card);
    }

    private void openInspection(Card card) {
        if (inspectionPanel != null) layeredPane.remove(inspectionPanel);

        Consumer<Card> playAction = null;
        if (player.getHand().contains(card)) {
            playAction = this::prepareToPlaceCard;
        }

        inspectionPanel = new CardInspectionPanel(card, this::closeInspection, playAction);
        layeredPane.add(inspectionPanel, JLayeredPane.PALETTE_LAYER);
        
        toggleGameInteraction(false);
        updateLayoutBounds();
    }

    private void closeInspection() {
        if (inspectionPanel != null) {
            inspectionPanel.setVisible(false);
            layeredPane.remove(inspectionPanel);
            inspectionPanel = null;
        }
        toggleGameInteraction(true);
        refreshUI();
    }

    private void toggleGameInteraction(boolean enabled) {
        gameContent.setEnabled(enabled);
        handPanel.setVisible(enabled);
        boardPanel.setVisible(enabled);
        topBar.setVisible(enabled); 
    }

    private void openPauseMenu(ArrayList<Object[]> sizes) {
        PauseWindow menuPause = new PauseWindow(sizes);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pause");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(menuPause);
            frame.setSize(425, 320);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void updateLayoutBounds() {
        int w = getWidth();
        int h = getHeight();
        gameContent.setBounds(0, 0, w, h);

        if (inspectionPanel != null && inspectionPanel.isVisible()) {
            int pw = (int)(w * INSPECTION_RATIO_W);
            int ph = (int)(h * INSPECTION_RATIO_H);
            inspectionPanel.setBounds((w - pw)/2, (h - ph)/2, pw, ph);
        }
        
        int handH = (int)(h * HAND_RATIO_H);
        handPanel.setPreferredSize(new Dimension(w, handH));
        
        int boardH = (int)(h * BOARD_RATIO_H);
        boardPanel.setPreferredSize(new Dimension(w, boardH));
        
        revalidate();
    }

    public void startGameSetup() {
        if (player.getHand().isEmpty()) {
            for(int i=0; i<5; i++) {
                player.drawCard();
                enemy.drawCard();
            }
            refreshUI();
        }
    }
}
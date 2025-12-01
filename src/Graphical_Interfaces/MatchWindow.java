package Graphical_Interfaces;

import Domain.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Optional;
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
    private Card pendingSpell = null;

    private GameManager gameManager;

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

        this.topBar = new GameInfoBar(
                player,
                enemy,
                e -> openPauseMenu(sizes),
                e -> {
                    if (gameManager != null)
                        gameManager.endPlayerTurn();
                },
                e -> {
                    handleExtraDraw();
                });
        gameContent.add(topBar, BorderLayout.NORTH);

        this.boardPanel = new BoardPanel();
        gameContent.add(boardPanel, BorderLayout.CENTER);

        this.handPanel = new HandPanel(width, (int) (height * HAND_RATIO_H));
        gameContent.add(handPanel, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayoutBounds();
            }
        });

        refreshUI();
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private void prepareToPlaceCard(Card card) {
        if (gameManager != null && !gameManager.isPlayerTurn()) {
            JOptionPane.showMessageDialog(this, "Aguarde seu turno!");
            return;
        }

        if (player.getMoney() < card.getCost()) {
            JOptionPane.showMessageDialog(this, "Dinheiro insuficiente!");
            return;
        }

        if (card instanceof MonsterCard) {
            this.pendingCard = card;
            this.pendingSpell = null;
            JOptionPane.showMessageDialog(this, "Escolha um espaço do campo para colocar o monstro.");
            refreshUI();
            return;
        }

        if (card instanceof SpellCard) {
            this.pendingSpell = card;
            this.pendingCard = null;
            JOptionPane.showMessageDialog(this, "Escolha um monstro para aplicar o efeito.");
            refreshUI();
        }
    }

    private void onBoardSlotClicked(int index, boolean isEnemy) {

        if (pendingCard != null && pendingCard instanceof MonsterCard monster) {
            boolean success = player.getBoard().placeMonsterAt(index, monster);
            if (success) {
                player.spendMoney(monster.getCost());
                player.getHand().remove(monster);
                monster.setBoardPosition(index);
                pendingCard = null;
                refreshUI();
            } else {
                JOptionPane.showMessageDialog(this, "Espaço ocupado!");
            }
            return;
        }

        if (pendingSpell != null && pendingSpell instanceof SpellCard) {
            var opt = (isEnemy ? enemy.getBoard().getMonsterAt(index)
                    : player.getBoard().getMonsterAt(index));
            if (opt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum monstro neste espaço!");
                return;
            }

            MonsterCard target = opt.get();

            applySpellToMonster(pendingSpell, target);

            player.spendMoney(pendingSpell.getCost());
            player.getHand().remove(pendingSpell);
            pendingSpell = null;
            refreshUI();
            return;
        }
    }

    private void applySpellToMonster(Card spell, MonsterCard target) {
        switch (spell.getName()) {
            case "Upgrade" -> SpellEffects.castUpgrade(player, target);
            case "Cura" -> SpellEffects.castHeal(player, target);
            case "Bola de Fogo" -> SpellEffects.castFireball(player, target);
            default -> JOptionPane.showMessageDialog(this, "Spell sem efeito implementado!");
        }
    }

    public void refreshUI() {
        handPanel.updateHand(player.getHand(), this);

        boolean selectionMode = (pendingCard != null) || (pendingSpell != null);

        boardPanel.updateBoard(
                enemy.getBoard().getMonsters(),
                player.getBoard().getMonsters(),
                this,
                this::onBoardSlotClicked,
                selectionMode);

        if (gameManager != null) {
            topBar.updateValues(gameManager.getTurnCount());
        } else {
            topBar.updateValues(1);
        }
        topBar.repaint();

        handPanel.revalidate();
        handPanel.repaint();
        revalidate();
        repaint();
    }

    @Override
    public void onCardClicked(Card card) {

        if (pendingCard != null || pendingSpell != null) {
            pendingCard = null;
            pendingSpell = null;
            refreshUI();
        }
        openInspection(card);
    }

    private void openInspection(Card card) {
        if (inspectionPanel != null) {
            layeredPane.remove(inspectionPanel);
            inspectionPanel = null;
            layeredPane.revalidate();
            layeredPane.repaint();
        }

        Consumer<Card> playAction = null;
        Consumer<MonsterCard> attackAction = null;

        if (player.getHand().contains(card)) {
            playAction = this::prepareToPlaceCard;

        } else if ((card instanceof MonsterCard monster)
                && monster.isOnField()
                && gameManager.getTurnCount() > 1
                && gameManager.isPlayerTurn()
                && !monster.hasAttacked()) {
            attackAction = m -> {
                performAttack(m);
                closeInspection();
            };
        }

        inspectionPanel = new CardInspectionPanel(card, this::closeInspection, playAction, attackAction);
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
            int pw = (int) (w * INSPECTION_RATIO_W);
            int ph = (int) (h * INSPECTION_RATIO_H);
            inspectionPanel.setBounds((w - pw) / 2, (h - ph) / 2, pw, ph);
        }

        int handH = (int) (h * HAND_RATIO_H);
        handPanel.setPreferredSize(new Dimension(w, handH));

        int boardH = (int) (h * BOARD_RATIO_H);
        boardPanel.setPreferredSize(new Dimension(w, boardH));

        revalidate();
    }

    public void startGameSetup() {
        if (player.getHand().isEmpty()) {
            for (int i = 0; i < 5; i++) {
                player.drawCard();
                enemy.drawCard();
            }
            refreshUI();
        }
    }

    private void handleExtraDraw() {
        if (gameManager == null)
            return;

        if (!gameManager.isPlayerTurn())
            return;

        if (player.getMoney() >= 3) {
            player.spendMoney(3);
            player.drawCard();
            refreshUI();
        }
    }

    private void performAttack(MonsterCard attacker) {

        if (attacker.hasAttacked())
            return;

        Player defendingPlayer = gameManager.isPlayerTurn() ? enemy : player;
        int index = attacker.getBoardPosition();

        Optional<MonsterCard> defender = player.getBoard().getOpposingMonster(attacker, defendingPlayer);
        attacker.setAttacked(true);

        if (defender.isEmpty()) { // ataque direto
            enemy.takeDamage(attacker.getAttack());
            refreshUI();
            return;
        }

        MonsterCard def = defender.get();
        def.receiveDamage(attacker.getAttack());
        if (def.getDefense() <= 0) {
            defendingPlayer.getBoard().removeMonsterFrom(index);
        }

        refreshUI();
    }

}
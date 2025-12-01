package Graphical_Interfaces;

import Domain.MonsterCard;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

public class BoardPanel extends JPanel {

    public interface OnSlotClickListener {
        void onSlotClick(int index, boolean isEnemySide);
    }

    public BoardPanel() {
        super(new GridLayout(2, 5, 10, 10));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
    }

    public void updateBoard(List<MonsterCard> enemyMonsters, List<MonsterCard> playerMonsters,
            CardWidget.OnCardClickListener cardListener,
            OnSlotClickListener slotListener,
            boolean isSelectionMode) {
        removeAll();

        // Renderiza Inimigo (Slots superiores)
        for (int i = 0; i < enemyMonsters.size(); i++) {
            MonsterCard monster = enemyMonsters.get(i);
            if (monster != null) {
                if (isSelectionMode && slotListener != null) {
                    add(createMonsterWidgetForSelection(monster, i, slotListener, true));
                } else {

                    add(new CardWidget(monster, 0, 0, cardListener, true));
                }
            } else {
                add(createEmptySlot(-1, false, null, true));
            }
        }

        // Renderiza Jogador (Slots inferiores)
        for (int i = 0; i < playerMonsters.size(); i++) {
            MonsterCard monster = playerMonsters.get(i);
            if (monster != null) {
                if (isSelectionMode && slotListener != null) {
                    add(createMonsterWidgetForSelection(monster, i, slotListener, false));
                } else {

                    add(new CardWidget(monster, 0, 0, cardListener, false));
                }
            } else {
                add(createEmptySlot(i, isSelectionMode, slotListener, false));
            }
        }

        revalidate();
        repaint();
    }

    private JComponent createMonsterWidgetForSelection(MonsterCard monster, int index, OnSlotClickListener slotListener,
            boolean isEnemy) {
        CardWidget.OnCardClickListener listener = card -> {
            if (slotListener != null)
                slotListener.onSlotClick(index, isEnemy);
        };

        return new CardWidget(monster, 0, 0, listener, isEnemy);
    }

    private JPanel createEmptySlot(int index, boolean isClickable, OnSlotClickListener listener, boolean isEnemy) {
        JPanel slot = new JPanel();

        if (isClickable && index >= 0) {
            slot.setBackground(new Color(50, 205, 50, 50));
            slot.setBorder(BorderFactory.createDashedBorder(Color.GREEN, 2, 5, 2, true));
            slot.setCursor(new Cursor(Cursor.HAND_CURSOR));
            slot.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listener.onSlotClick(index, isEnemy);
                }
            });
        } else {
            slot.setBackground(new Color(255, 255, 255, 30));
            slot.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2, 5, 2, true));
        }
        return slot;
    }
}
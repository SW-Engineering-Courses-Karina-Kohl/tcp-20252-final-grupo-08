package Graphical_Interfaces;

import Domain.MonsterCard;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

public class BoardPanel extends JPanel {

    public interface OnSlotClickListener {
        void onSlotClick(int index);
    }

    public BoardPanel() {
        // Garante layout fixo: 2 linhas, 5 colunas
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
        for (MonsterCard monster : enemyMonsters) {
            if (monster != null) {
                add(new CardWidget(monster, 0, 0, cardListener));
            } else {
                add(createEmptySlot(-1, false, null)); // Slot vazio inimigo
            }
        }

        // Renderiza Jogador (Slots inferiores)
        for (int i = 0; i < playerMonsters.size(); i++) {
            MonsterCard monster = playerMonsters.get(i);
            if (monster != null) {
                add(new CardWidget(monster, 0, 0, cardListener));
            } else {
                // Se estiver escolhendo onde jogar (isSelectionMode), o slot fica clicável
                add(createEmptySlot(i, isSelectionMode, slotListener));
            }
        }

        revalidate();
        repaint();
    }

    private JPanel createEmptySlot(int index, boolean isClickable, OnSlotClickListener listener) {
        JPanel slot = new JPanel();
        
        if (isClickable && index >= 0) {
            slot.setBackground(new Color(50, 205, 50, 50)); // Verde destaque
            slot.setBorder(BorderFactory.createDashedBorder(Color.GREEN, 2, 5, 2, true));
            slot.setCursor(new Cursor(Cursor.HAND_CURSOR));
            slot.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listener.onSlotClick(index);
                }
            });
        } else {
            slot.setBackground(new Color(255, 255, 255, 30));
            slot.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2, 5, 2, true));
        }
        return slot;
    }
}
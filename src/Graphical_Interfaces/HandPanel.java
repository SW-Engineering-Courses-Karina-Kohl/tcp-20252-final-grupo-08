package Graphical_Interfaces;

import Domain.Card;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class HandPanel extends JPanel {

    private static final int HAND_MARGIN = 20;
    private static final int HAND_GAP = 15;
    private static final double CARD_WIDTH_RATIO = 0.66;
    // Fundo preto semi-transparente para destacar a mão
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 180);

    public HandPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.CENTER, HAND_GAP, HAND_GAP));
        setOpaque(false);
    }

    public void updateHand(List<Card> cards, CardWidget.OnCardClickListener listener) {
        removeAll(); // Limpa componentes antigos

        int panelHeight = getPreferredSize().height;
        if (panelHeight == 0)
            panelHeight = 150;

        int cardHeight = panelHeight - HAND_MARGIN;
        int cardWidth = (int) (cardHeight * CARD_WIDTH_RATIO);

        for (Card card : cards) {
            CardWidget widget = new CardWidget(card, cardWidth, cardHeight, listener);
            add(widget);
        }

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Resolve o bug visual limpando a área antes de desenhar
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

        super.paintComponent(g);
    }
}
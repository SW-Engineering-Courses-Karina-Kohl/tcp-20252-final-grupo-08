package Graphical_Interfaces;

import Domain.Card;
import Domain.CardStatusIcons;
import Domain.CardType;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.*;

import java.util.List;
import java.util.Map;

import Domain.MonsterCard;
import utils.Colors;

public class CardWidget extends JPanel {
    
    private static final String PLACEHOLDER_PATH = "/resources/placeholder.png";
    private static final String HTML_CENTER_TAG = "<html><center>%s</center></html>";
    private static final int BORDER_THICKNESS = 2;
    private static final int FONT_SIZE = 14;
    private static final int LABEL_PADDING_TOP = 5;
    
    // Proporções da imagem dentro do Card
    private static final double IMG_WIDTH_RATIO = 0.6;
    private static final double IMG_HEIGHT_RATIO = 0.5;

    private final Card cardData;
    private boolean isHovered = false;
    private Image cardImage;
    private JLabel nameLabel;

    public interface OnCardClickListener {
        void onCardClicked(Card card);
    }

    public CardWidget(Card card, int width, int height, OnCardClickListener listener) {
        this.cardData = card;
        setPreferredSize(new Dimension(width, height));
        
        setBackground(getCardColor(card.getType()));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_THICKNESS));
        setLayout(new BorderLayout()); 

        loadImage(card.getImagePath());
        initializeLabel(card.getName());
        setupMouseInteractions(listener);
    }
    
    private Color getCardColor(CardType type) {
        return (type == CardType.MONSTER) ? Colors.MONSTER_COLOR : Colors.SPELL_COLOR;
    }

    private void loadImage(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            imageUrl = getClass().getResource(PLACEHOLDER_PATH);
        }

        if (imageUrl != null) {
            this.cardImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Imagem placeholder não encontrada em: " + PLACEHOLDER_PATH);
        }
    }

    private void initializeLabel(String name) {
        nameLabel = new JLabel(String.format(HTML_CENTER_TAG, name));
        nameLabel.setForeground(Colors.TEXT_BUTTON);
        nameLabel.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(LABEL_PADDING_TOP, 2, 0, 2));
        add(nameLabel, BorderLayout.NORTH);
    }

    private void setupMouseInteractions(OnCardClickListener listener) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                listener.onCardClicked(cardData);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(isHovered ? getBackground().brighter() : getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        drawCardImage(g2d);

        if (!(cardData instanceof MonsterCard monster)) return;

        Map<String, Integer> effectCounts = monster.getEffectCounts();
        if (effectCounts.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int iconSize = 16;
        int spacing   = 4;

        int x = getWidth() - iconSize;
        int y = 6;

        for (var entry : effectCounts.entrySet()) {
            String effect = entry.getKey();
            int count = entry.getValue();

            Image icon = CardStatusIcons.getIcon(effect);
            if (icon != null) {
                g2.drawImage(icon, x, y, iconSize, iconSize, null);

                if (count > 1) {
                    g2.setFont(getFont().deriveFont(Font.BOLD, 12f));
                    g2.setColor(Color.WHITE);
                    g2.drawString("" + count, x + 8, y + 14);
                }

                y += iconSize + spacing;
            }
        }
    }

    private void drawCardImage(Graphics2D g2d) {
        if (cardImage == null) return;

        int imgW = (int) (getWidth() * IMG_WIDTH_RATIO); 
        int imgH = (int) (getHeight() * IMG_HEIGHT_RATIO); 

        int titleHeight = nameLabel.getHeight();
        int availableHeight = getHeight() - titleHeight;
        
        int x = (getWidth() - imgW) / 2;
        int y = titleHeight + (availableHeight - imgH) / 2;

        g2d.drawImage(cardImage, x, y, imgW, imgH, this);
    }
}
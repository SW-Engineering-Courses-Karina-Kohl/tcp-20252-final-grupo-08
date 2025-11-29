package Graphical_Interfaces;

import Domain.Card;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.*;

public class CardWidget extends JPanel {
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
        
        Color cardColor = (card.getType() == Domain.CardType.MONSTER) 
                            ? Colors.MONSTER_COLOR 
                            : Colors.SPELL_COLOR;
        
        setBackground(cardColor);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout()); 

        URL imageUrl = getClass().getResource(card.getImagePath()); 
    
        if (imageUrl == null) {
            imageUrl = getClass().getResource("/resources/placeholder.png");
        }

        if (imageUrl != null) {
            this.cardImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Imagem placeholder.png não encontrada!");
        }
        
        //Texto em html para não ficar cortado
        nameLabel = new JLabel("<html><center>" + card.getName() + "</center></html>");
        nameLabel.setForeground(Colors.TEXT_BUTTON);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 2, 0, 2));
        add(nameLabel, BorderLayout.NORTH);

        //Integração do Mouse para clique e feedback visual da seleção de cartas
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
                //Dispara o evento de inspeção.
                listener.onCardClicked(cardData);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenho e feedback visual
        if (isHovered) {
            g2d.setColor(getBackground().brighter());
        } else {
            g2d.setColor(getBackground());
        }
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (cardImage != null) {
            int imgW = (int) (getWidth() * 0.6); 
            int imgH = (int) (getHeight() * 0.5); 

            int titleHeight = nameLabel.getHeight();
            int availableHeight = getHeight() - titleHeight;
            
            int x = (getWidth() - imgW) / 2;
            int y = titleHeight + (availableHeight - imgH) / 2;

            g2d.drawImage(cardImage, x, y, imgW, imgH, this);
        }
    }
}
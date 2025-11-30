package Graphical_Interfaces;

import Domain.Card;
import Domain.GameAction;
import Domain.MonsterCard;
import java.awt.*;
import java.net.URL;
import java.util.function.Consumer;
import javax.swing.*;
import utils.Colors;

public class CardInspectionPanel extends JPanel {

    private final Runnable onCloseCallback;
    private final Consumer<Card> onPlayCallback;

    public CardInspectionPanel(Card card, Runnable onCloseCallback, Consumer<Card> onPlayCallback) {
        this.onCloseCallback = onCloseCallback;
        this.onPlayCallback = onPlayCallback;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.OPTION_BUTTON.darker());
        setBorder(BorderFactory.createLineBorder(Colors.GENERAL_BUTTON, 5));
        setOpaque(true);

        buildUI(card);
    }

    private void buildUI(Card card) {
        add(Box.createVerticalStrut(15));
        
        JLabel imageLabel = createCardImageLabel(card);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(imageLabel);

        add(Box.createVerticalStrut(10));
        add(createTitleLabel(card.getName()));

        add(Box.createVerticalStrut(5));
        add(createStatsPanel(card));

        add(Box.createVerticalStrut(10));
        add(createDescriptionArea(card.getDescription()));

        //Só adiciona o botão se existir uma ação de jogar (se a carta estiver na mão)
        if (onPlayCallback != null) {
            add(Box.createVerticalStrut(10));
            add(createPlayButton(card));
        }

        if (!card.getAvailableActions().isEmpty()) {
            add(Box.createVerticalStrut(5));
            add(createActionsPanel(card));
        }

        add(Box.createVerticalGlue()); 
        add(createBackButton());
        add(Box.createVerticalStrut(15)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private JButton createPlayButton(Card card) {
        RoundedButton playBtn = new RoundedButton("Colocar em Campo", 10);
        playBtn.setBackground(new Color(50, 205, 50));
        playBtn.setForeground(Color.WHITE);
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        playBtn.addActionListener(e -> {
            if (onPlayCallback != null) {
                onPlayCallback.accept(card);
                onCloseCallback.run();
            }
        });
        return playBtn;
    }

    private JLabel createCardImageLabel(Card card) {
        URL imageUrl = getClass().getResource(card.getImagePath());
        if (imageUrl == null) imageUrl = getClass().getResource("/resources/placeholder.png");
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image img = icon.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        }
        return new JLabel("[Imagem não encontrada]");
    }

    private JLabel createTitleLabel(String name) {
        JLabel title = new JLabel(name);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(Colors.TEXT_OPTION_BUTTON);
        return title;
    }

    private JPanel createStatsPanel(Card card) {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
        String typeLabel = (card.getType() == Domain.CardType.MONSTER) ? "TROPA" : "FEITIÇO";
        statsPanel.add(createStatLabel(typeLabel, Color.LIGHT_GRAY));
        statsPanel.add(createStatLabel("Custo: " + card.getCost(), new Color(255, 215, 0)));
        if (card instanceof MonsterCard) {
            MonsterCard monster = (MonsterCard) card;
            statsPanel.add(createStatLabel("ATK: " + monster.getAttack(), new Color(255, 100, 100)));
            statsPanel.add(createStatLabel("DEF: " + monster.getDefense(), new Color(100, 149, 237)));
        }
        return statsPanel;
    }

    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Monospaced", Font.BOLD, 14));
        label.setForeground(color);
        return label;
    }

    private JTextArea createDescriptionArea(String description) {
        JTextArea desc = new JTextArea(description);
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setOpaque(false);
        desc.setForeground(Colors.TEXT_OPTION_BUTTON);
        desc.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        desc.setMaximumSize(new Dimension(Short.MAX_VALUE, 100)); 
        return desc;
    }

    private JPanel createActionsPanel(Card card) {
        JPanel actionsPanel = new JPanel();
        actionsPanel.setOpaque(false);
        actionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        actionsPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        for (GameAction action : card.getAvailableActions()) {
            RoundedButton btn = new RoundedButton(action.getLabel(), 10);
            btn.setBackground(Colors.GENERAL_BUTTON.brighter());
            btn.addActionListener(e -> {
                action.execute();
                if (onCloseCallback != null) onCloseCallback.run();
            });
            actionsPanel.add(btn);
        }
        return actionsPanel;
    }

    private JButton createBackButton() {
        RoundedButton backBtn = new RoundedButton("Voltar", 10);
        backBtn.setBackground(Colors.OPTION_BUTTON.brighter());
        backBtn.setForeground(Colors.TEXT_OPTION_BUTTON);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> {
            if (onCloseCallback != null) onCloseCallback.run();
        });
        return backBtn;
    }
}
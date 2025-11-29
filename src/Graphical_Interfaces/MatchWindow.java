package Graphical_Interfaces;

import Domain.Card;
import Domain.CardFactory;
import Domain.GameAction;
import Domain.MonsterCard;
import static Graphical_Interfaces.Colors.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


// Implementa o Listener do CardWidget para saber quando uma carta foi clicada 
public class MatchWindow extends JPanel implements CardWidget.OnCardClickListener {

    private JPanel handPanel;         // Mão do Jogador (contém CardWidgets)
    private JPanel inspectionPanel;   // Painel de Zoom 
    
    public MatchWindow(int width, int height) {
        setLayout(new BorderLayout());
        setSize(width, height);
        setBackground(GENERAL_BACKGROUND.darker());
        
        initializeUIControls();
        initializeHandPanel(width, height);
        initializeInspectionPanel(width, height); 
        
    }
    
    private void initializeUIControls() {
        // Botão Voltar ao Menu
        RoundedButton exitButton = new RoundedButton("Voltar ao Menu", 15);
        exitButton.setBackground(END_GAME_BUTTON);
        exitButton.setForeground(TEXT_END_GAME_BUTTON);
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false);
        topPanel.add(exitButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja voltar ao menu principal?", 
                "Sair", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (currentFrame != null) currentFrame.dispose();

                
                int w = getWidth();
                int h = getHeight();
                
                // recria a lista de configurações padrão para evitar possivel erro no OptionsMenu
                ArrayList<Object[]> sizes = new ArrayList<>();
                sizes.add(new Object[] {w, h, "640p", "100%"}); 

                //Recria o Listener (para novas partidas)
                GameStartListener starter = (newW, newH) -> {
                    SwingUtilities.invokeLater(() -> {
                        JFrame gameFrame = new JFrame("A Generic Card Game - Partida");
                        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        gameFrame.setResizable(false); 
                        
                        MatchWindow gamePanel = new MatchWindow(newW, newH);
                        gameFrame.add(gamePanel);
                        
                        // Garante que o layout BorderLayout calcule o tamanho correto sem cortar a base
                        gamePanel.setPreferredSize(new Dimension(newW, newH));
                        gameFrame.pack();
                        
                        gameFrame.setLocationRelativeTo(null);
                        gameFrame.setVisible(true);
                        gamePanel.startGameSetup();
                    });
                };

                //  Abre a Janela do Menu novamente
                SwingUtilities.invokeLater(() -> {
                    JFrame menuFrame = new JFrame("A Generic Card Game - Menu Principal");
                    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    menuFrame.setResizable(false);
                    
                    // Adiciona o menu recriado
                    menuFrame.add(new HomeMenu_GraphicWindow(sizes, starter));
                    
                    // Ajusta o tamanho e exibe
                    menuFrame.getContentPane().setPreferredSize(new Dimension(w, h));
                    menuFrame.pack();
                    menuFrame.setLocationRelativeTo(null);
                    menuFrame.setVisible(true);
                });
            }
        });
    }
    
    private void initializeHandPanel(int w, int h) {
        handPanel = new JPanel();
        int handHeight = (int)(h * 0.35);
        
        handPanel.setPreferredSize(new Dimension(w, handHeight));
        handPanel.setBackground(new Color(0, 0, 0, 80)); 
        handPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        
        add(handPanel, BorderLayout.SOUTH);
        loadMockHand();
    }
    
    private void initializeInspectionPanel(int w, int h) {
        inspectionPanel = new JPanel();
        inspectionPanel.setLayout(new BoxLayout(inspectionPanel, BoxLayout.Y_AXIS));
        
        int panelW = (int)(w * 0.5);
        int panelH = (int)(h * 0.8);
        inspectionPanel.setBounds((w - panelW)/2, (h - panelH)/2, panelW, panelH);
        
        inspectionPanel.setBackground(OPTION_BUTTON.darker());
        inspectionPanel.setBorder(BorderFactory.createLineBorder(GENERAL_BUTTON, 5));
        inspectionPanel.setVisible(false);
        
        add(inspectionPanel);
        setComponentZOrder(inspectionPanel, 0); 
    }

    private void loadMockHand() {
        List<Card> mockCards = new ArrayList<>();

        mockCards.add(CardFactory.createSwordsman());
        mockCards.add(CardFactory.createFireball());
        mockCards.add(CardFactory.createHealing());

        //Arruma o tamanho das cartas na janela
        int panelHeight = handPanel.getHeight();
        if (panelHeight == 0) {
            panelHeight = handPanel.getPreferredSize().height;
        }

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

    //Método chamado pelo CardWidget após o clique
    @Override
    public void onCardClicked(Card card) {
        showCardDetails(card);
    }

    private void showCardDetails(Card card) {
        inspectionPanel.removeAll();
        
        handPanel.setVisible(false);
        handPanel.setEnabled(false);
        inspectionPanel.setVisible(true); 
        
        inspectionPanel.add(Box.createVerticalStrut(20));
        JLabel imageLabel = createCardImageLabel(card); 
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inspectionPanel.add(imageLabel);

        //Adiciona Título e Descrição
        inspectionPanel.add(Box.createVerticalStrut(15));
        JLabel title = new JLabel(card.getName());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(TEXT_OPTION_BUTTON);
        inspectionPanel.add(title);
        
        inspectionPanel.add(Box.createVerticalStrut(10));
        inspectionPanel.add(createStatsPanel(card));

        JTextArea desc = new JTextArea(card.getDescription());
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setOpaque(false);
        desc.setForeground(TEXT_OPTION_BUTTON);
        desc.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        inspectionPanel.add(Box.createVerticalStrut(10));
        inspectionPanel.add(desc);
        
        //Botões de Ação Dinâmicas
        JPanel actionsPanel = new JPanel();
        actionsPanel.setOpaque(false);
        actionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        for (GameAction action : card.getAvailableActions()) {
            RoundedButton btn = new RoundedButton(action.getLabel(), 10);
            btn.setBackground(GENERAL_BUTTON.brighter());
            btn.addActionListener(e -> {
                action.execute(); 
                closeInspection(); 
            });
            actionsPanel.add(btn);
        }
        
        inspectionPanel.add(actionsPanel);

        //Botão Voltar
        RoundedButton backBtn = new RoundedButton("Voltar", 10);
        backBtn.setBackground(OPTION_BUTTON.brighter());
        backBtn.setForeground(TEXT_OPTION_BUTTON);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> closeInspection());
        
        inspectionPanel.add(backBtn);
        inspectionPanel.revalidate();
        repaint();
    }
    
    private JPanel createStatsPanel(Card card) {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        statsPanel.setOpaque(false);

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

    private JLabel createCardImageLabel(Card card) {
        URL imageUrl = getClass().getResource(card.getImagePath());
        if (imageUrl == null) {
            imageUrl = getClass().getResource("/resources/placeholder.png");
        }
        
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image img = icon.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        }
        return new JLabel("[Imagem não encontrada]");
    }
    
    private void closeInspection() {
        inspectionPanel.setVisible(false);
        handPanel.setEnabled(true);
        handPanel.setVisible(true);
    }
    
    public void startGameSetup() {
        System.out.println("Partida iniciada. Lógica de turno aqui...");
    }
}
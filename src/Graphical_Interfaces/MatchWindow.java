

package Graphical_Interfaces;

import Domain.Card;
import Domain.GameAction;
import Domain.MonsterCard;
import static Graphical_Interfaces.Colors.*;
import java.awt.*;
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
        //Botão Encerrar Partida
        RoundedButton exitButton = new RoundedButton("Encerrar Partida", 15);
        exitButton.setBackground(END_GAME_BUTTON);
        exitButton.setForeground(TEXT_END_GAME_BUTTON);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false);
        topPanel.add(exitButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        
        
        exitButton.addActionListener(e -> {
            // Lógica de sair 
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja encerrar a partida?");
            if (confirm == JOptionPane.YES_OPTION) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (frame != null) frame.dispose();
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

        mockCards.add(new MonsterCard()); 
        mockCards.add(new MonsterCard());
        mockCards.add(new MonsterCard());

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
        
        
        //Adiciona Título e Descrição
        JLabel title = new JLabel(card.getName());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(TEXT_OPTION_BUTTON);

        JTextArea desc = new JTextArea(card.getDescription());
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setOpaque(false);
        desc.setForeground(TEXT_OPTION_BUTTON);

        inspectionPanel.add(Box.createVerticalStrut(20));
        inspectionPanel.add(title);
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
    
    private void closeInspection() {
        inspectionPanel.setVisible(false);
        handPanel.setEnabled(true);
        handPanel.setVisible(true);
    }
    
    public void startGameSetup() {
        System.out.println("Partida iniciada. Lógica de turno aqui...");
    }
}


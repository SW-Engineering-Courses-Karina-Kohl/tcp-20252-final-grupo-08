package utils;

import Domain.Card;
import Domain.DeckFactory;
import Domain.Player;
import Graphical_Interfaces.MatchWindow;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MatchStarter implements GameStartListener {

    private final ArrayList<Object[]> sizes;
    
    // Configurações centralizadas
    private static final String PLAYER_NAME = "Jogador";
    private static final String ENEMY_NAME = "Oponente";
    private static final int INITIAL_HP = 2000;
    private static final int DECK_SIZE = 20;
    private static final String WINDOW_TITLE_MATCH = "A Generic Card Game - Partida";

    public MatchStarter(ArrayList<Object[]> sizes) {
        this.sizes = sizes;
    }

    @Override
    public void onStartGame(int width, int height) {
        SwingUtilities.invokeLater(() -> {
            // 1. Criação dos Decks e Jogadores (Lógica de Domínio)
            List<Card> pDeck = DeckFactory.createRandomDeck(DECK_SIZE);
            Player player = new Player(PLAYER_NAME, INITIAL_HP, pDeck);

            List<Card> eDeck = DeckFactory.createRandomDeck(DECK_SIZE);
            Player enemy = new Player(ENEMY_NAME, INITIAL_HP, eDeck);

            // 2. Criação da Janela (Lógica de UI)
            JFrame gameFrame = createGameFrame(width, height);
            
            // 3. Inicialização da Partida
            MatchWindow gamePanel = new MatchWindow(sizes, player, enemy);
            gameFrame.add(gamePanel);
            gameFrame.setVisible(true);

            gamePanel.startGameSetup();
        });
    }

    private JFrame createGameFrame(int w, int h) {
        JFrame gameFrame = new JFrame(WINDOW_TITLE_MATCH);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setPreferredSize(new Dimension(w, h));
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        return gameFrame;
    }
}
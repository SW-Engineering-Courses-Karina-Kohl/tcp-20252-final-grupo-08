package utils;

import Domain.Card;
import Domain.DeckFactory;
import Domain.GameManager;
import Domain.Player;
import Graphical_Interfaces.MatchWindow;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MatchStarter implements GameStartListener {

    private final ArrayList<Object[]> sizes;
    
    private static final String PLAYER_NAME = "Jogador";
    private static final String ENEMY_NAME = "Oponente";
    private static final int INITIAL_HP = 2000;
    private static final String WINDOW_TITLE_MATCH = "A Generic Card Game - Partida";

    public MatchStarter(ArrayList<Object[]> sizes) {
        this.sizes = sizes;
    }

    @Override
    public void onStartGame(int width, int height) {
        SwingUtilities.invokeLater(() -> {
            //Criação dos Decks e Jogadores
            List<Card> pDeck = DeckFactory.createStarterDeck();
            Player player = new Player(PLAYER_NAME, INITIAL_HP, pDeck);

            List<Card> eDeck = DeckFactory.createStarterDeck();
            Player enemy = new Player(ENEMY_NAME, INITIAL_HP, eDeck);

            //Criação da Janela
            JFrame gameFrame = createGameFrame(width, height);
            
            MatchWindow gamePanel = new MatchWindow(sizes, player, enemy);
            
            //Criação e injeção do GameManager
            GameManager gameManager = new GameManager(player, enemy, gamePanel);
            gamePanel.setGameManager(gameManager);

            gameFrame.add(gamePanel);
            gameFrame.setVisible(true);

            gamePanel.startGameSetup();
            gameManager.startGame();
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
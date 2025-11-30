import Domain.Card;
import Domain.DeckFactory;
import Domain.Player;
import Graphical_Interfaces.HomeMenu_GraphicWindow;
import Graphical_Interfaces.MatchWindow;
import Sounds.GlobalMusic;
import Sounds.MusicPlayer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import utils.EnumerateScales;
import utils.EnumerateSounds;
import utils.GameStartListener;

public class A_Generic_Card_Game {
    public static void main(String[] args) {
        int width = 640;
        int height = 480;
        int actualSound = 4;
        int actualScale = 0;

        EnumerateSounds[] sounds = EnumerateSounds.values();
        EnumerateScales[] scale = EnumerateScales.values();

        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[] {width, height, scale[actualScale].getLabel(), sounds[actualSound].getLabel()});

        GameStartListener starter = (w, h) -> {
            SwingUtilities.invokeLater(() -> {
                
                List<Card> playerDeck = DeckFactory.createRandomDeck(20);
                Player player = new Player("Hero", 2000, playerDeck);
                
                List<Card> enemyDeck = DeckFactory.createRandomDeck(20);
                Player enemy = new Player("Enemy", 2000, enemyDeck);

                JFrame gameFrame = new JFrame("A Generic Card Game - Partida");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                MatchWindow gamePanel = new MatchWindow(sizes, player, enemy);
                
                gameFrame.add(gamePanel);
                gameFrame.setSize(w, h);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
                
                gamePanel.startGameSetup();
            });
        };

        GlobalMusic.themeMusic = new MusicPlayer("src/Sounds/ambient-background-2-421085.wav");
        GlobalMusic.themeMusic.setVolume(sounds[actualSound].getValue());
        GlobalMusic.themeMusic.playLoop();


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("A Generic Card Game - Menu Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new HomeMenu_GraphicWindow(sizes, starter)); 
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
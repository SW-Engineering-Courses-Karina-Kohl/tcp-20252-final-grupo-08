import Graphical_Interfaces.GameStartListener;
import Graphical_Interfaces.HomeMenu_GraphicWindow;
import Graphical_Interfaces.MatchWindow;
import Sounds.MusicPlayer;
import java.util.ArrayList;
import javax.swing.*;
import utils.EnummerateSounds;

public class A_Generic_Card_Game {
    public static void main(String[] args) {
        int width = 640;
        int height = 480;
        int actualSound = 4;

        EnummerateSounds[] sounds = EnummerateSounds.values();

        String actualScale = new String("640p");

        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[] {width, height, actualScale, sounds[actualSound].getLabel()});

        GameStartListener starter = (w, h) -> {
            SwingUtilities.invokeLater(() -> {
                JFrame gameFrame = new JFrame("A Generic Card Game - Match");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                
                MatchWindow gamePanel = new MatchWindow(w, h);
                
                gameFrame.add(gamePanel);
                gameFrame.setSize(w, h);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
                
                gamePanel.startGameSetup();
            });
        };
        MusicPlayer player = new MusicPlayer("src/Sounds/ambient-background-2-421085.wav");
        player.setVolume(sounds[actualSound].getValue());
        player.playLoop();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Square com Container Interno");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new HomeMenu_GraphicWindow(sizes, starter)); //Alterado para integração com MatchWindow

            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}
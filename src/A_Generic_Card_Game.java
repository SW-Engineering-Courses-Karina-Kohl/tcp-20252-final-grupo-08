import Graphical_Interfaces.HomeMenu_GraphicWindow;
import Sounds.GlobalMusic;
import Sounds.MusicPlayer;
import java.util.ArrayList;
import javax.swing.*;
import utils.EnumerateScales;
import utils.EnumerateSounds;
import utils.MatchStarter; 

public class A_Generic_Card_Game {
    public static void main(String[] args) {
        int width = 1024;
        int height = 768;
        int actualSound = 4;
        int actualScale = 0;

        EnumerateSounds[] sounds = EnumerateSounds.values();
        EnumerateScales[] scale = EnumerateScales.values();

        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[] {width, height, scale[actualScale].getLabel(), sounds[actualSound].getLabel()});

        
        MatchStarter starter = new MatchStarter(sizes); 

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
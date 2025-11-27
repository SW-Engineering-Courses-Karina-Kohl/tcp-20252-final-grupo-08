import Graphical_Interfaces.HomeMenu_GraphicWindow;
import Sounds.MusicPlayer;
import utils.EnummerateSounds;

import javax.swing.*;
import java.util.ArrayList;

public class A_Generic_Card_Game {
    public static void main(String[] args) {
        int width = 640;
        int height = 480;
        int actualSound = 4;

        EnummerateSounds[] sounds = EnummerateSounds.values();

        String actualScale = new String("640p");

        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[] {width, height, actualScale, sounds[actualSound].getLabel()});

        MusicPlayer player = new MusicPlayer("src/Sounds/ambient-background-2-421085.wav");
        player.setVolume(sounds[actualSound].getValue());
        player.playLoop();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Square com Container Interno");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new HomeMenu_GraphicWindow(sizes));

            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}
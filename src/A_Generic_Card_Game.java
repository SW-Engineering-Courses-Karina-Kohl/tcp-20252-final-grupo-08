import Graphical_Interfaces.HomeMenu_GraphicWindow;

import javax.swing.*;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class A_Generic_Card_Game {
    public static void main(String[] args) {
        int width = 640;
        int height = 480;
        String actualScale = new String("640p");
        String actualSound = new String("100%");

        ArrayList<Object[]> sizes = new ArrayList<>();
        sizes.add(new Object[] {width, height, actualScale, actualSound});

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
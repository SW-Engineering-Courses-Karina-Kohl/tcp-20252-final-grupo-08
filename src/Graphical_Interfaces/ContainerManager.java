package Graphical_Interfaces;

import javax.swing.*;
import java.awt.*;

public class ContainerManager {

    private final JPanel container;

    public ContainerManager(JPanel container) {
        this.container = container;
    }

    public void ajust(int parentWidth, int parentHeight) {

        int containerWidth = (int) (parentWidth * 0.85);
        int containerHeight = (int) (parentHeight * 0.85);

        int x = (parentWidth - containerWidth) / 2;
        int y = (int) ((parentHeight - containerHeight) * 1.4);

        container.setBounds(x, y, containerWidth, containerHeight);
    }
}

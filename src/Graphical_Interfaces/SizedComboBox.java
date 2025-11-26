package Graphical_Interfaces;

import javax.swing.*;
import java.awt.*;

public class SizedComboBox<E> extends JComboBox<E> {

    private final int width, height;

    public SizedComboBox(E[] items, int width, int height) {
        super(items);
        this.width = width;
        this.height = height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}

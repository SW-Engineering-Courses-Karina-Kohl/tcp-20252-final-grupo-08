package Graphical_Interfaces;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;

        // Permite o Swing desenhar apenas o texto
        setContentAreaFilled(false);

        // Remove efeitos padrão
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cor de fundo
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Se quiser efeito ao clicar:
        if (getModel().isPressed()) {
            g2.setColor(new Color(0, 0, 0, 50)); // escurece um pouco
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }

        // Se quiser efeito hover:
        else if (getModel().isRollover()) {
            g2.setColor(new Color(255, 255, 255, 40)); // ilumina um pouco
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }

        g2.dispose();

        // Agora sim desenha texto / ícone por cima
        super.paintComponent(g);
    }

    public static void setButtonSize(JButton btn, int w, int h) {
        Dimension d = new Dimension(w, h);
        btn.setPreferredSize(d);
        btn.setMinimumSize(d);
        btn.setMaximumSize(d);
    }


    @Override
    protected void paintBorder(Graphics g) {
        // Se quiser borda:
        // Graphics2D g2 = (Graphics2D) g.create();
        // g2.setColor(Color.WHITE);
        // g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        // g2.dispose();
        // No momento: sem bordas
    }

}


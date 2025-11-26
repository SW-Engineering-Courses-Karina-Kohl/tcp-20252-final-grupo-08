package Interfaces_Graficas;

import javax.swing.*;
import java.awt.*;

public class MenuInicial_JanelaGrafica extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Obtém a largura e altura disponíveis
        int largura = getWidth();
        int altura = getHeight();

        // Tamanho do quadrado = menor lado da janela
        int lado = Math.min(largura, altura);

        // Calcula posição para centralizar
        int x = (largura - lado) / 2;
        int y = (altura - lado) / 2;

        // Desenha o quadrado
        g.setColor(Color.BLUE);
        g.fillRect(x, y, lado, lado);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quadrado Responsivo Centraliado");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MenuInicial_JanelaGrafica tela = new MenuInicial_JanelaGrafica();
            frame.add(tela);

            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null); // Centraliza a janela na tela
            frame.setVisible(true);
        });
    }
}

package utils;

@FunctionalInterface
public interface GameStartListener {
    /**
     * Chamado quando o botão de iniciar partida é clicado.
     * @param width A largura da janela de origem, a ser usada na nova janela.
     * @param height A altura da janela de origem, a ser usada na nova janela.
     */
    void onStartGame(int width, int height);
}
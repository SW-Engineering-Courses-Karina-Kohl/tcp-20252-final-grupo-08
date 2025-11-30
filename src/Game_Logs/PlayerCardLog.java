public class CardLog {
    private static final Logger logger = LogManager.getLogger(CardLog.class);

    public static void cardPlayed(String cardName) {
        logger.info("Jogador jogou a carta {}", cardName);
    }

    public static void InvalidCard(String cardName) {
        logger.warn("Tentativa inválida: Jogador tentou jogar a carta {}", carta);
    }

    public static void efeitoErro(String carta, Exception e) {
        logger.error("Erro ao ativar efeito da carta {}", carta, e);
    }
}

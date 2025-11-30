public class CardLog {
    private static final Logger logger = LogManager.getLogger(CardLog.class);

    public static void cardPlayed(String player, String cardName) {
        logger.info("{} jogou a carta {}", player, cardName);
    }

    public static void InvalidCard(String player, String cardName) {
        logger.warn("Tentativa inválida: {} tentou jogar a carta {}", player, cardName);
    }

    public static void cardError(String cardName, Exception e) {
        logger.error("Erro ao ativar efeito da carta {}", cardName, e);
    }
}

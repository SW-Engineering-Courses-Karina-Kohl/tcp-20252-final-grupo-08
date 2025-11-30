public class MatchLog {
  private static final Logger logger = LogManager.getLogger(MatchLog.class);

  public static void matchStart() {
    logger.info("A partida foi iniciada");
  }

  public static void matchBreak() {
    logger.warn("A partida foi interrompida");
  }

  public static void matchWin() {
    logger.info("O jogador venceu a partida!");
  }

  public static void matchLoss() {
    logger.info("O jogador perdeu a partida.");
  }
}

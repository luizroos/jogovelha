package jogovelha.jogo;

/**
 * Definição de um listener para escutar eventos do jogo da velha
 * 
 */
public interface JogoVelhaEventListener {

  /**
   * Chamado qdo o jogo é iniciado
   */
  void jogoInicio(TabuleiroLeitura tabuleiro);

  /**
   * Chamado qdo um o jogo é finalizado
   */
  void jogoFinal(TabuleiroLeitura tabuleiroLeitura);

  /**
   * Chamado todo inicio de rodada
   */
  void rodadaInicio(TabuleiroLeitura tabuleiro, int rodada, JogadorTabuleiro jogadorVez);

  /**
   * Chamado qdo uma jogada foi realizada
   */
  void jogadaRealizada(TabuleiroLeitura tabuleiro, Jogada jogada, int rodada, JogadorTabuleiro jogadorVez);

  /**
   * Chamado toda vez que o jogador escolhe um local já marcado
   */
  void jogadaJaRealizada(TabuleiroLeitura tabuleiroLeitura, Jogada jogada, int rodada, JogadorTabuleiro jogadorVez);

  /**
   * Chamada qdo o jogo decidiu pelo jogador, seja pq as respostas do jogador eram erradas ou deu
   */
  void decisaoAutomaticaRealizada(int rodada, JogadorTabuleiro jogadorVez, Exception e);

}

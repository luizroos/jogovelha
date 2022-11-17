package jogovelha.jogo;

import java.util.List;

/**
 * Representação de {@link Tabuleiro} que não permite fazer ou desfazer jogadas.
 */
public interface TabuleiroLeitura {

  /**
   * Faz uma copia desse tabuleiro, com o mesmo estado mas sem o histórico de jogadas. As modificações
   * no tabuleiro copiado não são refletidas no tabuleiro original. Use se vc quiser simular jogadas
   * no tabuleiro
   */
  Tabuleiro copiarTabuleiro();

  /**
   * Retorna o jogador da vez
   */
  JogadorTabuleiro getJogadorDaVez();

  /**
   * Obtem o jogador que jogou em determina linha e coluna
   */
  JogadorTabuleiro getJogadorQueJogou(int linha, int coluna);

  /**
   * Obtem o jogador que jogou determinada jogada
   */
  JogadorTabuleiro getJogadorQueJogou(Jogada jogada);

  /**
   * Obtem a lista de jogadas disponiveis
   */
  List<Jogada> getJogadasDisponiveis();

  /**
   * Obtem a lista de jogadas na ordem que foram realizadas
   */
  List<Jogada> getJogadasRealizadas();

  /**
   * Verifica se o tabuleiro esta completo (ou seja, sem mais jogadas disponiveis)
   */
  boolean verificaTabuleiroCompleto();

  /**
   * Verifica se a jogada está disponivel para ser feita
   */
  boolean verificaJogadaDisponivel(Jogada jogada);

  /**
   * Verifica se a jogada está disponivel para ser feita
   */
  boolean verificaJogadaDisponivel(int linha, int coluna);

  /**
   * Exibe o tabuleiro na saida do console
   */
  void exibirTabuleiro();

  /**
   * Veja comentário em {@link Tabuleiro#getVencedor()}}
   */
  JogadorTabuleiro getVencedor();

  /**
   * Retorna os jogadores desse tabuleiro
   */
  JogadorTabuleiro[] getJogadores();

}

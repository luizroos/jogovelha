package jogovelha.jogadores.minmax;

import jogovelha.jogo.Jogador;
import jogovelha.jogo.Tabuleiro;

public interface Heuristica {

  /**
   * Deve avaliar o tabuleiro para um determinado jogador e avaliar se está bom (pontos positivos), ou
   * ruim (pontos negativos) ou null (se não ta ok para tomar uma decisão).
   */
  Integer avaliar(Jogador jogador, Tabuleiro tabuleiro, int profundidadeAnalise);

  /**
   * As vezes, as pontuações por caminhos distintos podem ter pesos iguais, nesses casos, esse método
   * é chamada para desempatar, deve retornar true se a pontuacao1 for melhor que a pontuacao2, false no contrario
   */
  boolean desempata(PontuacaoMinMax pontuacao1, PontuacaoMinMax pontuacao2);

}

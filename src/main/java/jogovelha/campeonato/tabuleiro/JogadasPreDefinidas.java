package jogovelha.campeonato.tabuleiro;

import java.util.List;

import jogovelha.jogo.Jogada;

/**
 * Interface que define jogadas que devem ser pré configurados em um tabuleiro de uma rodada de um
 * campeonato. Serve para podermos criar campeonatos mais complexos, para testar melhor jogadores em
 * situações especificas do tabuleiro.
 */
public interface JogadasPreDefinidas {

  /**
   * Retorna as jogadas que devem ser aplicadas (na ordem) no tabuleiro da rodada. Começando pela
   * jogada do X depois do O e assim por diante.
   */
  List<Jogada> getJogadas();

}

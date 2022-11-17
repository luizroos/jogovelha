package jogovelha.jogadores.minmax;

import java.util.List;
import java.util.Objects;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.Tabuleiro;
import jogovelha.jogo.TabuleiroLeitura;

/**
 * Implementa o algoritmo de min max
 */
public class AlgoritmoMinMax {

  private final Heuristica heuristica;

  private final Jogador jogador;

  AlgoritmoMinMax(Heuristica heuristica, Jogador jogador) {
    this.jogador = Objects.requireNonNull(jogador);
    this.heuristica = Objects.requireNonNull(heuristica);
  }

  public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
    // faz uma copia do tabuleiro para poder testar as jogadas
    final Tabuleiro copiaTabuleiro = tabuleiro.copiarTabuleiro();

    // avalio cada jogada disponivel procurando a melhor
    Jogada melhorJogada = null;
    PontuacaoMinMax melhorPontuacao = null;
    for (Jogada jogada : jogadasDisponiveis) {

      // realiza a jogada no tabuleiro copiado
      copiaTabuleiro.realizarJogada(jogada);

      // avalia a jogada usando o algoritmo de min max (será a melhor pontuacao para mim da melhor jogada
      // do meu oponente)
      final PontuacaoMinMax pontuacao = minMax(copiaTabuleiro, 1);

      // desfaz a jogada no tabuleiro, para poder testar a proxima
      copiaTabuleiro.desfazUltimaJogada();

      // compara a pontuacao da jogada com melhor pontuacao até agora
      if (pontuacao.melhorQue(melhorPontuacao, heuristica)) {
        melhorPontuacao = pontuacao;
        melhorJogada = jogada;
      }
    }
    return melhorJogada;
  }

  private PontuacaoMinMax minMax(Tabuleiro tabuleiro, int profundidadeAnalise) {

    final Integer heuristicaPontuacao = heuristica.avaliar(jogador, tabuleiro, profundidadeAnalise);
    if (heuristicaPontuacao != null) {
      return new PontuacaoMinMax(heuristicaPontuacao, profundidadeAnalise);
    }

    final List<Jogada> jogadasDisponiveis = tabuleiro.getJogadasDisponiveis();
    if (jogadasDisponiveis.size() == 0) {
      throw new RuntimeException("Heuristica tem que dar uma pontuação");
    }
    PontuacaoMinMax melhorPontuacao = null;
    for (Jogada jogada : jogadasDisponiveis) {

      // eu quero o max se o jogador que for fazer a jogada for eu
      // TODO para um jogo que cada jogador joga uma rodada, isso poderia ser uma variavel boolean que vai
      // invertendo valor, mas como penso em usar para jogos que podem ter varias rodadas de um mesmo
      // jogador, vou deixar assim para testar, se não der certo, simplifica usando o parametro booleano)
      final boolean max = tabuleiro.getJogadorDaVez().ehJogador(jogador);

      // faz a jogada no tabuleiro
      tabuleiro.realizarJogada(jogada);

      // avalia a jogada com minmax
      final PontuacaoMinMax pontuacao = minMax(tabuleiro, profundidadeAnalise + 1);

      // desfaz a jogada
      tabuleiro.desfazUltimaJogada();

      if (max) {
        // a melhor jogada para mim (que tem maior valor)
        melhorPontuacao = pontuacao.getMax(melhorPontuacao);
      } else {
        // a melhor jogada do meu oponente (que tem menor valor)
        melhorPontuacao = pontuacao.getMin(melhorPontuacao);
      }
    }
    return melhorPontuacao;
  }

}

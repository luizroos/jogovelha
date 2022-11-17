package jogovelha.jogadores.minmax;

import java.util.Objects;

import jogovelha.jogadores.minmax.PontuacaoMinMax.EstrategiaDesempate;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.Tabuleiro;

/**
 * Implementação da heuristica para se analisar um tabuleiro de jogo da velha.
 */
public class HeuristicaJogoDaVelha implements Heuristica {

  private final int maxProfundidade;

  private final EstrategiaDesempate estrategiaDesempate;

  private final int pontosDerrota;

  private final int pontosVitoria;

  /**
   * @param maxProfundidade profundidade maxima de analise
   * @param estrategiaDesempate criterio de desempetate
   * @param pontosDerrota pontos por derrota
   * @param pontosVitoria pontos por vitoria
   */
  public HeuristicaJogoDaVelha(int maxProfundidade, EstrategiaDesempate estrategiaDesempate, int pontosDerrota,
      int pontosVitoria) {
    this.maxProfundidade = maxProfundidade;
    this.estrategiaDesempate = Objects.requireNonNull(estrategiaDesempate);
    this.pontosDerrota = pontosDerrota;
    this.pontosVitoria = pontosVitoria;
  }

  @Override
  public Integer avaliar(Jogador jogador, Tabuleiro tabuleiro, int profundidadeAnalise) {
    final JogadorTabuleiro vencedor = tabuleiro.getVencedor();
    if (vencedor != null) {
      if (vencedor.ehJogador(jogador)) {
        // ganhei o jogo, melgor jogada
        return pontosVitoria;
      } else {
        // o jogo tem um vencedor e não sou eu, pior jogada
        return pontosDerrota;
      }
    }
    if (tabuleiro.verificaTabuleiroCompleto() || profundidadeAnalise >= maxProfundidade) {
      // empate ou chega de analisar
      return 0;
    }
    return null;
  }

  @Override
  public boolean desempata(PontuacaoMinMax pontuacao1, PontuacaoMinMax pontuacao2) {
    if (pontuacao1.getValor() > 0) {
      switch (estrategiaDesempate) {
        case MAIS_JOGADAS:
          return pontuacao1.getProfundidadeAnalise() > pontuacao2.getProfundidadeAnalise();
        case MENOS_JOGADAS:
          return pontuacao1.getProfundidadeAnalise() < pontuacao2.getProfundidadeAnalise();
        default:
          return false;
      }
    } else if (pontuacao1.getValor() < 0) {
      // para o caso de derrota, perdo logo
      return pontuacao1.getProfundidadeAnalise() < pontuacao2.getProfundidadeAnalise();
    }
    return false;
  }

}

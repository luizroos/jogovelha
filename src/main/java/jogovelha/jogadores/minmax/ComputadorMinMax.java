package jogovelha.jogadores.minmax;

import java.util.List;

import jogovelha.jogadores.minmax.PontuacaoMinMax.EstrategiaDesempate;
import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.TabuleiroLeitura;

/**
 * Implementação min max do computador
 */
public final class ComputadorMinMax implements Jogador {

  private final AlgoritmoMinMax algoritmoMinMax;

  /**
   * @param inteligencia A inteligencia determina quantas jogadas o algoritmo min max vai avaliar no
   *        maximo, quanto maior, mais inteligente porém mais lento
   * @param estrategiaDesempate Caso tenhamos 2 pontuações iguais qual será a prioridade para
   *        desempate?
   */
  public ComputadorMinMax(int inteligencia, EstrategiaDesempate estrategiaDesempate) {
    if (inteligencia < 0) {
      throw new IllegalArgumentException("Inteligencia do computador deve ser maior ou igual a zero");
    }
    final HeuristicaJogoDaVelha heuristica = new HeuristicaJogoDaVelha(inteligencia, estrategiaDesempate, -10, 10);
    this.algoritmoMinMax = new AlgoritmoMinMax(heuristica, this);
  }

  @Override
  public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
    return algoritmoMinMax.decidirJogada(tabuleiro, jogadasDisponiveis);
  }

}

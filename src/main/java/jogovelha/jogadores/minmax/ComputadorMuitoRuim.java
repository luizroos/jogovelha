package jogovelha.jogadores.minmax;

import java.util.List;

import jogovelha.jogadores.minmax.PontuacaoMinMax.EstrategiaDesempate;
import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.TabuleiroLeitura;

/**
 * É um computador min max que configura a heuristica do jogo da velha para para priorizar derrota.
 */
public class ComputadorMuitoRuim implements Jogador {

  private AlgoritmoMinMax algoritmoMinMax;

  /**
   * Cria o Jogador
   */
  public ComputadorMuitoRuim() {
    final HeuristicaJogoDaVelha heuristica =
        new HeuristicaJogoDaVelha(Integer.MAX_VALUE, EstrategiaDesempate.MENOS_JOGADAS, 10, -10);
    this.algoritmoMinMax = new AlgoritmoMinMax(heuristica, this);
  }

  /**
   * Cria o Jogador, porém a heuristica sera configurada para um outro jogador. Isso permite esse
   * jogador ser usado por outros jogadores para decidir por eles.
   */
  public ComputadorMuitoRuim(Jogador jogadorSimulado) {
    final HeuristicaJogoDaVelha heuristica =
        new HeuristicaJogoDaVelha(Integer.MAX_VALUE, EstrategiaDesempate.MENOS_JOGADAS, 10, -10);
    this.algoritmoMinMax = new AlgoritmoMinMax(heuristica, jogadorSimulado);
  }

  @Override
  public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
    return algoritmoMinMax.decidirJogada(tabuleiro, jogadasDisponiveis);
  }

}

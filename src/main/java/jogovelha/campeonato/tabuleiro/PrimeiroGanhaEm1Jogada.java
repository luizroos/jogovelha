package jogovelha.campeonato.tabuleiro;

import java.util.ArrayList;
import java.util.List;

import jogovelha.jogadores.minmax.ComputadorMinMax;
import jogovelha.jogadores.minmax.PontuacaoMinMax.EstrategiaDesempate;
import jogovelha.jogadores.simples.ComputadorOpcaoAleatoria;
import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.JogoVelha;
import jogovelha.jogo.Tabuleiro;

/**
 * Cria um cenario aleatorio onde o primeiro jogador só tem que fazer uma jogada para ganhar
 */
public class PrimeiroGanhaEm1Jogada implements JogadasPreDefinidas {

  private static final List<Jogada> TODAS_JOGADAS = new ArrayList<>();
  static {
    for (int linha = 1; linha <= 3; linha++) {
      for (int coluna = 1; coluna <= 3; coluna++) {
        TODAS_JOGADAS.add(new Jogada(linha, coluna));
      }
    }
  }

  @Override
  public List<Jogada> getJogadas() {

    // usa o jogador aleatorio contra o min max para criar um cenario de vitoria, deixando ainda algumas
    // opções de jogadas para os jogadores decidir
    Tabuleiro tabuleiro;
    JogadorTabuleiro vencedor;
    final Jogador minMax = new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.TANTO_FAZ);
    final Jogador aleatorio = new ComputadorOpcaoAleatoria();
    do {

      tabuleiro = new Tabuleiro(minMax, aleatorio);
      // realiza as duas primeiras jogadas aleatorias, senão o min max sempre começa na mesma
      tabuleiro.realizarJogada(sorteia(TODAS_JOGADAS));
      tabuleiro.realizarJogada(sorteia(TODAS_JOGADAS));

      vencedor = new JogoVelha(tabuleiro).executar();
    } while (vencedor == null || !vencedor.ehJogador(minMax) || tabuleiro.getJogadasRealizadas().size() > 5);

    // teve um vencedor
    tabuleiro.desfazUltimaJogada();

    return tabuleiro.getJogadasRealizadas();
  }

  private static Jogada sorteia(List<Jogada> jogadas) {
    final int idx = (int) (Math.random() * jogadas.size());
    return jogadas.get(idx);
  }
}

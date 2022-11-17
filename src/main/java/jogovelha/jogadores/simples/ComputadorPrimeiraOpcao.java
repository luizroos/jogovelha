package jogovelha.jogadores.simples;

import java.util.List;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.TabuleiroLeitura;

/**
 * Computador que sempre manda jogar a primeira jogada disponivel
 */
public class ComputadorPrimeiraOpcao implements Jogador {

  @Override
  public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
    return jogadasDisponiveis.get(0);
  }

}

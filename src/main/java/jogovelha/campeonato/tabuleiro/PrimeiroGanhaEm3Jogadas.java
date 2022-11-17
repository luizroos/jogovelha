package jogovelha.campeonato.tabuleiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jogovelha.jogo.Jogada;

/**
 * Cria um cenario aleatorio onde o primeiro jogador pode ganhar em 3 jogadas
 */
public class PrimeiroGanhaEm3Jogadas implements JogadasPreDefinidas {

  private static final List<Jogada> QUINAS = new ArrayList<>();

  static {
    QUINAS.add(new Jogada(1, 1));
    QUINAS.add(new Jogada(3, 1));
    QUINAS.add(new Jogada(1, 3));
    QUINAS.add(new Jogada(3, 3));
  }

  @Override
  public List<Jogada> getJogadas() {

    final Jogada primeiraJogada = sorteia(QUINAS);

    // não pode ser na igual a primeira opcao, nem pode ser o meio
    final List<Jogada> opcaoSegundaJogada = new ArrayList<>();
    for (int linha = 1; linha <= 3; linha++) {
      for (int coluna = 1; coluna <= 3; coluna++) {
        if ((coluna != 2 || linha != 2) //
            && (primeiraJogada.getColuna() != coluna || primeiraJogada.getLinha() != linha)) {
          opcaoSegundaJogada.add(new Jogada(linha, coluna));
        }
      }
    }

    final Jogada segundaJogada = sorteia(opcaoSegundaJogada);

    return Arrays.asList(primeiraJogada, segundaJogada);
  }

  private static Jogada sorteia(List<Jogada> jogadas) {
    final int idx = (int) (Math.random() * jogadas.size());
    return jogadas.get(idx);
  }

}

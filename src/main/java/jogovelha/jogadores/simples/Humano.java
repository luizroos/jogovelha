package jogovelha.jogadores.simples;

import java.util.List;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.TabuleiroLeitura;
import jogovelha.ui.jogo.JogoUI;

public class Humano implements Jogador {

  private final JogoUI ui;

  public Humano(JogoUI gui) {
    this.ui = gui;;
  }

  @Override
  public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
    return ui.perguntaJogada();
  }

}

package jogovelha.jogo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jogovelha.jogadores.minmax.ComputadorMuitoRuim;

/**
 * Executa o jogo da velha em um tabuleiro configurado
 */
public final class JogoVelha {

  private final Tabuleiro tabuleiro;

  private final TabuleiroNotifica tabuleiroNotifica;

  private final Set<JogoVelhaEventListener> listeners;

  private int rodada = 1;

  public JogoVelha(Tabuleiro tabuleiro, JogoVelhaEventListener listener) {
    this.tabuleiro = Objects.requireNonNull(tabuleiro);
    this.tabuleiroNotifica = new TabuleiroNotifica(tabuleiro);
    this.listeners = listener == null ? Collections.emptySet() : Collections.singleton(listener);
  }

  public JogoVelha(Tabuleiro tabuleiro) {
    this(tabuleiro, null);
  }

  public JogadorTabuleiro executar() {
    // renderiza o tabuleiro
    listeners.forEach(e -> e.jogoInicio(tabuleiroNotifica));
    while (temOpcaoJogo());
    listeners.forEach(e -> e.jogoFinal(tabuleiroNotifica));
    return tabuleiro.getVencedor();
  }

  private boolean temOpcaoJogo() {
    final JogadorTabuleiro jogadorVez = tabuleiro.getJogadorDaVez();
    listeners.forEach(e -> e.rodadaInicio(tabuleiroNotifica, rodada, jogadorVez));

    boolean jogadaOk;
    int tentativasJogador = 0;
    Jogada jogada;
    do {
      final List<Jogada> jogadasDisponiveis = tabuleiro.getJogadasDisponiveis();
      if (jogadasDisponiveis.size() == 1) {
        // se não tiver mais jogadas disponiveis, joga a unica possivel
        jogada = jogadasDisponiveis.get(0);
      } else if (tentativasJogador >= 5) {
        // na quinta tentativa, escolhe um jogada ruim pelo jogador pq ele não ta sabendo escolher uma
        // válida
        jogada = new ComputadorMuitoRuim(jogadorVez.getJogador()).decidirJogada(tabuleiroNotifica, jogadasDisponiveis);
        // System.out.printf("ATENCAO %s: Jogada %s realizada de forma automatica para o jogador %s,
        // tabuleiro: \n", jogadorVez.getJogador().getClass(), jogada, jogadorVez.getSimbolo());
        // tabuleiro.exibirTabuleiro();
        listeners.forEach(e -> e.decisaoAutomaticaRealizada(rodada, jogadorVez, null));
      } else {
        // deixa o jogador decidir
        try {
          jogada = jogadorVez.getJogador().decidirJogada(tabuleiroNotifica, tabuleiro.getJogadasDisponiveis());
        } catch (Exception e) {
          jogada = null;
        }
      }
      tentativasJogador++;
      jogadaOk = jogada != null && tabuleiro.realizarJogada(jogada);
      if (!jogadaOk) {
        for (JogoVelhaEventListener listener : listeners) {
          listener.jogadaJaRealizada(tabuleiroNotifica, jogada, rodada, jogadorVez);
        }
      }
    } while (!jogadaOk);

    for (JogoVelhaEventListener l : listeners) {
      l.jogadaRealizada(tabuleiroNotifica, jogada, rodada, jogadorVez);
    }

    final JogadorTabuleiro vencedor = tabuleiro.getVencedor();
    if (vencedor != null) {
      return false;
    } else if (tabuleiro.verificaTabuleiroCompleto()) {
      return false;
    }
    rodada++;
    return true;
  }

}

package jogovelha.jogo;

import java.util.List;

/**
 * Um tabuleiro de leitura que não permite casting para {@link Tabuleiro}, só para não notificarmos
 * para fora do jogo um tabuleiro onde algum jogador pode tentar um casting e atrapalhar o jogo
 */
public final class TabuleiroNotifica implements TabuleiroLeitura {

  private final Tabuleiro tabuleiro;

  TabuleiroNotifica(Tabuleiro tabuleiro) {
    this.tabuleiro = tabuleiro;
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#copiarTabuleiro()}}
   */
  public Tabuleiro copiarTabuleiro() {
    return tabuleiro.copiarTabuleiro();
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#getJogadorDaVez()}}
   */
  public JogadorTabuleiro getJogadorDaVez() {
    return tabuleiro.getJogadorDaVez();
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#getJogadorQueJogou(int, int)}
   */
  public JogadorTabuleiro getJogadorQueJogou(int linha, int coluna) {
    return tabuleiro.getJogadorQueJogou(linha, coluna);
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#getJogadorQueJogou(Jogada)}
   */
  public JogadorTabuleiro getJogadorQueJogou(Jogada jogada) {
    return tabuleiro.getJogadorQueJogou(jogada);
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#getJogadasDisponiveis()}}
   */
  public List<Jogada> getJogadasDisponiveis() {
    return tabuleiro.getJogadasDisponiveis();
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#getJogadasRealizadas()}}
   */
  @Override
  public List<Jogada> getJogadasRealizadas() {
    return tabuleiro.getJogadasRealizadas();
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#verificaTabuleiroCompleto()}}
   */
  public boolean verificaTabuleiroCompleto() {
    return tabuleiro.verificaTabuleiroCompleto();
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#verificaJogadaDisponivel(Jogada)}}
   */
  public boolean verificaJogadaDisponivel(Jogada jogada) {
    return tabuleiro.verificaJogadaDisponivel(jogada);
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#verificaJogadaDisponivel()}}
   */
  public boolean verificaJogadaDisponivel(int linha, int coluna) {
    return tabuleiro.verificaJogadaDisponivel(linha, coluna);
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#exibirTabuleiro()}}
   */
  public void exibirTabuleiro() {
    tabuleiro.exibirTabuleiro();
  }

  /**
   * Veja comentário em {@link TabuleiroLeitura#getVencedor()}}
   */
  public JogadorTabuleiro getVencedor() {
    return tabuleiro.getVencedor();
  }

  @Override
  public JogadorTabuleiro[] getJogadores() {
    return tabuleiro.getJogadores();
  }
}

package jogovelha.jogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * Representa o tabuleiro do jogo da velha
 */
public class Tabuleiro implements TabuleiroLeitura {

  public static final int TAMANHO_TABULEIRO = 3;

  // o tabuleiro é identificado por uma matriz onde o valor é o jogador que jogou
  // naquela posição
  private final JogadorTabuleiro[][] tabuleiro;

  // mantemos uma pilha de jogadas para o desfaz
  private final Stack<Jogada> jogadas;

  // os jogadores são representados por um array de 2 posições, onde a posição 0 é
  // do jogador X e a posição 1 é do jogador O
  private JogadorTabuleiro[] jogadores;

  // a vez par é do jogador X e a vez impar é do jogador 2
  private int vez = 0;

  public Tabuleiro(Jogador jogador1, Jogador jogador2) {
    this.jogadores = new JogadorTabuleiro[2];
    this.jogadores[0] = new JogadorTabuleiro(jogador1, "X");
    this.jogadores[1] = new JogadorTabuleiro(jogador2, "O");
    this.vez = 0;
    this.jogadas = new Stack<>();
    this.tabuleiro = new JogadorTabuleiro[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
    for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++) {
      for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++) {
        tabuleiro[linha][coluna] = null;
      }
    }
  }

  private Tabuleiro(JogadorTabuleiro[][] tabuleiro, JogadorTabuleiro[] jogadores, int vez) {
    this.jogadores = Objects.requireNonNull(jogadores);
    this.vez = vez;
    this.tabuleiro = Objects.requireNonNull(tabuleiro);
    this.jogadas = new Stack<>();
  }

  @Override
  public Tabuleiro copiarTabuleiro() {
    return new Tabuleiro(cloneMatriz(tabuleiro), jogadores, vez);
  }

  @Override
  public JogadorTabuleiro getJogadorDaVez() {
    return jogadores[vez % 2];
  }

  @Override
  public List<Jogada> getJogadasDisponiveis() {
    final List<Jogada> jogadasDisponiveis = new ArrayList<>();
    for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++) {
      for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++) {
        if (tabuleiro[linha][coluna] == null) {
          jogadasDisponiveis.add(new Jogada(linha + 1, coluna + 1));
        }
      }
    }
    return Collections.unmodifiableList(jogadasDisponiveis);
  }

  @Override
  public List<Jogada> getJogadasRealizadas() {
    final List<Jogada> jogadasRealizadas = new ArrayList<>(jogadas);
    return Collections.unmodifiableList(jogadasRealizadas);
  }

  @Override
  public boolean verificaTabuleiroCompleto() {
    return getJogadasDisponiveis().size() == 0;
  }

  @Override
  public JogadorTabuleiro getJogadorQueJogou(int linha, int coluna) {
    if (linha < 1 || linha > 3 || coluna < 1 || coluna > 3) {
      return null;
    }
    return tabuleiro[linha - 1][coluna - 1];
  }

  @Override
  public JogadorTabuleiro getJogadorQueJogou(Jogada jogada) {
    return getJogadorQueJogou(jogada.getLinha(), jogada.getColuna());
  }

  @Override
  public boolean verificaJogadaDisponivel(Jogada jogada) {
    return getJogadorQueJogou(jogada) == null;
  }

  @Override
  public boolean verificaJogadaDisponivel(int linha, int coluna) {
    return getJogadorQueJogou(linha, coluna) == null;
  }

  @Override
  public JogadorTabuleiro[] getJogadores() {
    return jogadores;
  }

  /**
   * Executa a jogada no tabuleiro para o jogador da vez
   */
  public boolean realizarJogada(Jogada jogada) {
    // confirma que a jogada não foi feito ainda
    if (!verificaJogadaDisponivel(jogada)) {
      return false;
    }
    // marca no tabuleiro o dono da jogada
    tabuleiro[jogada.getLinha() - 1][jogada.getColuna() - 1] = getJogadorDaVez();

    // empilha a jogada
    jogadas.push(jogada);

    // avança a vez
    vez++;

    return true;
  }

  /**
   * Desfaz a ultima jogada
   */
  public boolean desfazUltimaJogada() {
    if (jogadas.size() == 0) {
      return false;
    }
    // pega a ultima jogada feita
    final Jogada ultimaJogada = jogadas.pop();

    // desfaz a jogada
    tabuleiro[ultimaJogada.getLinha() - 1][ultimaJogada.getColuna() - 1] = null;

    // volta a vez
    vez--;

    return true;
  }

  /**
   * Analisa o tabuleiro e retorna o jogador vencedor. Se o tabuleiro não tiver nenhum vencedor,
   * retorna null.
   */
  public JogadorTabuleiro getVencedor() {
    // checa se alguma linha tem vencedor
    for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++) {
      if (tabuleiro[linha][0] != null //
          && tabuleiro[linha][0] == tabuleiro[linha][1] //
          && tabuleiro[linha][1] == tabuleiro[linha][2]) {
        return tabuleiro[linha][0];
      }
    }

    // checa se alguma coluna tem vencedor
    for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++) {
      if (tabuleiro[0][coluna] != null //
          && tabuleiro[0][coluna] == tabuleiro[1][coluna] //
          && tabuleiro[1][coluna] == tabuleiro[2][coluna]) {
        return tabuleiro[0][coluna];
      }
    }

    // checa se alguma diagonal tem vencedor
    if (tabuleiro[0][0] != null && tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[0][0] == tabuleiro[2][2]) {
      return tabuleiro[0][0];
    } else if (tabuleiro[0][2] != null && tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[0][2] == tabuleiro[2][0]) {
      return tabuleiro[0][2];
    }

    return null;
  }

  @Override
  public void exibirTabuleiro() {
    System.out.println(toString());
  }

  /**
   * Retorna a ultima jogada que foi feita
   */
  private Jogada getUltimaJogada() {
    if (jogadas.size() > 0) {
      return jogadas.peek();
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    final Jogada ultimaJogada = getUltimaJogada();
    final StringBuilder str = new StringBuilder();
    str.append(System.lineSeparator());
    for (int linha = 1; linha <= Tabuleiro.TAMANHO_TABULEIRO; linha++) {

      for (int coluna = 1; coluna <= Tabuleiro.TAMANHO_TABULEIRO; coluna++) {
        final JogadorTabuleiro jogador = getJogadorQueJogou(linha, coluna);
        if (jogador == null) {
          str.append(" _ ");
        } else {
          if (ultimaJogada != null && ultimaJogada.getLinha() == linha && ultimaJogada.getColuna() == coluna) {
            str.append(String.format("(%s)", jogador.getSimbolo()));
          } else {
            str.append(String.format(" %s ", jogador.getSimbolo()));
          }
        }

        if (coluna == 1 || coluna == 2) {
          str.append("|");
        }
      }
      str.append(System.lineSeparator());
    }
    return str.toString();
  }

  private static JogadorTabuleiro[][] cloneMatriz(JogadorTabuleiro[][] origem) {
    if (origem == null) {
      return null;
    }
    final JogadorTabuleiro[][] copia = new JogadorTabuleiro[origem.length][];
    for (int r = 0; r < origem.length; r++) {
      copia[r] = origem[r].clone();
    }
    return copia;
  }

}

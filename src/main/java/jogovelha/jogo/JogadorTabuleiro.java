package jogovelha.jogo;

import java.util.Objects;

/**
 * Representação do jogador no tabuleiro, basicamente é um jogador que possui um simbolo (X ou O).
 */
public final class JogadorTabuleiro {

  private final Jogador jogador;

  private final String simbolo;

  JogadorTabuleiro(Jogador jogador, String simbolo) {
    this.jogador = Objects.requireNonNull(jogador);
    this.simbolo = Objects.requireNonNull(simbolo);
  }

  /**
   * Retorna o jogador
   */
  public Jogador getJogador() {
    return jogador;
  }

  /**
   * Retorna o simbolo desse jogador
   */
  public String getSimbolo() {
    return simbolo;
  }

  /**
   * Compara se o {@link Jogador} informado é o mesmo jogador representado por essa classe
   */
  public boolean ehJogador(Jogador jogador) {
    return getJogador() == jogador;
  }

}

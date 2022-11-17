package jogovelha.campeonato;

import java.util.List;
import java.util.Objects;

import jogovelha.campeonato.tabuleiro.JogadasPreDefinidas;
import jogovelha.jogo.Jogada;

/**
 * Representa uma rodada do campeonato
 */
public class Rodada {

  private final int numero;

  private final List<Partida> partidas;

  private final List<Jogada> jogadasPreDefinidas;

  Rodada(int numero, List<Partida> partidas, JogadasPreDefinidas jogadasPreDefinidas) {
    this.numero = numero;
    this.partidas = Objects.requireNonNull(partidas);
    this.jogadasPreDefinidas = jogadasPreDefinidas.getJogadas();
  }

  public List<Partida> getPartidas() {
    return partidas;
  }

  public int getNumero() {
    return numero;
  }

  public List<Jogada> getJogadasPreDefinidas() {
    return jogadasPreDefinidas;
  }

}

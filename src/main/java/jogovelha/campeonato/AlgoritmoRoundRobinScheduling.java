package jogovelha.campeonato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jogovelha.campeonato.tabuleiro.JogadasPreDefinidas;
import jogovelha.jogo.Competidor;

/**
 * Implementa o algoritmo https://tabelas.fiatjaf.com/ para criar as rodadas para criação das
 * rodadas
 */
public class AlgoritmoRoundRobinScheduling {

  public static List<Rodada> criaRodadas(Collection<Competidor> competidores, int numeroJogosPorPartida,
      JogadasPreDefinidas jogadasPreDefinidas, int numeroRodadaInicial) {
    final List<Competidor> competidoresSorteio = new ArrayList<>(competidores);

    if (competidoresSorteio.size() % 2 == 1) {
      // adiciona um competidor para que o numero seja sempre par, o jogador que for jogar contra o
      // jogador falso não joga na rodada
      competidoresSorteio.add(null);
    }
    final int qtdeJogadores = competidoresSorteio.size();

    final Competidor fixo = competidoresSorteio.get(0);
    final List<Competidor> cima = competidoresSorteio.subList(1, qtdeJogadores / 2);
    final List<Competidor> baixo = competidoresSorteio.subList(qtdeJogadores / 2, qtdeJogadores);

    final List<Rodada> rodadas = new ArrayList<>();
    for (int rodada = 0; rodada < qtdeJogadores - 1; rodada++) {

      // monta as partidas da rodada
      final List<Partida> partidas = new ArrayList<>();
      for (int i = 0; i < qtdeJogadores / 2; i++) {
        final Competidor jogador1;
        if (i == 0) {
          jogador1 = fixo;
        } else {
          jogador1 = cima.get(i - 1);
        }
        final Competidor jogador2 = baixo.get(i);
        if (jogador1 != null && jogador2 != null) {
          partidas.add(new Partida(jogador1, jogador2, numeroJogosPorPartida));
        }
      }

      // cria a rodada
      rodadas.add(new Rodada(rodada + numeroRodadaInicial, partidas, jogadasPreDefinidas));

      // faz a rotação horária dos dois arrays
      Collections.rotate(cima, 1);
      Collections.rotate(baixo, -1);
      final Competidor aux = cima.get(0);
      cima.set(0, baixo.get(baixo.size() - 1));
      baixo.set(baixo.size() - 1, aux);

    }
    return rodadas;
  }
}

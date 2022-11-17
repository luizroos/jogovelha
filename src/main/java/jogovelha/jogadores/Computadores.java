package jogovelha.jogadores;

import java.util.ArrayList;
import java.util.List;

import jogovelha.jogadores.minmax.ComputadorMinMax;
import jogovelha.jogadores.minmax.ComputadorMuitoRuim;
import jogovelha.jogadores.minmax.PontuacaoMinMax.EstrategiaDesempate;
import jogovelha.jogadores.simples.ComputadorEspelho;
import jogovelha.jogadores.simples.ComputadorOpcaoAleatoria;
import jogovelha.jogadores.simples.ComputadorPrimeiraOpcao;
import jogovelha.jogo.Competidor;

public class Computadores {

  public static List<Competidor> competidoresPadrao() {
    final List<Competidor> todosJogadores = new ArrayList<>();
    todosJogadores.add(//
        new Competidor("Computador espelho (?)", () -> new ComputadorEspelho()));
    todosJogadores.add(//
        new Competidor("Computador aleatório (fácil)", () -> new ComputadorOpcaoAleatoria()));

    todosJogadores.add(//
        new Competidor("Computador da 1º opção (fácil)", () -> new ComputadorPrimeiraOpcao()));
    todosJogadores.add(//
        new Competidor("Computador um pouco esperto (médio)", () -> new ComputadorMinMax(2, EstrategiaDesempate.TANTO_FAZ)));
    todosJogadores.add(//
        new Competidor("Computador apressado (difícil)",
            () -> new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MENOS_JOGADAS)));
    todosJogadores.add(//
        new Competidor("Computador sacana (difícil)",
            () -> new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MAIS_JOGADAS)));
    todosJogadores.add(//
        new Competidor("Computador que não pensa (muito fácil)", () -> new ComputadorMuitoRuim()));
    return todosJogadores;
  }
}

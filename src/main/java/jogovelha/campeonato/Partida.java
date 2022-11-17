package jogovelha.campeonato;

import java.util.List;
import java.util.Objects;

import jogovelha.jogo.Competidor;
import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.JogoVelha;
import jogovelha.jogo.Tabuleiro;

/**
 * Representa a partida de um campeonato de jogo da velha, onde temos 2 competidores
 * ({@link Competidor}), e uma quantidade de jogos. Ambos competidores se vevezam a cada partida
 * para saber quem começa jogado.
 */
public class Partida {

  private final Competidor competidor1;

  private final Competidor competidor2;

  private final int qtdeJogos;

  private ResultadoPartida resultado;

  public Partida(Competidor competidor1, Competidor competidor2, int qtdeJogos) {
    this.competidor1 = Objects.requireNonNull(competidor1);
    this.competidor2 = Objects.requireNonNull(competidor2);
    this.qtdeJogos = qtdeJogos;
    this.resultado = null;
  }

  public Competidor getCompetidor1() {
    return competidor1;
  }

  public Competidor getCompetidor2() {
    return competidor2;
  }

  public int getQtdeJogos() {
    return qtdeJogos;
  }

  /**
   * Retorna o resultado da partida, null se a partida ainda não tiver sido executada
   */
  public ResultadoPartida getResultado() {
    return resultado;
  }

  /**
   * Executa a partida, retornando o resultado. Uma partida só pode ser executada uma vez, se tentar
   * executar de novo, uma exceção é lançada
   */
  synchronized ResultadoPartida executarPartida(List<Jogada> jogadasPreDefinidas) {
    if (resultado != null) {
      throw new IllegalStateException("Partida já executada");
    }
    int empates = 0;
    int vitoriasCompetidor1 = 0;
    int vitoriasCompetidor2 = 0;
    for (int i = 0; i < qtdeJogos; i++) {

      // para cada jogo, cria um jogador novo, um tabuleiro novo e executa já as jogadas pre definidas
      final Tabuleiro tabuleiro;
      final Jogador jogadorCompetidor1 = competidor1.criaJogador();
      final Jogador jogadorCompetidor2 = competidor2.criaJogador();
      if (i % 2 == 0) {
        tabuleiro = new Tabuleiro(jogadorCompetidor1, jogadorCompetidor2);
      } else {
        tabuleiro = new Tabuleiro(jogadorCompetidor2, jogadorCompetidor1);
      }

      // configura o tabuleiro para a partida com as jogadas pré definidas
      jogadasPreDefinidas.forEach(tabuleiro::realizarJogada);

      // simula o jogo em cima do tabuleiro
      final JogoVelha jogoVelha = new JogoVelha(tabuleiro);
      final JogadorTabuleiro vencedor = jogoVelha.executar();

      // contabiliza o resultado
      if (vencedor == null) {
        empates++;
      } else if (vencedor.ehJogador(jogadorCompetidor1)) {
        vitoriasCompetidor1++;
      } else {
        vitoriasCompetidor2++;
      }
    }

    this.resultado = new ResultadoPartida(this, empates, vitoriasCompetidor1, vitoriasCompetidor2);
    return this.resultado;
  }

  /**
   * Representa o resultado de uma partida
   */
  public static class ResultadoPartida {

    private final Partida partida;
    private final int empates;
    private final int vitoriasCompetidor1;
    private final int vitoriasCompetidor2;

    private ResultadoPartida(Partida partida, int empates, int vitoriasJogador1, int vitoriasJogador2) {
      this.partida = Objects.requireNonNull(partida);
      this.empates = empates;
      this.vitoriasCompetidor1 = vitoriasJogador1;
      this.vitoriasCompetidor2 = vitoriasJogador2;
    }

    public Partida getPartida() {
      return partida;
    }

    public int getEmpates() {
      return empates;
    }

    public int getVitoriasCompetidor1() {
      return vitoriasCompetidor1;
    }

    public int getVitoriasCompetidor2() {
      return vitoriasCompetidor2;
    }

  }
}

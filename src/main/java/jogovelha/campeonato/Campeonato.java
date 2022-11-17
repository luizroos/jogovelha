package jogovelha.campeonato;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jogovelha.campeonato.Partida.ResultadoPartida;
import jogovelha.campeonato.tabuleiro.JogadasPreDefinidas;
import jogovelha.jogo.Competidor;

/**
 * Representa um campeonato de jogo da velha
 */
public class Campeonato {

  private static final Comparator<PontosCompetidor> RANKING_SORT = Comparator//
      .comparingInt(PontosCompetidor::getPontos)//
      .thenComparingInt(PontosCompetidor::getVitorias)//
      .reversed() //
      .thenComparingInt(PontosCompetidor::getJogos);

  private final Optional<PrintStream> arquivoLog;

  private final List<Competidor> competidores;

  private final List<Rodada> rodadas;

  private final CampeonatoEventListener listener;

  private final Map<Competidor, PontosCompetidor> pontuacao;

  private final int pontosEmpate;

  private final int pontosVitoria;

  private int rodadaAtual = 0;

  /**
   * Cria um campeonato, configurando todas as rodadas onde todos jogam contra todos
   * 
   * @param listener listener dos eventos do campeonato
   * @param competidores quais são os jogadores que vão competir
   * @param jogos configurações dos jogos que serão disputados
   * @param pontosEmpate quantos pontos cada empate vai dar
   * @param pontosVitoria quantos pontos cada vitoria vai dar
   * @param arquivoLog opcionalmente, o nome do arquivo que deve ser gravado o log de todas partidas
   *        (para posterior checagem)
   */
  public Campeonato(CampeonatoEventListener listener, List<Competidor> competidores, Map<JogadasPreDefinidas, Integer> jogos,
      int pontosEmpate, int pontosVitoria, Optional<String> arquivoLog) {

    this.arquivoLog = arquivoLog.map(arq -> {
      try {
        return new PrintStream(arq);
      } catch (FileNotFoundException e) {
        throw new RuntimeException();
      }
    });

    // embaralha aleatoriamente os competidores (para gerar rodadas diferentes toda vez que roda o
    // programa)
    final List<Competidor> copiaCompetidores = new ArrayList<>(competidores);
    Collections.shuffle(copiaCompetidores);

    this.competidores = copiaCompetidores;
    this.listener = listener;
    this.pontuacao = new HashMap<>();

    this.rodadas = new ArrayList<>();
    jogos.forEach((jogadasPreDefinidas, numeroJogos) -> {
      this.rodadas.addAll(
          AlgoritmoRoundRobinScheduling.criaRodadas(copiaCompetidores, numeroJogos, jogadasPreDefinidas, this.rodadas.size() + 1));
    });
    this.pontosEmpate = pontosEmpate;
    this.pontosVitoria = pontosVitoria;
  }

  /**
   * @see {@link #Campeonato(CampeonatoEventListener, List, Map, int, int, Optional)}
   */
  public Campeonato(CampeonatoEventListener listener, List<Competidor> competidores, int numeroJogos, int pontosEmpate,
      int pontosVitoria, Optional<String> arquivoLog) {
    this(listener, competidores, Collections.singletonMap(() -> Collections.emptyList(), numeroJogos), pontosEmpate,
        pontosVitoria, arquivoLog);
  }

  /**
   * Logica do inicio do campeonato, notifica os listeners tb
   */
  private void campeonatoInicio() {
    pontuacao.clear();
    for (Competidor competidor : competidores) {
      pontuacao.put(competidor, new PontosCompetidor(competidor, pontosVitoria, pontosEmpate));
    }
    listener.campeonatoInicio(this);
  }

  /**
   * Retorna a classificação atual (do primeiro ao ultimo)
   */
  public List<PontosCompetidor> getClassificacao() {
    final List<PontosCompetidor> classificacao = new ArrayList<>(pontuacao.values());
    classificacao.sort(RANKING_SORT);
    return classificacao;
  }

  /**
   * Retorna todos os competidores configurados para esse campeonato
   */
  public List<Competidor> getCompetidores() {
    return Collections.unmodifiableList(competidores);
  }

  /**
   * Executa todas as partidas da rodada, atualizando os pontos dos competidores
   */
  private List<ResultadoPartida> executarPartidas(Rodada rodada) {
    return rodada.getPartidas().stream().map(partida -> {
      listener.partidaInicio(this, rodada, partida);

      final ResultadoPartida resultado = partida.executarPartida(rodada.getJogadasPreDefinidas());

      arquivoLog.ifPresent(log -> {
        log.printf("rodada=%s, jogos=%s, jogador1=%s, jogador2=%s, vitorias1=%s, vitorias2=%s, jogadasIniciais=%s\n",
            rodada.getNumero(), partida.getQtdeJogos(), partida.getCompetidor1().getNome(), partida.getCompetidor2().getNome(),
            resultado.getVitoriasCompetidor1(), resultado.getVitoriasCompetidor2(), rodada.getJogadasPreDefinidas());
      });

      final PontosCompetidor pontosComp1 = pontuacao.get(partida.getCompetidor1());
      pontosComp1.jogos += partida.getQtdeJogos();
      pontosComp1.empates += partida.getResultado().getEmpates();
      pontosComp1.vitorias += partida.getResultado().getVitoriasCompetidor1();

      final PontosCompetidor pontosComp2 = pontuacao.get(partida.getCompetidor2());
      pontosComp2.jogos += partida.getQtdeJogos();
      pontosComp2.empates += partida.getResultado().getEmpates();
      pontosComp2.vitorias += partida.getResultado().getVitoriasCompetidor2();

      listener.partidaFinal(this, rodada, partida);
      return resultado;
    }).collect(Collectors.toList());
  }

  /**
   * Executa todo o campeonato (mesmo que chamar {@link #executarRodada() até que retorne false}
   */
  public synchronized void executar() {
    while (executarRodada());
  }

  /**
   * Executa a proxima rodada, retorna true teve rodada executada, senão false
   */
  public synchronized boolean executarRodada() {
    if (rodadaAtual == 0) {
      campeonatoInicio();
    }
    if (rodadaAtual < rodadas.size()) {
      final Rodada rodada = rodadas.get(rodadaAtual);

      final boolean ultimaRodada = rodada.getNumero() == rodadas.size();

      listener.rodadaInicio(this, rodada, ultimaRodada);

      executarPartidas(rodada);

      rodadaAtual++;

      listener.rodadaFinal(this, rodada, ultimaRodada);

      if (ultimaRodada) {
        listener.campeonatoFinal(this);
      }
      return true;
    }
    return false;
  }

  /**
   * Retorna todas as rodadas
   */
  public List<Rodada> getRodadas() {
    return Collections.unmodifiableList(rodadas);
  }

  /**
   * Define os pontos que cada competidor tem, serve para montar a classificação do campeonato
   */
  public static class PontosCompetidor {

    private final Competidor competidor;

    private final int pontosPorVitoria;

    private final int pontosPorEmpate;

    private int jogos;

    private int vitorias;

    private int empates;

    private PontosCompetidor(Competidor competidor, int pontosPorVitoria, int pontosPorEmpate) {
      this.competidor = competidor;
      this.pontosPorEmpate = pontosPorEmpate;
      this.pontosPorVitoria = pontosPorVitoria;
      this.jogos = 0;
      this.vitorias = 0;
      this.empates = 0;
    }

    public Competidor getCompetidor() {
      return competidor;
    }

    public int getVitorias() {
      return vitorias;
    }

    public int getEmpates() {
      return empates;
    }

    public int getJogos() {
      return jogos;
    }

    public int getPontos() {
      return (vitorias * pontosPorVitoria) + (empates * pontosPorEmpate);
    }

  }

}

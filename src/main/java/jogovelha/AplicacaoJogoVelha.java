package jogovelha;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import jogovelha.campeonato.Campeonato;
import jogovelha.campeonato.Campeonato.PontosCompetidor;
import jogovelha.jogadores.Computadores;
import jogovelha.jogadores.simples.Humano;
import jogovelha.jogo.Competidor;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.JogoVelha;
import jogovelha.jogo.Tabuleiro;
import jogovelha.ui.AplicacaoUI;
import jogovelha.ui.campeonato.CampeonatoUI;
import jogovelha.ui.jogo.JogoUI;

public class AplicacaoJogoVelha {

  public static void main(String[] args) {
    AplicacaoJogoVelha.executar(Collections.emptyList());
  }

  public static void executar(Competidor... outrosJogadores) {
    executar(Arrays.asList(outrosJogadores));
  }

  public static void executar(List<Competidor> outrosJogadores) {
    final List<Competidor> competidores = Computadores.competidoresPadrao();
    competidores.addAll(outrosJogadores);

    final AplicacaoUI ui = new AplicacaoUI();
    final boolean modoCompeticao = ui.perguntaSimNao("Modo competição?");
    if (!modoCompeticao) {
      executaJogo(ui, competidores);
    } else {
      executaCampeonato(ui, competidores);
    }
  }

  private static void executaCampeonato(AplicacaoUI ui, List<Competidor> competidores) {
    final CampeonatoUI campeonatoUI = ui.criaCampeonatoUI();
    do {
      final Map<String, Supplier<Integer>> opcoesJogosPorPartida = new LinkedHashMap<>();
      opcoesJogosPorPartida.put("50", () -> 50);
      opcoesJogosPorPartida.put("100", () -> 100);
      opcoesJogosPorPartida.put("250", () -> 250);
      opcoesJogosPorPartida.put("500", () -> 500);
      final int qtdeJogos = ui.perguntaOpcoes("Quantos jogos por partida?", opcoesJogosPorPartida); //

      final Map<String, Supplier<Integer>> opcoesPontosEmpate = new LinkedHashMap<>();
      opcoesPontosEmpate.put("1", () -> 1);
      opcoesPontosEmpate.put("3", () -> 3);
      final int pontosEmpate = ui.perguntaOpcoes("Quantos pontos por empate?", opcoesPontosEmpate); //

      final Map<String, Supplier<Integer>> opcoesPontosVitoria = new LinkedHashMap<>();
      opcoesPontosVitoria.put("2", () -> 2);
      opcoesPontosVitoria.put("10", () -> 10);
      final int pontosVitoria = ui.perguntaOpcoes("Quantos pontos por vitória?", opcoesPontosVitoria);
      final Campeonato campeonato =
          new Campeonato(campeonatoUI, competidores, qtdeJogos, pontosEmpate, pontosVitoria, Optional.empty());
      campeonato.executar();
      final List<PontosCompetidor> classificao = campeonato.getClassificacao();
      ui.exibeMensagem(String.format("Primeiro lugar %s com %s pontos", classificao.get(0).getCompetidor().getNome(),
          classificao.get(0).getPontos()));
    } while (ui.perguntaSimNao("Novo campeonato?"));
    campeonatoUI.fechar();
  }

  private static void executaJogo(AplicacaoUI ui, List<Competidor> competidores) {
    final JogoUI jogoUI = ui.criaJogoUI();
    competidores.add(new Competidor("Humano", () -> new Humano(jogoUI)));
    competidores.sort((c1, c2) -> c1.getNome().compareTo(c2.getNome()));

    final Map<String, Supplier<Jogador>> opcoesCompetidores = new LinkedHashMap<>();
    competidores.forEach(jogador -> {
      opcoesCompetidores.put(jogador.getNome(), jogador.getJogador());
    });

    do {

      final Jogador jogador1 = ui.perguntaOpcoes("Quem vai ser o Jogador 'X' ?", opcoesCompetidores);
      final Jogador jogador2 = ui.perguntaOpcoes("Quem vai ser o Jogador 'O' ?", opcoesCompetidores);

      final Tabuleiro tabuleiro = new Tabuleiro(jogador1, jogador2);
      final JogoVelha jogo = new JogoVelha(tabuleiro, jogoUI);
      final JogadorTabuleiro vencedor = jogo.executar();
      if (vencedor != null) {
        ui.exibeMensagem("Jogador '" + vencedor.getSimbolo() + "' ganhou!");
      } else {
        ui.exibeMensagem("Tabuleiro Completo. Jogo empatado");
      }

    } while (ui.perguntaSimNao("Novo jogo?"));
    jogoUI.fechar();
  }

}

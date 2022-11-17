package jogovelha.jogadores.simples;

import java.util.List;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.Tabuleiro;
import jogovelha.jogo.TabuleiroLeitura;

/**
 * Computador que imita o oponente. Para cada jogada, ele cria um novo tabuleiro, invertendo a ordem
 * dos joadores e as jogadas (minhas jogadas passam a ser as jogadas do meu oponente e vice versa),
 * e então ele pede para o oponente decidir onde vai jogar, tomando a decisão de jogar onde o
 * oponente acha que deve ser jogado
 */
public class ComputadorEspelho implements Jogador {

  @Override
  public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
    final JogadorTabuleiro[] jogadores = tabuleiro.getJogadores();

    // cria o novo tabuleiro de simulação invertendo os jogadores
    final Tabuleiro simulacao;
    final Jogador oponente;
    if (jogadores[0].getJogador() == this) {

      // eu sou o primeiro jogador
      oponente = jogadores[1].getJogador();

      // crio um tabuleiro onde meu adversario é o primeiro
      simulacao = new Tabuleiro(oponente, this);

    } else {
      // eu sou o segundo jogador
      oponente = jogadores[0].getJogador();

      // crio um tabuleiro onde meu adversario é o segundo
      simulacao = new Tabuleiro(this, oponente);
    }

    // se o oponente for eu mesmo, não posso delegar para ele, pq senão vamos entrar em um looping
    // inifinito. Tb não posso delegar se meu oponente for um humano. Nesse caso, me torno um jogador
    // aleatorio
    if (oponente instanceof ComputadorEspelho || oponente instanceof Humano) {
      return new ComputadorOpcaoAleatoria().decidirJogada(tabuleiro, jogadasDisponiveis);
    }

    // replica todas as jogadas já realizadas
    for (Jogada jogada : tabuleiro.getJogadasRealizadas()) {
      simulacao.realizarJogada(jogada);
    }

    // pede para o oponente decidir
    return oponente.decidirJogada(simulacao, jogadasDisponiveis);
  }

}

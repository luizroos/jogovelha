package jogovelha;

import org.junit.Test;

import jogovelha.jogadores.minmax.ComputadorMinMax;
import jogovelha.jogadores.minmax.PontuacaoMinMax.EstrategiaDesempate;
import jogovelha.jogo.Jogada;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.JogoVelha;
import jogovelha.jogo.Tabuleiro;
import junit.framework.TestCase;

public class TesteJogadoresMinMax {

  /**
   * Cenario onde o jogador 2 começou errado, então o jogador 1 deve saber ganhar
   */
  @Test
  public void teste1() {
    final ComputadorMinMax jogadorX = new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MAIS_JOGADAS);
    final ComputadorMinMax jogadorO = new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MENOS_JOGADAS);

    // cria o tabuleiro
    final Tabuleiro tabuleiro = new Tabuleiro(jogadorX, jogadorO);
    tabuleiro.realizarJogada(new Jogada(1, 1));
    tabuleiro.realizarJogada(new Jogada(1, 3));

    // inicia o jogo
    final JogoVelha jogo = new JogoVelha(tabuleiro);
    JogadorTabuleiro vencedor = jogo.executar();
    TestCase.assertEquals(vencedor.getJogador(), jogadorX);
  }

  /**
   * Uma situacao que eh inevitavel X ganhar, porém pode ganhar em 1 ou 2 jogadas, priorizando
   * MAIS_JOGADAS, ele deve ganhar em 2
   */
  @Test
  public void teste2() {
    final ComputadorMinMax jogadorX = new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MAIS_JOGADAS);
    final ComputadorMinMax jogadorO = new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MAIS_JOGADAS);

    // cria o tabuleiro
    final Tabuleiro tabuleiro = new Tabuleiro(jogadorX, jogadorO);
    tabuleiro.realizarJogada(new Jogada(1, 1));
    tabuleiro.realizarJogada(new Jogada(1, 3));
    tabuleiro.realizarJogada(new Jogada(3, 3));
    tabuleiro.realizarJogada(new Jogada(1, 2));

    // inicia o jogo
    final JogoVelha jogo = new JogoVelha(tabuleiro);
    JogadorTabuleiro vencedor = jogo.executar();
    TestCase.assertEquals(vencedor.getJogador(), jogadorX);

    // teve que ter ganho a coluna (priorizou mais jogadas, não ganhando na diagonal)
    TestCase.assertEquals(tabuleiro.getJogadorQueJogou(1, 1).getJogador(), jogadorX);
    TestCase.assertEquals(tabuleiro.getJogadorQueJogou(2, 1).getJogador(), jogadorX);
    TestCase.assertEquals(tabuleiro.getJogadorQueJogou(3, 1).getJogador(), jogadorX);
  }

  /**
   * Uma situacao que eh inevitavel X ganhar, porém pode ganhar em 1 ou 2 jogadas, priorizando
   * MENOS_JOGADAS, ele deve ganhar em 1
   */
  @Test
  public void teste3() {
    final ComputadorMinMax jogadorX = new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MENOS_JOGADAS);
    final ComputadorMinMax jogadorO = new ComputadorMinMax(Integer.MAX_VALUE, EstrategiaDesempate.MAIS_JOGADAS);

    // cria o tabuleiro
    final Tabuleiro tabuleiro = new Tabuleiro(jogadorX, jogadorO);
    tabuleiro.realizarJogada(new Jogada(1, 1));
    tabuleiro.realizarJogada(new Jogada(1, 3));
    tabuleiro.realizarJogada(new Jogada(3, 3));
    tabuleiro.realizarJogada(new Jogada(1, 2));

    // inicia o jogo
    final JogoVelha jogo = new JogoVelha(tabuleiro);
    JogadorTabuleiro vencedor = jogo.executar();
    TestCase.assertEquals(vencedor.getJogador(), jogadorX);

    // teve que ter ganho na diagonal (priorizou menos jogadas, ganhando logo)
    TestCase.assertEquals(tabuleiro.getJogadorQueJogou(1, 1).getJogador(), jogadorX);
    TestCase.assertEquals(tabuleiro.getJogadorQueJogou(2, 2).getJogador(), jogadorX);
    TestCase.assertEquals(tabuleiro.getJogadorQueJogou(3, 3).getJogador(), jogadorX);
  }
}

package jogovelha.ui.jogo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.JogoVelhaEventListener;
import jogovelha.jogo.TabuleiroLeitura;

/**
 * Definição de uma interface para exibir o jogo da velha
 */
public class JogoUI implements JogoVelhaEventListener, ActionListener {

  private final TelaJogo telaJogo;

  private final TelaHistoricoJogadas telaHistoricoJogadas;

  private CountDownLatch countDown = null;

  private Jogada jogadaSelecionada;

  public JogoUI() {
    this.telaHistoricoJogadas = new TelaHistoricoJogadas();

    this.telaJogo = new TelaJogo(this);
    this.telaJogo.setLocationRelativeTo(telaHistoricoJogadas);

    this.telaJogo.setVisible(true);
    this.telaHistoricoJogadas.setVisible(true);
  }

  public void fechar() {
    telaJogo.dispatchEvent(new WindowEvent(telaJogo, WindowEvent.WINDOW_CLOSING));
  }

  public void exibeMensagemAcompanhamento(String texto) {
    telaJogo.configurarMensagemAcompanhamento(texto);
  }

  public Jogada perguntaJogada() {
    countDown = new CountDownLatch(1);
    try {
      countDown.await();
    } catch (InterruptedException e) {
    }
    countDown = null;
    return jogadaSelecionada;
  }

  @Override
  public void jogoInicio(TabuleiroLeitura tabuleiro) {
    telaJogo.atualizaTabuleiro(tabuleiro);
    telaHistoricoJogadas.resetaHistorico();
  }

  @Override
  public void jogoFinal(TabuleiroLeitura tabuleiroLeitura) {}

  @Override
  public void rodadaInicio(TabuleiroLeitura tabuleiro, int rodada, JogadorTabuleiro jogadorVez) {
    telaJogo.configurarMensagemAcompanhamento(//
        String.format("Rodada %s, é a vez do jogador '%s'", rodada, jogadorVez.getSimbolo()));
  }

  @Override
  public void jogadaRealizada(TabuleiroLeitura tabuleiro, Jogada jogada, int rodada, JogadorTabuleiro jogadorVez) {
    // marca a jogada
    telaJogo.atualizaTabuleiro(tabuleiro);

    // registra historico
    telaHistoricoJogadas.atualizaRodada(rodada, tabuleiro);
  }

  @Override
  public void decisaoAutomaticaRealizada(int rodada, JogadorTabuleiro jogadorVez, Exception e) {
    telaJogo.configurarMensagemAcompanhamento(" jogador com dificuldades para escolher... tomando decisão por ele.");
  }

  @Override
  public void jogadaJaRealizada(TabuleiroLeitura tabuleiroLeitura, Jogada jogada, int rodada, JogadorTabuleiro jogadorVez) {
    telaJogo.configurarMensagemAcompanhamento("Esse local já foi marcado. Tente outro.");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (countDown == null) {
      return;
    }
    this.jogadaSelecionada = telaJogo.getJogada((JButton) e.getSource());
    countDown.countDown();
  }
}

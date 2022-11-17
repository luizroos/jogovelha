package jogovelha.ui.campeonato;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;

import jogovelha.campeonato.Campeonato;
import jogovelha.campeonato.CampeonatoEventListener;
import jogovelha.campeonato.Partida;
import jogovelha.campeonato.Rodada;
import jogovelha.jogo.Competidor;

public class CampeonatoUI implements CampeonatoEventListener, ActionListener {

  private final TelaRodada telaRodada;

  private final TelaClassificacao telaClassificacao;

  private final TelaTabuleiroRodada telaTabuleiroRodada;

  private CountDownLatch countDown = null;

  private Campeonato campeonato = null;

  private int rodadaAtual;

  private int rodadaExibindo;

  private boolean rodadaExecutando;

  public CampeonatoUI() {
    this.telaRodada = new TelaRodada(this);
    this.telaRodada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.telaTabuleiroRodada = new TelaTabuleiroRodada();
    this.telaTabuleiroRodada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.telaClassificacao = new TelaClassificacao();
    this.telaClassificacao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.telaRodada.setLocationRelativeTo(this.telaClassificacao);
    this.telaTabuleiroRodada.setLocationRelativeTo(this.telaRodada);
    this.telaRodada.setVisible(true);
    this.telaTabuleiroRodada.setVisible(true);
    this.telaClassificacao.setVisible(true);
  }

  private void atualizaTelaRodada(int numeroRodada) {
    final Rodada rodada = campeonato.getRodadas().get(numeroRodada - 1);
    atualizaTelaRodada(campeonato, rodada);
  }

  private void atualizaTelaRodada(Campeonato campeonato, Rodada rodada) {
    final int totalRodadas = campeonato.getRodadas().size();
    telaRodada.atualizaRodada(rodada, //
        rodada.getNumero() < totalRodadas && !rodadaExecutando, //
        rodada.getNumero() > 1 && !rodadaExecutando, //
        rodadaAtual == rodada.getNumero() && !rodadaExecutando);
    this.rodadaExibindo = rodada.getNumero();
  }

  @Override
  public void campeonatoInicio(Campeonato campeonato) {
    this.campeonato = campeonato;

    // atualiza a tela de classificacao
    telaClassificacao.atualizaClassificacao(campeonato.getClassificacao());

    // preenche a primeira rodada
    final Rodada rodada = campeonato.getRodadas().get(0);
    rodadaAtual = rodada.getNumero();
    atualizaTelaRodada(campeonato, rodada);
  }

  @Override
  public void campeonatoFinal(Campeonato campeonato) {
    final Competidor vencedor = campeonato.getClassificacao().get(0).getCompetidor();
    telaClassificacao.marcaCompetidores(vencedor);
  }

  @Override
  public void rodadaInicio(Campeonato campeonato, Rodada rodada, boolean ultimaRodada) {
    rodadaAtual = rodada.getNumero();

    // configura o tabuleiro da rodada
    telaTabuleiroRodada.atualizaTabuleiroRodada(rodada);

    // ao inicio da rodada, atualiza
    atualizaTelaRodada(campeonato, rodada);

    // espera o ok para execucao da rodada
    esperaBotaoExecutar();

    // marca que existe rodadas executando
    rodadaExecutando = true;

    // desabilita todos botões
    telaRodada.desabilitarTodosBotoes();
  }

  @Override
  public void rodadaFinal(Campeonato campeonato, Rodada rodada, boolean ultimaRodada) {
    // atualiza a classificacao
    telaClassificacao.atualizaClassificacao(campeonato.getClassificacao());

    // marca que não existe rodadas executando
    rodadaExecutando = false;
  }

  @Override
  public void partidaInicio(Campeonato campeonato, Rodada rodada, Partida partida) {
    // marca os competidores na classificacao
    telaClassificacao.marcaCompetidores(partida.getCompetidor1(), partida.getCompetidor2());

    // deixa selecionado a partida na tela de rodada
    telaRodada.marcaPartida(partida);
  }

  @Override
  public void partidaFinal(Campeonato campeonato, Rodada rodada, Partida partida) {
    // a cada final de partida, atualiza a rodada com o resultado
    atualizaTelaRodada(campeonato, rodada);
  }

  public void fechar() {
    telaClassificacao.dispatchEvent(new WindowEvent(telaClassificacao, WindowEvent.WINDOW_CLOSING));
  }

  void esperaBotaoExecutar() {
    telaRodada.getBotaoExecutar().setEnabled(true);
    countDown = new CountDownLatch(1);
    try {
      countDown.await();
    } catch (InterruptedException e) {
    }
    countDown = null;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    final JButton btn = (JButton) e.getSource();
    if (btn == telaRodada.getBotaoExecutar()) {
      countDown.countDown();
    } else if (btn == telaRodada.getBotaoProximo()) {
      atualizaTelaRodada(rodadaExibindo + 1);
    } else if (btn == telaRodada.getBotaoAnterior()) {
      atualizaTelaRodada(rodadaExibindo - 1);
    }
  }

}

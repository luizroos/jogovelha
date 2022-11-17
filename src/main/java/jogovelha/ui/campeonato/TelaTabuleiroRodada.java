package jogovelha.ui.campeonato;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jogovelha.campeonato.Rodada;
import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.Tabuleiro;
import jogovelha.jogo.TabuleiroLeitura;
import jogovelha.ui.PanelTabuleiro;
import jogovelha.ui.jogo.TelaJogo.TextoJLabel;

public class TelaTabuleiroRodada extends JFrame {

  private static final long serialVersionUID = 1L;

  private PanelTabuleiro panelTabuleiro;

  public TelaTabuleiroRodada() {
    final JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BorderLayout());
    titlePanel.setBounds(0, 0, 300, 50);
    titlePanel.add(new TextoJLabel("Tabuleiro da Rodada", new Color(120, 20, 124), new Color(255, 231, 0), true));

    this.panelTabuleiro = new PanelTabuleiro(50, null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 450);
    setTitle("Tabuleiro da Rodada");
    setLayout(new BorderLayout());
    add(titlePanel, BorderLayout.NORTH);
    add(panelTabuleiro);
  }

  void atualizaTabuleiroRodada(Rodada rodada) {
    final Tabuleiro tabuleiro = new Tabuleiro(new FakeJogador(), new FakeJogador());
    rodada.getJogadasPreDefinidas().forEach(tabuleiro::realizarJogada);
    panelTabuleiro.atualiza(tabuleiro);
  }

  private static class FakeJogador implements Jogador {

    @Override
    public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
      return null;
    }

  }

}

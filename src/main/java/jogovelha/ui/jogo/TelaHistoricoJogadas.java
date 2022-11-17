package jogovelha.ui.jogo;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import jogovelha.jogo.Tabuleiro;
import jogovelha.jogo.TabuleiroLeitura;
import jogovelha.ui.PanelTabuleiro;

/**
 * Frame que mostra o histórico de jogadas no jogo
 */
public class TelaHistoricoJogadas extends JFrame {

  private static final long serialVersionUID = 1L;

  private final List<PanelTabuleiro> historicoJogadas = new ArrayList<>();

  public TelaHistoricoJogadas() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 350);
    setTitle("Histórico de jogadas");
    setLayout(new GridLayout(3, 3));

    for (int i = 0; i < Tabuleiro.TAMANHO_TABULEIRO * Tabuleiro.TAMANHO_TABULEIRO; i++) {
      final PanelTabuleiro hstTabuleiro = new PanelTabuleiro(15, null);
      hstTabuleiro.setAlignmentY(PanelTabuleiro.CENTER_ALIGNMENT);
      hstTabuleiro.setAlignmentX(PanelTabuleiro.CENTER_ALIGNMENT);
      historicoJogadas.add(hstTabuleiro);
      add(hstTabuleiro);
    }
  }

  public void resetaHistorico() {
    for (int i = 0; i < historicoJogadas.size(); i++) {
      historicoJogadas.get(i).atualiza(null);
    }
  }

  public void atualizaRodada(int rodada, TabuleiroLeitura tabuleiro) {
    historicoJogadas.get(rodada - 1).atualiza(tabuleiro);
  }

}

package jogovelha.ui.jogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.TabuleiroLeitura;
import jogovelha.ui.PanelTabuleiro;

/**
 * Frame onde o jogo é jogado
 */
public class TelaJogo extends JFrame {

  private static final long serialVersionUID = 1L;

  private final TextoJLabel messageFrameJogo;

  private final PanelTabuleiro panelTabuleiro;

  public TelaJogo(ActionListener lstJogada) {
    final JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BorderLayout());
    titlePanel.setBounds(0, 0, 300, 50);
    titlePanel.add(new TextoJLabel("Jogo da Velha", new Color(120, 20, 124), new Color(255, 231, 0), true));

    messageFrameJogo = new TextoJLabel("", new Color(120, 20, 124), new Color(255, 231, 0), true);
    final JPanel messageJogoPanel = new JPanel();
    messageJogoPanel.setLayout(new BorderLayout());
    messageJogoPanel.setBounds(0, 0, 300, 50);
    messageJogoPanel.add(messageFrameJogo);

    this.panelTabuleiro = new PanelTabuleiro(50, lstJogada);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 450);
    setTitle("Jogo da Velha");
    setLayout(new BorderLayout());
    add(titlePanel, BorderLayout.NORTH);
    add(panelTabuleiro);
    add(messageJogoPanel, BorderLayout.SOUTH);
  }

  void configurarMensagemAcompanhamento(String texto) {
    messageFrameJogo.setText(texto);
  }

  void atualizaTabuleiro(TabuleiroLeitura tabuleiro) {
    panelTabuleiro.atualiza(tabuleiro);
  }

  public static class TextoJLabel extends JLabel {

    private static final long serialVersionUID = 1L;

    public TextoJLabel(String texto, Color bgColor, Color fgColor, boolean centralizado) {
      setBackground(bgColor);
      setForeground(fgColor);
      setFont(new Font("Ink Free", Font.BOLD, 20));
      setHorizontalAlignment(centralizado ? JLabel.CENTER : JLabel.LEFT);
      setText(texto);
      setOpaque(true);
    }
  }

  public Jogada getJogada(JButton botao) {
    return panelTabuleiro.getJogada(botao);
  }

}

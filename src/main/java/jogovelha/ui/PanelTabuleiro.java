package jogovelha.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.JogadorTabuleiro;
import jogovelha.jogo.Tabuleiro;
import jogovelha.jogo.TabuleiroLeitura;

public class PanelTabuleiro extends JPanel {

  private static final long serialVersionUID = 1L;

  private static final String TEXTO_BOTAO_VAZIO = " ";

  private final JButton[][] buttons = new JButton[3][3];

  public PanelTabuleiro(int fontSize, ActionListener lst) {
    setLayout(new GridLayout(Tabuleiro.TAMANHO_TABULEIRO, Tabuleiro.TAMANHO_TABULEIRO));
    setBackground(new Color(150, 150, 150));
    for (int linha = 0; linha < Tabuleiro.TAMANHO_TABULEIRO; linha++) {
      for (int coluna = 0; coluna < Tabuleiro.TAMANHO_TABULEIRO; coluna++) {
        buttons[linha][coluna] = new JButton();
        buttons[linha][coluna].setText(TEXTO_BOTAO_VAZIO);
        buttons[linha][coluna].setFont(new Font("Ink Free", Font.BOLD, fontSize));
        buttons[linha][coluna].setFocusable(false);
        buttons[linha][coluna].setMnemonic((linha + 1) * 10 + coluna + 1);
        if (lst != null) {
          buttons[linha][coluna].addActionListener(lst);
          buttons[linha][coluna].setActionCommand("Click posicao: (" + linha + "," + coluna + ")");
        }
        add(buttons[linha][coluna]);
      }
    }
    setBorder(BorderFactory.createLineBorder(Color.WHITE));
  }

  public void atualiza(TabuleiroLeitura tabuleiro) {
    for (int linha = 1; linha <= Tabuleiro.TAMANHO_TABULEIRO; linha++) {
      for (int coluna = 1; coluna <= Tabuleiro.TAMANHO_TABULEIRO; coluna++) {
        final JogadorTabuleiro jogador = tabuleiro != null ? tabuleiro.getJogadorQueJogou(linha, coluna) : null;
        final String texto;
        if (jogador != null) {
          texto = jogador.getSimbolo();
        } else {
          texto = TEXTO_BOTAO_VAZIO;
        }
        buttons[linha - 1][coluna - 1].setText(texto);
      }
    }
  }

  public Jogada getJogada(JButton button) {
    if (!button.getText().trim().equals("")) {
      return null;
    }

    // encontra onde foi clicado
    for (int linha = 1; linha <= Tabuleiro.TAMANHO_TABULEIRO; linha++) {
      for (int coluna = 1; coluna <= Tabuleiro.TAMANHO_TABULEIRO; coluna++) {
        if (buttons[linha - 1][coluna - 1] == button) {
          return new Jogada(linha, coluna);
        }
      }
    }
    return null;
  }

}

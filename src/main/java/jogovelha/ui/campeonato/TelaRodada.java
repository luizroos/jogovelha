package jogovelha.ui.campeonato;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jogovelha.campeonato.Partida;
import jogovelha.campeonato.Rodada;
import jogovelha.campeonato.Partida.ResultadoPartida;
import jogovelha.ui.TabelaGenerica;

public class TelaRodada extends JFrame {

  private static final long serialVersionUID = 1L;

  private final TabelaGenerica tabela;

  private final JButton botaoExecutar;

  private final JButton botaoProximo;

  private final JButton botaoAnterior;

  TelaRodada(ActionListener listener) {
    this.tabela = new TabelaGenerica(new String[] {"Jogos", "Vitórias", "Competidor", " ", "Competidor", "Vitórias"});
    tabela.configuraCorColuna(0, Color.RED, Color.WHITE, true);
    tabela.configuraCorColuna(1, Color.BLACK, Color.WHITE, true);
    tabela.configuraCorColuna(3, Color.RED, Color.WHITE, true);
    tabela.configuraCorColuna(5, Color.BLACK, Color.WHITE, true);
    tabela.setFillsViewportHeight(true);

    final JScrollPane scrollPanel = new JScrollPane(tabela);
    add(scrollPanel, BorderLayout.CENTER);

    this.botaoProximo = new JButton();
    botaoProximo.setText("Próxima");
    botaoProximo.addActionListener(listener);
    botaoProximo.setEnabled(false);

    this.botaoAnterior = new JButton();
    botaoAnterior.setText("Anterior");
    botaoAnterior.addActionListener(listener);
    botaoAnterior.setEnabled(false);

    this.botaoExecutar = new JButton();
    botaoExecutar.setText("Executar Partidas");
    botaoExecutar.addActionListener(listener);
    botaoExecutar.setEnabled(false);

    final JPanel panelAcoes = new JPanel();
    panelAcoes.add(botaoAnterior);
    panelAcoes.add(botaoExecutar);
    panelAcoes.add(botaoProximo);
    add(panelAcoes, BorderLayout.PAGE_END);

    getContentPane().setPreferredSize(new Dimension(1300, 400));
    pack();
  }

  void atualizaRodada(Rodada rodada, boolean enableBtProximo, boolean enableBtAnterior, boolean enableBtExecutar) {
    // atualiza o titulo da tela
    setTitle(String.format("Rodada %s", rodada.getNumero()));

    // atualiza os dados da tabela da rodada
    final String[][] dados = new String[rodada.getPartidas().size()][6];
    final List<Partida> partidas = rodada.getPartidas();
    for (int i = 0; i < partidas.size(); i++) {
      final Partida partida = partidas.get(i);
      final ResultadoPartida resultado = partida.getResultado();
      dados[i][0] = String.valueOf(partida.getQtdeJogos());
      dados[i][1] = resultado == null ? "" : String.valueOf(resultado.getVitoriasCompetidor1());
      dados[i][2] = partida.getCompetidor1().getNome();
      dados[i][3] = "vs";
      dados[i][4] = partida.getCompetidor2().getNome();
      dados[i][5] = resultado == null ? "" : String.valueOf(resultado.getVitoriasCompetidor2());
    }
    tabela.atualizaDados(dados);

    // atualiza os botões de navegação
    botaoProximo.setEnabled(enableBtProximo);
    botaoAnterior.setEnabled(enableBtAnterior);
    botaoExecutar.setEnabled(enableBtExecutar);
  }

  void marcaPartida(Partida partida) {
    tabela.selecionaLinha(2, Collections.singleton(partida.getCompetidor1().getNome()));
  }

  void desabilitarTodosBotoes() {
    botaoExecutar.setEnabled(false);
    botaoProximo.setEnabled(false);
    botaoAnterior.setEnabled(false);
  }

  JButton getBotaoAnterior() {
    return botaoAnterior;
  }

  JButton getBotaoExecutar() {
    return botaoExecutar;
  }

  JButton getBotaoProximo() {
    return botaoProximo;
  }

  public static interface TelaRodadaEventListener {

    void botaoAnteriorClicado();

    void botaoExecutarClicado();

    void botaoProximoClicado();

  }

}

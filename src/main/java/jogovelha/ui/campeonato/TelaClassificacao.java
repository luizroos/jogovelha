package jogovelha.ui.campeonato;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import jogovelha.campeonato.Campeonato.PontosCompetidor;
import jogovelha.jogo.Competidor;
import jogovelha.ui.TabelaGenerica;

public class TelaClassificacao extends JFrame {

  private static final long serialVersionUID = 1L;

  private final TabelaGenerica tabela;

  TelaClassificacao() {
    this.tabela =
        new TabelaGenerica(new String[] {"Posição", "Competidor", "Jogos", "Vitórias", "Empates", "Derrotas", "Pontos"});
    tabela.configuraCorColuna(0, Color.RED, Color.WHITE, true);
    add(new JScrollPane(tabela));
    setSize(1100, 600);
    setTitle("Classificação");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  void atualizaClassificacao(List<PontosCompetidor> classificacao) {
    final String[][] dados = new String[classificacao.size()][7];
    for (int i = 0; i < classificacao.size(); i++) {
      final PontosCompetidor pontos = classificacao.get(i);
      dados[i][0] = String.valueOf(i + 1);
      dados[i][1] = pontos.getCompetidor().getNome();
      dados[i][2] = String.valueOf(pontos.getJogos());
      dados[i][3] = String.valueOf(pontos.getVitorias());
      dados[i][4] = String.valueOf(pontos.getEmpates());
      dados[i][5] = String.valueOf(pontos.getJogos() - pontos.getVitorias() - pontos.getEmpates());
      dados[i][6] = String.valueOf(pontos.getPontos());
    }
    tabela.atualizaDados(dados);
  }

  void marcaCompetidores(Competidor... competidores) {
    final Set<String> nomesAMarcar = Arrays.stream(competidores)//
        .map(Competidor::getNome)//
        .collect(Collectors.toSet());
    tabela.selecionaLinha(1, nomesAMarcar);
  }

}

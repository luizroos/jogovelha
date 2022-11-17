package jogovelha.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jogovelha.ui.campeonato.CampeonatoUI;
import jogovelha.ui.jogo.JogoUI;

public class AplicacaoUI {

  public void exibeMensagem(String mensagem) {
    JOptionPane.showMessageDialog(null, mensagem);
  }

  public <T> T perguntaOpcoes(String titulo, Map<String, Supplier<T>> opcoes) {

    final List<Entry<String, Supplier<T>>> listaOpcoes = new ArrayList<>(opcoes.entrySet());

    final String[] arrayOps = new String[listaOpcoes.size()];
    for (int i = 0; i < listaOpcoes.size(); i++) {
      arrayOps[i] = listaOpcoes.get(i).getKey();
    }
    final Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");
    String opcaoSelecionada;
    do {
      opcaoSelecionada = (String) JOptionPane.showInputDialog(null, titulo, "ShowInputDialog", JOptionPane.PLAIN_MESSAGE,
          errorIcon, arrayOps, arrayOps[0]);
    } while (opcaoSelecionada == null);
    final String opt = opcaoSelecionada;
    return listaOpcoes.stream()//
        .filter(e -> e.getKey().equalsIgnoreCase(opt))//
        .findFirst()//
        .get()//
        .getValue()//
        .get();
  }

  public boolean perguntaSimNao(String titulo) {
    if (JOptionPane.showConfirmDialog(null, titulo, "Selecione a opção", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
      return true;
    } else {
      return false;
    }
  }

  public synchronized JogoUI criaJogoUI() {
    return new JogoUI();
  }

  public CampeonatoUI criaCampeonatoUI() {
    return new CampeonatoUI();
  }

  public String perguntaTexto() {
    return JOptionPane.showInputDialog("Senha");
  }

}

package jogovelha.ui;

import java.util.function.Supplier;

/**
 * Opcao escolha na interface para o usuário
 */
public interface OpcaoUI<T> {

  // descrição que deve aparecer na interface
  String getDescricao();

  // uma vez selecionado, retorna a "fabrica" do objeto selecionado
  Supplier<T> getValor();

}

package jogovelha.jogo;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * O competidor é basicamente uma fabrica de um {@link Jogador}, junto com o nome dele. Serve
 * basicamente para podermos simplificar a interface de jogador e para podermos ter uma forma de
 * gerar varias instancias do jogador
 */
public class Competidor {

  private final String nome;

  private final Supplier<Jogador> jogador;

  public Competidor(String nome, Supplier<Jogador> jogador) {
    this.nome = Objects.requireNonNull(nome);
    this.jogador = Objects.requireNonNull(jogador);
  }

  public Supplier<Jogador> getJogador() {
    return jogador;
  }

  public Jogador criaJogador() {
    return jogador.get();
  }

  public String getNome() {
    return nome;
  }

}

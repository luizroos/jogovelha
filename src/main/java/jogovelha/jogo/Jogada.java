package jogovelha.jogo;

import java.util.Objects;

/**
 * Representa uma jogada do jogo da velha (combinação de linha e coluna)
 */
public final class Jogada {

  private final int linha;

  private final int coluna;

  public Jogada(int linha, int coluna) {
    this.linha = linha;
    this.coluna = coluna;
  }

  public int getLinha() {
    return linha;
  }

  public int getColuna() {
    return coluna;
  }

  @Override
  public String toString() {
    return "Jogada [linha=" + linha + ", coluna=" + coluna + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(coluna, linha);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Jogada other = (Jogada) obj;
    return coluna == other.coluna && linha == other.linha;
  }

}

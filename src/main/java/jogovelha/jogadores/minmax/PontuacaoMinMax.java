package jogovelha.jogadores.minmax;

/**
 * Pontuacao Min Max
 */
public class PontuacaoMinMax {

  // valor da pontuacao
  private final int valor;

  // qual a profundidade dessa analise
  private final int profundidadeAnalise;

  public PontuacaoMinMax(int valor, int profundidadeAnalise) {
    this.profundidadeAnalise = profundidadeAnalise;
    this.valor = valor;
  }

  public int getValor() {
    return valor;
  }

  public int getProfundidadeAnalise() {
    return profundidadeAnalise;
  }

  PontuacaoMinMax getMax(PontuacaoMinMax pontuacao) {
    if (pontuacao == null) {
      return this;
    }
    if (pontuacao.valor > this.valor) {
      // a outra pontuacao é melhor
      return pontuacao;
    } else {
      // a outra pontuacao é pior
      return this;
    }
  }

  PontuacaoMinMax getMin(PontuacaoMinMax pontuacao) {
    if (pontuacao == null) {
      return this;
    }
    if (pontuacao.valor < this.valor) {
      // a outra pontuacao é melhor
      return pontuacao;
    } else {
      // a outra pontuacao é pior
      return this;
    }
  }

  /**
   * Compara com uma outra pontuacao para definir qual eh melhor (aquela que tem o maior valor)
   */
  boolean melhorQue(PontuacaoMinMax outraPontuacao, Heuristica heuristica) {
    if (outraPontuacao == null) {
      return true;
    }
    if (valor == outraPontuacao.valor) {
      return heuristica.desempata(this, outraPontuacao);
    }

    return valor > outraPontuacao.valor;
  }

  @Override
  public String toString() {
    return "PontuacaoMinMax [valor=" + valor + ", profundidadeAnalise=" + profundidadeAnalise + "]";
  }

  /**
   * Estratégia de desempate para ser usado em melhorQue
   */
  public static enum EstrategiaDesempate {

    MAIS_JOGADAS,

    MENOS_JOGADAS,

    TANTO_FAZ;

  }

}

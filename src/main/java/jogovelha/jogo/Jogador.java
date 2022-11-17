package jogovelha.jogo;

import java.util.List;

/**
 * Definição de um jogador
 */
public interface Jogador {

  /**
   * Você vai receber a visualização em modelo leitura do tabuleiro (não é possivel realizar jogadas
   * nesse tabuleiro) e deve então decidir, retornando entre a lista de jogadas disponiveis, qual
   * jogada o jogador deve realizar
   */
  Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis);

}

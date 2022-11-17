package jogovelha.jogadores.simples;

import java.util.List;

import jogovelha.jogo.Jogada;
import jogovelha.jogo.Jogador;
import jogovelha.jogo.TabuleiroLeitura;

public class ComputadorOpcaoAleatoria implements Jogador {

  @Override
  public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
    // pega a qtde de jgoadas disponiveis
    final int qtdeJogadasDisponiveis = jogadasDisponiveis.size();
    // sorteia um numero aleatorio (Math.random: entre 0 - 1) e multiplica pela qde de jogadas
    // disponiveis para chegar no indice da jogada que vai pegar
    final int indiceJogadaAleatoria = (int) (Math.random() * qtdeJogadasDisponiveis);
    // retorna a jogada que estiver nesse indice (ou seja, uma jogada aleatoria)
    return jogadasDisponiveis.get(Math.min(indiceJogadaAleatoria, jogadasDisponiveis.size() - 1));
  }

}

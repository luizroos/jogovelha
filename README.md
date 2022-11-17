# Jogo da Velha
---

Este é um programa que foi feito para que alunos, que estão aprendendo a programar em <b>java</b>, pratiquem a linguagem criando jogadores para decidir as jogadas de uma partida de jogo da velha.

Para executar, rode:

```java
  AplicacaoJogoVelha.executar(Collections.emptyList());
```

Quando você executar o jogo, será questionado se você deseja executar no modo competição ou não. 
O modo competição é um modo onde todos jogadores vão jogar várias partidas uns contra os outros, revezando quem é o jogador que começa o jogo para ser justo com todos. Vence o campeonato aquele jogador que fizer mais pontos.
Já no modo sem competição, você escolhe os dois jogadores e executa uma partida de cada vez.

O jogo será iniciado com alguns jogadores já implementados:
- Computador aleatório: joga de forma aleatória.
- Computador da 1º opção: joga sempre na primeira opção disponível.
- Computador que não pensa: jogador que implementa o algoritmo de minmax, porém focando em perder.
- Computador um pouco esperto: jogador que implementa o algoritmo de minmax, mas que não avalia todas as possibilidades.
- Computador apressado: jogador que implementa o algoritmo de minmax, avaliando todas as possibilidades, e que ganha sempre no caminho mais curto.
- Computador sacana: jogador que implementa o algoritmo de minmax, avaliando todas as possibilidades, e que, se tiver mais de um caminho para vitória, escolhe o mais longo (só para zuar o adversário).
- Computador espelho: jogador que imita o oponente.

Os jogadores mais espertos, são propositalmente feitos para que não possam ser reutilizados (de forma fácil) dentro de outros jogadores (por isso o AlgoritmoMinMax não tem construtor público e ComputadorMinMax só sabe avaliar jogadas de tabuleiros onde ele é um dos jogadores).  

## Criando jogador

O legal é você implementar seu jogador sem copiar os já existentes, para isso, crie uma classe que implemente a interface <b>Jogador</b>. 

```java
public interface Jogador {

  Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis);

}
```

No método que essa interface define, você terá que devolver a jogada que seu jogador quer que seja realizada, onde: 
- tabuleiro: representa o tabuleiro do jogo da velha em modo leitura (e aqui, propositalmente é usado uma classe para impedir que alunos espertos tentem burlar o jogo realizando jogadas direto no tabuleiro).  
- lista de jogadas que ainda estão disponíveis para serem feitas no tabuleiro (você pode conseguir a mesma coisa via tabuleiro.getJogadasDisponiveis).

Caso seu jogador não decida uma jogada, retornando nulo, ou seu jogador lance uma exceção ou seu jogador escolha jogadas que já foram feitas, o jogo vai escolher por ele, e essa escolha vai usar um jogador pré implementado que é muito ruim, que joga para perder, então não faça isso.

Esse tabuleiro que você recebe tem os seguintes métodos:

- getJogadasDisponiveis(): retorna todas jogadas disponíveis para o tabuleiro, o mesmo que você recebeu no parâmetro jogadasDisponiveis.
- getJogadasRealizadas(): retorna todas as jogadas já realizadas, na ordem que foram realizadas.
- getVencedor(): retorna o vencedor caso haja um, não havendo ainda retorna null.
- getJogadorQueJogou(new Jogada(linha, coluna)): retorna o jogador que fez uma determinada jogada (null se a posição está disponível).
- getJogadorQueJogou(linha, coluna): retorna o jogador que fez uma determinada jogada utilizando uma linha e coluna (null se a posição está disponível).
- getJogadores(): retorna um array com os dois jogadores do tabuleiro.
- getJogadorDaVez(): retorna o jogador da vez.
- verificaJogadaDisponivel(new Jogada(linha, coluna)): verifica se uma jogada foi feita, retornando true se sim e false se não
- verificaJogadaDisponivel(linha, coluna): verifica se uma jogada foi feita utilizando direto a linha e coluna, retornando true se sim e false se não.
- verificaTabuleiroCompleto(): verifica se todas as posições do tabuleiro estão completas e não tem mais jogadas para fazer.
- copiarTabuleiro(): faz uma cópia do tabuleiro, que dá acesso a métodos de movimentação (realizarJogada e desfazerJogada), tudo que vc fizer nesse tabuleiro de cópia não vai valer, serve apenas para você simular situações que queira fazer antes de decidir. Todos os métodos acima tb estão disponiveis nesse tabuleiro de simulação.

Usando copiarTabuleiro, você tem acesso a mais dois métodos nesse tabuleiro de cópia, esse é a dica para criar jogadores mais complexos, porque aí você pode simular jogadas através dos métodos:

- realizarJogada(new Jogada(linha, coluna)): Realiza uma jogada no tabuleiro, exemplo realizarJogada(new Jogada(1,1)), retornando true se a jogada pode ser feita e false se a jogada não foi feita. Note que, uma vez que você realiza uma jogada, o jogadorDaVez desse tabuleiro muda, e você pode ter um vencedor ou um tabuleiro completo.
- desfazUltimaJogada(): desfaz a última jogada feita (considerando apenas as jogadas realizadas no tabuleiro de simulação). Retorna true se a jogada pode ser desfeita ou false se ela não pode. Note que, uma vez que você desfaz uma jogada, o jogadorDaVez desse tabuleiro muda.

Com seus jogadores criados, você pode iniciar a aplicação passando uma lista contendo eles. Essa lista, na verdade, é de competidores, e um Competidor é basicamente a "fábrica" de um jogador. O competidor deve ter um nome e um meio de criar novas instâncias do jogador. É assim porque alguns alunos podem acabar usando variaveis de instância na implementação de jogador deles, e isso poderia gerar um problema ao longo de vários jogos com a mesma instância sendo reutilizada. Por isso, a cada novo jogo, uma instância de jogador nova é criada. 

Exemplo de inicialização do jogo usando um jogador customizado:

```java
  public static void main(String[] args) {
    final List<Competidor> jogadores = new ArrayList<>();
    jogadores.add(new Competidor("meu jogador", () -> new MeuJogador()));
    AplicacaoJogoVelha.executar(jogadores);
  }

  public static class MeuJogador implements Jogador {
    public Jogada decidirJogada(TabuleiroLeitura tabuleiro, List<Jogada> jogadasDisponiveis) {
      //TODO
      return null;
    }
  }
```

## Interface do jogo
---

Eu não sou uma pessoa de java desktop, não me interesso por swing e awt, se alguém algum dia quiser criar uma interface mais legal (ou corrigir alguma gambiarra que eu posso ter feito nessas interfaces), por favor, fique a vontade rs. 

Duas interfaces que vocês podem olhar para criar uma interface são JogoVelhaEventListener, que notifica tudo que acontece em um jogo, e CampeonatoEventListener, que notifica tudo que acontece no campeonato.

## Campeonato mais avançado

Fazer um campeonato entre os alunos é legal, porém, o jogo sempre começar com o tabuleiro vazio pode ser um pouco chato, não testando para valer a inteligência dos jogadores. Então tem uma forma de iniciar o campeonato definindo jogadas pré definidas para as rodadas, veja por exemplo:

```java
  public static void main(String[] args) {
    // adiciona os jogadores
    final List<Competidor> jogadores = new ArrayList<>();
    jogadores.addAll(Computadores.competidoresPadrao()); // todos computadores
    jogadores.add(new Competidor("meu jogador", () -> new MeuJogador()));
    // ...

    final Map<JogadasPreDefinidas, Integer> configuracaoJogos = new LinkedHashMap<>();
    // configura 300 jogos que serão jogadas em um tabuleiro vazio
    configuracaoJogos.put(() -> Collections.emptyList(), 300);
    // configura 120 jogos que serão jogadas em tabuleiros onde o X pode ganhar em até 3 jogadas
    configuracaoJogos.put(new PrimeiroGanhaEm3Jogadas(), 120);
    // configura 80 jogos que serão jogados em tabuleiros onde o X pode ganhar em até 1 jogada
    configuracaoJogos.put(new PrimeiroGanhaEm1Jogada(), 80);

    // executa o campeonato para essa configuração de jogos e setando 1 ponto por empate e 2 pontos para vitória.
    final AplicacaoUI ui = new AplicacaoUI();
    final CampeonatoUI campenatoUi = ui.criaCampeonatoUI();
    final Campeonato campeonato = new Campeonato(campenatoUi, jogadores, configuracaoJogos, 1, 2, Optional.empty());
    campeonato.executar();

    //exibe o vencedor
    final List<PontosCompetidor> classificacao = campeonato.getClassificacao();
    ui.exibeMensagem(String.format("Primeiro lugar %s com %s pontos", //
        classificacao.get(0).getCompetidor().getNome(), classificacao.get(0).getPontos()));

    campenatoUi.fechar();
  }
```

Se quiser criar outras configurações de tabuleiro, crie sua implementação de JogadasPreDefinidas. 

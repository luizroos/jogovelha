package jogovelha.campeonato;

/**
 * Definição de um listener para escutar eventos do campeonato
 */
public interface CampeonatoEventListener {

  /**
   * Chamado no inicio do campeaonto
   */
  void campeonatoInicio(Campeonato campeonato);

  /**
   * Chamado no termino do campeaonto
   */
  void campeonatoFinal(Campeonato campeonato);

  /**
   * Chamado no inicio de cada rodada
   */
  void rodadaInicio(Campeonato campeonato, Rodada rodada, boolean ultimaRodada);

  /**
   * Chamado ao final de cada rodada
   */
  void rodadaFinal(Campeonato campeonato, Rodada rodada, boolean ultimaRodada);

  /**
   * Chamado ao inicio de cada partida
   */
  void partidaInicio(Campeonato campeonato, Rodada rodada, Partida partida);

  /**
   * Chamado toda vez que uma partida eh executada
   */
  void partidaFinal(Campeonato campeonato, Rodada rodada, Partida partida);

}

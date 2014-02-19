package models;

import java.util.ArrayList;
import java.util.List;

import javax.naming.LimitExceededException;


public class Periodo {

	private List<Disciplina> disciplinas;
	public static final int MINIMO_DE_CREDITOS = 12;
	public static final int MAXIMO_DE_CREDITOS = 28;
	
	/**
	 * Construtor
	 */
	public Periodo() {
		disciplinas = new ArrayList<Disciplina>();
	}
	
	/**
	 * retorna a lista de disciplinas alocadas no período
	 * @return
	 */
	public List<Disciplina> disciplinasAlocadas() {
		return this.disciplinas;
	}

	/**
	 * Controller: como eu quero adicionar uma disciplina no período, a funcionalidade deve ser colocada aqui
	 * adciona uma disciplina na lista de disciplinas alocadas do período 
	 * @param nome nome da disciplina que desejo alocar
	 * @param numeroDeCreditos numero de créditos da disciplina
	 * @throws LimitExceededException só devo alocar uma disciplina se não exceder a quantidade máxima de créditos do período
	 */
	public void adicionaDisciplina(String nome, int numeroDeCreditos) throws LimitExceededException {
		
		adicionaDisciplina(nome, numeroDeCreditos, new ArrayList<Disciplina>());
	}
	
	/**
	 * Controller: como eu quero adicionar uma disciplina no período, a funcionalidade deve ser colocada aqui
	 * adciona uma disciplina na lista de disciplinas alocadas do período 
	 * @param nome no da disciplina
	 * @param numeroDeCreditos creditos da disciplina
	 * @param preRequisitos pre-requisitos para que essa disciplina possa ser alocada
	 * @return a disciplina alocada ou false se exceder a quantidade de créditos limite do período ao adiciona-la
	 * @throws LimitExceededException só devo alocar uma disciplina se não exceder a quantidade máxima de créditos do período
	 */
	public boolean adicionaDisciplina(String nome, int numeroDeCreditos, List<Disciplina> preRequisitos) 
			throws LimitExceededException {
		
		Disciplina disciplina = new Disciplina(nome, numeroDeCreditos, preRequisitos);
		if (!disciplinas.contains(disciplina)) {
			if ((getNumeroDeCreditos() + numeroDeCreditos) > MAXIMO_DE_CREDITOS) {
				throw new LimitExceededException("Nao foi possivel alocar " + nome + ". Limite maximo " +
						"de "+MAXIMO_DE_CREDITOS+" creditos!");
			}
			return disciplinas.add(disciplina);
		}
		return false;
	}
	
	/**
	 * get do total de créditos do período
	 * @return total de créditos
	 */
	public int getNumeroDeCreditos() {
	    	int total = 0;
		for(Disciplina disciplina : disciplinas)
			total += disciplina.getNumeroDeCreditos();
		return total;
	}

	/**
	 * Information Expert: como o período que tem a informação da quantidade de créditos, então ele que deve ter essa funcionalidade
	 * método pra saber se o total mínimo de créditos do periodo já foi atingido
	 * @return verdade ou falso
	 */
	public boolean abaixoDoLimiteMinimoDeCreditos() {
		return getNumeroDeCreditos() < Periodo.MINIMO_DE_CREDITOS;
	}

	/**
	 * Controller: como eu quero remover uma disciplina no período, a funcionalidade deve ser colocada aqui
	 * consigo remover uma disciplina alocada anteriormente
	 * @param nomeDaDisciplina 
	 * @return
	 */
	public Disciplina removeDisciplina(String nomeDaDisciplina) {
		for(Disciplina disciplina : disciplinas) {
			if(disciplina.getNome().equals(nomeDaDisciplina))
				return disciplinas.remove(disciplinas.indexOf(disciplina));
		}
		return null;
	}

	/**
	 * 
	 * @param nomeDaDisciplina
	 * @return
	 */
	public Disciplina getDisciplina(String nomeDaDisciplina) {
		for(Disciplina disciplina : disciplinas) {
			if(disciplina.getNome().equals(nomeDaDisciplina))
				return disciplinas.get(disciplinas.indexOf(disciplina));
		}
		return null;
	}
}

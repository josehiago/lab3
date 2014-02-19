package models;

import java.util.*;


public class GradeCurricular {
	
	private LeitorDeDisciplinas leitorDeDisciplinas;
	private List<Disciplina> disciplinas;
	
	
	public GradeCurricular() {
		disciplinas = new ArrayList<Disciplina>();
		leitorDeDisciplinas = LeitorDeDisciplinas.getInstance();
		geraDisciplinas();
	}
		
	/**
	 * get das disciplinas (cadeiras)
	 * @return  lista de disciplinas
	 */
	public List<Disciplina> getDisciplinas() {
		Collections.sort(disciplinas);
		return disciplinas;
	}
	
	/**
	 * 
	 * @param nome nome da disciplina que deseja procurar
	 * @return a disciplina se encontrada na lista, se não 	retorna null
	 */
	public Disciplina getDisciplina(String nome) {
		for(Disciplina disciplina: disciplinas) {
			if(disciplina.getNome().equals(nome))
				return disciplina;
		}
		return null;
	}
	
	/**
	 * retira disciplina da lista quando alocada
	 * @param nome nome da disciplina procurada
	 * @return 
	 */
	public Disciplina retiraDisciplina(String nome) {
		for(Disciplina disciplina : disciplinas) {
			if(disciplina.getNome().equals(nome)) {
				return disciplinas.remove(disciplinas.indexOf(disciplina));
			}
		}
		return null;
	}
	
	/**
	 * Controller: como eu quero adicionar uma disciplina na grade, a funcionalidade deve ser colocada aqui
	 * @param nome
	 */
	public void adicionaDisciplina(String nome) {
		List<Disciplina> preRequisitos = new ArrayList<Disciplina>();
		int numeroDeCreditos = leitorDeDisciplinas.getNumeroDeCreditosDeDisciplina(nome);
		geraPreRequisitosDeDisciplina(nome, preRequisitos);
		disciplinas.add(new Disciplina(nome, numeroDeCreditos, preRequisitos));
	}
	
	private void geraDisciplinas() {
		for(String info : leitorDeDisciplinas.getInformacoesDasDisciplinas()) {
			String nome = info.split("-")[0];
			adicionaDisciplina(nome);
		}
	}
	
	private void geraPreRequisitosDeDisciplina(String nome,
			List<Disciplina> preRequisitos) {
		for(String nomeDePreRequisto : leitorDeDisciplinas.getNomesDosPreRequisitosDeDisciplina(nome)) {
			
			preRequisitos.add(new Disciplina(nomeDePreRequisto, leitorDeDisciplinas.
					getNumeroDeCreditosDeDisciplina(nomeDePreRequisto)));
		}
	}
}

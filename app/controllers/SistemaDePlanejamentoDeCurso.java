package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.naming.LimitExceededException;

import exceptios.AlocacaoInvalidaException;


import models.Disciplina;
import models.GradeCurricular;
import models.Periodo;
/**
 * 
 * @author 
 *
 */
public class SistemaDePlanejamentoDeCurso {
	
	private List<Periodo> periodos;
	private GradeCurricular grade;
	private List<Disciplina> handlerDisciplinas;
	
	public SistemaDePlanejamentoDeCurso() {
		this.periodos = new ArrayList <Periodo>();
		this.grade = new GradeCurricular();
		primeiroPeriodo(); 
		handlerDisciplinas = new ArrayList<Disciplina>();
	}
	
	public List<Disciplina> listaDisciplinasDoCurso() {
		return grade.getDisciplinas();
	}
	
	public Periodo getPeriodo(int i) {
		return periodos.get(i);
	}
	
	public List<Disciplina> getDisciplinasDoPeriodo(int indicePeriodo) {
		return getPeriodo(indicePeriodo).disciplinasAlocadas();
	}
	
	private void adicionarDisciplinaSePreRequisitosSatisfeitos(int indicePeriodo, String nome, int numeroDeCreditos, 
			List<Disciplina> preRequisitos) throws AlocacaoInvalidaException, LimitExceededException {
		if (! preRequisitosSatisfeitos(indicePeriodo, preRequisitos)) {	
			throw new AlocacaoInvalidaException("Falha na alocação " + nome + " pro "+ (indicePeriodo+1) 
					+ "º periodo. " + "pre-requisito nao satisfeito.");
		}
		getPeriodo(indicePeriodo).adicionaDisciplina(nome, numeroDeCreditos, preRequisitos);
	}
	
	public int numeroDeCreditosDoPeriodo(int indicePeriodo) {
		return periodos.get(indicePeriodo).getNumeroDeCreditos(); 
	}
	
	private void primeiroPeriodo() {
		getPeriodos().add(new Periodo());
		alocaDisciplinasDoPrimeiroPeriodo();
	}
	
	private void alocaDisciplinasDoPrimeiroPeriodo() { 
		String[] nomesDasDisciplinas = {"Calculo Dif. e Int. 1", "Algebra Vet. e Geom. Analitica", 
				"Lab. de Programacao 1", "Programacao 1", 
				"Introducao a Computacao"};
		
		for(String nome : nomesDasDisciplinas) {
			int numeroDeCreditos = grade.getDisciplina(nome).getNumeroDeCreditos();
			try {
				getPeriodos().get(0).adicionaDisciplina(nome, numeroDeCreditos);
			} catch (LimitExceededException e) {
				e.printStackTrace();
			}
			grade.retiraDisciplina(nome);
		}	
	}
	
	public List<Periodo> getPeriodos() {
		return this.periodos;
	}
	
	
	public void adicionaPeriodo() {
		periodos.add(new Periodo());
	}
	
	private boolean preRequisitosSatisfeitos(int indicePeriodo, List<Disciplina> preRequisitos) {
		int contPreRequisitos = 0;
		if (!preRequisitos.isEmpty()) {
			for(int i = 0; i < indicePeriodo; i++) {
				for(Disciplina disciplinaPreRequisito : preRequisitos) {
					if(periodos.get(i).disciplinasAlocadas().contains(disciplinaPreRequisito)) {
						contPreRequisitos ++;
					}
				}
			}
		} else {
			return true;
		}
		return contPreRequisitos == preRequisitos.size();
	}

	public void adicionaDisciplinaAoPeriodo(int indicePeriodo, String nome, int numeroDeCreditos) 
			throws AlocacaoInvalidaException, LimitExceededException {
		if(grade.getDisciplina(nome) != null) {
			adicionarDisciplinaSePreRequisitosSatisfeitos(indicePeriodo, nome, numeroDeCreditos, 
					grade.getDisciplina(nome).getPreRequisitos());
				grade.retiraDisciplina(nome);
		}
	}

	public Disciplina getDisciplinaDaGrade(String nome) {
		return grade.getDisciplina(nome);
	}
	
	public boolean periodoComCreditosAbaixoDoLimiteMinimo(int idPeriodo) {
		return getPeriodo(idPeriodo).abaixoDoLimiteMinimoDeCreditos();
	}

	public void devolveDisciplinaParaGrade(String nomeDaDisciplina) {
		int indicePeriodo = indicePeriodoDeDisciplina(nomeDaDisciplina);
		Disciplina aRemover = getPeriodo(indicePeriodo).getDisciplina(nomeDaDisciplina);
		grade.adicionaDisciplina(nomeDaDisciplina);
		handlerDisciplinas.add(aRemover);
		for(int i = indicePeriodo+1; i < periodos.size(); i++) {
			for(Disciplina disciplina : periodos.get(i).disciplinasAlocadas()) {
				if(disciplina.getPreRequisitos().contains(aRemover))
					devolveDisciplinaParaGrade(disciplina.getNome());
			}
		}
	}
	
	public int indicePeriodoDeDisciplina(String nome) {
		for(int i = 0; i < periodos.size(); i++)
			if(periodos.get(i).getDisciplina(nome) != null)
				return i;
		return -1;
	}
	
	private void removeDisciplina(String nomeDaDisciplina) {
		for(int i = 0; i < periodos.size(); i++)
			for(Disciplina disciplina : periodos.get(i).disciplinasAlocadas())
				if(disciplina.getNome().equals(nomeDaDisciplina)) {
					periodos.get(i).removeDisciplina(nomeDaDisciplina);
					return;
				} 	
	}
	
	public void removeDisciplinasDesalocadas() {
		for(Disciplina disciplina : handlerDisciplinas) {
			removeDisciplina(disciplina.getNome());
		}
	}
		
	public List<Disciplina> getDisciplinasAlocadas() {
		List<Disciplina> alocadas = new ArrayList<Disciplina>();
		for(int i = 1; i < periodos.size(); i++) {
			if(! periodos.get(i).disciplinasAlocadas().isEmpty()) {
				alocadas.addAll(periodos.get(i).disciplinasAlocadas());
			}
		}
		return alocadas;
	}
}

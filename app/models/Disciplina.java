package models;

import java.util.ArrayList;
import java.util.List;


public class Disciplina implements Comparable<Disciplina> {
	
	private String nome;
	private int numeroDeCreditos;
	private List<Disciplina> preRequisitos;
	public Disciplina(String nome, int numeroDeCreditos) {
		this(nome, numeroDeCreditos, new ArrayList<Disciplina> ());
	}
	
	public Disciplina(String nome, int numeroDeCreditos, List<Disciplina> preRequisitos) {
		this.nome = nome;
		this.numeroDeCreditos = numeroDeCreditos;
		this.preRequisitos =  preRequisitos;
	}
	 /**
	  * get da nome da disciplina
	  * @return nome da  disciplina
	  */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * get do numero de creditos da disciplina
	 * @return numero de creditos
	 */
	public int getNumeroDeCreditos() {
		return this.numeroDeCreditos;
	}
	
	/**
	 * get dos pe-requisitos da disciplina
	 * @return pre-requisitos
	 */
	public List<Disciplina> getPreRequisitos() {
		return this.preRequisitos;
	}
	
	/**
	 * Equals
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Disciplina)) {
			return false;
		}
		return ((Disciplina) obj).getNome().equals(nome) 
				&& ((Disciplina) obj).getNumeroDeCreditos() == numeroDeCreditos;
	}
	
		@Override
	public String toString() {
		return this.getNome();
	}
    
	@Override
	public int compareTo(Disciplina outraDisciplina) {
		return this.getNome().compareTo(outraDisciplina.getNome());
	}

}

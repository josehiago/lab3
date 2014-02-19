package form;

import play.data.validation.Constraints.*;



public class FormHandler {
	
	@Required
	private String inputNameDisciplina;
	
	@Required
	private int idPeriodo;
	
	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	public int getIdPeriodo() {
		return idPeriodo;
	}
	
	public void setInputNameDisciplina(String inputNameDisciplina) {
		this.inputNameDisciplina = inputNameDisciplina;
	}
	
	public String getInputNameDisciplina() {
		return inputNameDisciplina;
	}
	

}

package controllers;

import javax.naming.LimitExceededException;


import exceptios.AlocacaoInvalidaException;
import form.FormHandler;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {
	
	static SistemaDePlanejamentoDeCurso sistema = new SistemaDePlanejamentoDeCurso();
	static Form<FormHandler> formHandler = Form.form(FormHandler.class);
	static String message = "";

    public static Result index() {
    	return redirect(routes.Application.planejamentoDeCurso());
    }
    
    public static Result planejamentoDeCurso() {
    	String tempMsg = message; 
    	message = ""; 
    	return ok(views.html.index.render(sistema, formHandler, tempMsg));
    }
    
    public static Result novoPeriodo() {
    	sistema.adicionaPeriodo();
    	return redirect(routes.Application.planejamentoDeCurso());
    }
    
    public static Result adicionaDisciplinaEmPeriodo() {
    	Form<FormHandler> form = formHandler.bindFromRequest();
    	int numeroDeCreditos = sistema.getDisciplinaDaGrade(form.get().getInputNameDisciplina()).
    			getNumeroDeCreditos();
    	int idPeriodo = form.get().getIdPeriodo()-1;
    	try {
    		sistema.adicionaDisciplinaAoPeriodo(idPeriodo, form.get().getInputNameDisciplina(), 
    				numeroDeCreditos);
    	}
    	catch(AlocacaoInvalidaException | LimitExceededException e) {
    		 message = e.getMessage();
    	}
    	return redirect(routes.Application.planejamentoDeCurso());
    }
    
    public static Result desalocarDisciplinaDePeriodo() {
    	Form<FormHandler> form = formHandler.bindFromRequest();
    	sistema.devolveDisciplinaParaGrade(form.get().getInputNameDisciplina());
    	sistema.removeDisciplinasDesalocadas();
    	return redirect(routes.Application.planejamentoDeCurso());
    }

}

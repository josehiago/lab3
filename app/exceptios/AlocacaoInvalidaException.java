package exceptios;


 
public class AlocacaoInvalidaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Cria uma nova AlocacaoInvalidaException.
	 * @param mensagemDeErro A mensagem da excecao.
	 */
	public AlocacaoInvalidaException(String mensagemDeErro) {
		super(mensagemDeErro);
	}
	

}

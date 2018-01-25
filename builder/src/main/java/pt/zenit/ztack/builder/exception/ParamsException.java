package pt.zenit.ztack.builder.exception;

/**
 * Paramsexception
 * 		Excepção customizada para diferenciar todos os erros que têem origem nos parametros de entrada da aplicação
 */
public class ParamsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParamsException(String message) {
		super(message);
	}

	public ParamsException(Exception exception) {
		super(exception);
	}

	public ParamsException(String message, Exception exception) {
		super(message, exception);
	}

}

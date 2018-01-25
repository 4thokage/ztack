package pt.zenit.ztack.builder.exception;

/**
 * UtilsException
 * 	Excepção customizada para diferenciar todos os erros que têem origem na invocação de uma função utililtária.
 */
public class UtilsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UtilsException(String message) {
		super(message);
	}
	
	public UtilsException(Exception exception) {
		super(exception);
	}
	
	public UtilsException(String message, Exception exception) {
		super(message, exception);
	}

}

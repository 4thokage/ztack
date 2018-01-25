package pt.zenit.ztack.builder.exception;

/**
 * ComposerException
 * 	Excepção customizada para diferenciar todos os erros que fazem parte da composição dos ficheiros
 */
public class ComposerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ComposerException(String message) {
		super(message);
	}

	public ComposerException(Exception exception) {
		super(exception);
	}

	public ComposerException(String message, Exception exception) {
		super(message, exception);
	}

}

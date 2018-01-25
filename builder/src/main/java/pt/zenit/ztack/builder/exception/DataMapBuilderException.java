package pt.zenit.ztack.builder.exception;

import org.apache.cayenne.map.DataMap;

/**
 * DataMapBuilderException
 * 	Excepção customizada para diferenciar todos os erros que fazem parte da geração de um {@link DataMap} de Apache Cayenne.
 */
public class DataMapBuilderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataMapBuilderException(String message) {
		super(message);
	}

	public DataMapBuilderException(Exception exception) {
		super(exception);
	}

	public DataMapBuilderException(String message, Exception exception) {
		super(message, exception);
	}

}

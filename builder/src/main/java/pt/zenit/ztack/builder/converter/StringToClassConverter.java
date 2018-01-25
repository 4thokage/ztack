package pt.zenit.ztack.builder.converter;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import pt.zenit.ztack.builder.constants.Constants;

/**
 * StringToClassConverter - Converte uma String na class com o respectivo nome.
 */
public class StringToClassConverter implements IStringConverter<Class> {

    private static final Logger logger = LoggerFactory.getLogger(StringToClassConverter.class);

    @Override
    public Class convert(String value) {
        Class c;
        String defaultValue = Constants.DefaultValues.EXCEL_MODELS_PATH.getValue() + value;
        try {
            c = Class.forName(defaultValue);
        } catch (ClassNotFoundException e) {
            logger.error("Classe não encontrada: " + defaultValue);
            throw new ParameterException("Classe "+ defaultValue +" introduzida no argumento --template não foi encontrada!");
        }
        return c;
    }
}

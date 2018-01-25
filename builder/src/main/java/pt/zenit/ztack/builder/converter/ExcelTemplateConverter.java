package pt.zenit.ztack.builder.converter;

import com.beust.jcommander.IStringConverter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import pt.zenit.ztack.builder.constants.Constants;

/**
 * ExcelTemplateConverter - Converte uma String no {@link Constants.ExcelTemplates} com o respectivo nome.
 */
public class ExcelTemplateConverter implements IStringConverter<Constants.ExcelTemplates> {

    private static final Logger logger = LoggerFactory.getLogger(StringToClassConverter.class);

    @Override
    public Constants.ExcelTemplates convert(String value) {
        return Constants.ExcelTemplates.valueOf(value);
    }
}

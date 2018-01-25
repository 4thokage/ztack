package pt.zenit.ztack.builder.excel.impl;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.sheet.XceliteSheet;
import pt.zenit.ztack.builder.ApplicationArguments;
import pt.zenit.ztack.builder.excel.ExcelExtractor;

import java.util.Collection;

/**
 * Implementação simples de um {@link ExcelExtractor} pode ser usada para implementar codigo com regras simples de negócio.
 */
public class DefaultExcelExtractor implements ExcelExtractor{

    @Override
    public Collection<Object> convertExcelDataToObjectCollection(ApplicationArguments arguments) {
        Xcelite xcelite = new Xcelite(arguments.excelFile);

        if(arguments.sheetName != null) {
            XceliteSheet sheet = xcelite.getSheet(arguments.sheetName);
            return sheet.getBeanReader(arguments.templateClass).read();
        } else {
            XceliteSheet sheet = xcelite.getSheet(arguments.sheetIndex);
            return sheet.getBeanReader(arguments.templateClass).read();
        }
    }

}

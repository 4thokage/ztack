package pt.zenit.ztack.builder.excel.impl;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.exceptions.XceliteException;
import com.ebay.xcelite.sheet.XceliteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.excel.ExcelProcessor;

import java.io.File;

/**
 * Classe default de processamento excel
 */
public class DefaultExcelProcessor implements ExcelProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExcelProcessor.class);


    @Override
    public void processFile(File inputFile, Constants.ExcelTemplates template)
    {
        Xcelite xcelite = new Xcelite(inputFile);

        /** Obter a folha de configuração */
        try {
            XceliteSheet sheetFases = xcelite.getSheet(Constants.ExcelSheets.CONFIG.getValue());


        } catch (XceliteException xe) {
            logger.error("Folha de configuração não encontrada, processamento manual");
            promptExcelConfig();
        }
    }

    private void promptExcelConfig() {
    }
}

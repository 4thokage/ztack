package pt.zenit.ztack.builder.service;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.builder.excel.ExcelProcessor;

import java.io.File;

/**
 * Serviço que contém todos os componentes que vão preparar o ficheiro excel antes da sua utilização para gerar código. ({@link ExcelProcessor})
 */
public class ProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorService.class);

    /** Processador de ficheiros de Excel. */
    private final ExcelProcessor excelProcessor;

    @Inject
    public ProcessorService(ExcelProcessor excelProcessor) {
        this.excelProcessor = excelProcessor;
    }


    /** Invoca o método {@link ExcelProcessor#processFile(File)}
     *
     * @param inputFile Ficheiro {@link File} a ser processado.
     */
    public void preProcessExcelFile(File inputFile) {
        logger.info("A processar o ficheiro {}", inputFile.getAbsolutePath());
    }
}




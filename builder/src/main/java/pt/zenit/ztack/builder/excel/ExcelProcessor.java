package pt.zenit.ztack.builder.excel;

import pt.zenit.ztack.builder.constants.Constants;

import java.io.File;

/**
 * Interface de processamento dos ficheiros de Excel.
 */
public interface ExcelProcessor {

    void processFile(File inputFile, Constants.ExcelTemplates template);
}

package pt.zenit.ztack.builder.excel.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.excel.ExcelProcessor;

import java.io.File;

/**
 * Created by jsilvaro on 31-05-2017.
 */
public class TuaExcelProcessor implements ExcelProcessor {

    public static final Logger logger = LoggerFactory.getLogger(TuaExcelProcessor.class);
    @Override
    public void processFile(File inputfile , Constants.ExcelTemplates template) {
        logger.info("TDB: blame José!!");
    }
}

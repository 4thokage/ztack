package pt.zenit.ztack.builder.excel;

import pt.zenit.ztack.builder.ApplicationArguments;

import java.util.Collection;

/**
 * Classse que permite extrair dados de um {@link java.io.File} do tipo .xlsx (Excel) e traduzir esses dados para um Objecto.
 */
public interface ExcelExtractor<T> {

    /** Obtém uma {@link java.util.Collection} de Objectos do tipo passado por parametro obtidos através dos dados existentes no ficheiro excel que está a ser processado. */
    Collection<T> convertExcelDataToObjectCollection(ApplicationArguments arguments);
}

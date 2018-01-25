package pt.zenit.ztack.builder.module;

import com.google.inject.AbstractModule;


/**
 * Classe que interliga cada componente reponsavel pela preparação do ficheiro Excel.
 */
public class ProcessorModule extends AbstractModule{
    @Override
    protected void configure() {

        //bind(ExcelProcessor.class).to(CeleExcelProcessor.class);
    }
}

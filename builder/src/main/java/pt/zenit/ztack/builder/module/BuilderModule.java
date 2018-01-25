package pt.zenit.ztack.builder.module;

import com.google.inject.AbstractModule;
import pt.zenit.ztack.builder.cayenne.DataMapBuilder;
import pt.zenit.ztack.builder.cayenne.impl.DefaultDataMapBuilder;
import pt.zenit.ztack.builder.composer.VelocityComposer;
import pt.zenit.ztack.builder.composer.impl.DefaultVelocityComposer;
import pt.zenit.ztack.builder.excel.ExcelExtractor;
import pt.zenit.ztack.builder.excel.impl.DefaultExcelExtractor;

/**
 * Classe que interliga cada componente reponsavel por gerar uma aplicação a uma classe respetiva, isto permite um funcionamento diferente para diferentes regras de negócio.
 */
public class BuilderModule extends AbstractModule{
    @Override
    protected void configure() {

        bind(ExcelExtractor.class).to(DefaultExcelExtractor.class);

        bind(VelocityComposer.class).to(DefaultVelocityComposer.class);

        bind(DataMapBuilder.class).to(DefaultDataMapBuilder.class);
    }
}

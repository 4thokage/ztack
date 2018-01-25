package pt.zenit.ztack.builder.service;
import com.google.inject.Inject;
import org.apache.cayenne.access.DataDomain;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import pt.zenit.ztack.builder.ApplicationArguments;
import pt.zenit.ztack.builder.cayenne.DataMapBuilder;
import pt.zenit.ztack.builder.composer.VelocityComposer;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.excel.ExcelExtractor;

import java.io.FileNotFoundException;
import java.util.Collection;

/**
 * Serviço que contém todos os componentes que vão construir a aplicação ({@link ExcelExtractor}, {@link VelocityComposer})
 */
public class BuilderService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BuilderService.class);

    /** Conversor de dados Excel para objectos Java. */
    private final ExcelExtractor excelExtractor;

    /** Gerador de código c/ Apache Velocity */
    private final VelocityComposer velocityComposer;

    /** Construtor de DataMaps do cayenne (ORM) */
    private final DataMapBuilder dataMapBuilder;

    @Inject
    public BuilderService(ExcelExtractor excelExtractor, VelocityComposer velocityComposer, DataMapBuilder dataMapBuilder) {
        this.excelExtractor = excelExtractor;
        this.velocityComposer = velocityComposer;
        this.dataMapBuilder = dataMapBuilder;
    }

    /** Invoca o método {@link ExcelExtractor#convertExcelDataToObjectCollection(ApplicationArguments)}
     *
     * @param args  {@link ApplicationArguments} passados por parametro
     */
    @SuppressWarnings("unchecked")
    public Collection<T> getJavaObjectsFromExcel(ApplicationArguments args) {
        logger.info("A obter dados do ficheiro: {}",args.excelFile.getName());
        return excelExtractor.convertExcelDataToObjectCollection(args);
    }


    @SuppressWarnings("unchecked")
    public void composeCayenneDataMap(Collection data, ApplicationArguments args) throws FileNotFoundException {
        logger.info("A obter os DataMaps do Cayenne");
        DataDomain dataDomain = dataMapBuilder.loadExistingDataDomain();

        logger.info("A criar datamap");
        //dataMapBuilder.generateDatamapFile(dataDomain, data, args);
        logger.info("Datamap criado");
    }

    /** Invoca o método {@link VelocityComposer#composeBeans(String, ApplicationArguments, String)}
     *  @param data {@link Collection} de objectos obtidos do ficheiro de excel passado como parametro
     * @param args {@link ApplicationArguments} argumentos de entrada da aplicação
     */
    @SuppressWarnings("unchecked")
    public void generateApplicationFiles(Collection<T> data, ApplicationArguments args) {
        
    	String templateDirectory = Constants.DefaultValues.DEFAULT_TEMPLATE_DIR.getValue();
    	
        logger.info("A gerar os Services");
        velocityComposer.composeServices(templateDirectory, args);

        logger.info("A gerar os ficheiros properties");
        velocityComposer.composePropertiesFiles(templateDirectory, args, data);

        logger.info("A gerar as Views");
        velocityComposer.composeViews(templateDirectory, args, data);

        logger.info("A gerar os Beans");
        velocityComposer.composeBeans(templateDirectory, args, data);
    }
}

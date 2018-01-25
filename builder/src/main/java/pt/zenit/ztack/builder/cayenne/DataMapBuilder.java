package pt.zenit.ztack.builder.cayenne;

import org.apache.cayenne.access.DataDomain;
import org.apache.cayenne.map.DataMap;
import pt.zenit.ztack.builder.ApplicationArguments;

import java.io.FileNotFoundException;
import java.util.Collection;


/**
 * Classe de tratamento de um DataMap do Cayenne.
 *      Carrega os datamaps necessários ao projecto;
 *      Converte Objectos Java para DataMaps;
 *      Cria um DataMap novo a ser inserido no projecto, ou altera um DataMap pré-existente.
 */
public interface DataMapBuilder<T> {

    /**
     * Carrega para um {@link DataDomain} todos {@link DataMap} contidos na pasta.
     *
     * @return {@link DataDomain} com todos os {@link DataMap} necessários ao projecto
     */
    DataDomain loadExistingDataDomain() throws FileNotFoundException;

    /**
     * Método que através dos dados obtidos do ficheiro excel, vai criar novas {@link org.apache.cayenne.map.Entity} do
     * Cayenne e vai adicionar a/construir um {@link DataMap} com a representação desses objectos.
     *
     * @param dataDomain {@link DataDomain} (ou conjunto de {@link DataMap}) de suporte
     * @param data {@link Collection} dos objectos que foram extraidos do Excel
     * @param arguments {@link ApplicationArguments} parametros de entrada da applicação
     * @throws FileNotFoundException
     */
    void generateDatamapFile(DataDomain dataDomain, Collection<T> data, ApplicationArguments arguments) throws FileNotFoundException;
}

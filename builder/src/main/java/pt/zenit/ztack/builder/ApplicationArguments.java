package pt.zenit.ztack.builder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.converter.ExcelTemplateConverter;
import pt.zenit.ztack.builder.excel.model.DefaultComponent;
import pt.zenit.ztack.builder.converter.StringToClassConverter;

import java.io.File;

/**
 * Classe que contém a parametrização dos argumetnos da aplicação.
 *
 * <pre>
 *     Ler documentação (manual.pdf) para mais indicações sobre as flags de entrada
 * </pre>
 *
 * @author jsilvaro
 * @version 2018-01-26
 */
public class ApplicationArguments {

    /** Ficheiro que contém as proptiedades da aplicação (como esta classe, mas em file) */
    @Parameter(names = { "--config-file", "-cf"}, description = "ficheiro de configuração da aplicação", converter = FileConverter.class)
    File appPropertiesFile;

    /** Ficheiro a processar, deve ser do tipo .xlsx (Excel). */
    @Parameter(names = { "--base-file", "-bf" }, description = "ficheiro input (apenas suporta Excel2007+)", required = true, converter = FileConverter.class)
    public File excelFile;

    /** Template do Ficheiro a processar, por default utiliza o formato de ficheiro standard (ver manual). */
    @Parameter(names = { "--base-file-template", "-bft" }, description = "Template ficheiro Excel", converter = ExcelTemplateConverter.class)
    Constants.ExcelTemplates excelTemplate = Constants.ExcelTemplates.DEFAULT;

    /** Nome da folha do ficheiro Excel (parametro --file ou -f) a ser processada, se for vazio usa a primeira folha do ficheiro. */
    @Parameter(names = { "--sheet-name", "-sn" }, description = "nome da folha do documento")
    public String sheetName;

    /** Número da folha do ficheiro Excel (parametro --file ou -f) a ser processada, por default, usa a primeria folha do ficheiro. */
    @Parameter(names = { "--sheet-index", "-si" }, description = "numero de folha do documento (starts @0!")
    public int sheetIndex = 0;

    /** Nome do package dos objectos que representam uma tabela em BD, ao preencher vai ser efetuado um override de todos os dados relativos ao schema presentes no Excel. */
    @Parameter(names = { "--package","-p" }, description = "Nome do package completo das classes cayenne a ser geradas")
    public String packageName = "org.apa.siliamb.cayenne";

    /** Nome do schema BD ao qual pertence a tabela, ao preencher vai ser efetuado um override de todos os dados relativos ao schema presentes no Excel.  */
    @Parameter(names = { "--db-schema", "-ds" }, description = "Nome do schema, será também o nome do datamap.map.xml")
    public String schemaName;

    /** Vai apenas gerar a parte do DataMap da aplicação (util p.ex na geração de listas de valores) */
    @Parameter(names = "--skipApplicationFiles", description = "cria só o dataMap da applicação")
    boolean skipAppFiles = false;

    /** Mostra (em stdout) os parametros de entrada que podem ser utilizados ao chamar a aplicação. */
    @Parameter(names = "--help", help = true)
    boolean help = false;

    /** Vai invocar um ExcelProceessor para pre Processar o Ficheiro */
    @Parameter(names = "--process", description = "Pré processa o Excel para estar pronto a ser convertido")
    boolean process = false;

}

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
 */
public class ApplicationArguments {

    /** Ficheiro a processar, deve ser do tipo .xlsx (Excel). */
    @Parameter(names = { "--file", "-f" }, description = "ficheiro input (apenas suporta Excel2007+)", required = true, converter = FileConverter.class)
    public File excelFile;

    /** Template do Ficheiro a processar, por default utiliza o formato de ficheiro standard (ver manual). */
    @Parameter(names = { "--fileType", "-ft" }, description = "Template ficheiro Excel", converter = ExcelTemplateConverter.class)
    public Constants.ExcelTemplates excelTemplate = Constants.ExcelTemplates.DEFAULT;

    /** Nome da folha do ficheiro Excel (parametro --file ou -f) a ser processada, se for vazio usa a primeira folha do ficheiro. */
    @Parameter(names = { "--sheet", "-sn" }, description = "nome da folha do documento")
    public String sheetName;

    /** Número da folha do ficheiro Excel (parametro --file ou -f) a ser processada, por default, usa a primeria folha do ficheiro. */
    @Parameter(names = { "--sheetIndex", "-si" }, description = "numero de folha do documento (starts @0!")
    public int sheetIndex = 0;

    /** Nome do package dos objectos que representam uma tabela em BD, ao preencher vai ser efetuado um override de todos os dados relativos ao schema presentes no Excel. */
    @Parameter(names = { "--package","-p" }, description = "Nome do package completo das classes cayenne a ser geradas")
    public String packageName = "org.apa.siliamb.cayenne";

    /** Nome do schema BD ao qual pertence a tabela, ao preencher vai ser efetuado um override de todos os dados relativos ao schema presentes no Excel.  */
    @Parameter(names = { "--schema", "-s" }, description = "Nome do schema, será também o nome do datamap.map.xml")
    public String schemaName;

    /** Nome do JSF Bean principal a ser gerado, se estiver vazio vai utilizar o a constante Default como valor.  */
    @Parameter(names = { "--controller", "-cn" }, description = "Nome a utilizar para o controller principal")
    public String controllerName = Constants.DefaultValues.DEFAULT_BEAN_NAME.getValue();

    /** Vai apenas gerar a parte do DataMap da aplicação (util p.ex na geração de listas de valores) */
    @Parameter(names = "--skipApplicationFiles", description = "cria só o dataMap da applicação")
    public boolean skipAppFiles = false;

    /** Mostra (em stdout) os parametros de entrada que podem ser utilizados ao chamar a aplicação. */
    @Parameter(names = "--help", help = true)
    public boolean help = false;

    /** Vai invocar um ExcelProceessor para pre Processar o Ficheiro */
    @Parameter(names = "--process", description = "Pré processa o Excel para estar pronto a ser convertido")
    public boolean process = false;

    /** Vai utilizar os caminhos presentes no ficheiro "Compile & Deploy.bat" para compilar e efetuar o deploy do projecto */
    @Parameter(names = "--deploy", description = "No fim da geração da aplicação faz deploy no servidor (local).")
    public boolean deploy = false;

    /** Vai iniciar (se existirem) os testes automaticos*/
    @Parameter(names = "--test", description = "Inicia Testes automaticos no Servidor (local).")
    public boolean test = false;

    /** Objecto que vai suportar os dados necessários á construção da aplicação, que vão ser populados atravéz do ficheiro excel, se estiver vazio usa por default {@link  DefaultComponent} */
    @Parameter(names= "--template", description = "Classe de suporte aos dados.", converter = StringToClassConverter.class)
    public Class templateClass = DefaultComponent.class;

}

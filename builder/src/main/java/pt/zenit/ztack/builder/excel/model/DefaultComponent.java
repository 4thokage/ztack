package pt.zenit.ztack.builder.excel.model;


import com.ebay.xcelite.annotations.Column;
import pt.zenit.ztack.builder.ApplicationArguments;
import pt.zenit.ztack.builder.constants.Constants;

/**
 * Classe que representa um objecto Cayenne
 *
 * 
 * @author everis
 */
public class DefaultComponent {

    public static final String GEN_MODE = Constants.DefaultValues.DEFAULT_GEN_MODE.getValue();

    //region Fields
    /** Schema do objecto, se estiver vazio vai ser utilizado o schemaName em {@link ApplicationArguments} (passado por parametro) */
	@Column(name = "schemaName")
	private String schemaName;

	/** Definição do package da classe, se estiver vazio vai ser utilizado o packageName em {@link ApplicationArguments} (passado por parametro) */
	@Column (name = "packageName")
	private String packageName;

	/** Designação da tabela (nome da tabela em BD) */
	@Column (name = "tableName")
	private String tableName;

	/** Texto Texto a apresentar para o nome da tabela (portugues) */
	@Column(name= "ptTableDescription")
	private String ptTableDescription;

    /** Texto Texto a apresentar para o nome da tabela (portugues) */
    @Column(name= "enTableDescription")
    private String enTableDescription;

    /** Designação de um atributo da tabela (nome de coluna tabela em BD) */
	@Column(name="columnName")
	private String columnName;

    /** Texto Apresentar para o campo da tabela (portugues) */
	@Column(name="ptColumnDescription")
	private String ptColumnDescription;

    /** Texto Apresentar para o campo da tabela (ingles) */
    @Column(name="enColumnDescription")
    private String enColumnDescription;

	/** Tipo de dados da coluna */
	@Column(name="columnType")
	private String columnType;

	/** Tamanho maximo dos dados da respectiva coluna */
	@Column(name="columnSize")
	private String columnSize;

	/** Obrigatóriedade do preenchimento da coluna */
	@Column(name="requiredString")
	private String requiredString;

    /** Regras de validação, devem seguir um padrao */
    @Column(name="validationRules")
    private String validationRules;

    /** indicação de uma {@link  DefaultComponent tableName}
     * para criação de chave estrangeira entre o atributo de origem e a coluna id da {@link DefaultComponent targetTable}
     */
    @Column(name="targetTable")
    private String targetTable;

    /** indicação de uma {@link  DefaultComponent tableName}
     * para criação de chave estrangeira entre o atributo de origem e a coluna id da {@link DefaultComponent targetTable}
     */
    @Column(name="toManyTarget")
    private String toManyTarget;

    /** Texto informativo do campo (a apresentar como toolltip) PT */
    @Column(name="ptInfoText")
    private String ptInfoText;

    /** Texto informativo do campo (a apresentar como tooltip) EN */
    @Column(name="enInfoText")
    private String enInfoText;
    //endregion

    //region Getters/Setters
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPtTableDescription() {
        return ptTableDescription;
    }

    public void setPtTableDescription(String ptTableDescription) {
        this.ptTableDescription = ptTableDescription;
    }

    public String getEnTableDescription() {
        return enTableDescription;
    }

    public void setEnTableDescription(String enTableDescription) {
        this.enTableDescription = enTableDescription;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPtColumnDescription() {
        return ptColumnDescription;
    }

    public void setPtColumnDescription(String ptColumnDescription) {
        this.ptColumnDescription = ptColumnDescription;
    }

    public String getEnColumnDescription() {
        return enColumnDescription;
    }

    public void setEnColumnDescription(String enColumnDescription) {
        this.enColumnDescription = enColumnDescription;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getRequiredString() {
        return requiredString;
    }

    public void setRequiredString(String requiredString) {
        this.requiredString = requiredString;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }


    public String getToManyTarget() {
        return toManyTarget;
    }

    public void setToManyTarget(String toManyTarget) {
        this.toManyTarget = toManyTarget;
    }

    public String getPtInfoText() {
        return ptInfoText;
    }

    public void setPtInfoText(String ptInfoText) {
        this.ptInfoText = ptInfoText;
    }

    public String getEnInfoText() {
        return enInfoText;
    }

    public void setEnInfoText(String enInfoText) {
        this.enInfoText = enInfoText;
    }

    public String getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(String validationRules) {
        this.validationRules = validationRules;
    }
//endregion

    @Override
    public String toString() {
        return "DefaultComponent{" +
                "schemaName='" + schemaName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", ptTableDescription='" + ptTableDescription + '\'' +
                ", enTableDescription='" + enTableDescription + '\'' +
                ", columnName='" + columnName + '\'' +
                ", ptColumnDescription='" + ptColumnDescription + '\'' +
                ", enColumnDescription='" + enColumnDescription + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnSize='" + columnSize + '\'' +
                ", requiredString='" + requiredString + '\'' +
                ", targetTable='" + targetTable + '\'' +
                ", ptInfoText='" + ptInfoText + '\'' +
                ", enInfoText='" + enInfoText + '\'' +
                ", validationRules='" + validationRules + '\'' +
                '}';
    }
}

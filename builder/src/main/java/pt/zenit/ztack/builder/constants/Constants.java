package pt.zenit.ztack.builder.constants;

public class Constants {

	public enum Languages {
        EN ("_en"),
		PT ("_pt");


		private String suffix;


        Languages(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return suffix;
        }
    }

    public enum ExcelTemplates {
		DEFAULT,
		LUA,
		FLUXOS;
	}

	public enum ExcelSheets {
	    CONFIG  ("config"),
        TABELAS ("tabelas");

	    private String value;

	    ExcelSheets(String value) {
	        this.value = value;
        }

        public String getValue() {
	        return value;
        }
    }

    public enum ViewTemplates {
	    INFO    ("Info"),
        FORM    ("Form");

	    private String value;

	    ViewTemplates(String value) {
	        this.value = value;
        }

        public String getValue() {
	        return value;
        }
    }

	public enum DefaultValues{

		DATA_MAP_FILES_PATH			    ("./src/main/resources/datamaps"),
        VELOCITY_PROPERTIES_PATH        ("velocity.properties"),

        DEFAULT_ENCODING                ("UTF-8"),
		DEFAULT_COLUMN_SIZE			    ("500"),
		DEFAULT_DATA_DOMAIN 		    ("APA"),
		DEFAULT_DB_SCHEMA 			    ("tabelas"),
		DEFAULT_BEAN_NAME				("TesteController"),

		DEFAULT_TEMPLATE_DIR 		    ("/default"),
		DEFAULT_SERVICE_OUTPUT_PATH     ("org/apa/siliamb/fe/services"),
		DEFAULT_BEAN_OUTPUT_PATH  		("org/apa/siliamb/teste/bean"),
		DEFAULT_WS_OUTPUT_PATH  		("org/apa/siliamb/fe/ws"),
        DEFAULT_PROPERTIES_OUTPUT_PATH  ("resources/pages/teste"),
        DEFAULT_VIEW_OUTPUT_PATH        ("pages/public/teste"),
        DEFAULT_INSERTS_OUTPUT_PATH     ("sql/001-ddl.sql"),

		DEFAULT_GEN_MODE			    (""),
		DEFAULT_GEN_MODE_LV 		    (""),

		EXCEL_MODELS_PATH 			    ("pt.apa.dbutils.excel.model."),
		OUTPUT_DIR 					    ("./output/"),

		APP_TITLE 					    ("Gerador de modulos 'siliamb' APA/Everis."),

		FXML_FILE_PATH 				    ("/gui/composer/composer.fxml"),
		PDF_MANUAL_PATH 			    ("./manual-dbutils.pdf"),

        MAX_LABELS_PER_FILE             ("420"),
		MAX_VALID_DATE					("2050-01-01 00:00:00"),

		TRUE_PT 					    ("Sim"),
		TRUE_EN						    ("Yes"),
		FALSE_PT 					    ("Não"),
		FALSE_EN 					    ("No"),

        REGEX_MATCH_HIFEN_SPACES        ("[-\\s+]"),
        REGEX_MATCH_NON_LETTERS         ("[\\W]|_");

		private String value;

		DefaultValues(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum ComposerFiles {
		DBUTILS_FILE	("Ficheiros DBUtils", "(*.dbu)"),
		COMPILE_BAT		("Compile & Deploy", "(*.bat)");

		private String fileDescription;
		private String fileExtension;

		ComposerFiles(String fileDescription, String fileExtension) {
			this.fileDescription = fileDescription;
			this.fileExtension = fileExtension;
		}

		public String getFileDescription() {
			return fileDescription;
		}

		public String getFileExtension() {
			return fileExtension;
		}
	}

	public enum DefaultColumns {
		ID_COLUMN ("id", "Int", 11, true, true, "PK"),
		DATA_INICIO ("data_inicio", "Data", 0, false, true, "DATA_INICIO"),
		DATA_FIM ("data_fim", "Data", 0, false, true, "DATA_FIM"),
        CODIGO ("codigo", "Texto", 255, false, true, "CODIGO"),
        LABEL ("label", "Texto", 255, false, true, "LABEL");

		private String columnName;
		private String columnType;
		private int columnSize;
		private boolean isPrimaryKey;
		private boolean isRequired;
		private String columnCodeName;


		DefaultColumns(String columnName, String columnType, int columnSize, boolean isPrimaryKey, boolean isRequired, String columnCodeName) {
			this.columnName = columnName;
			this.columnType = columnType;
			this.columnSize = columnSize;
			this.isPrimaryKey = isPrimaryKey;
			this.isRequired = isRequired;
			this.columnCodeName = columnCodeName;
		}

		//region Getters/setters
		public String getColumnName() {
			return columnName;
		}

		public String getColumnType() {
			return columnType;
		}

		public int getColumnSize() {
			return columnSize;
		}

		public boolean isPrimaryKey() {
			return isPrimaryKey;
		}

		public boolean isRequired() {
			return isRequired;
		}

		public String getColumnCodeName() {
			return columnCodeName;
		}
		//endregion
	}
}
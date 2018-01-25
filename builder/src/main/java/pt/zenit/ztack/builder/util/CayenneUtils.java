package pt.zenit.ztack.builder.util;

import static java.util.stream.Collectors.groupingBy;
import static org.apache.cayenne.dba.TypesMapping.*;
import static java.sql.Types.*;

import java.io.File;
import java.sql.Types;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.cayenne.access.DataDomain;
import org.apache.cayenne.dba.TypesMapping;

import com.google.common.base.CaseFormat;

import org.apache.cayenne.map.*;
import org.apache.commons.lang.StringUtils;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.excel.model.DefaultComponent;
import pt.zenit.ztack.builder.excel.model.DefaultDropdown;
import pt.zenit.ztack.builder.exception.UtilsException;

/**
 * Classe de funções utilitárias
 *  NÂO DEVE SER INSTANCIADA
 * @author everis
 *
 */
public final class CayenneUtils {

	private CayenneUtils() {
		throw new UtilsException("Utility class, never instanciar!");
	}
	
	/**
	 * Método que agruda a lista de {@link DefaultComponent} pelo nome das tabelas
	 * 
	 * @param dbAttributes dados obtidos do ficheiro Excel
	 * @return o objecto map com a listas e a sua chave (nome de tabela) correspondente
	 */
	public static Map<String, List<DefaultComponent>> groupByTableName(Collection<DefaultComponent> dbAttributes) {
		return dbAttributes
				.stream()
				.collect(groupingBy(DefaultComponent::getTableName));
		
	}

	/**
	 * Processa o argumento {@String}, retornando o seu valor sem acentos, para facilitar a criação dos objectos/beans
	 * 
	 * @param value, String a ser processada
	 * @return a nova String normalizada.
	 */
	public static String normalizeValue(String value) {		
		return convertSpaceToUnderscore(Normalizer.normalize(value.trim(), Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]", "")).toLowerCase();

	}
	
	/**
	 * Troca todos os espaços (\\s) existentes na String por underscores
	 * 
	 * @param value, String a ser processada
	 * @return a nova String processada
	 */
	private static String convertSpaceToUnderscore(String value) {
		return value.replaceAll(" ", "_");	

	}
	
	/**
	 * Processa a String e retorna a mesma String com o primeiro caracter em lowerCase
	 * 
	 * @param value, String a ser processada
	 * @return a String com primeiro caracter em lowerCase
	 */
	public static String firstLowerCase(String value) {
		if (value == null || value.length() == 0) {
	        return value;
	    }
		char[] c = value.toCharArray();
		c[0] += 32;
		return new String(c);
	}
	
	/**
	 * Processa a String e retorna a mesma String com o primeiro caracter ca+pitalizado
	 * 
	 * @param value, String a ser processada
	 * @return a String com primeiro caracter capitalizado
	 */
	public static String firstUpperCase(String value) {
		if (value == null || value.length() == 0) {
	        return value;
	    }
		
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}

	/**
	 * Para a criação dos ObjEntitites, o cayenne utiliza a forma camelCase.
	 * 	Este método retorna a String nesse formato e sem underscores.
	 * 
	 * @param value, String a ser processada
	 * @return a nova String no formato camelCase
	 */
	public static String upperCaseOnUnderscore(String value) {

		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, value);
	}

	/**
	 * Converte uma {@link String} no formato camelCase
	 *
	 * @param value {@link String} a converter
	 * @return String convertida, ex, horas_do_dia > horasDoDia
	 */
	public static String convertToCamelCase(String value) {
		return firstLowerCase(upperCaseOnUnderscore(value));
	}

	/**
	 * Converte a propriedade "tipo" do {@link org.apache.cayenne.map.DbAttribute} no numero indicador do tipo correspondente
	 * 
	 * @see Types
	 * 
	 * @param value, String a ser processada
	 * @return a String correspondente ao valor do {@link org.apache.cayenne.map.DbAttribute}
	 */
	public static int convertSQLType(String value) {
		if(value == null) {
			return INTEGER;
		}
		switch (value.toLowerCase()) {
		case "int":
			return INTEGER;
		case "decimal":
			return DECIMAL;
		case "data":
			return TIMESTAMP;			
		case "boolean":
			return BOOLEAN;
		default:
			return VARCHAR;
		}

	}

	/**
	 * Converte a propriedade "tipo" do {@link org.apache.cayenne.map.DbAttribute} no tipo correspondente
	 * 
	 * @see TypesMapping
	 * 
	 * @param value, String a ser processada
	 * @return a String correspondente ao valor do {@link org.apache.cayenne.map.DbAttribute}
	 */
	public static String convertJDBCType(String value) {
		if(value == null) {
			return JAVA_INTEGER;
		}
		switch (value.toLowerCase()) {
		case "int":
			return JAVA_INTEGER;
		case "decimal":
			return JAVA_BIGDECIMAL;
		case "data":
			return JAVA_UTILDATE;
		case "boolean":
			return JAVA_BOOLEAN;
		default:
			return JAVA_STRING;
		}

	}

	/** Converte a {@link java.util.Collection} de daods obtidos do excel para uma lista de objectos conhecida, {@link DefaultComponent} */
	public static List<DefaultComponent> convertToDefaultList(Collection data) {

		List<DefaultComponent> defaultComponentList = new ArrayList<>();

		for (Object unknownObject :
				data) {
			defaultComponentList.add((DefaultComponent) unknownObject);
		}

		return defaultComponentList;
	}

	/** Converte a {@link java.util.Collection} de daods obtidos do excel para uma lista de objectos conhecida, {@link DefaultComponent} */
	public static List<DefaultDropdown> convertToDefaultDropDownList(Collection data) {

		List<DefaultDropdown> defaultComponentList = new ArrayList<>();

		for (Object unknownObject :
				data) {
			defaultComponentList.add((DefaultDropdown) unknownObject);
		}

		return defaultComponentList;
	}

	public static String createRelationShipName(DbRelationship dbRelationship) {
		return convertToCamelCase(dbRelationship.getTargetEntityName());
	}

	public static DbEntity findDbEntityInDataDomain(DataDomain dataDomain, DataMap currentDataMap, String tableName) {
		String[] s = splitByDot(currentDataMap, tableName);
		return dataDomain.getDataMap(s[0]).getDbEntity(s[1]);

	}

	public static ObjEntity findObjEntityInDataDomain(DataDomain dataDomain, DataMap currentDataMap, String tableName) {
		String[] s = splitByDot(currentDataMap, tableName);
		return dataDomain.getDataMap(s[0]).getObjEntity(upperCaseOnUnderscore(s[1]));
	}

	private static String[] splitByDot(DataMap currentDataMap, String value) {
		System.out.println("Map: "+ currentDataMap +"val:"+value);
		String dataMapName =currentDataMap.getName();
		return value.contains(".") ? value.split("\\.", 2) : new String[] {dataMapName, value};
	}

	public static boolean isPk(String columnType) {
		return Constants.DefaultColumns.ID_COLUMN.getColumnType().equalsIgnoreCase(columnType);
	}

	public static boolean convertToBool(String requiredString) {
		return Constants.DefaultValues.TRUE_PT.getValue().equalsIgnoreCase(requiredString);
	}

	public static int convertExcelStringToInt(String columnSize) {
        return Integer.valueOf(StringUtils.isEmpty(columnSize) ? Constants.DefaultValues.DEFAULT_COLUMN_SIZE.getValue() : columnSize.replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1"));
	}


    public static String generateFileNameFromExcelFile(File excelfile) {
		String fileName = removeFileExtension(excelfile.getName());
		return convertToCamelCase(fileName);
    }

	private static String removeFileExtension(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}

    public static String prependTablePrefix(String key) {
       return key.startsWith("t_") ? key : "t_"+key;
    }

	public static String toClassName(String key) {
		return firstUpperCase(upperCaseOnUnderscore(key));
	}
}

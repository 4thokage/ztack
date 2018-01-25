package pt.zenit.ztack.builder.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.excel.model.DefaultComponent;
import pt.zenit.ztack.builder.excel.model.DefaultDropdown;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Metodos de suporte á construção de ficheiroc c/ Apache velocity.
 */
public class VelocityUtils {

    private static final Logger logger = LoggerFactory.getLogger(VelocityUtils.class);

    public VelocityUtils() {
        super();
    }

    /**
     * Cria o codigo para a label/nome do attributo em db, utiliza algumas convenções pré definidas num ficheiro properties
     *
     * @param value String com o valor a ser processado
     * @return {@link String} oom o valor da label gerada
     */
    public String labelCodeForValue(String value) {

        final StringBuilder label = new StringBuilder();

        String[] words = value.split(Constants.DefaultValues.REGEX_MATCH_HIFEN_SPACES.getValue());
        Arrays.stream(words)
                .forEach(w -> label.append(makeShiftLbl(CayenneUtils.normalizeValue(w))));
        return label.toString();
    }


    /**
     * Cria o codigo da lavel para tradução, troca todos os caracteres que não são letras por ponto, sendo estes maioritariamente o caracter '_'
     * @param value
     * @return
     */
    private String makeShiftLbl(String value) {
        return value.replaceAll(Constants.DefaultValues.REGEX_MATCH_NON_LETTERS.getValue(), ".");
    }

    /**
     * Remove todas as linhas duplicadas do ficheiro passado por parametro, (ex: chamado quando é necessário remover labels duplicadas)
     * @param filename nome do ficheiro a processar
     * @throws IOException
     */
    public void stripDuplicatesFromFile(String filename) throws IOException {
        IOUtils.writeLines(
                new LinkedHashSet<String>(IOUtils.readLines(new FileInputStream(filename), Constants.DefaultValues.DEFAULT_ENCODING.getValue())),
                        "\n", new FileOutputStream(filename), Constants.DefaultValues.DEFAULT_ENCODING.getValue());
    }

    //TODO: jsr criar regras de validação mais utilizadas automaticamente

    /**
     * Retorna em formato {@link String} a regra de vaildação a ser aplicada.
     *  Tem como objectivo facilitar a geração de código custom para cada logica de négocio aplicávél
     * @param component componente cuja regra se aplica
     * @param beanName nome do controller que valida a regra
     * @return Representação em formato {@link String} da regra que é aplicada
     */
    public String createValidationRule(DefaultComponent component, String beanName) {
        String rule = component.getValidationRules();
        if (!StringUtils.isEmpty(rule)) {
            String s = rule.substring(0, rule.indexOf(':'));
            switch (s) {
                case "visivel":
                    String columnName = StringUtils.substringBetween(rule, ":");
                    String value = rule.substring(rule.lastIndexOf(':')+1, rule.length());
                    return "rendered=\"#{" + beanName + "." + CayenneUtils.convertToCamelCase(component.getTableName()) + "." + CayenneUtils.convertToCamelCase(columnName) + " eq \'"+value+"\'}\"";
                case "pre":
                    return "disabled=\"true\"";
                default:
                    return "";
            }
        }
        return "";
    }

    public String createRenderRule(DefaultComponent component) {
        String rule = component.getValidationRules();
        if (!StringUtils.isEmpty(rule)) {
            String s = rule.substring(0, rule.indexOf(':'));
            switch (s) {
                case "render":
                    return "<f:ajax render=\""+component.getColumnName()+"_motivo_panel "+component.getColumnName()+"_motivo_label\" />";
                case "pre":
                    return "disabled=\"true\"";
                default:
                    return "";
            }
        }
        return "";
    }

    /**
     * Remove o prefixo do schema da {@link String} que é passada por parametro (ex: tabelas.t_unidade => t_unidade)
     * @param value valor a ser processado
     * @return valor processado (sem o prefixo do schema de BD)
     */
    private String removeSchemaPrefix(String value) {
        return value.substring(value.lastIndexOf('.') + 1);
    }

    /**
     * Cria o ficheiro com o DML das listas de valores
     *  Gera por pré definição uma tabela com id, codigo e label com suporte a tradução pt/en
     * @param parameterTableList {@link HashMap} de parametros a ser gerados
     */
    public static void generateParameterInserts(Map<String, List<DefaultDropdown>> parameterTableList) {
        try {

            String filePath = Constants.DefaultValues.OUTPUT_DIR.getValue() + Constants.DefaultValues.DEFAULT_INSERTS_OUTPUT_PATH.getValue();
            File f = new File(filePath);
            Boolean fileCreated = false;
            if (!f.exists() && !f.isDirectory()) {
                f.getParentFile().mkdirs();
                fileCreated = f.createNewFile();
            } else if(f.length() > 0) {
                Files.write(Paths.get(filePath), "".getBytes());
            }

            if(fileCreated) {
                logger.info("Ficheiro vazio criado em  {}", f.getAbsolutePath());
            }

            for (Map.Entry<String, List<DefaultDropdown>> entry : parameterTableList.entrySet()) {

                String tableName = CayenneUtils.prependTablePrefix(entry.getKey());

                List<DefaultDropdown> listaValoresList = entry.getValue();

                int index = 0;
                StringBuilder insertBuilder = new StringBuilder();
                insertBuilder.append("INSERT INTO ")
                        .append(Constants.DefaultValues.DEFAULT_DB_SCHEMA.getValue())
                        .append(".")
                        .append(tableName)
                        .append("\n(id, codigo, label, data_inicio, data_fim) VALUES\n");
                for (DefaultDropdown l : listaValoresList) {
                    String label = new VelocityUtils().labelCodeForValue(l.getColumnName());
                    String startDate = StringUtils.isEmpty(l.getStartDate()) ? "now()" : "'" + l.getStartDate() + "'";
                    String endDate = StringUtils.isEmpty(l.getEndDate()) ? Constants.DefaultValues.MAX_VALID_DATE.getValue() : l.getEndDate();
                    insertBuilder.append(String.format("(%s, '%s', '%s', %s, '%s'),%n", ++index, l.getColumnName(), label, startDate, endDate));
                }
                insertBuilder.setLength(insertBuilder.length() - 3);
                insertBuilder.append(";\n");

                Files.write(Paths.get(filePath), insertBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            logger.error("Erro a gerar o ficheiro de inserts", e);
        }
    }

    //region métodos do CayenneUtils

    /**
     * Retorna o resultado da chamada ao método {@link CayenneUtils#convertToCamelCase(String)}
     * @param value
     * @return
     */
    public String convertToCamelCase(String value) {
        return CayenneUtils.convertToCamelCase(removeSchemaPrefix(value));
    }

    /**
     * Retorna o resultado da chamada ao método {@link CayenneUtils#convertExcelStringToInt(String)}
     * @param value
     * @return
     */
    public int convertExcelStringToInt(String value) {
        return CayenneUtils.convertExcelStringToInt(value);
    }

    /**
     * Retorna o resultado da chamada ao método {@link CayenneUtils#upperCaseOnUnderscore(String)}
     */
    public String upperCamel(String value) {
        return CayenneUtils.firstUpperCase(value);
    }

    /**
     * Retorna o resultado da chamada ao método {@link CayenneUtils#convertToBool(String)}
     * @param value
     * @return
     */
    public boolean convertToBool(String value) {
        return CayenneUtils.convertToBool(value);
    }

    public String toClassName(String value) { return CayenneUtils.toClassName(value);}
    //endregion

}
package pt.zenit.ztack.builder.cayenne.impl;

import org.apache.cayenne.access.DataDomain;
import org.apache.cayenne.map.*;
import org.apache.cayenne.util.XMLEncoder;
import org.xml.sax.InputSource;
import pt.zenit.ztack.builder.ApplicationArguments;
import pt.zenit.ztack.builder.cayenne.DataMapBuilder;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.excel.model.DefaultComponent;
import pt.zenit.ztack.builder.excel.model.DefaultDropdown;
import pt.zenit.ztack.builder.exception.DataMapBuilderException;
import pt.zenit.ztack.builder.util.CayenneUtils;
import pt.zenit.ztack.builder.util.VelocityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class DefaultDataMapBuilder implements DataMapBuilder{

    /**
     * {@inheritDoc}
     */
    @Override
    public DataDomain loadExistingDataDomain() throws FileNotFoundException {
        DataDomain dataDomain = new DataDomain(Constants.DefaultValues.DEFAULT_DATA_DOMAIN.getValue());
        MapLoader ml = new MapLoader();

        File dir = new File(Constants.DefaultValues.DATA_MAP_FILES_PATH.getValue());
        File[] directoryFiles = dir.listFiles();

        if(directoryFiles != null) {
            for(File dataMap : directoryFiles) {
                DataMap cayenneDatamap = ml.loadDataMap(new InputSource(new FileInputStream(dataMap)));
                dataDomain.addDataMap(cayenneDatamap);
            }
        } else {
            throw new DataMapBuilderException("Não existem ficheiros em: "+dir.getAbsolutePath());
        }
        return dataDomain;
    }

    /**
     * {@inheritDoc}
     * @param dataDomain {@link DataDomain} (ou conjunto de {@link DataMap}) de suporte
     * @param data {@link Collection} dos objectos que foram extraidos do Excel
     * @param arguments {@link ApplicationArguments} parametros de entrada da applicação
     * @throws FileNotFoundException
     */
    @Override
    public void generateDatamapFile(DataDomain dataDomain, Collection data, ApplicationArguments arguments) throws FileNotFoundException {
        if (arguments.schemaName == null) {
            arguments.schemaName = Constants.DefaultValues.DEFAULT_DB_SCHEMA.getValue();
        }

        XMLEncoder encoder = new XMLEncoder(new PrintWriter(new File(Constants.DefaultValues.OUTPUT_DIR.getValue()+"OutputMap.map.xml")));
        Map<String, List<DefaultComponent>> groupedComponents;

        if(arguments.schemaName.equals(Constants.DefaultValues.DEFAULT_DB_SCHEMA.getValue())) {
            Map<String, List<DefaultDropdown>> parameterTableList = CayenneUtils.convertToDefaultDropDownList(data).stream().collect(groupingBy(DefaultDropdown::getTableName));
            VelocityUtils.generateParameterInserts(parameterTableList);
            groupedComponents = convertParameterTables(parameterTableList);
        } else {
            groupedComponents = CayenneUtils.groupByTableName(CayenneUtils.convertToDefaultList(data));
        }

        DataMap currentDataMap = dataDomain.getDataMap(arguments.schemaName);

        for (Map.Entry<String, List<DefaultComponent>> entry : groupedComponents.entrySet()) {
            String key = entry.getKey();
            String normalizedKey = CayenneUtils.normalizeValue(key);
            Collection<DefaultComponent> defaultComponents = entry.getValue();

            DbEntity dbEntity = new DbEntity(normalizedKey);
            ObjEntity objEntity = new ObjEntity(CayenneUtils.upperCaseOnUnderscore(dbEntity.getName()));
            currentDataMap.addDbEntity(dbEntity);
            currentDataMap.addObjEntity(objEntity);

            for (DefaultComponent component : defaultComponents) {

                dbEntity.setSchema(arguments.schemaName);
                DbAttribute dbAttribute = new DbAttribute(CayenneUtils.normalizeValue(component.getColumnName()));

                dbAttribute.setPrimaryKey(false);
                dbAttribute.setMandatory(CayenneUtils.convertToBool(component.getRequiredString()));
                dbAttribute.setType(CayenneUtils.convertSQLType(component.getColumnType()));
                if(dbAttribute.getType() == Types.DECIMAL) {
                    dbAttribute.setScale(CayenneUtils.convertExcelStringToInt(component.getColumnSize()));
                } else {
                    dbAttribute.setMaxLength(CayenneUtils.convertExcelStringToInt(component.getColumnSize()));
                }
                dbAttribute.setEntity(dbEntity);
                dbEntity.addAttribute(dbAttribute);

                objEntity.setDbEntityName(component.getTableName());
                objEntity.setClassName(getPackageName(component, arguments.packageName)+"."+objEntity.getName());
                ObjAttribute objAttribute = new ObjAttribute(CayenneUtils.convertToCamelCase(dbAttribute.getName()));
                objAttribute.setEntity(objEntity);
                objAttribute.setDbAttributePath(component.getColumnName());
                objAttribute.setType(CayenneUtils.convertJDBCType(component.getColumnType()));
                objEntity.addAttribute(objAttribute);
            }
            validateAndCorrectEntity(dbEntity);

        }
        createRelationships(dataDomain, currentDataMap, groupedComponents);
        currentDataMap.encodeAsXML(encoder);
        encoder.getPrintWriter().close();
    }

    private Map<String,List<DefaultComponent>> convertParameterTables(Map<String, List<DefaultDropdown>> parameterTableList) {

        List<DefaultComponent> defaultComponentList = new ArrayList<>();
        for (String tableName: parameterTableList.keySet()) {
            tableName = CayenneUtils.prependTablePrefix(tableName);
            for (Constants.DefaultColumns col : Constants.DefaultColumns.values() ) {
                if(col.isPrimaryKey()) {
                    continue;
                }
                DefaultComponent defaultComponent = new DefaultComponent();
                defaultComponent.setTableName(tableName);
                defaultComponent.setColumnType(col.getColumnType());
                defaultComponent.setColumnName(col.getColumnName());
                defaultComponent.setColumnSize(Integer.toString(col.getColumnSize()));
                defaultComponentList.add(defaultComponent);
            }
        }
        return CayenneUtils.groupByTableName(defaultComponentList);
    }

    /**
     * Obtem o pacge name do componente passado por parametro, se o mesmo nao existir, vai utilizar o passado como argumento na construção da aplicação
     *
     * @param component {@link DefaultComponent} para o qual se vai obter o nome do package.
     * @param packageName
     * @return {@link String} valor do packageName
     */
    private String getPackageName(DefaultComponent component, String packageName) {
        return component.getPackageName() != null ? component.getPackageName() : packageName;
    }

    /**
     * Valida se a entidade gerada tem um atributo que seja chave primaria da tabela,
     * Se nao existir vai adicionar a PK com mdados default
     * @param dbEntity
     */
    private void validateAndCorrectEntity(DbEntity dbEntity) {
        if(dbEntity.getPrimaryKeys().isEmpty()) {
            DbAttribute dbAttribute = new DbAttribute(Constants.DefaultColumns.ID_COLUMN.getColumnName(), CayenneUtils.convertSQLType(Constants.DefaultColumns.ID_COLUMN.getColumnType()), dbEntity);
            dbAttribute.setPrimaryKey(Constants.DefaultColumns.ID_COLUMN.isPrimaryKey());
            dbAttribute.setMandatory(Constants.DefaultColumns.ID_COLUMN.isRequired());
            dbAttribute.setMaxLength(Constants.DefaultColumns.ID_COLUMN.getColumnSize());
            dbEntity.addAttribute(dbAttribute);

        }
    }

    /**
     * Método que cria as relações entre {@link DbEntity}/{@link ObjEntity} do cayenne
     *
     * @param dataDomain {@link DataDomain} de suporte
     * @param currentDataMap {@link DataMap} que vai ser alterado/criado
     * @param groupedComponents {@link Map<String, List<DefaultComponent>>} mapa dos objectos agrupados por {@link DefaultComponent#tableName}
     */
    private void createRelationships(DataDomain dataDomain, DataMap currentDataMap, Map<String, List<DefaultComponent>> groupedComponents) {
        for (Map.Entry<String, List<DefaultComponent>> entry : groupedComponents.entrySet()) {
            Collection<DefaultComponent> defaultComponents = entry.getValue();

            for (DefaultComponent component : defaultComponents) {

                if (component.getTargetTable() != null) {

                    DbEntity sourceDbEntity = CayenneUtils.findDbEntityInDataDomain(dataDomain, currentDataMap, component.getTableName());
                    ObjEntity sourceObjEntity = CayenneUtils.findObjEntityInDataDomain(dataDomain, currentDataMap, component.getTableName());
                    DbEntity targetDbEntity = CayenneUtils.findDbEntityInDataDomain(dataDomain, dataDomain.getDataMap(component.getTargetTable().substring(0, component.getTargetTable().indexOf('.'))), component.getTargetTable());
                    ObjEntity targetObjEntity = CayenneUtils.findObjEntityInDataDomain(dataDomain, dataDomain.getDataMap(component.getTargetTable().substring(0, component.getTargetTable().indexOf('.'))), component.getTargetTable());


                    DbRelationship dbRelationship = new DbRelationship();

                    dbRelationship.setSourceEntity(sourceDbEntity);
                    dbRelationship.setToMany(component.getToManyTarget() != null);
                    dbRelationship.setTargetEntityName(targetDbEntity.getName());
                    dbRelationship.setName(CayenneUtils.createRelationShipName(dbRelationship));
                    if("t_freguesia".equals(dbRelationship.getTargetEntityName())) {
                        dbRelationship.setName(CayenneUtils.convertToCamelCase(component.getColumnName().substring(component.getColumnName().indexOf('_'), component.getColumnName().length())));
                    }

                    if(component.getToManyTarget() != null) {
                        DbJoin dbJoin = new DbJoin(dbRelationship, Constants.DefaultColumns.ID_COLUMN.getColumnName(), "fk_"+component.getColumnName());
                    }
                    DbJoin dbJoin = new DbJoin(dbRelationship, component.getColumnName(), Constants.DefaultColumns.ID_COLUMN.getColumnName());
                    List<DbJoin> lstJoins = new ArrayList<>();
                    lstJoins.add(dbJoin);
                    dbRelationship.setJoins(lstJoins);

                    //sourceDbEntity.removeAttribute(component.getColumnName());
                    sourceDbEntity.addRelationship(dbRelationship);

                    ObjRelationship objRelationship = new ObjRelationship();
                    objRelationship.setSourceEntity(sourceObjEntity);
                    objRelationship.setTargetEntityName(targetObjEntity.getName());
                    objRelationship.setDbRelationshipPath(dbRelationship.getName());
                    objRelationship.setDeleteRule(DeleteRule.DENY);
                    objRelationship.setName(dbRelationship.getName());

                    sourceObjEntity.removeAttribute(CayenneUtils.convertToCamelCase(component.getColumnName()));
                    sourceObjEntity.addRelationship(objRelationship);
                }
            }
        }
    }
}

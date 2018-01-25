package pt.zenit.ztack.builder.composer.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.zenit.ztack.builder.ApplicationArguments;
import pt.zenit.ztack.builder.ApplicationProperties;
import pt.zenit.ztack.builder.composer.VelocityComposer;
import pt.zenit.ztack.builder.constants.Constants;
import pt.zenit.ztack.builder.excel.model.DefaultComponent;
import pt.zenit.ztack.builder.util.CayenneUtils;
import pt.zenit.ztack.builder.util.VelocityUtils;

import static pt.zenit.ztack.builder.util.CayenneUtils.convertToDefaultList;
import static pt.zenit.ztack.builder.util.CayenneUtils.groupByTableName;

public class DefaultVelocityComposer<T> implements VelocityComposer<T> {

	private static final Logger logger = LoggerFactory.getLogger(DefaultVelocityComposer.class);

	private static final String CLASS_NAME = "className";
	private static final String ROOT_FILE = "rootFile";
	private static final String FILE_NAME = "fileName";
	private static final String VELOCITY_UTILS = "velocityUtils";
	private static final String MB_NAME = "mbName";
	private static final String SCHEMA = "schema";

	private static final Properties properties = ApplicationProperties.getInstance();

	@Override
	public void composeServices(String templateDir, ApplicationArguments args) {

		try {

			final String outputPath = Constants.DefaultValues.DEFAULT_SERVICE_OUTPUT_PATH.getValue();

			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);

			VelocityContext context = new VelocityContext();

			Template serviceTemplate = ve.getTemplate(templateDir + "/Service.vm", "UTF-8");

			context.put(ROOT_FILE, args.excelFile);
			context.put("packagePath", outputPath.replaceAll("/", "."));
			context.put(CLASS_NAME, "CayenneRepository");

			File outputDir = new File(Constants.DefaultValues.OUTPUT_DIR.getValue() + outputPath);
			if (!outputDir.exists() && !outputDir.isDirectory()) {
				outputDir.mkdirs();
			}

			FileWriter writer = new FileWriter(outputDir + "/" + context.get(CLASS_NAME) + ".java");
			serviceTemplate.merge(context, writer);
			writer.close();

		} catch (Exception e) {
			logger.error("Erro na geração do ficheiro CayenneRepository", e);
		}
	}

	@Override
	public void composePropertiesFiles(String templateDir, ApplicationArguments args, Collection<T> data) {
		try {

			final String outputPath = Constants.DefaultValues.DEFAULT_PROPERTIES_OUTPUT_PATH.getValue();

			File outputDir = new File(Constants.DefaultValues.OUTPUT_DIR.getValue() + outputPath);

			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);

			VelocityContext context = new VelocityContext();
			VelocityUtils vu = new VelocityUtils();

			for (Constants.Languages l : Constants.Languages.values()) {
				Template propertiesTemplate = ve
						.getTemplate(String.format("%s/Properties%s.vm", templateDir, l.getSuffix()));

				context.put("data", data);
				context.put("locale", l.getSuffix());
				context.put(FILE_NAME, "/msg_" + args.schemaName + context.get("locale").toString() + ".properties");
				context.put(ROOT_FILE, args.excelFile);
				context.put(VELOCITY_UTILS, vu);

				if (!outputDir.exists() && !outputDir.isDirectory()) {
					outputDir.mkdirs();
				}

				String filePath = outputDir + context.get(FILE_NAME).toString();
				FileWriter writer = new FileWriter(filePath);
				propertiesTemplate.merge(context, writer);
				writer.close();
				vu.stripDuplicatesFromFile(filePath);
			}

		} catch (Exception e) {
			logger.error("Erro na geração de ficheiros properties", e);
		}

	}

	@Override
	public void composeViews(String templateDir, ApplicationArguments args, Collection<T> data) {
		try {
			final String outputPath = Constants.DefaultValues.DEFAULT_VIEW_OUTPUT_PATH.getValue();

			File outputDir = new File(Constants.DefaultValues.OUTPUT_DIR.getValue() + outputPath);

			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);

			VelocityContext context = new VelocityContext();
			VelocityUtils vu = new VelocityUtils();

			Map<String, List<DefaultComponent>> groupedComponents = groupByTableName(convertToDefaultList(data));

			for (Map.Entry<String, List<DefaultComponent>> entry : groupedComponents.entrySet()) {

				for (Constants.ViewTemplates t : Constants.ViewTemplates.values()) {

					String key = entry.getKey();
					String camelCaseKey = CayenneUtils.upperCaseOnUnderscore(key);
					String mbName = CayenneUtils.firstLowerCase(args.controllerName) + "Bean";
					Collection<DefaultComponent> defaultComponents = entry.getValue();

					Template fragmentTemplate = ve
							.getTemplate(String.format("%s/%sFragment.vm", templateDir, t.getValue()));

					context.put("data", defaultComponents);
					context.put(FILE_NAME, camelCaseKey + t.getValue() + ".xhtml");
					context.put(ROOT_FILE, args.excelFile);
					context.put(SCHEMA, args.schemaName);
					context.put(VELOCITY_UTILS, vu);
					context.put(MB_NAME, mbName);

					if (!outputDir.exists() && !outputDir.isDirectory()) {
						outputDir.mkdirs();
					}

					String filePath = outputDir + File.separator + context.get(FILE_NAME).toString();
					FileWriter writer = new FileWriter(filePath);
					fragmentTemplate.merge(context, writer);
					writer.close();
				}
			}

			composeViewPage(groupedComponents, templateDir, ve, args, outputDir);

		} catch (Exception e) {
			logger.error("Erro na geração de ficheiros xhtml", e);
		}

	}

	@Override
	public void composeBeans(String templateDir, ApplicationArguments args, Collection<T> data) {

		try {
			final String outputPath = Constants.DefaultValues.DEFAULT_BEAN_OUTPUT_PATH.getValue();

			File outputDir = new File(Constants.DefaultValues.OUTPUT_DIR.getValue() + outputPath);

			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);

			VelocityContext context = new VelocityContext();
			VelocityUtils vu = new VelocityUtils();

			Template controllerTemplate = ve.getTemplate(String.format("%s/ControllerBean.vm", templateDir));

			Map<String, List<DefaultComponent>> groupedComponents = groupByTableName(convertToDefaultList(data));
			List<String> includes = generateIncludes(groupedComponents);

			context.put(VELOCITY_UTILS, vu);
			context.put(MB_NAME, args.controllerName);
			context.put("data", includes);
			context.put(FILE_NAME, args.controllerName + "Bean.java");
			context.put(ROOT_FILE, args.excelFile);
			context.put(SCHEMA, args.schemaName);
			context.put("packagePath", outputPath.replaceAll("/", "."));

			if (!outputDir.exists() && !outputDir.isDirectory()) {
				outputDir.mkdirs();
			}

			String filePath = outputDir + File.separator + context.get(FILE_NAME).toString();
			FileWriter writer = new FileWriter(filePath);
			controllerTemplate.merge(context, writer);
			writer.close();

		} catch (Exception e) {
			logger.error("Erro a gerar os controllers", e);
		}
	}

	private void composeViewPage(Map<String, List<DefaultComponent>> groupedComponents, String templateDir,
			VelocityEngine ve, ApplicationArguments args, File outputDir) {

		try {
			Template viewTemplate = ve
					.getTemplate(String.format("%s/%sView.vm", templateDir, Constants.ViewTemplates.FORM.getValue()));
			VelocityContext context = new VelocityContext();

			String fileName = CayenneUtils.generateFileNameFromExcelFile(args.excelFile);

			List<String> includes = generateIncludes(groupedComponents);

			context.put("data", includes);
			context.put(SCHEMA, args.schemaName);
			context.put(FILE_NAME, fileName + ".xhtml");
			context.put(MB_NAME, CayenneUtils.firstLowerCase(args.controllerName) + "Bean");

			if (!outputDir.exists() && !outputDir.isDirectory()) {
				outputDir.mkdirs();
			}

			String filePath = outputDir + File.separator + context.get(FILE_NAME).toString();
			FileWriter writer = new FileWriter(filePath);
			viewTemplate.merge(context, writer);
			writer.close();
		} catch (Exception e) {
			logger.error("Erro a gerar a view principal", e);
		}
	}

	/***
	 * Gera os ficheiros a includir com base no codigo das tabelas do ficheiro
	 * Excel.
	 * 
	 * @param groupedComponents
	 *            {@link Map<String, List<DefaultComponent>} dados do ficheiro
	 *            excel agrupados por codigo de coluna
	 * @return {@link List<String> lista dos codigos em formato camelCase}
	 */
	private List<String> generateIncludes(Map<String, List<DefaultComponent>> groupedComponents) {
		return groupedComponents.keySet().stream().map(CayenneUtils::convertToCamelCase).collect(Collectors.toList());
	}

	@Override
	public void composeWebservices(String templateDir, ApplicationArguments args, Collection<T> data) {
		try {
			final String outputPath = Constants.DefaultValues.DEFAULT_VIEW_OUTPUT_PATH.getValue();

			File outputDir = new File(Constants.DefaultValues.OUTPUT_DIR.getValue() + outputPath);

			VelocityEngine ve = new VelocityEngine();
			ve.init(properties);

			VelocityContext context = new VelocityContext();
			VelocityUtils vu = new VelocityUtils();

			Map<String, List<DefaultComponent>> groupedComponents = groupByTableName(convertToDefaultList(data));

			for (Map.Entry<String, List<DefaultComponent>> entry : groupedComponents.entrySet()) {

				String key = entry.getKey();
				String className = CayenneUtils.toClassName(key);
				Collection<DefaultComponent> defaultComponents = entry.getValue();

				Template fragmentTemplate = ve
						.getTemplate(String.format("%s/SoapModel.vm", templateDir));

				context.put("data", defaultComponents);
				context.put(FILE_NAME, className + ".java");
				context.put(ROOT_FILE, args.excelFile);
				context.put(SCHEMA, args.schemaName);
				context.put(VELOCITY_UTILS, vu);

				if (!outputDir.exists() && !outputDir.isDirectory()) {
					outputDir.mkdirs();
				}

				String filePath = outputDir + File.separator + context.get(FILE_NAME).toString();
				FileWriter writer = new FileWriter(filePath);
				fragmentTemplate.merge(context, writer);
				writer.close();
			}
		} catch (Exception e) {
			logger.error("Erro a gerar os webservices:", e);
		}

	}

}

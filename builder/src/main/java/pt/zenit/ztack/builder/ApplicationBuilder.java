package pt.zenit.ztack.builder;

import com.google.inject.Guice;
import com.google.inject.Injector;
import pt.zenit.ztack.builder.module.BuilderModule;
import pt.zenit.ztack.builder.module.ProcessorModule;
import pt.zenit.ztack.builder.service.BuilderService;
import pt.zenit.ztack.builder.service.ProcessorService;

import java.io.FileNotFoundException;
import java.util.Collection;

/** Classe que contém todos os métodos centrais da aplicação (geração de código, pré-processamento de ficheiros, testes e deploys */
class ApplicationBuilder<T> {

	private ApplicationArguments arguments;

	ApplicationBuilder(ApplicationArguments arguments) {
	    this.arguments = arguments;
	}

	/** Ponto de entrada de geração do código */
	@SuppressWarnings("unchecked")
	void composeApp() throws FileNotFoundException {

        Injector injector = Guice.createInjector(new BuilderModule());
        BuilderService builderService = injector.getInstance(BuilderService.class);

        Collection<T> data = builderService.getJavaObjectsFromExcel(arguments);

        builderService.composeCayenneDataMap(data, arguments);

		if(!arguments.skipAppFiles) {
			builderService.generateApplicationFiles(data, arguments);
		}
    }

    /** Move os ficheiros e efetua o Deploy & Compile do código */
	void compileAndDeploy() {
	    throw new UnsupportedOperationException();
	}

	/** Ponto de entrada para os testes automaticos */
	void initTests() {
        throw new UnsupportedOperationException();
	}

	/** Ponto de entrada para o pre processamento do ficheiro Excel */
    void processExcel() {
        Injector injector = Guice.createInjector(new ProcessorModule());

        ProcessorService processorService = injector.getInstance(ProcessorService.class);

        processorService.preProcessExcelFile(arguments.excelFile);
    }
}
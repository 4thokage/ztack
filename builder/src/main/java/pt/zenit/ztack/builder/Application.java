package pt.zenit.ztack.builder;

import java.io.IOException;

import com.beust.jcommander.JCommander;
import pt.zenit.ztack.builder.constants.Constants;

public class Application {

	/**
	 * Método inicial da aplicação, processa os argumentos de entrada recebidos
	 * @param args {@link ApplicationArguments} argumentos de entrada
	 * @throws IOException caso não encontre o ficheiro excel (obrigatório)
	 */
	public static void main(String[] args) throws IOException {

		ApplicationArguments jCommanderArgs = new ApplicationArguments();
		JCommander jCommander = new JCommander(jCommanderArgs, args);
		jCommander.setProgramName(Constants.DefaultValues.APP_TITLE.getValue());

		if (jCommanderArgs.help) {
			jCommander.usage();
		} else {
			ApplicationBuilder applicationBuilder = new ApplicationBuilder(jCommanderArgs);
			if (jCommanderArgs.process) {
				applicationBuilder.processExcel();
			}
			applicationBuilder.composeApp();
		}
	}
}

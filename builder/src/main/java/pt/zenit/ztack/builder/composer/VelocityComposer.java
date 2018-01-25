package pt.zenit.ztack.builder.composer;

import java.util.Collection;

import pt.zenit.ztack.builder.ApplicationArguments;

public interface VelocityComposer<T> {

	void composeServices(String templateDir, ApplicationArguments args);
	
	void composePropertiesFiles(String templateDir, ApplicationArguments args, Collection<T> data);

	void composeViews(String templateDir, ApplicationArguments args, Collection<T> data);

	void composeBeans(String templateDir, ApplicationArguments args, Collection<T> data);
	
	void composeWebservices(String templateDir, ApplicationArguments args, Collection<T> data);
}

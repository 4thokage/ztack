package pt.zenit.ztack.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.builder.constants.Constants;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by jsilvaro on 25-07-2017.
 */
public class ApplicationProperties {

    private static Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private boolean loaded=false;
    private Properties properties = new Properties();

    private static final String PATH = Constants.DefaultValues.VELOCITY_PROPERTIES_PATH.getValue();

    public String getPropertyValue(String pName){
        if (!this.loaded)
            loadProperties(PATH);
        return this.properties.getProperty(pName);
    }

    public Properties getProperties(){
        if (!this.loaded)
            loadProperties(PATH);
        return this.properties;
    }

    private void loadProperties(String fileProperties){
        try{
            properties = new Properties();
            properties.load(ApplicationProperties.class.getClassLoader().getResourceAsStream(fileProperties));
            loaded = true;

        }catch (IOException e) {
            logger.error(" ",e);
        }
    }

    private static class AppPropertiesSingletonHolder {

        private AppPropertiesSingletonHolder() {}

        private static final Properties INSTANCE = new ApplicationProperties().getProperties();
    }

    public static Properties getInstance() {
        return AppPropertiesSingletonHolder.INSTANCE;
    }

}

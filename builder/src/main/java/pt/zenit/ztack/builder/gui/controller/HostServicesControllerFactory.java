package pt.zenit.ztack.builder.gui.controller;

import javafx.application.HostServices;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.zenit.ztack.builder.exception.UtilsException;

import java.lang.reflect.Constructor;

public class HostServicesControllerFactory implements Callback<Class<?>,Object> {

    private final HostServices hostServices;

    public static final Logger logger = LoggerFactory.getLogger(HostServicesControllerFactory.class);

    public HostServicesControllerFactory(HostServices hostServices) {
        this.hostServices = hostServices ;
    }

    @Override
    public Object call(Class<?> type) {
        try {
            for (Constructor<?> c : type.getConstructors()) {
                if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == HostServices.class) {
                    return c.newInstance(hostServices) ;
                }
            }
            return type.newInstance();
        } catch (Exception e) {
           throw new UtilsException("Erro ao injectar os hostServices Utils", e);
        }
    }
}
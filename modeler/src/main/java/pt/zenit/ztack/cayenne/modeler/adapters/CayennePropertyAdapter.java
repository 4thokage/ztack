package pt.zenit.ztack.cayenne.modeler.adapters;

import pt.zenit.ztack.cayenne.modeler.project.CayenneProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

/**
 * Provides a common mechanism for creating property adapters. Property adapters
 * map existing Java/Beans classes to JavaFX properties which can be used in
 * bi-directional bindings within the UI.
 */
public abstract class CayennePropertyAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(CayennePropertyAdapter.class);

    /**
     * Binds a Java/Bean String property to a JavaFX property. Note: The JavaFX
     * property observes changes and marks the Cayenne project dirty when a
     * change occurs.
     *
     * @param property
     *            The Java/Bean property to bind to.
     * @return A new JavaFX property bound to the Java/Bean property.
     * @throws NoSuchMethodException
     *             If the specified Java/Bean property does not exist (check the
     *             property's spelling).
     */
    BooleanProperty bindBoolean(String property) throws NoSuchMethodException
    {
        return observePropertyChanges(JavaBeanBooleanPropertyBuilder.create().bean(getWrappedObject()).name(property).build());
    }

    /**
     * Binds a Java/Bean String property to a JavaFX property. Note: The JavaFX
     * property observes changes and marks the Cayenne project dirty when a
     * change occurs.
     *
     * @param property
     *            The Java/Bean property to bind to.
     * @return A new JavaFX property bound to the Java/Bean property.
     * @throws NoSuchMethodException
     *             If the specified Java/Bean property does not exist (check the
     *             property's spelling).
     */
    IntegerProperty bindInteger(String property) throws NoSuchMethodException
    {
        return observePropertyChanges(JavaBeanIntegerPropertyBuilder.create().bean(getWrappedObject()).name(property).build());
    }

    /**
     * Binds a Java/Bean String property to a JavaFX property. Note: The JavaFX
     * property observes changes and marks the Cayenne project dirty when a
     * change occurs.
     *
     * @param property
     *            The Java/Bean property to bind to.
     * @return A new JavaFX property bound to the Java/Bean property.
     * @throws NoSuchMethodException
     *             If the specified Java/Bean property does not exist (check the
     *             property's spelling).
     */
    StringProperty bindString(String property) throws NoSuchMethodException
    {
        return observePropertyChanges(JavaBeanStringPropertyBuilder.create().bean(getWrappedObject()).name(property).build());
    }

    /**
     * Observes changes to the given property and marks the project dirty
     * (edited) when the property is changed.
     *
     * @param property
     *            The property to observe changes.
     * @return The observed property.
     */
    private <T extends Property<?>> T observePropertyChanges(T property)
    {
        property.addListener((observable, oldValue, newValue) ->
            {
                JavaBeanProperty<?> changedProperty = (JavaBeanProperty<?>) observable;

                getCayennePropject().setDirty(true);

                LOG.debug("Property Changed: [" + changedProperty.getBean().getClass().getSimpleName() + " " + changedProperty.getName() + "] " + oldValue + " -> " + newValue);
            });

        return property;
    }

    /**
     * @return The Cayenne project this property adapter is attached to.
     */
    public abstract CayenneProject getCayennePropject();

    /**
     * @return The Cayenne Java/Bean object this property adapter wraps.
     */
    public abstract Object getWrappedObject();
}
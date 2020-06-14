package org.battleplugins.arena.file.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to annotate configuration properties.
 * 
 * @author Redned
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {

    /**
     * The name (value) of the property. If empty,
     * the name of the field will be used instead.
     * 
     * @return the name of the property
     */
    String value() default "";
    
    /**
     * If the property is required
     * 
     * @return if the property is required
     */
    boolean required() default false;
}

package com.w2ji.vitaminsaber;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotation for fields which indicate that it should be looked up in the activity intent's extras
 * or fragment arguments.
 * The extra will automatically be cast to the field type. If no key is provided, the variable name
 * will be used.
 * <pre><code>
 * {@literal @}InjectExtra("key") String title;
 * {@literal @}InjectExtra String content; // "content" is the key for the extra
 * </code></pre>
 *
 */
@Retention(CLASS)
@Target(FIELD)
public @interface InjectResource {
    String value() default "";
}


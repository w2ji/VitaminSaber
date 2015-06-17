package test;

import com.w2ji.vitaminsaber.VitaminSaber.ResourceFinder;

public class Test$$ResourceInjector {
    public static void inject(ResourceFinder finder, final test.Test target, Object resource) {
        Object object;
        object = finder.getResource(resource, 123, "java.lang.String");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '123' for field 'string' was not found.");
        }
        target.string = (java.lang.String) object;
    }
}
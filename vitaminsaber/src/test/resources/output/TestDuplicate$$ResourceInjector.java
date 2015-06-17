// Generated code from VitaminSaber. Do not modify!
package test;

import com.w2ji.vitaminsaber.VitaminSaber.ResourceFinder;

public class TestDuplicate$$ResourceInjector {
    public static void inject(ResourceFinder finder, final test.TestDuplicate target, Object resource) {
        Object object;
        object = finder.getResource(resource, 1, "java.lang.String");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '1' for field 'string1', field 'string2', and field 'string3' was not found.");
        }
        target.string1 = (java.lang.String) object;
        target.string2 = (java.lang.String) object;
        target.string3 = (java.lang.String) object;
    }
}

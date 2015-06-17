package test;

import com.w2ji.vitaminsaber.VitaminSaber.ResourceFinder;

public class TestAllResource$$ResourceInjector {
    public static void inject(ResourceFinder finder, final test.TestAllResource target, Object resource) {
        Object object;
        object = finder.getResource(resource, 1, "java.lang.String");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '1' for field 'string' was not found.");
        }
        target.string = (java.lang.String) object;
        object = finder.getResource(resource, 2, "java.lang.Float");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '2' for field 'aFloat' was not found.");
        }
        target.aFloat = (java.lang.Float) object;
        object = finder.getResource(resource, 3, "java.lang.Integer");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '3' for field 'integers' was not found.");
        }
        target.integers = (java.lang.Integer) object;
        object = finder.getResource(resource, 4, "android.graphics.drawable.Drawable");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '4' for field 'drawable' was not found.");
        }
        target.drawable = (android.graphics.drawable.Drawable) object;
        object = finder.getResource(resource, 5, "int[]");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '5' for field 'intArrays' was not found.");
        }
        target.intArrays = (int[]) object;
        object = finder.getResource(resource, 6, "java.lang.String[]");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '6' for field 'stringArrays' was not found.");
        }
        target.stringArrays = (java.lang.String[]) object;
        object = finder.getResource(resource, 7, "android.content.res.XmlResourceParser");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '7' for field 'xmlFile' was not found.");
        }
        target.xmlFile = (android.content.res.XmlResourceParser) object;
        object = finder.getResource(resource, 8, "java.io.InputStream");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '8' for field 'rawResource' was not found.");
        }
        target.rawResource = (java.io.InputStream) object;
        object = finder.getResource(resource, 9, "java.lang.CharSequence");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '9' for field 'text' was not found.");
        }
        target.text = (java.lang.CharSequence) object;
        object = finder.getResource(resource, 10, "android.graphics.Movie");
        if (object == null) {
            throw new IllegalStateException("Required resource with key '10' for field 'movie' was not found.");
        }
        target.movie = (android.graphics.Movie) object;
    }
}
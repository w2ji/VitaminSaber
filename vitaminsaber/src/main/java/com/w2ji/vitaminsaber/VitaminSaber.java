package com.w2ji.vitaminsaber;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import com.w2ji.vitaminsaber.internal.InjectResourceProcessor;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class VitaminSaber {
    static final Map<Class<?>, Method> INJECTORS = new LinkedHashMap<Class<?>, Method>();
    static final Method NO_OP = null;
    private static final String TAG = "VitaminSaber";
    private static boolean debug = false;

    private VitaminSaber() {
        // No instances.
    }

    /** Control whether debug logging is enabled. */
    public static void setDebug(boolean debug) {
        VitaminSaber.debug = debug;
    }

    public static void inject(Activity target) {
        inject(target, target, ResourceFinder.CONTEXT);
    }

    public static void inject(Fragment target) {
        inject(target, target, ResourceFinder.FRAGMENT);
    }

    public static void inject(Context context, Object target) {
        inject(context, target, ResourceFinder.CONTEXT);
    }

    static void inject(Object context, Object target, ResourceFinder resourceFinder) {
        Class<?> targetClass = target.getClass();
        try {
            if (debug) Log.d(TAG, "Looking up resource injector for " + targetClass.getName());
            Method inject = findInjectorForClass(targetClass);
            if (inject != null) {
                inject.invoke(null, resourceFinder, target, context);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new UnableToInjectException("Unable to inject resource for " + target, e);
        }
    }

    private static Method findInjectorForClass(Class<?> cls) throws NoSuchMethodException {
        Method inject = INJECTORS.get(cls);
        if (inject != null) {
            if (debug) Log.d(TAG, "HIT: Cached in injector map.");
            return inject;
        }
        String clsName = cls.getName();
        if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
            if (debug) Log.d(TAG, "MISS: Reached framework class. Abandoning search.");
            return NO_OP;
        }
        try {
            Class<?> injector = Class.forName(clsName + InjectResourceProcessor.SUFFIX);
            inject = injector.getMethod("inject", ResourceFinder.class, cls, Object.class);
            if (debug) Log.d(TAG, "HIT: Class loaded injection class.");
        } catch (ClassNotFoundException e) {
            if (debug) Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            inject = findInjectorForClass(cls.getSuperclass());
        }
        INJECTORS.put(cls, inject);
        return inject;
    }

    /** Simpler version of {@link android.os.Bundle#get(String)} which infers the target type. */
    @SuppressWarnings({ "unchecked", "UnusedDeclaration" })
    // Checked by runtime cast. Public API.
    public static <T> T get(Bundle bundle, String key) {
        return (T) bundle.get(key);
    }

    /**
     * A means of finding an extra in either an {@link android.app.Activity}, {@link
     * android.app.Fragment} or a {@link android.os.Bundle}. Exposed for use only
     * by generated code.
     * If any of the means to get a bundle are null, this will simply return a null.
     */
    public enum ResourceFinder {
        CONTEXT {
            @Override public Object getResource(Object target, int resourceId, String variableType) {
                Context activity = (Context) target ;
                return activity == null ? null : findResourceType(activity.getResources(), resourceId, variableType);
            }
        },
        FRAGMENT {
            @Override public Object getResource(Object target, int resourceId, String variableType) {
                Fragment fragment = (Fragment) target;
                return fragment == null ? null : findResourceType(fragment.getResources(), resourceId, variableType);
            }
        };

        public Object findResourceType(Resources resources, int resourceId, String variableType){
            String resourceTypeName = resources.getResourceTypeName(resourceId);
            if (debug) Log.d(TAG, "resourceTypes : " + resourceTypeName);
            ResourceTypes resourceTypes = ResourceTypes.valueOf(resourceTypeName);
            switch (resourceTypes){
                case anim:
                    return resources.getAnimation(resourceId);
                case animator:
                    return resources.getAnimation(resourceId);
                case array:
                    if (variableType.contains(String[].class.getSimpleName())){
                        return resources.getStringArray(resourceId);
                    } else if (variableType.contains(int[].class.getSimpleName())){
                        return resources.getIntArray(resourceId);
                    }
                case attr:
                    // TODO not supported?
                    return null;
                case bool:
                    return resources.getBoolean(resourceId);
                case color:
                    return resources.getColor(resourceId);
                case dimen:
                    return resources.getDimension(resourceId);
                case drawable:
                    return resources.getDrawable(resourceId);
                case fraction:
                    // TODO not supported?
                    return null;
                case integer:
                    return resources.getInteger(resourceId);
                case interpolator:
                    // TODO not supported?
                    return null;
                case layout:
                    return resources.getLayout(resourceId);
                case menu:
                    // TODO not supported?
                    return null;
                case mipmap:
                    // TODO not supported?
                    return null;
                case plurals:
                    // TODO not supported?
                    return null;
                case raw:
                    // TODO not supported?
                    return null;
                case string:
                    return resources.getString(resourceId);
                case style:
                    // TODO not supported?
                    return null;
                case xml:
                    return resources.getXml(resourceId);
                default:
                    return null;
            }
        }

        public enum ResourceTypes{
            anim,
            animator,
            array,
            attr,
            bool,
            color,
            dimen,
            drawable,
            fraction,
            integer,
            interpolator,
            layout,
            menu,
            mipmap,
            plurals,
            raw,
            string,
            style,
            xml
        }
        public abstract Object getResource(Object source, int resourceId, String variableType);
    }

    public static class UnableToInjectException extends RuntimeException {
        UnableToInjectException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

package com.w2ji.vitaminsaber;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
        inject(target, target, ResourceFinder.ACTIVITY);
    }

    public static void inject(Fragment target) {
        inject(target, target, ResourceFinder.FRAGMENT);
    }

    public static void inject(Object target, Activity source) {
        inject(target, source, ResourceFinder.ACTIVITY);
    }

    static void inject(Object target, Object source, ResourceFinder resourceFinder) {
        Class<?> targetClass = target.getClass();
        try {
            if (debug) Log.d(TAG, "Looking up extra injector for " + targetClass.getName());
            Method inject = findInjectorForClass(targetClass);
            if (inject != null) {
                inject.invoke(null, resourceFinder, target, source);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new UnableToInjectException("Unable to inject extras for " + target, e);
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
        ACTIVITY {
            @Override public Object getResource(Object source, int resourceId) {
                Activity activity = (Activity) source;
                return activity == null ? null : findResourceType(activity.getResources(), resourceId);
            }
        },
        FRAGMENT {
            @Override public Object getResource(Object source, int resourceId) {
                Fragment fragment = (Fragment) source;
                return fragment == null ? null : findResourceType(fragment.getResources(), resourceId);
            }
        };

        public Object findResourceType(Resources resources, int resourceId){
            String resourceTypeName = resources.getResourceTypeName(resourceId);
            ResourceTypes resourceTypes = ResourceTypes.valueOf(resourceTypeName);
            switch (resourceTypes){
                case anim:
                    return resources.getAnimation(resourceId);
                case animator:
                    return resources.getAnimation(resourceId);
                case array:
                    // TODO determine type of array
                    return resources.getIntArray(resourceId);
                case attr:
                    // TODO WHAT IS THIS?
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
                    // TODO HMMM?
                    return null;
                case interger:
                    return resources.getInteger(resourceId);
                case interpolator:
                    // TODO WHAT?
                    return null;
                case layout:
                    return resources.getLayout(resourceId);
                case menu:
                    return null;
                case mipmap:
                    return null;
                case plurals:
                    // TODO cant inject this
                    return null;
                case raw:
                    return null;
                case string:
                    return resources.getString(resourceId);
                case style:
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
            interger,
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
        public abstract Object getResource(Object source, int resourceId);
    }

    public static class UnableToInjectException extends RuntimeException {
        UnableToInjectException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

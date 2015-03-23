package com.w2ji.vitaminsaber.internal;

import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class ResourceInjector {
    private final Map<String, ResourceInjection> injectionMap =
            new LinkedHashMap<String, ResourceInjection>();
    private final String classPackage;
    private final String className;
    private final String targetClass;
    private String parentInjector;

    ResourceInjector(String classPackage, String className, String targetClass) {
        this.classPackage = classPackage;
        this.className = className;
        this.targetClass = targetClass;
    }

    static void emitCast(StringBuilder builder, TypeMirror fieldType) {
        builder.append('(').append(getType(fieldType)).append(") ");
    }

    static String getType(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            // Get wrapper for primitive types
            switch (type.getKind()) {
                case BOOLEAN:
                    return "java.lang.Boolean";
                case BYTE:
                    return "java.lang.Byte";
                case SHORT:
                    return "java.lang.Short";
                case INT:
                    return "java.lang.Integer";
                case LONG:
                    return "java.lang.Long";
                case CHAR:
                    return "java.lang.Character";
                case FLOAT:
                    return "java.lang.Float";
                case DOUBLE:
                    return "java.lang.Double";
                default:
                    // Shouldn't happen
                    throw new RuntimeException();
            }
        } else {
            return type.toString();
        }
    }

    static void emitHumanDescription(StringBuilder builder, List<FieldBinding> bindings) {
        switch (bindings.size()) {
            case 1:
                builder.append(bindings.get(0).getDescription());
                break;
            case 2:
                builder.append(bindings.get(0).getDescription())
                        .append(" and ")
                        .append(bindings.get(1).getDescription());
                break;
            default:
                for (int i = 0, count = bindings.size(); i < count; i++) {
                    FieldBinding requiredField = bindings.get(i);
                    if (i != 0) {
                        builder.append(", ");
                    }
                    if (i == count - 1) {
                        builder.append("and ");
                    }
                    builder.append(requiredField.getDescription());
                }
                break;
        }
    }

    void addField(String key, String name, TypeMirror type) {
        getOrCreateExtraBinding(key).addFieldBinding(new FieldBinding(name, type));
    }

    void setParentInjector(String parentInjector) {
        this.parentInjector = parentInjector;
    }

    private ResourceInjection getOrCreateExtraBinding(String key) {
        ResourceInjection extraInjection = injectionMap.get(key);
        if (extraInjection == null) {
            extraInjection = new ResourceInjection(key);
            injectionMap.put(key, extraInjection);
        }
        return extraInjection;
    }

    String getFqcn() {
        return classPackage + "." + className;
    }

    String brewJava() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code from VitaminSaber. Do not modify!\n");
        builder.append("package ").append(classPackage).append(";\n\n");
        builder.append("import com.w2ji.vitaminsaber.VitaminSaber.ResourceFinder;\n\n");
        builder.append("public class ").append(className).append(" {\n");
        emitInject(builder);
        builder.append("}\n");
        return builder.toString();
    }

    private void emitInject(StringBuilder builder) {
        builder.append("  public static void inject(Finder finder, final ")
                .append(targetClass)
                .append(" target, Object source) {\n");

        // Emit a call to the superclass injector, if any.
        if (parentInjector != null) {
            builder.append("    ").append(parentInjector).append(".inject(finder, target, source);\n\n");
        }

        // Local variable in which all extras will be temporarily stored.
        builder.append("    Object object;\n");

        // Loop over each extras injection and emit it.
        for (ResourceInjection injection : injectionMap.values()) {
            emitExtraInjection(builder, injection);
        }

        builder.append("  }\n");
    }

    private void emitExtraInjection(StringBuilder builder, ResourceInjection injection) {
        builder.append("    object = finder.getResource(source, \"")
                .append(injection.getKey())
                .append("\");\n");

        List<FieldBinding> requiredBindings = injection.getRequiredBindings();
        if (!requiredBindings.isEmpty()) {
            builder.append("    if (object == null) {\n")
                    .append("      throw new IllegalStateException(\"Required resource with key '")
                    .append(injection.getKey())
                    .append("' for ");
            emitHumanDescription(builder, requiredBindings);
            builder.append(" was not found. If this extra is optional add '@Optional' annotation.\");\n")
                    .append("    }\n");
            emitFieldBindings(builder, injection);
        } else {
            // an optional extra, wrap it in a check to keep original value, if any
            builder.append("    if (object != null) {\n");
            builder.append("  ");
            emitFieldBindings(builder, injection);
            builder.append("    }\n");
        }
    }

    private void emitFieldBindings(StringBuilder builder, ResourceInjection injection) {
        Collection<FieldBinding> fieldBindings = injection.getFieldBindings();
        if (fieldBindings.isEmpty()) {
            return;
        }

        for (FieldBinding fieldBinding : fieldBindings) {
            builder.append("    target.").append(fieldBinding.getName()).append(" = ");

            emitCast(builder, fieldBinding.getType());
            builder.append("object;\n");

        }
    }
}
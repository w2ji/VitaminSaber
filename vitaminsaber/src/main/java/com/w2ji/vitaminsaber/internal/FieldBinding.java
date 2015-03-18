package com.w2ji.vitaminsaber.internal;

import javax.lang.model.type.TypeMirror;

public class FieldBinding {
    private final String name;
    private final TypeMirror type;

    FieldBinding(String name, TypeMirror type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }

    public String getDescription() {
        return "field '" + name + "'";
    }
}

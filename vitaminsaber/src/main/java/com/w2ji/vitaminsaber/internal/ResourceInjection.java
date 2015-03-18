package com.w2ji.vitaminsaber.internal;

import java.util.*;

final class ResourceInjection {
    private final String key;
    private final Set<FieldBinding> fieldBindings = new LinkedHashSet<FieldBinding>();

    ResourceInjection(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Collection<FieldBinding> getFieldBindings() {
        return fieldBindings;
    }

    public List<FieldBinding> getRequiredBindings() {
        List<FieldBinding> requiredBindings = new ArrayList<FieldBinding>();
        for (FieldBinding fieldBinding : fieldBindings) {
            requiredBindings.add(fieldBinding);
        }
        return requiredBindings;
    }

    public void addFieldBinding(FieldBinding fieldBinding) {
        fieldBindings.add(fieldBinding);
    }
}

package com.w2ji.vitaminsaber.internal;

import java.util.*;

final class ResourceInjection {
    private final int key;
    private final Set<FieldBinding> fieldBindings = new LinkedHashSet<FieldBinding>();

    ResourceInjection(int key) {
        this.key = key;
    }

    public int getKey() {
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

    public FieldBinding getFirstBinding(){
        if (!fieldBindings.isEmpty()) {
            return new ArrayList<FieldBinding>(fieldBindings).get(0);
        } else {
            return null;
        }
    }
}

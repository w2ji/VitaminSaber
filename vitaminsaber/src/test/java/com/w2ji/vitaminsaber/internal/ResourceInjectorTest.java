package com.w2ji.vitaminsaber.internal;

import org.junit.Test;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static com.w2ji.vitaminsaber.internal.ResourceInjector.emitHumanDescription;

public class ResourceInjectorTest {

    @Test
    public void humanDescriptionJoinWorks() {

        FieldBinding one = new FieldBinding("one", new FakeTypeMirror());
        FieldBinding two = new FieldBinding("two", new FakeTypeMirror());
        FieldBinding three = new FieldBinding("three", new FakeTypeMirror());

        StringBuilder builder1 = new StringBuilder();
        emitHumanDescription(builder1, asList(one));
        assertThat(builder1.toString()).isEqualTo("field 'one'");

        StringBuilder builder2 = new StringBuilder();
        emitHumanDescription(builder2, asList(one, two));
        assertThat(builder2.toString()).isEqualTo("field 'one' and field 'two'");

        StringBuilder builder3 = new StringBuilder();
        emitHumanDescription(builder3, asList(one, two, three));
        assertThat(builder3.toString()).isEqualTo("field 'one', field 'two', and field 'three'");
    }

    private class FakeTypeMirror implements TypeMirror {

        @Override
        public TypeKind getKind() {
            return null;
        }

        @Override
        public <R, P> R accept(TypeVisitor<R, P> v, P p) {
            return null;
        }
    }
}

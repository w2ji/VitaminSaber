package com.w2ji.vitaminsaber.internal;

import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;
import static com.w2ji.vitaminsaber.internal.TestUtil.vitaminSaberProcessors;

public class InjectResourceTest {
    @Test
    public void testInjectExtraFields(){
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("input/Test.java"))
                .processedWith(vitaminSaberProcessors())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource("output/Test$$ResourceInjector.java"));
    }

    @Test
    public void testAllResources(){
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("input/TestAllResource.java"))
                .processedWith(vitaminSaberProcessors())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource("output/TestAllResource$$ResourceInjector.java"));
    }

    @Test
    public void testDuplicateResources(){
        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource("input/TestDuplicate.java"))
                .processedWith(vitaminSaberProcessors())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource("output/TestDuplicate$$ResourceInjector.java"));
    }
}

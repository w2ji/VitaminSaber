package com.w2ji.vitaminsaber;

import android.app.Activity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;

@RunWith(RobolectricTestRunner.class) @Config(manifest = Config.NONE)
public class VitaminSaberTest {

    @Before
    @After // Clear out cache of injectors  before and after each test.
    public void resetExtrasCache() {
        VitaminSaber.INJECTORS.clear();
    }

    @Test
    public void zeroInjectionsInjectDoesNotThrowException() {
        class Example {
        }

        Example example = new Example();
        VitaminSaber.inject(null, example, null);
        assertThat(VitaminSaber.INJECTORS).contains(entry(Example.class, VitaminSaber.NO_OP));
    }

    @Test public void injectingKnownPackagesIsNoOp() {
        VitaminSaber.inject(new Activity());
        assertThat(VitaminSaber.INJECTORS).isEmpty();
        VitaminSaber.inject(new Activity(), new Object());
        assertThat(VitaminSaber.INJECTORS).isEmpty();
    }

}

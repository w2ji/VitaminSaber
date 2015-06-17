package com.w2ji.vitaminsaber.internal;

import javax.annotation.processing.Processor;
import java.util.Arrays;

public class TestUtil {
    static Iterable<? extends Processor> vitaminSaberProcessors() {
        return Arrays.asList(new InjectResourceProcessor());
    }
}

package com.w2ji.vitaminsaber.example;

import android.content.Context;
import com.w2ji.vitaminsaber.InjectResource;
import com.w2ji.vitaminsaber.VitaminSaber;


public class SampleUtil {
    @InjectResource (R.string.string) public String appName;

    public SampleUtil (Context context){
        VitaminSaber.inject(context, this);
    }

}

/*
 * Copyright 2013 Jake Wharton
 * Copyright 2014 Prateek Srivastava (@f2prateek)
 * Copyright 2015 Wentao Ji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.w2ji.vitaminsaber.example;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.w2ji.vitaminsaber.InjectResource;
import com.w2ji.vitaminsaber.VitaminSaber;

import java.util.Arrays;

public class SampleActivity extends Activity {

    @InjectView(R.id.string)
    TextView testString;
    @InjectView(R.id.image)
    ImageView imageView;
    @InjectResource(R.string.string)
    String string;
    @InjectResource(R.dimen.dimen)
    float dimen;
    @InjectResource(R.integer.integer)
    int integer;
    @InjectResource(R.color.green)
    int color;
    @InjectResource(R.bool.bool)
    boolean bool;
    @InjectResource(R.drawable.example_drawable)
    Drawable exampleDrawable;
    @InjectResource(R.array.int_array)
    int[] intArray;
    @InjectResource(R.array.string_array)
    String[] stringArray;
    @InjectResource(R.anim.anim)
    XmlResourceParser anim;
    @InjectResource(R.animator.animator)
    XmlResourceParser animator;
    @InjectResource(R.layout.activity_main)
    XmlResourceParser layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        VitaminSaber.inject(this);
        testString.setTextColor(color);
        imageView.setImageDrawable(exampleDrawable);

        SampleUtil util = new SampleUtil(this);

        StringBuffer outputString = new StringBuffer();
        outputString.append(String.format("String value: %s\n", string));
        outputString.append(String.format("Dimen value: %f\n", dimen));
        outputString.append(String.format("Integer value: %d\n", integer));
        outputString.append(String.format("Color value: %d\n", color));
        outputString.append(String.format("Bool value: %s\n", bool));
        outputString.append(String.format("Int array value: %s\n", Arrays.toString(intArray)));
        outputString.append(String.format("String array value: %s\n", Arrays.toString(stringArray)));
        outputString.append(String.format("String value in Object: %s\n", util.appName));

        if (anim == null) {
            throw new RuntimeException("Cannot inject anim");
        }

        if (animator == null) {
            throw new RuntimeException("Cannot inject animator");
        }

        if (layout == null){
            throw new RuntimeException("Cannot inject layout");
        }

        testString.setText(outputString.toString());
    }
}

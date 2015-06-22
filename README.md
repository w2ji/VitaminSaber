Vitamin Saber [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.w2ji.vitaminsaber/vitaminsaber/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.w2ji.vitaminsaber/vitaminsaber)
============

Vitamin Saber provides resource injection for Android (@InjectResource(resId)).
It is annotation processor based and will provide all the speed you need on Android by avoiding reflection.

The code was originally a fork of the
Extra dependency library [Dart](https://github.com/f2prateek/dart).


Usages
------

Injecting into activity or fragment:
```java
class ExampleActivity extends Activity {
  @InjectResource(R.string.hello) String str1;
  @InjectResource(R.color.red) int color;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    VitaminSaber.inject(this);
  }
}
```

Injecting into object class:
```java
public class SampleObject {
    @InjectResource (R.string.app_name) public String appName;

    public SampleObject (Context context){
        VitaminSaber.inject(context, this);
    }
}
```


Supported Resource Types
-----
```
    anim,
    animator,
    array,
    attr, <- Not supported
    bool,
    color,
    dimen,
    drawable,
    fraction, <- Not supported
    integer,
    interpolator, <- Not supported
    layout,
    menu, <- Not supported
    mipmap, <- Not supported
    plurals, <- Not supported
    raw, <- Not supported
    string,
    style, <- Not supported
    xml
```

Gradle Dependency
-----
Add the following lines to your gradle dependency

```
compile "com.w2ji.vitaminsaber:vitaminsaber:1.0.2"
```
-----

Proguard
--------

If Proguard is enabled be sure to add these rules on your configuration:

```
-dontwarn com.w2ji.vitaminsaber.internal.**
-keep class **$$ResourceInjector { *; }
-keepnames class * { @com.w2ji.vitaminsaber.InjectResource *;}
```

### Credits

Vitamin Saber has been possible thanks to [Groupon](http://groupon.com) ! 

<img src="https://pbs.twimg.com/profile_images/428288841082871808/Q114lCq3_400x400.png" alt="Groupon logo" width= "200px" height= "200px"/>

And, yes, [we are hiring Android coders](https://jobs.groupon.com/careers/engineering/).

Vitamin Saber is part of [our open source effort](https://github.com/groupon). 


License
-------

    Copyright 2015 Wentao Ji

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


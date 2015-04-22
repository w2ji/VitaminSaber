Vitamin Saber
============

This library provides resource injection for Android (@InjectResource(resId)). The code is a slight modification of the
Extra dependency library Dart (https://github.com/f2prateek/dart).


Usages
------
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


Proguard
--------

If Proguard is enabled be sure to add these rules on your configuration:

```
-dontwarn com.w2ji.vitaminsaber.internal.**
-keep class **$$ResourceInjector { *; }
-keepnames class * { @com.w2ji.vitaminsaber.InjectResource *;}
```

Download
--------
Gradle:
compile 'com.f2prateek.dart:dart:(insert latest version)'


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


package test;

import android.app.Activity;
import com.w2ji.vitaminsaber.InjectResource;

public class TestDuplicate extends Activity {
    @InjectResource(1) String string1;
    @InjectResource(1) String string2;
    @InjectResource(1) String string3;
}
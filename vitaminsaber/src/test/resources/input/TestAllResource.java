package test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import com.w2ji.vitaminsaber.InjectResource;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import java.io.InputStream;

public class TestAllResource extends Activity {
    @InjectResource(1) String string;
    @InjectResource(2) float aFloat;
    @InjectResource(3) int integers;
    @InjectResource(4) Drawable  drawable;
    @InjectResource(5) int[] intArrays;
    @InjectResource(6) String[] stringArrays;
    @InjectResource(7) XmlResourceParser xmlFile;
    @InjectResource(8) InputStream rawResource;
    @InjectResource(9) CharSequence text;
    @InjectResource(10) Movie movie;
}
package es.guillermoorellana.travisforandroid.devtools;

import android.app.Application;
import android.util.DisplayMetrics;

import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import dagger.Module;

import static android.view.Gravity.START;
import static android.view.Gravity.TOP;

@Module
public class DeveloperTools {
    private DeveloperTools() {
    }

    public static void init(Application application) {
        Stetho.initializeWithDefaults(application);
        LeakCanary.install(application);
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        TinyDancer.create()
                .redFlagPercentage(0.2f)
                .yellowFlagPercentage(0.05f)
                .startingGravity(TOP | START)
                .startingXPosition(displayMetrics.widthPixels / 10)
                .startingYPosition(displayMetrics.heightPixels / 4)
                .show(application);
    }
}

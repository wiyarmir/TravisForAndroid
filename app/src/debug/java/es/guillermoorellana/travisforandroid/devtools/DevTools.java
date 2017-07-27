
package es.guillermoorellana.travisforandroid.devtools;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import dagger.Module;

@Module
public class DevTools {
    private DevTools() {
    }

    public static void init(Application application) {
        Stetho.initializeWithDefaults(application);
        LeakCanary.install(application);
    }
}

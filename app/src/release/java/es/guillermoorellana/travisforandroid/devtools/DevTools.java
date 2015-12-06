package es.guillermoorellana.travisforandroid.devtools;

import android.app.Application;

import dagger.Module;

@Module
public class DevTools {
    private DevTools() {
    }

    public static void init(Application application) {
        // noop
    }
}

package es.guillermoorellana.travisforandroid.devtools;

import android.app.Application;

import dagger.Module;

@Module
public class DeveloperTools {
    private DeveloperTools() {
    }

    public static void init(Application application) {
        // noop
    }
}

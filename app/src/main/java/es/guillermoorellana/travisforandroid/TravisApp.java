package es.guillermoorellana.travisforandroid;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import net.danlew.android.joda.JodaTimeAndroid;

import es.guillermoorellana.travisforandroid.devtools.DeveloperTools;
import es.guillermoorellana.travisforandroid.api.ApiModule;
import timber.log.Timber;

public class TravisApp extends Application {

    @SuppressWarnings("NullableProblems")
    // Initialized in onCreate. But be careful if you have ContentProviders in different processes -> their onCreate will be called before app.onCreate().
    @NonNull
    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static TravisApp get(@NonNull Context context) {
        return (TravisApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Since this is app for developers we can log even in release build.
        Timber.plant(new Timber.DebugTree());

        JodaTimeAndroid.init(this);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule("https://api.travis-ci.org/"))
                .build();

        applicationComponent.inject(this);

        developerTools();
    }

    // To be overriden in robolectric tests
    protected void developerTools() {
        DeveloperTools.init(this);
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}

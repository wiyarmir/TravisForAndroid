package es.guillermoorellana.travisforandroid;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

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

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
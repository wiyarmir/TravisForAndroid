package es.guillermoorellana.travisforandroid;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import es.guillermoorellana.travisforandroid.model.api.ApiModule;
import timber.log.Timber;

import static android.view.Gravity.START;
import static android.view.Gravity.TOP;

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
                .apiModule(new ApiModule("https://api.travis-ci.org/"))
                .build();

        applicationComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            TinyDancer.create()
                    .redFlagPercentage(0.2f)
                    .yellowFlagPercentage(0.05f)
                    .startingGravity(TOP | START)
                    .startingXPosition(displayMetrics.widthPixels / 10)
                    .startingYPosition(displayMetrics.heightPixels / 4)
                    .show(this);
        }
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}

package es.guillermoorellana.travisforandroid;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

    @NonNull
    private final TravisApp travisApp;

    public ApplicationModule(@NonNull TravisApp travisApp) {
        this.travisApp = travisApp;
    }

    @Provides
    @NonNull
    @Singleton
    public TravisApp provideQualityMattersApp() {
        return travisApp;
    }

    @Provides
    @NonNull
    @Named(MAIN_THREAD_HANDLER)
    @Singleton
    public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }
}
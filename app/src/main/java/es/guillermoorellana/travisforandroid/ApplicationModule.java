package es.guillermoorellana.travisforandroid;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    @Provides
    @NonNull
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}

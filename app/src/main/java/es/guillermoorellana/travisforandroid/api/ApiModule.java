package es.guillermoorellana.travisforandroid.api;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.guillermoorellana.travisforandroid.BuildConfig;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class ApiModule {

    @NonNull
    private final ChangeableBaseUrl changeableBaseUrl;

    public ApiModule(@NonNull String baseUrl) {
        changeableBaseUrl = new ChangeableBaseUrl(baseUrl);
    }

    @Provides
    @NonNull
    @Singleton
    public ChangeableBaseUrl provideChangeableBaseUrl() {
        return changeableBaseUrl;
    }

    @Provides
    @NonNull
    @Singleton
    public TravisRestApi provideQualityMattersApi(@NonNull OkHttpClient okHttpClient,
                                                  @NonNull ChangeableBaseUrl changeableBaseUrl) {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(changeableBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        // Fail early: check Retrofit configuration at creation time
        if (BuildConfig.DEBUG) {
            builder.validateEagerly();
        }

        return builder.build().create(TravisRestApi.class);
    }
}


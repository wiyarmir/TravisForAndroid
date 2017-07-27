
package es.guillermoorellana.travisforandroid.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.guillermoorellana.travisforandroid.BuildConfig;
import es.guillermoorellana.travisforandroid.services.network.TravisApi;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @NonNull
    private final HttpUrl baseUrl;

    public ApiModule(@NonNull String baseUrl) {
        this.baseUrl = HttpUrl.parse(baseUrl);
    }

    @Provides
    @Named("baseUrl")
    @Singleton
    @NonNull
    public HttpUrl provideChangeableBaseUrl() {
        return baseUrl;
    }

    @Provides
    @Singleton
    @NonNull
    public TravisApi provideTravisApi(@NonNull OkHttpClient okHttpClient,
                                      @NonNull @Named("baseUrl") HttpUrl changeableBaseUrl,
                                      @NonNull Gson gson) {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(changeableBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                // Fail early: check Retrofit configuration at creation time
                .validateEagerly(BuildConfig.DEBUG);

        return builder.build().create(TravisApi.class);
    }
}


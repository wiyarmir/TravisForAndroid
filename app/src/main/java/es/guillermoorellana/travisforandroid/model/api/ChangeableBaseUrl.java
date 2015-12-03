package es.guillermoorellana.travisforandroid.model.api;

import android.support.annotation.NonNull;

import com.squareup.okhttp.HttpUrl;

import java.util.concurrent.atomic.AtomicReference;

import retrofit.BaseUrl;

public class ChangeableBaseUrl implements BaseUrl {

    @NonNull
    private final AtomicReference<HttpUrl> baseUrl;

    public ChangeableBaseUrl(@NonNull String baseUrl) {
        this.baseUrl = new AtomicReference<>(HttpUrl.parse(baseUrl));
    }

    public void setBaseUrl(@NonNull String baseUrl) {
        this.baseUrl.set(HttpUrl.parse(baseUrl));
    }

    @Override
    @NonNull
    public HttpUrl url() {
        return baseUrl.get();
    }
}

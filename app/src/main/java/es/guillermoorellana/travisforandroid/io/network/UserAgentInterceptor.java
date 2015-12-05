package es.guillermoorellana.travisforandroid.io.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static es.guillermoorellana.travisforandroid.BuildConfig.VERSION_NAME;

public final class UserAgentInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String USER_AGENT_HEADER_VALUE = "Travis4Android/" + VERSION_NAME;

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(USER_AGENT_HEADER_NAME)
                .addHeader(USER_AGENT_HEADER_NAME, USER_AGENT_HEADER_VALUE)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}
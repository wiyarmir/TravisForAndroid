package es.guillermoorellana.travisforandroid.services.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public final class HeadersInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String USER_AGENT_HEADER_VALUE = "Travis4Android/" + "";
    private static final String ACCEPT_HEADER_NAME = "Accept";
    private static final String ACCEPT_HEADER_VALUE = "application/vnd.travis-ci.2+json";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(USER_AGENT_HEADER_NAME)
                .addHeader(USER_AGENT_HEADER_NAME, USER_AGENT_HEADER_VALUE)
                .addHeader(ACCEPT_HEADER_NAME, ACCEPT_HEADER_VALUE)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}
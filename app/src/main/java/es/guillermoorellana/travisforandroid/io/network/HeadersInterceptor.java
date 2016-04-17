/*
 * Copyright 2015 Guillermo Orellana Ruiz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.guillermoorellana.travisforandroid.io.network;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static es.guillermoorellana.travisforandroid.BuildConfig.VERSION_NAME;

public final class HeadersInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String USER_AGENT_HEADER_VALUE = "Travis4Android/" + VERSION_NAME;
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
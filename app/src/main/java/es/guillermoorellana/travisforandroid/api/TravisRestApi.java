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

package es.guillermoorellana.travisforandroid.api;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.entity.ApiBuildDetails;
import es.guillermoorellana.travisforandroid.api.entity.ApiBuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Single;

public interface TravisRestApi {
    @GET("repos/")
    Single<List<ApiRepo>> repos();

    @GET("repos/")
    Single<List<ApiRepo>> repos(@Query("search") String search);

    @GET("repos/{id}")
    Single<ApiRepo> repo(@Path("id") int id);

    @GET("builds/{id}")
    Single<ApiBuildDetails> build(@Path("id") long id);

    @GET("builds")
    Single<ApiBuildHistory> buildHistory(@Query("repository_id") long repositoryId);

    @GET("builds")
    Single<ApiBuildHistory> buildHistoryByEventType(@Query("repository_id") long repositoryId, @Query("event_type") String eventType);
}

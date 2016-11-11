/*
 *   Copyright 2016 Guillermo Orellana Ruiz
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package es.guillermoorellana.travisforandroid.services.network;

import java.util.List;

import es.guillermoorellana.travisforandroid.services.network.model.entity.BuildDetails;
import es.guillermoorellana.travisforandroid.services.network.model.entity.BuildHistory;
import es.guillermoorellana.travisforandroid.services.network.model.entity.Repo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

public interface TravisApi {
    @GET("repos/")
    Single<List<Repo>> repos();

    @GET("repos/")
    Single<List<Repo>> repos(@Query("search") String search);

    @GET("repos/{id}")
    Single<Repo> repo(@Path("id") int id);

    @GET("builds/{id}")
    Single<BuildDetails> build(@Path("id") long id);

    @GET("builds")
    Single<BuildHistory> buildHistory(@Query("repository_id") long repositoryId);

    @GET("builds")
    Single<BuildHistory> buildHistoryByEventType(@Query("repository_id") long repositoryId, @Query("event_type") String eventType);
}

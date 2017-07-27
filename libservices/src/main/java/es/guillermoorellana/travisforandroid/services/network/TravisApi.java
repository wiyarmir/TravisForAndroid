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

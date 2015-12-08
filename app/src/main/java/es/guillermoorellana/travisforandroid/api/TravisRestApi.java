package es.guillermoorellana.travisforandroid.api;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.entity.BuildDetails;
import es.guillermoorellana.travisforandroid.api.entity.BuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.Repo;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Single;

public interface TravisRestApi {
    @GET("repos/")
    Single<List<Repo>> repos();

    @GET("repos/{id}")
    Single<Repo> repo(@Path("id") int id);

    @GET("builds/{id}")
    Single<BuildDetails> build(@Path("id") int id);

    @GET("repos/{slug}/builds")
    Single<BuildHistory> buildsHistory(@Path("slug") String slug);
}

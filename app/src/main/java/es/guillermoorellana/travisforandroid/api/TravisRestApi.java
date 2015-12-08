package es.guillermoorellana.travisforandroid.api;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.entity.Repo;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Single;

public interface TravisRestApi {
    @GET("repos/")
    Single<List<Repo>> repos();

    @GET("repos/{id}")
    Single<Repo> repo(@Path("id") int id);
}

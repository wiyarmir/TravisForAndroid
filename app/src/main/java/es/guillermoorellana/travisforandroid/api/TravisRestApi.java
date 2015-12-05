package es.guillermoorellana.travisforandroid.api;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.entity.Repo;
import retrofit.http.GET;
import rx.Single;

public interface TravisRestApi {
    @GET("repos/")
    Single<List<Repo>> repos();
}

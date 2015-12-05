package es.guillermoorellana.travisforandroid.api;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.entities.Repo;
import retrofit.http.GET;
import rx.Single;

public interface TravisRestApi {
    @GET
    Single<List<Repo>> getRepos();
}

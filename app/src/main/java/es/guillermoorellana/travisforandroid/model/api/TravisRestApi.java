package es.guillermoorellana.travisforandroid.model.api;

import java.util.List;

import es.guillermoorellana.travisforandroid.model.entities.Repo;
import retrofit.http.GET;
import rx.Observable;

public interface TravisRestApi {
    @GET
    Observable<List<Repo>> getRepos();
}

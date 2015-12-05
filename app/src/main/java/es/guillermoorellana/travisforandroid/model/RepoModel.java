package es.guillermoorellana.travisforandroid.model;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entities.Repo;
import rx.Single;

public class RepoModel {
    @NonNull
    private final TravisRestApi mTravisRestApi;

    @Inject
    public RepoModel(@NonNull TravisRestApi travisRestApi) {
        mTravisRestApi = travisRestApi;
    }

    public Single<List<Repo>> getRepos() {
        return mTravisRestApi.getRepos();
    }
}

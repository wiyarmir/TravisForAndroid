package es.guillermoorellana.travisforandroid.model;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entity.Build;
import es.guillermoorellana.travisforandroid.api.entity.BuildHistory;
import rx.Single;

public class BuildModel {

    private final TravisRestApi mTravisRestApi;

    @Inject
    public BuildModel(@NonNull TravisRestApi travisRestApi) {
        mTravisRestApi = travisRestApi;
    }

    @RxLogObservable
    public Single<List<Build>> getBuilds(String slug) {
        return mTravisRestApi.buildsHistory(slug)
                .map(BuildHistory::getBuilds);
    }
}

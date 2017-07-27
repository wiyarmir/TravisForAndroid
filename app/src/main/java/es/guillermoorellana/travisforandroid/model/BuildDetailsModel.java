package es.guillermoorellana.travisforandroid.model;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.services.network.TravisApi;
import es.guillermoorellana.travisforandroid.services.network.model.entity.BuildDetails;
import rx.Single;

public class BuildDetailsModel {
    private final TravisApi mTravisApi;

    @Inject
    public BuildDetailsModel(TravisApi travisApi) {
        mTravisApi = travisApi;
    }

    public Single<BuildDetails> getBuildDetails(long buildId) {
        return mTravisApi.build(buildId);
    }
}

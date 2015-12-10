package es.guillermoorellana.travisforandroid.ui.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.api.entity.Build;
import es.guillermoorellana.travisforandroid.api.entity.Repo;
import es.guillermoorellana.travisforandroid.model.BuildModel;
import es.guillermoorellana.travisforandroid.mvp.BaseRxLcePresenter;
import es.guillermoorellana.travisforandroid.ui.view.BuildsView;

public class BuildsPresenter extends BaseRxLcePresenter<BuildsView, List<Build>> {
    @NonNull private final BuildModel mBuildModel;
    @NonNull private final Repo mRepo;

    @Inject
    public BuildsPresenter(@NonNull BuildModel buildModel, @NonNull Repo repo) {
        mBuildModel = buildModel;
        mRepo = repo;
    }

    public void reloadData(boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        unsubscribe();

        subscribe(mBuildModel.getBuilds(mRepo.getSlug()), pullToRefresh);
    }
}

package es.guillermoorellana.travisforandroid.ui.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.api.entity.Repo;
import es.guillermoorellana.travisforandroid.model.RepoModel;
import es.guillermoorellana.travisforandroid.mvp.BaseRxLcePresenter;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;

public class ReposPresenter extends BaseRxLcePresenter<ReposView, List<Repo>> {
    @NonNull private final RepoModel repoModel;

    @Inject
    public ReposPresenter(@NonNull RepoModel repoModel) {
        this.repoModel = repoModel;
    }


    public void reloadData(boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        unsubscribe();

        subscribe(repoModel.getRepos(), pullToRefresh);
    }
}

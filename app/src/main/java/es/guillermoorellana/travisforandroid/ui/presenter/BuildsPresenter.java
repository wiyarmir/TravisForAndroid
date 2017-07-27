package es.guillermoorellana.travisforandroid.ui.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.data.TravisDatabase;
import es.guillermoorellana.travisforandroid.model.Build_Table;
import es.guillermoorellana.travisforandroid.mvp.BasePresenter;
import es.guillermoorellana.travisforandroid.ui.view.BuildsView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BuildsPresenter extends BasePresenter<BuildsView> {
    private final Repository repository;

    public BuildsPresenter(Repository repository) {
        this.repository = repository;
    }

    public void reloadData(long repoId) {
        repository.getBuildHistory(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        integer -> {
                            Timber.d("subscription yielded " + integer);
                        },
                        error -> {
                            Timber.e(error, "error reloading data");
                            if (isViewAttached()) {
                                getView().showError(error);
                            }
                        }
                );
    }

    @NonNull
    public Loader<Cursor> getCursorLoader(Context context, long repoId) {
        return new CursorLoader(
                context,
                TravisDatabase.BUILD_MODEL.URI_WITH_REPO(repoId),
                TravisDatabase.BUILD_MODEL.FULL_PROJECTION,
                null,
                null,
                Build_Table.startedAt + "DESC"
        );
    }
}

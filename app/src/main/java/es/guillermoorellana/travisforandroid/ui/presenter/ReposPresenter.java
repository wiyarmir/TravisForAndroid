
package es.guillermoorellana.travisforandroid.ui.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.data.TravisDatabase;
import es.guillermoorellana.travisforandroid.model.Repo_Table;
import es.guillermoorellana.travisforandroid.mvp.BasePresenter;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import rx.Scheduler;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ReposPresenter extends BasePresenter<ReposView> {

    @NonNull
    private final Repository repository;
    private Subscription subscription;
    private Scheduler scheduler;

    @Inject
    public ReposPresenter(@NonNull Repository repository) {
        this(repository, AndroidSchedulers.mainThread());
    }

    @VisibleForTesting
    ReposPresenter(@NonNull Repository repository, @NonNull Scheduler scheduler) {

        this.repository = repository;
        this.scheduler = scheduler;
    }

    public void reloadData() {
        subscribe(repository.getRepos());
    }

    public void reloadData(String search) {
        subscribe(repository.getRepos(search));
    }

    private void subscribe(Single<Integer> repos) {
        unsubscribe();
        subscription = repos.subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe((n) -> {
                }, (error) -> {
                    Timber.e(error, "error reloading data");
                    if (isViewAttached()) {
                        getView().showError(error);
                    }
                });
    }

    private void unsubscribe() {
        // unsubscribing is deemed NetworkOnMainThreadException :/
        Schedulers.io().createWorker().schedule(() -> {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        });
    }

    @NonNull
    public Loader<Cursor> getCursorLoader(Context context) {
        return new CursorLoader(
                context,
                TravisDatabase.REPO_MODEL.CONTENT_REPO_URI,
                TravisDatabase.REPO_MODEL.PROJECTION,
                null,
                null,
                Repo_Table.lastBuildStartedAt.getContainerKey() + "DESC"
        );
    }

    @NonNull
    public Loader<Cursor> getSearchCursorLoader(Context context, String search) {
        String selection = null;
        String[] selectionArgs = null;
        if (!search.isEmpty()) {
            selection = Repo_Table.slug.toString() + " LIKE %?%";
            selectionArgs = new String[]{search};
        }

        return new CursorLoader(
                context,
                TravisDatabase.REPO_MODEL.CONTENT_REPO_URI,
                TravisDatabase.REPO_MODEL.PROJECTION,
                selection,
                selectionArgs,
                Repo_Table.lastBuildStartedAt.getContainerKey() + "DESC"
        );
    }
}

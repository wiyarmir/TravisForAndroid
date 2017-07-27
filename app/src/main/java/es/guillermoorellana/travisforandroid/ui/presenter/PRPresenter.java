package es.guillermoorellana.travisforandroid.ui.presenter;

import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.mvp.BasePresenter;
import es.guillermoorellana.travisforandroid.ui.view.PRView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class PRPresenter extends BasePresenter<PRView> {
    private final Repository repository;

    public PRPresenter(Repository repository) {
        this.repository = repository;
    }

    public void reloadData(long repoId) {
        repository.getPRHistory(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                            Timber.d("subscription yielded " + integer);
                        },
                        error -> {
                            Timber.e(error, "error reloading data");
                            if (isViewAttached()) {
                                getView().showError(error);
                            }
                        });
    }
}

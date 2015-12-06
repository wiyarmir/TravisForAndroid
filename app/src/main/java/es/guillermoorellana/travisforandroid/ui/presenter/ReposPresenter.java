package es.guillermoorellana.travisforandroid.ui.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.model.RepoModel;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ReposPresenter extends BasePresenter<ReposView> {
    @NonNull private final RepoModel repoModel;
    private Subscription subscription;

    @Inject
    public ReposPresenter(@NonNull RepoModel repoModel) {
        this.repoModel = repoModel;
    }


    public void reloadData() {
        if (isViewAttached()) {
            getView().showLoadingUi();
        }

        ensureUnsubscribed();

        subscription = repoModel.getRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            Timber.d("items success");
                            if (isViewAttached()) {
                                getView().showContentUi(items);
                            }
                        },
                        error -> {
                            Timber.e(error, "items error");
                            if (isViewAttached()) {
                                getView().showErrorUi(error);
                            }
                        }
                );
    }

    @Override
    public void detachView(boolean retainInstance) {
        ensureUnsubscribed();
        super.detachView(retainInstance);
    }

    private void ensureUnsubscribed() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}

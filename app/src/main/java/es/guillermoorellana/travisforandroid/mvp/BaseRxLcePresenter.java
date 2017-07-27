
package es.guillermoorellana.travisforandroid.mvp;

import android.support.annotation.VisibleForTesting;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseRxLcePresenter<V extends MvpLceView<M>, M>
        extends BasePresenter<V>
        implements MvpPresenter<V> {

    @VisibleForTesting Subscriber<M> subscriber;

    protected void unsubscribe() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            // there is a bug that marks unsubscription as network on UI thread
            Schedulers.io().createWorker().schedule(subscriber::unsubscribe);
        }

        subscriber = null;
    }

    public void subscribe(Single<M> single, final boolean pullToRefresh) {

        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        unsubscribe();

        subscriber = new Subscriber<M>() {
            private boolean ptr = pullToRefresh;

            @Override
            public void onCompleted() {
                BaseRxLcePresenter.this.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                BaseRxLcePresenter.this.onError(e, ptr);
            }

            @Override
            public void onNext(M m) {
                BaseRxLcePresenter.this.onNext(m);
            }
        };

        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    protected void onCompleted() {
        if (isViewAttached()) {
            getView().showContent();
        }
        unsubscribe();
    }

    protected void onError(Throwable e, boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showError(e, pullToRefresh);
        }
        unsubscribe();
    }

    protected void onNext(M data) {
        if (isViewAttached()) {
            getView().setData(data);
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            unsubscribe();
        }
    }
}

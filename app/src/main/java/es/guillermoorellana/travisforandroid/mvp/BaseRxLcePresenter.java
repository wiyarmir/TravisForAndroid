/*
 * Copyright 2015 Guillermo Orellana Ruiz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.guillermoorellana.travisforandroid.mvp;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseRxLcePresenter<V extends MvpLceView<M>, M>
        extends BasePresenter<V>
        implements MvpPresenter<V> {

    protected Subscriber<M> subscriber;

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

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

package es.guillermoorellana.travisforandroid.ui.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.data.TravisDatabase;
import es.guillermoorellana.travisforandroid.model.Repo_Table;
import es.guillermoorellana.travisforandroid.mvp.BasePresenter;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ReposPresenter extends BasePresenter<ReposView> {

    @NonNull private final Repository repository;
    private Subscription subscription;

    @Inject
    public ReposPresenter(@NonNull Repository repository) {
        this.repository = repository;
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
                .observeOn(AndroidSchedulers.mainThread())
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

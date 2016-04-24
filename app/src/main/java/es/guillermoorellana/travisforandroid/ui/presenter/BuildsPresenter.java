/*
 *   Copyright 2016 Guillermo Orellana Ruiz
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

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

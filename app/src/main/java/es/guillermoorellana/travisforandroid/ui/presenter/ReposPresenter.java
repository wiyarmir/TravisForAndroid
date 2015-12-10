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

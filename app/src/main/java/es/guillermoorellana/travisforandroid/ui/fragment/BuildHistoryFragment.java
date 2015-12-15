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

package es.guillermoorellana.travisforandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.api.entity.BuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.Repo;
import es.guillermoorellana.travisforandroid.model.BuildHistoryModel;
import es.guillermoorellana.travisforandroid.mvp.BaseMvpLceFragment;
import es.guillermoorellana.travisforandroid.ui.DividerItemDecoration;
import es.guillermoorellana.travisforandroid.ui.adapter.BuildHistoryAdapter;
import es.guillermoorellana.travisforandroid.ui.presenter.BuildHistoryPresenter;
import es.guillermoorellana.travisforandroid.ui.view.BuildsView;

@FragmentWithArgs
public class BuildHistoryFragment
        extends BaseMvpLceFragment<RecyclerView, BuildHistory, BuildsView, BuildHistoryPresenter>
        implements BuildsView {

    @NonNull
    @Arg
    Repo repo;
    @NonNull
    @Inject
    BuildHistoryPresenter buildHistoryPresenter;
    private BuildHistoryAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        FragmentArgs.inject(this);
        TravisApp.get(getContext()).applicationComponent()
                .plus(new BuildsFragmentModule(repo))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_builds, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new BuildHistoryAdapter();
        contentView.setAdapter(mAdapter);
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.addItemDecoration(new DividerItemDecoration(getContext()));
        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    public BuildHistoryPresenter createPresenter() {
        return buildHistoryPresenter;
    }

    @Override
    public void setData(BuildHistory data) {
        mAdapter.setData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().reloadData(pullToRefresh);
    }

    @Override
    public LceViewState<BuildHistory, BuildsView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public BuildHistory getData() {
        return mAdapter == null ? null : mAdapter.getData();
    }

    @Subcomponent(modules = BuildsFragmentModule.class)
    public interface BuildsFragmentComponent {
        void inject(@NonNull BuildHistoryFragment buildHistoryFragment);
    }

    @Module
    public static class BuildsFragmentModule {
        private final Repo mRepo;

        public BuildsFragmentModule(Repo repo) {
            this.mRepo = repo;
        }

        @Provides
        @NonNull
        public BuildHistoryPresenter provideReposPresenter(@NonNull BuildHistoryModel buildHistoryModel) {
            return new BuildHistoryPresenter(buildHistoryModel, mRepo);
        }
    }
}

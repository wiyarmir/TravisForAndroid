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

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;
import es.guillermoorellana.travisforandroid.model.RepoModel;
import es.guillermoorellana.travisforandroid.mvp.BaseMvpLceFragment;
import es.guillermoorellana.travisforandroid.ui.DividerItemDecoration;
import es.guillermoorellana.travisforandroid.ui.adapter.RepoAdapter;
import es.guillermoorellana.travisforandroid.ui.presenter.ReposPresenter;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import timber.log.Timber;

public class ReposFragment
        extends BaseMvpLceFragment<RecyclerView, List<ApiRepo>, ReposView, ReposPresenter>
        implements ReposView {

    @Inject ReposPresenter reposPresenter;

    private RepoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        TravisApp.get(getContext()).applicationComponent().plus(new ReposFragmentModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.view_repos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new RepoAdapter();
        contentView.setAdapter(mAdapter);
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.addItemDecoration(new DividerItemDecoration(getContext()));
        mAdapter.getOnClickSubject().subscribe(
                clickedView -> {
                    int adapterPosition = contentView.getChildAdapterPosition(clickedView);
                    Timber.d("clicked position " + adapterPosition);
                    getMainView().replaceFragmentBackStack(BuildHistoryFragmentBuilder.newBuildHistoryFragment(mAdapter.getItem(adapterPosition)));
                }
        );
        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    public ReposPresenter createPresenter() {
        return reposPresenter;
    }

    @Override
    public void setData(List<ApiRepo> repos) {
        Timber.d("Recived data: %d repos", repos.size());
        mAdapter.setData(repos);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().reloadData(pullToRefresh);
    }

    @Override
    public LceViewState<List<ApiRepo>, ReposView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public List<ApiRepo> getData() {
        return mAdapter == null ? null : mAdapter.getData();
    }

    @Subcomponent(modules = ReposFragmentModule.class)
    public interface ReposFragmentComponent {
        void inject(@NonNull ReposFragment itemsFragment);
    }

    @Module
    public static class ReposFragmentModule {

        @Provides
        @NonNull
        public ReposPresenter provideReposPresenter(@NonNull RepoModel repoModel) {
            return new ReposPresenter(repoModel);
        }
    }
}

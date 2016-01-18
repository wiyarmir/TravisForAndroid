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

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

import butterknife.Bind;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.data.TravisDatabase;
import es.guillermoorellana.travisforandroid.model.Repo;
import es.guillermoorellana.travisforandroid.model.Repo_Table;
import es.guillermoorellana.travisforandroid.mvp.BaseFragment;
import es.guillermoorellana.travisforandroid.ui.DividerItemDecoration;
import es.guillermoorellana.travisforandroid.ui.adapter.RepoAdapter;
import es.guillermoorellana.travisforandroid.ui.presenter.ReposPresenter;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import timber.log.Timber;

public class ReposFragment
        extends BaseFragment<ReposView, ReposPresenter>
        implements LoaderManager.LoaderCallbacks<Cursor>, ReposView, SwipeRefreshLayout.OnRefreshListener {

    public static final int LOADER_ID = 1001;

    private static final String[] PROJECTION = new String[]{
            Repo_Table._id.toString(),
            Repo_Table.repoId.toString(),
            Repo_Table.slug.toString(),
            Repo_Table.active.toString(),
            Repo_Table.description.toString(),
            Repo_Table.lastBuildId.toString(),
            Repo_Table.lastBuildNumber.toString(),
            Repo_Table.lastBuildState.toString(),
            Repo_Table.lastBuildDuration.toString(),
            Repo_Table.lastBuildLanguage.toString(),
            Repo_Table.lastBuildStartedAt.toString(),
            Repo_Table.lastBuildFinishedAt.toString(),
            Repo_Table.githubLanguage.toString(),
    };

    @Inject ReposPresenter reposPresenter;
    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @Bind(R.id.recyclerView) RecyclerView contentView;
    @Bind(R.id.errorView) TextView errorView;

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
        swipeContainer.setOnRefreshListener(this);
        mAdapter = new RepoAdapter(null);
        contentView.setAdapter(mAdapter);
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.addItemDecoration(new DividerItemDecoration(getContext()));
        mAdapter.getOnClickSubject().subscribe(
                clickedView -> {
                    mAdapter.getCursor().moveToPosition(contentView.getChildAdapterPosition(clickedView));
                    long repoId = FlowManager.getModelAdapter(Repo.class).loadFromCursor(mAdapter.getCursor()).getRepoId();
                    Timber.d("looking for repo " + repoId);
                    getMainView().replaceFragmentBackStack(BuildsFragmentBuilder.newBuildsFragment(repoId));
                }
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public ReposPresenter createPresenter() {
        return reposPresenter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                TravisDatabase.REPO_MODEL.CONTENT_REPO_URI,
                PROJECTION,
                null,
                null,
                Repo_Table.lastBuildStartedAt.getContainerKey() + "DESC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Timber.d("onLoadFinished");
        mAdapter.changeCursor(data);
        contentView.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Timber.d("onLoaderReset");
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getPresenter().reloadData();
    }

    @Override
    public void showError(Throwable error) {
        errorView.setText(error.getMessage());
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }

    @Subcomponent(modules = ReposFragmentModule.class)
    public interface ReposFragmentComponent {
        void inject(@NonNull ReposFragment itemsFragment);
    }

    @Module
    public static class ReposFragmentModule {

        @Provides
        @NonNull
        public ReposPresenter provideReposPresenter(@NonNull Repository repository) {
            return new ReposPresenter(repository);
        }
    }
}

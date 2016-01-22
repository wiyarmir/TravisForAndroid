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

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import javax.inject.Inject;

import butterknife.Bind;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.data.TravisDatabase;
import es.guillermoorellana.travisforandroid.model.Build_Table;
import es.guillermoorellana.travisforandroid.mvp.BaseFragment;
import es.guillermoorellana.travisforandroid.ui.DividerItemDecoration;
import es.guillermoorellana.travisforandroid.ui.adapter.BuildsAdapter;
import es.guillermoorellana.travisforandroid.ui.presenter.BuildsPresenter;
import es.guillermoorellana.travisforandroid.ui.view.BuildsView;

@FragmentWithArgs
public class BuildsFragment
        extends BaseFragment<BuildsView, BuildsPresenter>
        implements LoaderManager.LoaderCallbacks<Cursor>, BuildsView, SwipeRefreshLayout.OnRefreshListener {

    private static final String[] PROJECTION = {
            Build_Table._id.toString(),
            Build_Table.id.toString(),
            Build_Table.duration.toString(),
            Build_Table.startedAt.toString(),
            Build_Table.number.toString(),
            Build_Table.repositoryId.toString(),
            Build_Table.state.toString(),
            Build_Table.finishedAt.toString(),
            Build_Table.pullRequest.toString(),
            Build_Table.pullRequestNumber.toString(),
            Build_Table.pullRequestTitle.toString(),
            Build_Table.commitId.toString()
    };

    private static final int LOADER_ID = 1002;

    @Arg long repoId;
    @NonNull
    @Inject
    BuildsPresenter buildsPresenter;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.errorView) TextView errorView;
    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    private BuildsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        FragmentArgs.inject(this);
        TravisApp.get(getContext()).applicationComponent()
                .plus(new BuildsFragmentModule())
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_builds, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new BuildsAdapter(null);
        swipeContainer.setOnRefreshListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public BuildsPresenter createPresenter() {
        return buildsPresenter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                TravisDatabase.BUILD_MODEL.URI_WITH_REPO(repoId),
                PROJECTION,
                null,
                null,
                Build_Table.startedAt + "DESC"
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);

        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        swipeContainer.setRefreshing(false);
        mAdapter.changeCursor(null);
    }

    @Override
    public void onRefresh() {
        getPresenter().reloadData(repoId);
    }

    @Override
    public void showError(Throwable error) {
        errorView.setText(error.getMessage());
        errorView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }

    @Subcomponent(modules = BuildsFragmentModule.class)
    public interface BuildsFragmentComponent {
        void inject(@NonNull BuildsFragment buildsFragment);
    }

    @Module
    public static class BuildsFragmentModule {

        @Provides
        @NonNull
        public BuildsPresenter provideReposPresenter(Repository repository) {
            return new BuildsPresenter(repository);
        }
    }
}

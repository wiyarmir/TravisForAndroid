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

import butterknife.Bind;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.mvp.BaseFragment;
import es.guillermoorellana.travisforandroid.ui.DividerItemDecoration;
import es.guillermoorellana.travisforandroid.ui.adapter.BuildsAdapter;
import es.guillermoorellana.travisforandroid.ui.presenter.BuildsPresenter;
import es.guillermoorellana.travisforandroid.ui.view.BuildsView;
import hugo.weaving.DebugLog;

@FragmentWithArgs
public class BuildsFragment
        extends BaseFragment<BuildsView, BuildsPresenter>
        implements LoaderManager.LoaderCallbacks<Cursor>, BuildsView, SwipeRefreshLayout.OnRefreshListener {

    private static final int LOADER_ID = 1003;

    @Arg long repoId;

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.errorView) TextView errorView;
    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    private BuildsAdapter mAdapter;
    private BuildsFragmentComponent mComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        mComponent = TravisApp.get(getContext()).applicationComponent().plus(new BuildsFragmentModule());
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

    @NonNull
    @Override
    public BuildsPresenter createPresenter() {
        return mComponent.presenter();
    }

    @DebugLog
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return getPresenter().getCursorLoader(getActivity(), repoId);
    }

    @DebugLog
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);

        swipeContainer.setRefreshing(false);
    }

    @DebugLog
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

        @NonNull
        BuildsPresenter presenter();
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

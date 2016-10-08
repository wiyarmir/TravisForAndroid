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
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.Bind;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.model.Repo;
import es.guillermoorellana.travisforandroid.mvp.BaseFragment;
import es.guillermoorellana.travisforandroid.ui.adapter.RepoAdapter;
import es.guillermoorellana.travisforandroid.ui.presenter.ReposPresenter;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import timber.log.Timber;

public class ReposFragment extends BaseFragment<ReposView, ReposPresenter>
        implements LoaderManager.LoaderCallbacks<Cursor>, ReposView,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    public static final String KEY_SEARCH = "SEARCHTEXT";
    public static final int REGULAR = 1001;
    public static final int SEARCH = 1002;

    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @Bind(R.id.recyclerView) RecyclerView contentView;
    @Bind(R.id.errorView) TextView errorView;

    private RepoAdapter mAdapter;
    private ReposFragmentComponent mComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComponent = TravisApp.get(getContext()).applicationComponent().plus(new ReposFragmentModule());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_repos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer.setOnRefreshListener(this);
        mAdapter = new RepoAdapter(null);
        contentView.setAdapter(mAdapter);
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.getOnClickSubject().subscribe(
                clickedView -> {
                    mAdapter.getCursor().moveToPosition(contentView.getChildAdapterPosition(clickedView));
                    long repoId = FlowManager.getModelAdapter(Repo.class).loadFromCursor(mAdapter.getCursor()).getRepoId();
                    Timber.d("looking for repo " + repoId);
                    getMainView().replaceFragmentBackStack(SingleRepoFragmentBuilder.newSingleRepoFragment(repoId));
                }
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(REGULAR, null, this);
    }

    @NonNull
    @Override
    public ReposPresenter createPresenter() {
        return mComponent.reposPresenter();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case SEARCH:
                return getPresenter().getSearchCursorLoader(getActivity(), args.getString(KEY_SEARCH, ""));
            case REGULAR:
            default:
                return getPresenter().getCursorLoader(getActivity());
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Timber.d("onLoadFinished");
        if (data.getCount() > 0) {
            mAdapter.changeCursor(data);
            contentView.setVisibility(View.VISIBLE);
        } else {
            fatalError("No quick search results.");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onRefresh() {
        swipeContainer.setRefreshing(true);
        getPresenter().reloadData();
    }

    @Override
    public void showError(Throwable error) {
        if (mAdapter.getItemCount() > 0) {
            discreteError(error.getMessage());
        } else {
            fatalError(error.getMessage());
        }
        swipeContainer.setRefreshing(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        performSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        swipeContainer.setRefreshing(true);
        getPresenter().reloadData(newText);
        return true;
    }

    private void performSearch(String search) {
        Bundle args = new Bundle();
        args.putString(KEY_SEARCH, search);
        getLoaderManager().restartLoader(SEARCH, args, this);
    }

    private void fatalError(String message) {
        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    private void discreteError(String message) {
        Snackbar.make(contentView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Subcomponent(modules = ReposFragmentModule.class)
    public interface ReposFragmentComponent {
        void inject(@NonNull ReposFragment itemsFragment);

        ReposPresenter reposPresenter();
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

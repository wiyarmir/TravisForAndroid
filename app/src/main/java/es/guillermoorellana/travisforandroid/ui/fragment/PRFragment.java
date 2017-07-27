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

import butterknife.BindView;
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
import es.guillermoorellana.travisforandroid.ui.presenter.PRPresenter;
import es.guillermoorellana.travisforandroid.ui.view.PRView;

@FragmentWithArgs
public class PRFragment extends BaseFragment<PRView, PRPresenter>
        implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener, PRView {
    @Arg long repoId;

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

    private static final int LOADER_ID = 1004;

    @NonNull
    @Inject
    PRPresenter prPresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.errorView)
    TextView errorView;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private BuildsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        TravisApp.get(getContext()).applicationComponent()
                .plus(new PRFragmentModule())
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pr, container, false);
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
    public PRPresenter createPresenter() {
        return prPresenter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                TravisDatabase.BUILD_MODEL.URI_WITH_REPO(repoId),
                PROJECTION,
                Build_Table.pullRequest.eq(true).getQuery(),
                null,
                Build_Table.startedAt + "DESC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
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

    @Subcomponent(modules = PRFragmentModule.class)
    public interface PRFragmentComponent {
        void inject(@NonNull PRFragment prFragment);
    }

    @Module
    public static class PRFragmentModule {

        @Provides
        @NonNull
        public PRPresenter provideReposPresenter(Repository repository) {
            return new PRPresenter(repository);
        }
    }
}

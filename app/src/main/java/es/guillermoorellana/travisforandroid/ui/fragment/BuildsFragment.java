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

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.api.entity.Build;
import es.guillermoorellana.travisforandroid.api.entity.Repo;
import es.guillermoorellana.travisforandroid.model.BuildModel;
import es.guillermoorellana.travisforandroid.mvp.BaseMvpLceFragment;
import es.guillermoorellana.travisforandroid.ui.DividerItemDecoration;
import es.guillermoorellana.travisforandroid.ui.adapter.BuildAdapter;
import es.guillermoorellana.travisforandroid.ui.presenter.BuildsPresenter;
import es.guillermoorellana.travisforandroid.ui.view.BuildsView;

@FragmentWithArgs
public class BuildsFragment extends BaseMvpLceFragment<RecyclerView, List<Build>, BuildsView, BuildsPresenter>
        implements BuildsView {

    @NonNull
    @Arg
    Repo repo;
    @NonNull
    @Inject
    BuildsPresenter buildsPresenter;
    private BuildAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mAdapter = new BuildAdapter();
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
    public BuildsPresenter createPresenter() {
        return buildsPresenter;
    }

    @Override
    public void setData(List<Build> data) {
        mAdapter.setData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().reloadData(pullToRefresh);
    }

    @Subcomponent(modules = BuildsFragmentModule.class)
    public interface BuildsFragmentComponent {
        void inject(@NonNull BuildsFragment buildsFragment);
    }

    @Module
    public static class BuildsFragmentModule {
        private final Repo mRepo;

        public BuildsFragmentModule(Repo repo) {
            this.mRepo = repo;
        }

        @Provides
        @NonNull
        public BuildsPresenter provideReposPresenter(@NonNull BuildModel buildModel) {
            return new BuildsPresenter(buildModel, mRepo);
        }
    }
}

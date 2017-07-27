package es.guillermoorellana.travisforandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.mvp.BaseFragment;
import es.guillermoorellana.travisforandroid.ui.presenter.BranchesPresenter;
import es.guillermoorellana.travisforandroid.ui.view.BranchesView;

@FragmentWithArgs
public class BranchesFragment extends BaseFragment<BranchesView, BranchesPresenter> {
    @Arg long repoId;

//    private static final int LOADER_ID = 1005;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_branches, container, false);
    }

    @Override
    public BranchesPresenter createPresenter() {
        return new BranchesPresenter();
    }
}

package es.guillermoorellana.travisforandroid.ui.fragment;

import es.guillermoorellana.travisforandroid.ui.presenter.BuildsPresenter;
import es.guillermoorellana.travisforandroid.ui.view.BuildsView;

public class BuildsFragment extends BaseFragment<BuildsView, BuildsPresenter> {
    @Override
    public BuildsPresenter createPresenter() {
        return new BuildsPresenter();
    }
}

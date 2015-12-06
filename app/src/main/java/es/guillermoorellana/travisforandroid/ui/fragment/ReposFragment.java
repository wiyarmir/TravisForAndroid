package es.guillermoorellana.travisforandroid.ui.fragment;

import android.support.annotation.NonNull;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.entity.Repo;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;

public class ReposFragment extends BaseFragment implements ReposView {
    @Override
    public void showLoadingUi() {
// TODO impl
    }

    @Override
    public void showErrorUi(@NonNull Throwable error) {
// TODO impl
    }

    @Override
    public void showContentUi(@NonNull List<Repo> items) {
// TODO impl
    }
}

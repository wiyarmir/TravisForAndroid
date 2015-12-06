package es.guillermoorellana.travisforandroid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.TravisApp;

@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        TravisApp.get(getContext()).applicationComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }
}
